package com.hiveofthoughts.mc.rpgold;

/**
 * Created by Michael George on 3/15/2018.
 */
import java.util.ArrayList;

import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Sign;

public class BlockListener implements Listener{

    public static final String reserved = "[forge],[smelter],";

    public BlockListener(Main jp)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, jp);
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        event.setExpToDrop(0);
        Block b = event.getBlock();
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE && (b.getType() == Material.GLOWING_REDSTONE_ORE || b.getType() == Material.REDSTONE_ORE || b.getType() == Material.COBBLESTONE || b.getType() == Material.IRON_ORE || b.getType() == Material.COAL_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.LAPIS_ORE))
        {
            if(Data.mineralBlocks.contains(b))
            {
                event.getPlayer().sendMessage("That block has already been mined.");
                event.setCancelled(true);
            }else if(b.getType() != Material.COBBLESTONE){
                // TODO give based on mining level

                event.setCancelled(true);
                int exp = 0;
                int hits = 0;

                int lvl = Data.getUserLevel(event.getPlayer().getUniqueId(),"mining");

                hits = Data.getHitsRequired(b.getType(),lvl);
                exp = Data.getExpFromMaterial(b.getType());



                if(Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits+1 >= hits && b.equals(Data.getPlayerData(event.getPlayer().getUniqueId()).mineMaterial)){
                    Data.mineralBlocks.add(b);
                    Data.mineralTimes.add(0);
                    if(b.getType() == Material.REDSTONE_ORE || b.getType() == Material.GLOWING_REDSTONE_ORE)
                    {
                        Data.mineralType.add(Material.REDSTONE_ORE);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.REDSTONE_ORE,8));
                    }else{
                        Data.mineralType.add(b.getType());
                        event.getPlayer().getInventory().addItem(new ItemStack(b.getType(),1));
                    }
                    event.getPlayer().sendMessage("You successfully mined a "+b.getType());
                    Data.addUserExperience(event.getPlayer().getUniqueId(), "mining", (int)(Data.MINE_EXP_BONUS*exp));
                    b.setType(Material.COBBLESTONE);
                }else if(!b.equals(Data.getPlayerData(event.getPlayer().getUniqueId()).mineMaterial))
                {
                    Data.getPlayerData(event.getPlayer().getUniqueId()).mineMaterial = b;
                    Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits = 1;

                    Data.getPlayerData(event.getPlayer().getUniqueId()).currentskill = "mining";
                }else if(Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits < hits)
                {
                    Data.getPlayerData(event.getPlayer().getUniqueId()).mineHits++;
                }
            }
        }
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE && (b.getType() == Material.LOG || b.getType() == Material.LOG_2) && event.getPlayer().getItemInHand() != null &&
                (event.getPlayer().getItemInHand().getType() == Material.WOOD_AXE ||event.getPlayer().getItemInHand().getType() == Material.IRON_AXE ||event.getPlayer().getItemInHand().getType() == Material.DIAMOND_AXE ||event.getPlayer().getItemInHand().getType() == Material.GOLD_AXE ||event.getPlayer().getItemInHand().getType() == Material.STONE_AXE))
        {

            Material m = b.getType();

            int durabilityLost = 0;
            int amountChoppeds = destroyTree(b,0).size();
            int amountChopped = 0;

            for(int i=0;i<amountChoppeds;i++)
            {
                if(Math.random()*10<=Math.floor(Data.getUserLevel(event.getPlayer().getUniqueId(), "woodcutting"))/10+1)
                {
                    amountChopped++;
                    durabilityLost++;
                }
            }

            event.getPlayer().sendMessage("You successfully chopped "+amountChopped+" logs.");

            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(m,amountChopped));

            event.setCancelled(true);
            event.getPlayer().getItemInHand().setDurability((short)(event.getPlayer().getItemInHand().getDurability()+durabilityLost));
            Data.addUserExperience(event.getPlayer().getUniqueId(), "woodcutting", amountChopped);
        }
    }

    public int bringTreeDown(Block b)
    {
        int count = 0;
        if(b.getType() != Material.AIR || b.isEmpty() == false)
            return count;
        if(b.getRelative(0, 1, 0).getType() == Material.LOG || b.getRelative(0, 1, 0).getType() == Material.LOG_2)
        {
            b.setType(b.getRelative(0, 1, 0).getType());
            b.setData(b.getRelative(0, 1, 0).getData());
            b.getRelative(0, 1, 0).setType(Material.AIR);
            count += 1;

            count += bringTreeDown(b.getRelative(0, 1, 0));
            count += bringTreeDown(b.getRelative(1, 0, 0));
            count += bringTreeDown(b.getRelative(-1, 0, 0));
            count += bringTreeDown(b.getRelative(0, 0, 1));
            count += bringTreeDown(b.getRelative(0, 0, -1));
            count += bringTreeDown(b.getRelative(1, 0, 1));
            count += bringTreeDown(b.getRelative(1, 0, -1));
            count += bringTreeDown(b.getRelative(-1, 0, 1));
            count += bringTreeDown(b.getRelative(-1, 0, -1));

        }

        return count;
    }

    public ArrayList<Block> destroyTree(Block b,int timesRun)
    {
        int count = 0;

        ArrayList<Block> res = new ArrayList<>();

        int tR = timesRun+1;

        if(b.getType() == Material.LOG || b.getType() == Material.LOG_2)
        {
            count+=1;
            b.setType(Material.AIR);
            //count += destroyTree(b.getRelative(0, 1, 0));
            res.addAll(destroyTree(b.getRelative(1, 0, 0),tR));
            res.addAll(destroyTree(b.getRelative(-1, 0, 0),tR));
            res.addAll(destroyTree(b.getRelative(0, 0, 1),tR));
            res.addAll(destroyTree(b.getRelative(0, 0, -1),tR));
            res.addAll(destroyTree(b.getRelative(1, 0, 1),tR));
            res.addAll(destroyTree(b.getRelative(1, 0, -1),tR));
            res.addAll(destroyTree(b.getRelative(-1, 0, 1),tR));
            res.addAll(destroyTree(b.getRelative(-1, 0, -1),tR));

            res.add(b);
        }

        if(tR == 1 || tR == 0)
        {
            for(Block bl : res)
            {
                bringTreeDown(bl);
            }
        }

        return res;
    }

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent evt)
    {
        Block b = evt.getBlock().getRelative(0, -3, 0);
        if(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
        {
            Sign s = (Sign)b.getState();
            if(s.getLine(1).equalsIgnoreCase("[smelter]"))
            {
                // This is a smelter. Lets dup some items.

                ItemStack i = evt.getResult();
                i.setAmount(i.getAmount()*2);
            }
        }
    }

    @EventHandler
    public void preventSignSpecialOne(BlockPlaceEvent evt)
    {
        Block b = evt.getBlock();
        if(evt.getPlayer().isOp() == false && (b.getType() == Material.GLOWING_REDSTONE_ORE || b.getType() == Material.REDSTONE_ORE || b.getType() == Material.COBBLESTONE || b.getType() == Material.IRON_ORE || b.getType() == Material.COAL_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.LAPIS_ORE))
        {
            evt.getPlayer().sendMessage("You do not have permission to place that.");
            evt.setCancelled(false);
            return;
        }

        if(evt.getBlock().getType() == Material.SIGN || evt.getBlock().getType() == Material.SIGN_POST || evt.getBlock().getType() == Material.WALL_SIGN)
        {

            boolean contains = false;
            Sign s = (Sign)evt.getBlock().getState();
            for(String ss : reserved.split(","))
            {
                for(String sss: s.getLines()){
                    if(sss.contains(ss))
                        contains = true;
                }
            }

            if(contains == true && evt.getPlayer().isOp() == false)
            {
                evt.setCancelled(true);
                evt.getPlayer().sendMessage("You do not have permission to put that on a sign");
            }
        }
    }

    @EventHandler
    public void preventSignSpecialTwo(SignChangeEvent evt)
    {

        boolean contains = false;

        for(String ss : reserved.split(","))
        {
            for(String sss: evt.getLines()){
                if(sss.contains(ss))
                    contains = true;
            }
        }

        if(contains == true && evt.getPlayer().isOp() == false)
        {
            evt.setCancelled(true);
            evt.getPlayer().sendMessage("You do not have permission to put that on a sign");
        }
    }
}
