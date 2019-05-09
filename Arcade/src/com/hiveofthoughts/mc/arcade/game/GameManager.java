package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.connector.Connector;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import com.hiveofthoughts.mc.arcade.ArcadeServer;
import com.hiveofthoughts.mc.arcade.games.GameSpleef;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Listener{

    protected List<Class > m_gameList;

    protected BaseGame m_currentGame;

    protected GameMap m_lobby;

    // Keep track of when the start conditions have been met.
    private long m_conditionReachStart = -1;

    private static GameManager m_instance;

    public static GameManager getInstance(){
        if(m_instance == null)
            m_instance = new GameManager();
        return m_instance;
    }

    public GameManager(){
        m_gameList = new ArrayList<>();
        m_gameList.add(GameSpleef.class);

        // Setup the lobby map.
        {
            GameMap t_map = new GameMap();
            t_map.setWorldName("lobby");

            t_map.setSetting("spawn", new Location(Bukkit.getWorld(t_map.getWorldName()), -90.5, 28, 226.5));

            this.m_lobby = t_map;
        }
        loadWorld(m_lobby.getWorldName());
    }

    public GameManager(Main a_main) {
    }

    public BaseGame getCurrentGame(){
        return m_currentGame;
    }

    // Check gamestate.
    public void onRun(){
        if(m_currentGame == null)
            setGame();
        GameState t_currentState = getCurrentGame().getGameState();

        switch(t_currentState){
            case LOBBY:
                onSetup();
                getCurrentGame().onSetup();
                break;
            case STARTING:
                onStart();
                getCurrentGame().onStart();
                break;
            case IN_GAME:
                getCurrentGame().onRun();

                if(getCurrentGame().checkWinCondition()) {
                    getCurrentGame().setGameState(GameState.ENDING);
                }
                break;
            case ENDING:
                getCurrentGame().onEnd();
                onEnd();
                break;
            case FINISHED:
                finishGame();
                break;
            default:
                break;
        }
    }

    public void onSetup(){
        // Load the world.
        // Bukkit.getWorld(getCurrentGame().getMap().getWorldName());
        loadWorld();

        // Check to see that start conditions are met.
        if(getCurrentGame().getMinPlayers() < getCurrentGame().getPlayerCount() && getCurrentGame().getPlayerCount() < getCurrentGame().getMaxPlayers() && m_conditionReachStart != -1){
            // Set the time we see the conditions met.
            m_conditionReachStart = System.currentTimeMillis();
            Connector.issueServerAction(new Connector.ActionGlobalMessage(ArcadeConfig.MessageGameStartingSoon), ServerInfo.getInstance().getServerName(), ServerInfo.getInstance().getServerNumber());
        } else if(getCurrentGame().getMinPlayers() > getCurrentGame().getPlayerCount() || getCurrentGame().getPlayerCount() > getCurrentGame().getMaxPlayers())
            m_conditionReachStart = -1;

        // If we have passed X seconds from when conditions were reached, start the game.
        if(m_conditionReachStart > 0 && m_conditionReachStart + (1000 * ArcadeConfig.TimeBeforeStartSeconds) < System.currentTimeMillis()){
            getCurrentGame().setGameState(GameState.STARTING);
        }
    }

    public boolean loadWorld(){
        if(!Bukkit.getServer().getWorlds().contains(getCurrentGame().getMap().getWorldName()))
            Bukkit.getServer().createWorld(new WorldCreator(getCurrentGame().getMap().getWorldName()));

        return true;
    }
    public boolean loadWorld(String a_worldName){
        if(!Bukkit.getServer().getWorlds().contains(a_worldName))
            Bukkit.getServer().createWorld(new WorldCreator(a_worldName));

        return true;
    }

    public World getWorld(){
        loadWorld();
        World r_world = Bukkit.getWorld(getCurrentGame().getMap().getWorldName());
        return r_world;
    }
    public World getWorld(String a_name){
        loadWorld(a_name);
        World r_world = Bukkit.getWorld(a_name);
        return r_world;
    }

    public void onStart(){
        // Set the game state for the next loop
        getCurrentGame().setGameState(GameState.IN_GAME);
        // Give players their items depending on their kit / team
        List<PlayerInfo > t_players = getCurrentGame().getAllPlayerInfo();

        for(PlayerInfo t_p : t_players){
            // Move player to the set spawn.
            playerSpawn(t_p);
            // Set game mode
            t_p.getPlayer().setGameMode(getCurrentGame().getGameMode());
            healPlayer(t_p.getPlayer());
            // Clear inventory
            t_p.getPlayer().getInventory().clear();
            // Give items
            t_p.getTeam().giveItems(t_p.getPlayer());
            t_p.getKit().giveItems(t_p.getPlayer());

            // Set the PlayerInfo to have the players active,
            t_p.setStatus(PlayerStatus.PLAYING);
        }
        // Items have been given.

        // Add the proper listeners,
        addListener();

    }

    public void onEnd(){
        getCurrentGame().finalCheckScore();

        // Output final scores.
        String t_message = ArcadeConfig.ChatLine + "\n" +
                ArcadeConfig.Prefix + " Score Results" + "\n" +
                ArcadeConfig.ChatLine + "\n\n";

        int t_lossSize = getCurrentGame().getLossOrder().size();
        if(t_lossSize > 0)
            t_message += "1st Place - " + getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 1).getName()
                    + getCurrentGame().getPlayerInfo(getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 1)).getScore() + "\n\n";
        if(t_lossSize > 1) {
            t_message += "2nd Place - " + getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 2).getName() + getCurrentGame().getPlayerInfo(getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 2)).getScore() + "\n\n";
        } else if(t_lossSize > 2) {
            t_message += "3rd Place - " + getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 3).getName() + getCurrentGame().getPlayerInfo(getCurrentGame().getLossOrder().get(getCurrentGame().getLossOrder().size() - 3)).getScore() + "\n\n";
        }


        // Teleport players back to default lobby server.

        for(Player t_p : Bukkit.getOnlinePlayers()) {
            playerSpawn(t_p);
            healPlayer(t_p);
            // Send the generated score.
            t_p.sendMessage(t_message);
        }


        getCurrentGame().setGameState(GameState.FINISHED);
    }

    public void healPlayer(Player a_p){
        a_p.setHealth(a_p.getMaxHealth());
        a_p.setFireTicks(0);
        a_p.setFoodLevel(20);
        a_p.getActivePotionEffects().clear();
    }

    public void playerSpawn(PlayerInfo a_pi){
        playerSpawn(a_pi.getPlayer());
    }

    public Location getPlayerSpawn(Player a_player) {
        Location r_loc = null;
        if(getCurrentGame().getGameState().equals(GameState.LOBBY) || getCurrentGame().getGameState().equals(GameState.STARTING) || getCurrentGame().getGameState().equals(GameState.ENDING)) {
            Location t_loc = m_lobby.getSpawnLocation(getCurrentGame().getPlayerInfo(a_player));
            t_loc.setWorld(getWorld(m_lobby.getWorldName()));
            if (getWorld() != null) {
                a_player.teleport(new Location(getWorld(), t_loc.getX(), t_loc.getY(), t_loc.getZ()));
            } else {
                Bukkit.getLogger().info("Unable to load world, showing as null.");
            }
            r_loc = t_loc;
        } else {
            Location t_loc = getCurrentGame().getMap().getSpawnLocation(getCurrentGame().getPlayerInfo(a_player));
            t_loc.setWorld(getWorld());
            if(getWorld() != null) {
                a_player.teleport(new Location(getWorld(), t_loc.getX(), t_loc.getY(), t_loc.getZ()));
            } else {
                Bukkit.getLogger().info("Unable to load world, showing as null.");
            }
            r_loc = t_loc;
        }
        return r_loc;
    }

    public void playerSpawn(Player a_player) {
        healPlayer(a_player);
        Location t_loc = getPlayerSpawn(a_player);
        if(t_loc != null)
            a_player.teleport(t_loc);
    }


    public void setGame(){
        setGame((int) Math.floor(Math.random() * m_gameList.size()));
    }
    public void setGame(int a_index){
        Bukkit.getLogger().info("Setting new game to " + a_index);
        try {
            m_currentGame = (BaseGame) m_gameList.get(a_index).getConstructor().newInstance();
            // In addition to setting the game, add all players on the server.
            for(Player t_p : Bukkit.getOnlinePlayers())
                m_currentGame.addPlayer(t_p);
        } catch(Exception e){
            e.printStackTrace();
        }
        Bukkit.getLogger().info("Set new game. ");
    }

    public void addListener(){
        Bukkit.getLogger().info("Adding Listener");
        Bukkit.getPluginManager().registerEvents(getCurrentGame(), ArcadeServer.GlobalMain);
        Bukkit.getLogger().info("Added Listener");
    }
    public void removeListener(){
        Bukkit.getLogger().info("Removing Listener");
        HandlerList.unregisterAll(getCurrentGame());
        Bukkit.getLogger().info("Removed Listener");
    }

    public void finishGame(){
        getCurrentGame().onCleanup();
        // After current game is cleaned up, remove and start a new one.
        removeListener();

        // Unload the previous used world.
        Bukkit.unloadWorld(getCurrentGame().getMap().getWorldName(), false);

        setGame();
    }

    public GameMode getGameMode(){
        GameMode r_game = null;
        if(getCurrentGame().getGameState().equals(GameState.LOBBY) || getCurrentGame().getGameState().equals(GameState.STARTING)) {
            r_game = GameMode.ADVENTURE;
        } else {
            r_game = getCurrentGame().getGameMode();
        }
        return r_game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent a_event){
        getInstance().getCurrentGame().addPlayer(a_event.getPlayer());

        getInstance().playerSpawn(a_event.getPlayer());
        a_event.getPlayer().setGameMode(getInstance().getGameMode());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent a_event){
        getInstance().getCurrentGame().removePlayer(a_event.getPlayer());
    }



    @EventHandler
    public void onDeath(PlayerRespawnEvent a_event) {
        Player t_p = a_event.getPlayer();
        a_event.setRespawnLocation(getInstance().getPlayerSpawn(t_p));
        a_event.getPlayer().setGameMode(getInstance().getGameMode());
    }
}
