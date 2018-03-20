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
    public static final int EXP_PER_COMPLETE = 1;

    public static final String Field_RPGPlugin = "hotrpg";

    public static final String Field_MiningHits = "mine_hits";
    public static final String Field_MiningMaterial = "mine_material";
    public static final String Field_LevelMining = "level_mining";
    public static final String Field_ExperienceMining = "exp_mining";

    public static Document getPlayerRPGInfo(Player a_p){
        try{
            try {
                Document t_doc = (Document) Database.getInstance().getDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString());
                return t_doc;
            }catch(Exception e) {
                Database.getInstance().insertDocument(getTableName(), new Document(Database.Field_UUID, a_p.getUniqueId().toString())
                        .append(Field_MiningHits, 0).append(Field_LevelMining, 1).append(Field_MiningMaterial, "").append(Field_ExperienceMining, 0)
                );
                return Database.getInstance().getDocument(Database.Table_User, Database.Field_UUID, a_p.getUniqueId().toString());
            }
        }catch(Exception e){
            e.printStackTrace();
            return new Document();
        }
    }

    public static String getTableName(){
        return Database.Table_PluginPrefix + Field_RPGPlugin;
    }

    public static boolean addExp(Player a_p, String a_skillL, String a_skillE, int a_amount){
        boolean r_level = false;

        Document t_doc = getPlayerRPGInfo(a_p);

        int t_totalExp = a_amount + t_doc.getInteger(a_skillE);
        int t_currentLevel = t_doc.getInteger(a_skillL);
        try {
            Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), a_skillL, (int)(t_currentLevel + (t_totalExp / EXP_PER_LEVEL)));
            Database.getInstance().updateDocument(getTableName(), Database.Field_UUID, a_p.getUniqueId().toString(), a_skillE, (int)(t_totalExp % EXP_PER_LEVEL));

            if(t_totalExp >= EXP_PER_LEVEL)
                r_level = true;
        }catch(Exception e){
            a_p.sendMessage(Prefix + Config.MessageErrorUnknown);
            e.printStackTrace();
        }
        return r_level;
    }
}
