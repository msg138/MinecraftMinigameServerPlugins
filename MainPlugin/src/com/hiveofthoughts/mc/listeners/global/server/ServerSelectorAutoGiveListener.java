package com.hiveofthoughts.mc.listeners.global.server;

import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerSelectorAutoGiveListener implements Listener {
    private Main m_main;

    public ServerSelectorAutoGiveListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerAutoGiveJoin(PlayerJoinEvent t_event){
        final Player t_p = t_event.getPlayer();
        ServerSelector.getInstance().giveSelector(t_p);
        /**Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(m_main, new Runnable(){
            public void run(){
                ServerSelector.getInstance().giveSelector(t_p);
            }
        }, 50L);*/
    }
}
