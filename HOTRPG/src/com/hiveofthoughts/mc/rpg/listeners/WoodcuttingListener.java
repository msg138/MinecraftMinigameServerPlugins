package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.calculator.MiningCalculator;
import com.hiveofthoughts.mc.rpg.calculator.WoodcuttingCalculator;
import com.hiveofthoughts.mc.rpg.config.WoodcuttingConfig;
import com.hiveofthoughts.mc.rpgold.Data;
import com.hiveofthoughts.mc.server.ItemBuilder;
import org.bson.Document;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Tree;
import org.bukkit.material.Wood;

/**
 * Created by Michael George on 3/20/2018.
 */
public class WoodcuttingListener implements Listener {
    private Main m_main;

    public WoodcuttingListener(Main a_m){
        m_main = a_m;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onBlockChop(BlockBreakEvent t_event) {
        try {
            t_event.setExpToDrop(0);
            Block b = t_event.getBlock();
            if (t_event.getPlayer().getGameMode() != GameMode.CREATIVE &&
                    (t_event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOOD_AXE) || t_event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE) || t_event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE) || t_event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLD_AXE) || t_event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)) &&
                    (b.getType() == Material.LOG || b.getType() == Material.LOG_2)) {
                if(t_event.getBlock().hasMetadata(RPGConfig.PlayerPlacedSurvival))
                    return;

                String a_blockType = WoodcuttingConfig.getBlockType(b);
                // Player information
                Document t_doc = RPGConfig.getPlayerRPGInfo(t_event.getPlayer());
                /*if (WorldData.MinedBlocks.contains(b)) {
                    t_event.getPlayer().sendMessage(RPGConfig.Prefix + RPGConfig.MessageBlockMined);
                    t_event.setCancelled(true);
                } else */if (b.getType() != Material.SAPLING) {
                    // TODO give based on mining level

                    t_event.setCancelled(true);

                    try {

                        int t_lvl = t_doc.getInteger(RPGConfig.Field_LevelWoodcutting);
                        int t_exp = WoodcuttingCalculator.getMaterialExp(a_blockType);
                        int t_hits = WoodcuttingCalculator.getHitsRequired(a_blockType, t_lvl);


                        if (t_doc.getInteger(RPGConfig.Field_WoodcuttingHits) + 1 >= t_hits && a_blockType.equals(t_doc.getString(RPGConfig.Field_WoodcuttingMaterial))) {

                            Material t_material = b.getType();
                            byte t_byteData = b.getData();

                            int durabilityLost = 0;
                            int amountChoppeds = WoodcuttingCalculator.destroyTree(b, 0).size();
                            int amountChopped = 0;

                            double t_dropChance = Math.floor(t_lvl / WoodcuttingConfig.CHOP_LEVEL_CHANCE);
                            if(t_dropChance < WoodcuttingConfig.CHOP_MINIMUM_CHANCE)
                                t_dropChance = WoodcuttingConfig.CHOP_MINIMUM_CHANCE;
                            if(t_dropChance > WoodcuttingConfig.CHOP_MAXIMUM_CHANCE)
                                t_dropChance = WoodcuttingConfig.CHOP_MAXIMUM_CHANCE;
                            for (int i = 0; i < amountChoppeds; i++) {
                                if (Math.random() * 10 <= t_dropChance) {
                                    amountChopped++;
                                    durabilityLost++;
                                }
                            }

                            t_event.getPlayer().sendMessage(RPGConfig.Prefix + "You successfully chopped a " + a_blockType + " tree. You got " + amountChopped + " logs");

                            String t_rarity = "";
                            switch (t_event.getPlayer().getInventory().getItemInMainHand().getType()){
                                case WOOD_AXE:
                                    t_rarity = RPGConfig.QualityAbysmal;
                                    break;
                                case STONE_AXE:
                                    t_rarity = RPGConfig.QualityPoor;
                                    break;
                                case IRON_AXE:
                                    t_rarity = RPGConfig.QualityDecent;
                                    break;
                                case GOLD_AXE:
                                    t_rarity = RPGConfig.QualityOkay;
                                    break;
                                case DIAMOND_AXE:
                                    t_rarity = RPGConfig.QualityRefined;
                                    break;
                            }

                            ItemStack t_bDrop = ItemBuilder.buildItem(new ItemStack(t_material, amountChopped, t_byteData), t_rarity + " " + a_blockType + " Log", null);

                            t_event.getPlayer().getWorld().dropItem(t_event.getPlayer().getLocation(), t_bDrop);

                            t_event.setCancelled(true);
                            t_event.getPlayer().getInventory().getItemInMainHand().setDurability((short) (t_event.getPlayer().getInventory().getItemInMainHand().getDurability() + durabilityLost));

                            if(RPGConfig.addExp(t_event.getPlayer(), RPGConfig.Field_LevelWoodcutting, RPGConfig.Field_ExperienceWoodcutting, amountChopped * t_exp))
                                t_event.getPlayer().sendMessage(RPGConfig.Prefix + RPGConfig.MessageLevelWoodcutting);
                            Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_WoodcuttingHits, 0);
                        } else if (!a_blockType.equals(t_doc.getString(RPGConfig.Field_WoodcuttingMaterial))) {
                            //Data.getPlayerData(event.getPlayer().getUniqueId()).mineMaterial = b;
                            Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_WoodcuttingMaterial, a_blockType);

                            //Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits = 1;
                            Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_WoodcuttingHits, 1);

                            //Data.getPlayerData(event.getPlayer().getUniqueId()).currentskill = "mining";
                        } else if (t_doc.getInteger(RPGConfig.Field_WoodcuttingHits) < t_hits) {
                            //Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits++;
                            Database.getInstance().updateDocument(RPGConfig.getTableName(), Database.Field_UUID, t_event.getPlayer().getUniqueId().toString(), RPGConfig.Field_WoodcuttingHits, t_doc.getInteger(RPGConfig.Field_WoodcuttingHits) + 1);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        t_event.getPlayer().sendMessage(RPGConfig.Prefix + Config.MessageErrorUnknown);
                    }
                }
            }
        } catch (Exception e) {
            t_event.getPlayer().sendMessage(RPGConfig.Prefix + Config.MessageErrorUnknown);
            e.printStackTrace();
        }
    }
}
