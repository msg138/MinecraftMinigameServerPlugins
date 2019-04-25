package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.rpg.Main;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Michael George on 3/22/2018.
 */
public class BuildingRemoveListener implements Listener {
    private com.hiveofthoughts.mc.Main m_main;

    public BuildingRemoveListener(com.hiveofthoughts.mc.Main a_m){
        m_main = a_m;
    }

    @EventHandler
    public void onBuildingDestroy(BlockBreakEvent t_event){
        if(t_event.getBlock() != null && t_event.getBlock().hasMetadata(RPGConfig.MetaBuilding)){
            t_event.getBlock().removeMetadata(RPGConfig.MetaBuilding, m_main);
        }
    }
}
