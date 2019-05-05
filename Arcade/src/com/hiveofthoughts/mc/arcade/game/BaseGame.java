package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class BaseGame implements Listener {

    protected String m_gameName;
    protected String m_gameDescription;

    protected GameState m_gameState;

    protected int m_maxPlayers;
    protected int m_minPlayers;

    protected GameMap m_map;

    protected GameMode m_defaultGameMode;

    protected List<Team > m_teams;
    protected List<Kit > m_kits;

    protected List<Player > m_lossOrder;

    protected HashMap<Player, PlayerInfo> m_players;

    public BaseGame(){
        m_gameName = ArcadeConfig.DefaultString;
        m_gameDescription = ArcadeConfig.DefaultString;

        m_lossOrder = new ArrayList<>();

        m_gameState = GameState.LOBBY;

        m_defaultGameMode = GameMode.ADVENTURE;

        m_maxPlayers = ArcadeConfig.DefaultMaxPlayers;
        m_minPlayers = ArcadeConfig.DefaultMinPlayers;

        m_teams = new ArrayList<>();
        m_kits = new ArrayList<>();

        m_map = new GameMap();

        m_players = new HashMap<>();
    }

    public GameMode getGameMode() {
        return m_defaultGameMode;
    }

    public List<Player > getLossOrder(){
        return m_lossOrder;
    }

    public boolean addPlayer(Player a_player){
        if(playerExists(a_player))
            return false;
        if(m_teams.size() <= 0 || m_kits.size() <= 0)
            return false;
        PlayerInfo t_playerInfo = new PlayerInfo(a_player);
        t_playerInfo.setKit(m_kits.get(0));
        t_playerInfo.setTeam(m_teams.get(0));

        switch(m_gameState){
            case LOBBY:
                t_playerInfo.setStatus(PlayerStatus.PLAYING);
                break;
            default:
                t_playerInfo.setStatus(PlayerStatus.SPECTATOR);
        }

        m_players.put(a_player, t_playerInfo);

        return true;
    }

    public boolean removePlayer(Player a_player){
        if(!playerExists(a_player))
            return false;
        m_players.remove(a_player);
        return true;
    }

    public boolean playerExists(Player a_player){
        return m_players.containsKey(a_player);
    }

    public int getPlayerCount(){
        return m_players.size();
    }

    public PlayerInfo getPlayerInfo(Player a_player){
        if(playerExists(a_player))
            return m_players.get(a_player);
        return null;
    }

    public List<PlayerInfo > getAllPlayerInfo(){
        return new ArrayList<>(m_players.values());
    }

    public int getPlayerStatusCount(PlayerStatus a_status){
        int r_total = 0;
        for(PlayerInfo t_p : m_players.values()) {
            if(t_p.getStatus().equals(a_status)) {
                r_total += 1;
            }
        }
        return r_total;
    }

    public String getName(){
        return m_gameName;
    }
    public String getDescription(){
        return m_gameDescription;
    }

    public GameState getGameState(){
        return m_gameState;
    }
    public void setGameState(GameState a_gs){
        m_gameState = a_gs;
    }

    public int getMaxPlayers(){
        return m_maxPlayers;
    }
    public int getMinPlayers(){
        return m_minPlayers;
    }

    public GameMap getMap(){
        return m_map;
    }

    public abstract void onSetup();
    public abstract void onStart();
    public abstract void onEnd();
    public abstract void onCleanup();

    public abstract boolean checkWinCondition();

    public abstract void finalCheckScore();

    public abstract void onRun();

}
