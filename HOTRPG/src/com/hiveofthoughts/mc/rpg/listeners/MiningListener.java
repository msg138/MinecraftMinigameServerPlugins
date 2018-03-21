package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.calculator.MiningCalculator;
import com.hiveofthoughts.mc.rpgold.Data;
import org.bson.Document;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Michael George on 3/19/2018.
 */
public class MiningListener implements Listener{
    private Main m_main;

    public MiningListener(Main a_m){
        m_main = a_m;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onBlockMine(BlockBreakEvent t_event) {
        if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedSurvival))
            return;
        try {
            t_event.setExpToDrop(0);
            Block b = t_event.getBlock();
            if (t_event.getPlayer().getGameMode() != GameMode.CREATIVE && (b.getType() == Material.GLOWING_REDSTONE_ORE || b.getType() == Material.REDSTONE_ORE
                    || b.getType() == Material.COBBLESTONE || b.getType() == Material.IRON_ORE || b.getType() == Material.COAL_ORE || b.getType() == Material.DIAMOND_ORE
                    || b.getType() == Material.EMERALD_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.LAPIS_ORE)) {
                // Player information
                Document t_doc = RPGConfig.getPlayerRPGInfo(t_event.getPlayer());
                if (WorldData.MinedBlocks.contains(b)) {
                    t_event.getPlayer().sendMessage(RPGConfig.Prefix + RPGConfig.MessageBlockMined);
                    t_event.setCancelled(true);
                } else if (b.getType() != Material.COBBLESTONE) {
                    // TODO give based on mining level

                    t_event.setCancelled(true);


                    int t_lvl = t_doc.getInteger(RPGConfig.Field_LevelMining);
                    int t_exp = MiningCalculator.getMaterialExp(b.getType());
                    int t_hits = MiningCalculator.getHitsRequired(b.getType(), t_lvl);


                    if (t_doc.getInteger(RPGConfig.Field_MiningHits) + 1 >= t_hits && b.getType().toString().equals(t_doc.getString(RPGConfig.Field_MiningMaterial))) {
                        WorldData.MinedBlocks.add(b);
                        WorldData.MinedBlocksTime.add(0);
                        WorldData.MinedBlocksType.add(b.getType());
                        if (b.getType() == Material.REDSTONE_ORE || b.getType() == Material.GLOWING_REDSTONE_ORE) {
                            Data.mineralType.add(Material.REDSTONE_ORE);
                            t_event.getPlayer().getInventory().addItem(new ItemStack(Material.REDSTONE_ORE, 8));
                        } else {
                            Data.mineralType.add(b.getType());
                            t_event.getPlayer().getInventory().addItem(new ItemStack(b.getType(), 1));
                        }
                        t_event.getPlayer().sendMessage(RPGConfig.Prefix + "You successfully mined a " + b.getType());
                        // Reset the players mineHits to 0, as they need to remine the progess bar thingy.
                        Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_MiningHits, 0);

                        if(RPGConfig.addExp(t_event.getPlayer(), RPGConfig.Field_LevelMining, RPGConfig.Field_ExperienceMining, t_exp))
                            t_event.getPlayer().sendMessage(RPGConfig.Prefix + RPGConfig.MessageLevelMining);
                        b.setType(Material.COBBLESTONE);
                    } else if (!b.getType().toString().equals(t_doc.getString(RPGConfig.Field_MiningMaterial))) {
                        //Data.getPlayerData(event.getPlayer().getUniqueId()).mineMaterial = b;
                        Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_MiningMaterial, b.getType().toString());

                        //Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits = 1;
                        Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_MiningHits, 1);

                        //Data.getPlayerData(event.getPlayer().getUniqueId()).currentskill = "mining";
                    } else if (t_doc.getInteger(RPGConfig.Field_MiningHits) < t_hits) {
                        //Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits++;
                        Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_MiningHits, t_doc.getInteger(RPGConfig.Field_MiningHits) + 1);

                    }
                }
            }
        } catch (Exception e) {
            t_event.getPlayer().sendMessage(RPGConfig.Prefix + Config.MessageErrorUnknown);
            e.printStackTrace();
        }
    }
}
