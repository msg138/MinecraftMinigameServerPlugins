package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeServer;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    protected List<Class > m_gameList;

    protected BaseGame m_currentGame;

    private static GameManager m_instance;

    public static GameManager getInstance(){
        if(m_instance == null)
            m_instance = new GameManager();
        return m_instance;
    }

    private GameManager(){
        m_gameList = new ArrayList<>();
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
                getCurrentGame().onSetup();
                break;
            case STARTING:
                getCurrentGame().onStart();
                break;
            case IN_GAME:
                getCurrentGame().onRun();
                break;
            case ENDING:
                getCurrentGame().onEnd();
                break;
            case FINISHED:
                finishGame();
                break;
            default:
                break;
        }
    }

    public void setGame(){
        setGame((int) Math.floor(Math.random() * m_gameList.size()));
    }
    public void setGame(int a_index){
        Bukkit.getLogger().info("Setting new game to " + a_index);
        try {
            m_currentGame = (BaseGame) m_gameList.get(a_index).getConstructor().newInstance();
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
        setGame();
    }

}
