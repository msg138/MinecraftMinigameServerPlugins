package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Created by Michael George on 3/20/2018.
 */
public class PlayerPlaceListener implements Listener {
    private Main m_main;

    public PlayerPlaceListener(Main a_m){
        m_main = a_m;
    }


    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent t_event){
        if(t_event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            t_event.getBlockPlaced().setMetadata(RPGConfig.PlayerPlacedCreative, new FixedMetadataValue(m_main, t_event.getPlayer().getUniqueId().toString()));
        else if(t_event.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
            t_event.getBlockPlaced().setMetadata(RPGConfig.PlayerPlacedSurvival, new FixedMetadataValue(m_main, t_event.getPlayer().getUniqueId().toString()));
    }
    @EventHandler
    public void onPlayerRemoveBlock(BlockBreakEvent t_event){
        if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedSurvival))
            Bukkit.getScheduler().scheduleSyncDelayedTask(m_main, new Runnable() {
                @Override
                public void run() {
                    t_event.getBlock().removeMetadata(RPGConfig.PlayerPlacedSurvival, m_main);
                }
            }, 1);
        if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedCreative))
            Bukkit.getScheduler().scheduleSyncDelayedTask(m_main, new Runnable() {
                @Override
                public void run() {
                    t_event.getBlock().removeMetadata(RPGConfig.PlayerPlacedCreative, m_main);
                }
            }, 1);
    }
    @EventHandler
    public void onPlayerChangeBlock(BlockGrowEvent t_event){
        if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedSurvival))
            Bukkit.getScheduler().scheduleSyncDelayedTask(m_main, new Runnable() {
                @Override
                public void run() {
                    t_event.getBlock().removeMetadata(RPGConfig.PlayerPlacedSurvival, m_main);
                }
            }, 1);
        if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedCreative))
            Bukkit.getScheduler().scheduleSyncDelayedTask(m_main, new Runnable() {
                @Override
                public void run() {
                    t_event.getBlock().removeMetadata(RPGConfig.PlayerPlacedCreative, m_main);
                }
            }, 1);
    }
    @EventHandler
    public void onBlockGrow(StructureGrowEvent t_event){
        for(BlockState b : t_event.getBlocks()) {
            if (b.hasMetadata(RPGConfig.PlayerPlacedSurvival))
                b.removeMetadata(RPGConfig.PlayerPlacedSurvival, m_main);
            if (b.hasMetadata(RPGConfig.PlayerPlacedCreative))
                b.removeMetadata(RPGConfig.PlayerPlacedCreative, m_main);
        }
    }
}
