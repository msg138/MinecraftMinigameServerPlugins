package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.rpg.Main;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.skills.BuildingMenu;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

/**
 * Created by Michael George on 3/22/2018.
 */
public class BuildingOpenListener implements Listener {
    private com.hiveofthoughts.mc.Main m_main;

    public BuildingOpenListener(com.hiveofthoughts.mc.Main a_m){
        m_main = a_m;
    }

    @EventHandler
    public void onBuildingOpen(PlayerInteractEvent t_event){
        if(!t_event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        Block t_lb = t_event.getClickedBlock();
        if(t_lb != null){
            if(t_lb.hasMetadata(RPGConfig.MetaBuilding)) {
                t_event.setCancelled(true);
                MetadataValue t_md = t_lb.getMetadata(RPGConfig.MetaBuilding).get(0);
                String t_bm = WorldData.TemplateMenuMap.get(t_md.value().toString());
                BuildingMenu t_nbm = BuildingMenu.findMenu(t_bm);
                if(t_nbm != null){
                    t_event.getPlayer().openInventory(t_nbm.generateGui());
                }
            }
        }
    }
}
