package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class BaseGame implements Listener {

    protected String m_gameName;
    protected String m_gameDescription;

    protected GameState m_gameState;

    protected int m_maxPlayers;
    protected int m_minPlayers;

    protected String m_worldName;

    public BaseGame(){
        m_gameName = ArcadeConfig.DefaultString;
        m_gameDescription = ArcadeConfig.DefaultString;

        m_gameState = GameState.LOBBY;

        m_maxPlayers = ArcadeConfig.DefaultMaxPlayers;
        m_minPlayers = ArcadeConfig.DefaultMinPlayers;

        m_worldName = ArcadeConfig.DefaultWorldName;
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

    public String getWorldName(){
        return m_worldName;
    }

    public abstract void onSetup();
    public abstract void onStart();
    public abstract void onEnd();
    public abstract void onCleanup();

    public abstract void onRun();

}
