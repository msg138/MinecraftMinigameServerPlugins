package com.hiveofthoughts.mc.rpg;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.config.Database;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Michael George on 3/19/2018.
 */
public class RPGConfig {

    public static final String Prefix = "[" + ChatColor.GOLD + "HOT.RPG" + ChatColor.RESET + "] ";

    public static final String MessageBlockMined = "That has already been mined.";

    public static final String MessageLevelMining = "Your mining skill has leveled up!";
    public static final String MessageLevelWoodcutting = "Your woodcutting skill has leveled up!";

    public static final String MessageStatsStart = "/*--------- STATS --------*/";
    public static final String MessageStatsEnd = "/*--------------------------*/";

    // Sword damage types
    public static final int WOOD_DAMAGE = 1;
    public static final int STONE_DAMAGE = 2;
    public static final int IRON_DAMAGE = 5;
    public static final int GOLD_DAMAGE = 7;
    public static final int DIAMOND_DAMAGE = 10;

    // Afk kicker times
    public static final int KICK_AFTER_SEC = 15*60;
    public static final int AFK_AFTER_SEC = 10*60;


    public static final int EXP_PER_LEVEL = 100;
    public static final double EXP_CONSTANT = 1.2;
    public static final int EXP_PER_COMPLETE = 1;

    public static final String Field_RPGPlugin = "hotrpg";

    public static final String Field_MiningHits = "mine_hits";
    public static final String Field_MiningMaterial = "mine_material";
    public static final String Field_LevelMining = "level_mining";
    public static final String Field_ExperienceMining = "exp_mining";

    public static final String Field_WoodcuttingHits = "woodcutting_hits";
    public static final String Field_WoodcuttingMaterial = "woodcutting_material";
    public static final String Field_LevelWoodcutting = "level_woodcutting";
    public static final String Field_ExperienceWoodcutting = "exp_woodcutting";

    public static final String PlayerPlacedSurvival = "survival";
    public static final String PlayerPlacedCreative = "creative";

    public static final String Field_LastAdded = Field_ExperienceWoodcutting;

    public static Document getPlayerRPGInfo(Player a_p){
        try{
            try {
                Document t_doc = (Document) Database.getInstance().getDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString());
                verifyAllFields(a_p);
                return t_doc;
            }catch(Exception e) {
                Database.getInstance().insertDocument(getTableName(), new Document(Database.Field_UUID, a_p.getUniqueId().toString()));
                verifyAllFields(a_p);
                return Database.getInstance().getDocument(Database.Table_User, Database.Field_UUID, a_p.getUniqueId().toString());
            }
        }catch(Exception e){
            e.printStackTrace();
            return new Document();
        }
    }

    public static boolean verifyAllFields(Player a_p){
        try {
            // Mining Fields
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_MiningHits))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_MiningHits, 0);
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_MiningMaterial))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_MiningMaterial, "");
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_LevelMining))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_LevelMining, 1);
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_ExperienceMining))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_ExperienceMining, 0);
            // Woodcutting Fields
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_WoodcuttingHits))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_WoodcuttingHits, 0);
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_WoodcuttingMaterial))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_WoodcuttingMaterial, "");
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_LevelWoodcutting))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_LevelWoodcutting, 1);
            if (!Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_ExperienceWoodcutting))
                Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_ExperienceWoodcutting, 0);

            return true;
        }catch(Exception e){
            e.printStackTrace();
            a_p.sendMessage(Prefix + Config.MessageErrorUnknown);
            return false;
        }
    }

    public static boolean checkLastAddedField(Player a_p){
        return Database.getInstance().fieldExists(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), Field_LastAdded);
    }

    public static boolean sendStats(Player a_p){
        Document t_doc = getPlayerRPGInfo(a_p);
        a_p.sendMessage(Prefix + MessageStatsStart);
        a_p.sendMessage(Prefix + " Mining - Lvl " + t_doc.getInteger(Field_LevelMining) + "   XP " + t_doc.getInteger(Field_ExperienceMining) + " / " + calculateExpNeeded(t_doc.getInteger(Field_LevelMining) + 1));
        a_p.sendMessage(Prefix + " Woodcutting - Lvl " + t_doc.getInteger(Field_LevelWoodcutting) + "   XP " + t_doc.getInteger(Field_ExperienceWoodcutting) + " / " + calculateExpNeeded(t_doc.getInteger(Field_LevelWoodcutting) + 1));
        a_p.sendMessage(Prefix + MessageStatsEnd);
        return true;
    }

    public static String getTableName(){
        return Database.Table_PluginPrefix + Field_RPGPlugin;
    }


    public static int calculateExpNeeded(int a_level){
        int r_required;

        r_required = (int)(Math.pow(a_level * EXP_CONSTANT, 2));

        return r_required;
    }

    public static boolean addExp(Player a_p, String a_skillL, String a_skillE, int a_amount){
        boolean r_level = false;

        Document t_doc = getPlayerRPGInfo(a_p);

        int t_totalExp = a_amount + t_doc.getInteger(a_skillE);
        int t_currentLevel = t_doc.getInteger(a_skillL);
        try {
            int t_expNeeded = calculateExpNeeded(t_currentLevel + 1);
            if(t_totalExp >= t_expNeeded) {
                r_level = true;
                t_currentLevel += 1;
                t_totalExp -= t_expNeeded;
            }
            Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), a_skillL, t_currentLevel);
            Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), a_skillE, t_totalExp);

        }catch(Exception e){
            a_p.sendMessage(Prefix + Config.MessageErrorUnknown);
            e.printStackTrace();
        }
        return r_level;
    }
}
