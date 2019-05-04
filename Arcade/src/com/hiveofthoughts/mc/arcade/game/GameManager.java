package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.connector.Connector;
import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import com.hiveofthoughts.mc.arcade.ArcadeServer;
import com.hiveofthoughts.mc.arcade.games.GameSpleef;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameManager implements Listener{

    protected List<Class > m_gameList;

    protected BaseGame m_currentGame;

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
                break;
            case ENDING:
                onEnd();
                getCurrentGame().onEnd();
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
        Bukkit.getWorld(getCurrentGame().getMap().getWorldName());

        // Check to see that start conditions are met.
        if(getCurrentGame().getMinPlayers() < getCurrentGame().getPlayerCount() && getCurrentGame().getPlayerCount() < getCurrentGame().getMaxPlayers() && m_conditionReachStart != -1){
            // Set the time we see the conditions met.
            m_conditionReachStart = System.currentTimeMillis();
            Connector.issueServerAction(new Connector.ActionGlobalMessage(ArcadeConfig.MessageGameStartingSoon), ServerInfo.getInstance().getServerName(), ServerInfo.getInstance().getServerNumber());
        } else if(getCurrentGame().getMinPlayers() > getCurrentGame().getPlayerCount() || getCurrentGame().getPlayerCount() > getCurrentGame().getMaxPlayers())
            m_conditionReachStart = -1;

        // If we have passed X seconds from when conditions were reached, start the game.
        if(m_conditionReachStart + (1000 * ArcadeConfig.TimeBeforeStartSeconds) < System.currentTimeMillis()){
            getCurrentGame().setGameState(GameState.STARTING);
        }
    }

    public void onStart(){
        // Give players their items depending on their kit / team
        List<PlayerInfo > t_players = getCurrentGame().getAllPlayerInfo();

        for(PlayerInfo t_p : t_players){
            // Move player to the set spawn.
            t_p.getPlayer().teleport(getCurrentGame().getMap().getSpawnLocation(t_p));

            // Give items
            t_p.getTeam().giveItems(t_p.getPlayer());
            t_p.getKit().giveItems(t_p.getPlayer());
        }
        // Items have been given.

        // For now set this here, until think further ahead.
        getCurrentGame().setGameState(GameState.IN_GAME);
    }

    public void onEnd(){
        getCurrentGame().setGameState(GameState.FINISHED);
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
        addListener();
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent a_event){
        getInstance().getCurrentGame().addPlayer(a_event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent a_event){
        getInstance().getCurrentGame().removePlayer(a_event.getPlayer());
    }

}
