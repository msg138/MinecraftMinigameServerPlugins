package com.hiveofthoughts.mc.rpg.calculator;

import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.config.MiningConfig;
import com.hiveofthoughts.mc.rpg.config.WoodcuttingConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael George on 3/20/2018.
 */
public class WoodcuttingCalculator {
    public static int getHitsRequired(String m, int lvl)
    {
        int hits = 1;
        String stat = "none";
        switch(m)
        {
            case WoodcuttingConfig.WOOD_OAK:
                hits = WoodcuttingConfig.CHOP_OAK_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
            case WoodcuttingConfig.WOOD_BIRCH:
                hits = WoodcuttingConfig.CHOP_BIRCH_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
            case WoodcuttingConfig.WOOD_ACACIA:
                hits = WoodcuttingConfig.CHOP_ACACIA_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
            case WoodcuttingConfig.WOOD_DARKOAK:
                hits = WoodcuttingConfig.CHOP_DARKOAK_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
            case WoodcuttingConfig.WOOD_JUNGLE:
                hits = WoodcuttingConfig.CHOP_JUNGLE_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
            case WoodcuttingConfig.WOOD_SPRUCE:
                hits = WoodcuttingConfig.CHOP_SPRUCE_TIME;
                stat = RPGConfig.Field_LevelWoodcutting;
                break;
        }
        if(stat.equals(RPGConfig.Field_LevelWoodcutting)){
            hits-= (lvl/ WoodcuttingConfig.CHOP_LEVEL_CHANGE);
            if(hits< WoodcuttingConfig.CHOP_MINIMUM)
                hits = WoodcuttingConfig.CHOP_MINIMUM;
        }
        return hits;
    }
    public static int getMaterialExp(String a_mat)
    {
        switch(a_mat)
        {
            case WoodcuttingConfig.WOOD_OAK:
                return WoodcuttingConfig.CHOP_OAK_EXP;
            case WoodcuttingConfig.WOOD_BIRCH:
                return WoodcuttingConfig.CHOP_BIRCH_EXP;
            case WoodcuttingConfig.WOOD_ACACIA:
                return WoodcuttingConfig.CHOP_ACACIA_EXP;
            case WoodcuttingConfig.WOOD_JUNGLE:
                return WoodcuttingConfig.CHOP_JUNGLE_EXP;
            case WoodcuttingConfig.WOOD_DARKOAK:
                return WoodcuttingConfig.CHOP_DARKOAK_EXP;
            case WoodcuttingConfig.WOOD_SPRUCE:
                return WoodcuttingConfig.CHOP_SPRUCE_EXP;
        }
        return 0;
    }


    public static int bringTreeDown(Block b)
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

    public static Set<Block> destroyTree(Block b, int timesRun){
        return destroyTree(b, timesRun, 0, 0);
    }

    public static Set<Block> destroyTree(Block b, int timesRun, int airPassed, int leavesPassed)
    {
        int count = 0;

        Set<Block> res = new HashSet<>();

        int tR = timesRun+1;

        if(b.getType() == Material.LOG || b.getType() == Material.LOG_2 || ((b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2) && leavesPassed < WoodcuttingConfig.CHOP_LEAVE_PASS) || (b.getType() == Material.AIR && airPassed < WoodcuttingConfig.CHOP_AIR_PASS))
        {
            count+=1;
            int isAir = 0;
            if(b.getType() == Material.AIR)
                isAir = 1;
            int isLeave = 0;
            if(b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2)
                isLeave = 1;
            // Only add if it is an actual log.
            if(b.getType() == Material.LOG || b.getType() == Material.LOG_2)
                res.add(b);
            b.setType(Material.AIR);
            res.addAll(destroyTree(b.getRelative(0, 1, 0), tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(1, 0, 0),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(-1, 0, 0),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(0, 0, 1),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(0, 0, -1),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(1, 0, 1),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(1, 0, -1),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(-1, 0, 1),tR, isAir + airPassed, isLeave + leavesPassed));
            res.addAll(destroyTree(b.getRelative(-1, 0, -1),tR, isAir + airPassed, isLeave + leavesPassed));
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
}
