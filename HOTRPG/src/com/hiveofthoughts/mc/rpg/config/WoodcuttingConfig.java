package com.hiveofthoughts.mc.rpg.config;

import org.bukkit.block.Block;

/**
 * Created by Michael George on 3/20/2018.
 */
public class WoodcuttingConfig {
    // Woodcutting exp bonus.
    public static final double CHOP_EXP_BONUS = 1;
    // Hits it takes per log type.
    public static final int CHOP_DARKOAK_TIME = 30;
    public static final int CHOP_ACACIA_TIME = 25;
    public static final int CHOP_SPRUCE_TIME = 20;
    public static final int CHOP_JUNGLE_TIME = 20;
    public static final int CHOP_BIRCH_TIME = 15;
    public static final int CHOP_OAK_TIME = 10;
    // Minimum number of hits per log type.
    public static final int CHOP_MINIMUM = 3;
    // How many levels per decrease in hits.
    public static final int CHOP_LEVEL_CHANGE = 3;
    // For every x levels, increase the chance that you will get Logs.
    public static final int CHOP_LEVEL_CHANCE = 20;

    public static final double CHOP_MINIMUM_CHANCE = 3;
    public static final double CHOP_MAXIMUM_CHANCE = 8;

    // HOw much exp that each ore provides upon successful mine.
    public static final int CHOP_DARKOAK_EXP = 70;
    public static final int CHOP_ACACIA_EXP = 50;
    public static final int CHOP_SPRUCE_EXP = 40;
    public static final int CHOP_JUNGLE_EXP = 20;
    public static final int CHOP_BIRCH_EXP = 10;
    public static final int CHOP_OAK_EXP = 5;

    public static final String WOOD_OAK = "Oak";
    public static final String WOOD_BIRCH = "Birch";
    public static final String WOOD_JUNGLE = "Jungle";
    public static final String WOOD_SPRUCE = "Spruce";
    public static final String WOOD_ACACIA = "Acacia";
    public static final String WOOD_DARKOAK = "Dark Oak";


    public static final int CHOP_AIR_PASS = 1;
    public static final int CHOP_LEAVE_PASS = 2;



    public static String getBlockType(Block a_b){
        /*switch(a_b.getType()){
            case LOG:
                switch(a_b.getData()){
                    case 0:
                        return WOOD_OAK;
                    case 2:
                        return WOOD_BIRCH;
                    case 1:
                        return WOOD_SPRUCE;
                    case 3:
                        return WOOD_JUNGLE;
                    default:
                        return WOOD_OAK;
                }
            case LOG_2:
                switch(a_b.getData()){
                    case 0:
                        return WOOD_ACACIA;
                    case 1:
                        return WOOD_DARKOAK;
                    default:
                        return WOOD_OAK;
                }
        }*/
        return WOOD_OAK;
    }
}
