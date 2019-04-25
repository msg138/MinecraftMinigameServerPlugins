package com.hiveofthoughts.mc.listeners.global.player;

import com.hiveofthoughts.mc.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Michael George on 3/21/2018.
 */
public class MovePlayerToSpawnListener implements Listener {
    private Main m_main;

    public MovePlayerToSpawnListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.getEntity().teleport(event.getEntity().getWorld().getSpawnLocation());
    }
}
