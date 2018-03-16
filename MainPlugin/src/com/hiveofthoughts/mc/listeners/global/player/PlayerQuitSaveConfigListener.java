package com.hiveofthoughts.mc.listeners.global.player;

import com.hiveofthoughts.mc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Michael George on 3/15/2018.
 */

public class PlayerQuitSaveConfigListener implements Listener {
    private Main m_main;

    public PlayerQuitSaveConfigListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        m_main.getPlayerList().get(event.getPlayer().getName()).save();
        m_main.getPlayerList().put(event.getPlayer().getName(), null);
        System.out.println("Player data saved!!!");
    }
}
