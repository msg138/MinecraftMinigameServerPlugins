package com.hiveofthoughts.mc.rpg.calculator;

import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.config.MiningConfig;
import org.bukkit.Material;

/**
 * Created by Michael George on 3/19/2018.
 */
public class MiningCalculator {
    public static int getHitsRequired(Material m, int lvl)
    {
        int hits = 1;
        String stat = "none";
        switch(m)
        {
            case COAL_ORE:
                hits = MiningConfig.MINE_COAL_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case DIAMOND_ORE:
                hits = MiningConfig.MINE_DIAMOND_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case IRON_ORE:
                hits = MiningConfig.MINE_IRON_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case EMERALD_ORE:
                hits = MiningConfig.MINE_EMERALD_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case LAPIS_ORE:
                hits = MiningConfig.MINE_LAPIS_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case GOLD_ORE:
                hits = MiningConfig.MINE_GOLD_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case REDSTONE_ORE:
                hits = MiningConfig.MINE_REDSTONE_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
            case GLOWING_REDSTONE_ORE:
                hits = MiningConfig.MINE_REDSTONE_TIME;
                stat = RPGConfig.Field_LevelMining;
                break;
        }
        if(stat.equals(RPGConfig.Field_LevelMining)){
            hits-= (lvl/ MiningConfig.MINE_LEVEL_CHANGE);
            if(hits< MiningConfig.MINE_MINIMUM)
                hits = MiningConfig.MINE_MINIMUM;
        }
        return hits;
    }
    public static void updateMineralSpawn(){
        updateMineralSpawn(1);
    }
    public static void updateMineralSpawn(int a_inc)
    {
        for(int i = 0; i< WorldData.MinedBlocks.size(); i++)
        {
            WorldData.MinedBlocksTime.set(i, WorldData.MinedBlocksTime.get(i)+a_inc);

            boolean isDone = false;
            int curTime = WorldData.MinedBlocksTime.get(i);
            switch(WorldData.MinedBlocksType.get(i))
            {
                case COAL_ORE:
                    if(curTime >= MiningConfig.MINE_COAL_RESPAWN_TIME)
                        isDone = true;
                    break;
                case DIAMOND_ORE:
                    if(curTime >= MiningConfig.MINE_DIAMOND_RESPAWN_TIME)
                        isDone = true;
                    break;
                case IRON_ORE:
                    if(curTime >= MiningConfig.MINE_IRON_RESPAWN_TIME)
                        isDone = true;
                    break;
                case EMERALD_ORE:
                    if(curTime >= MiningConfig.MINE_EMERALD_RESPAWN_TIME)
                        isDone = true;
                    break;
                case LAPIS_ORE:
                    if(curTime >= MiningConfig.MINE_LAPIS_RESPAWN_TIME)
                        isDone = true;
                    break;
                case GOLD_ORE:
                    if(curTime >= MiningConfig.MINE_GOLD_RESPAWN_TIME)
                        isDone = true;
                    break;
                case REDSTONE_ORE:
                    if(curTime >= MiningConfig.MINE_REDSTONE_RESPAWN_TIME)
                        isDone = true;
                    break;
                case GLOWING_REDSTONE_ORE:
                    if(curTime >= MiningConfig.MINE_REDSTONE_RESPAWN_TIME)
                        isDone = true;
                    break;
            }

            if(isDone)
            {
                WorldData.MinedBlocks.get(i).setType(WorldData.MinedBlocksType.get(i));
                WorldData.MinedBlocks.remove(i);
                WorldData.MinedBlocksTime.remove(i);
                WorldData.MinedBlocksType.remove(i);
                i--;
            }
        }
    }
    public static int getMaterialExp(Material a_mat)
    {
        switch(a_mat)
        {
            case COAL_ORE:
                return MiningConfig.MINE_COAL_EXP;
            case DIAMOND_ORE:
                return MiningConfig.MINE_DIAMOND_EXP;
            case IRON_ORE:
                return MiningConfig.MINE_IRON_EXP;
            case EMERALD_ORE:
                return MiningConfig.MINE_EMERALD_EXP;
            case LAPIS_ORE:
                return MiningConfig.MINE_LAPIS_EXP;
            case GOLD_ORE:
                return MiningConfig.MINE_GOLD_EXP;
            case REDSTONE_ORE:
                return MiningConfig.MINE_REDSTONE_EXP;
            case GLOWING_REDSTONE_ORE:
                return MiningConfig.MINE_REDSTONE_EXP;
        }
        return 0;
    }
}
