package com.hiveofthoughts.mc.listeners.global.server;

import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerSelectorListener implements Listener {
    private Main m_main;

    public ServerSelectorListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerUseSelector(PlayerInteractEvent t_event){
        Player t_p = t_event.getPlayer();
        if((t_event.getAction() == Action.RIGHT_CLICK_AIR || t_event.getAction() == Action.RIGHT_CLICK_BLOCK) && ServerSelector.getInstance().holdingSelector(t_p)){
            t_event.setCancelled(true);
            ServerSelector.getInstance().openMenu(t_p);
        }
    }

}
