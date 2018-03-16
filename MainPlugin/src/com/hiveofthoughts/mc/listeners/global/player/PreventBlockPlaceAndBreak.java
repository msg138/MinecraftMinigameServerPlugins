package com.hiveofthoughts.mc.listeners.global.player;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Michael George on 3/14/2018.
 */
public class PreventBlockPlaceAndBreak implements Listener {
    private Main m_main;

    public PreventBlockPlaceAndBreak(Main main){
        m_main = main;
    }

    @EventHandler
    public void PreventBlockPlace(PlayerInteractEvent event){
        if(event.isBlockInHand() && (event.hasBlock() || event.hasItem())){
            // Check to see that the player has the permission to place the block
            Main.PlayerData pd = m_main.getPlayerData(event.getPlayer());
            if(!pd.getPermissions().hasPermission(Config.PermissionBuild)) {
                event.getPlayer().sendMessage(Config.Prefix + Config.MessagePermission);
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void PreventBlockMine(PlayerInteractEvent event){
        if(event.getAction() == Action.LEFT_CLICK_BLOCK){
            // Check to see that the player has the permission to place the block
            Main.PlayerData pd = m_main.getPlayerData(event.getPlayer());
            if(!pd.getPermissions().hasPermission(Config.PermissionDig)) {
                event.getPlayer().sendMessage(Config.Prefix + Config.MessagePermission);
                event.setCancelled(true);
            }
        }
    }

}