package com.hiveofthoughts.mc.listeners.global.player;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Michael George on 3/21/2018.
 */
public class ClearInventoryOnJoin implements Listener {
    private Main m_main;

    public ClearInventoryOnJoin(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.getPlayer().getInventory().clear();
    }
}
