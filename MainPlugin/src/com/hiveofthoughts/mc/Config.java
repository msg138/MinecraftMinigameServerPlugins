package com.hiveofthoughts.mc;


import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.data.Warp;
import com.hiveofthoughts.mc.permissions.PermissionTemplate;
import com.hiveofthoughts.mc.server.ServerType;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Michael George on 11/20/2017.
 */
public class Config {

    public static final ServerType ServerType = com.hiveofthoughts.mc.server.ServerType.DEFAULT;

    public static final String Prefix = ChatColor.DARK_GREEN.toString() + "[HOT] " + ChatColor.WHITE.toString();
    public static final String MessageUnknown = "Unknown Command. Type /help to see possible commands.";
    public static final String MessagePermission = "You do not have permission to do that.";
    public static final String MessageErrorUnknown = "Something went wrong on the server. (You should report this)";
    public static final String MessageErrorKnown = "Something went wrong on the server. (We are working on this)";

    public static final boolean DisplayLoginMessage = true;
    public static final String LoginMessage = ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] $P";// $P is Player Name.
    public static final boolean DisplayLogoutMessage = true;
    public static final String LogoutMessage = ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] $P";// $P is Player Name.

    // More generic permissions
    public static final String PermissionBuild = "action.build";
    public static final String PermissionDig = "action.dig";

    private static ArrayList<Warp> warps = new ArrayList<Warp>();

    private static File userfile;
    private static File configfile;
    private static File warpfile;
    private static FileConfiguration userConfig;
    private static FileConfiguration warpConfig;
    private static FileConfiguration config;

    public static FileConfiguration getUserConfig(){
        return userConfig;
    }

    public static void loadFiles(JavaPlugin jp) throws Exception
    {
        userfile = new File(jp.getDataFolder(),"users.yml");
        configfile = new File(jp.getDataFolder(),"config.yml");
        warpfile = new File(jp.getDataFolder(),"warps.yml");
        createData(jp);

        userConfig = new YamlConfiguration();
        config = new YamlConfiguration();
        warpConfig = new YamlConfiguration();

        userConfig.load(userfile);
        config.load(configfile);
        warpConfig.load(warpfile);

        Set<String> w = warpConfig.getConfigurationSection("warps").getKeys(false);
        ArrayList<String> was = new ArrayList<String>();
        was.addAll(w);
        for(int i=0;i<was.size();i++)
        {
            Warp warp = new Warp();
            warp.setName(was.get(i));
            warp.setX(warpConfig.getDouble("warps."+warp.getName()+".x"));
            warp.setY(warpConfig.getDouble("warps."+warp.getName()+".y"));
            warp.setZ(warpConfig.getDouble("warps."+warp.getName()+".z"));
            warps.add(warp);
        }
    }

    public static void createData(JavaPlugin jp) throws Exception
    {
        if(!userfile.exists()){                        // checks if the yaml does not exists
            userfile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
            copy(jp.getResource("users.yml"), userfile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
        if(!configfile.exists()){
            configfile.getParentFile().mkdirs();
            copy(jp.getResource("config.yml"), configfile);
        }
        if(!warpfile.exists()){
            warpfile.getParentFile().mkdirs();
            copy(jp.getResource("warps.yml"), warpfile);
        }
    }public static void resetData(JavaPlugin jp) throws Exception
    {                     // checks if the yaml does not exists
        userfile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
        copy(jp.getResource("users.yml"), userfile); // copies the yaml from your jar to the folder /plugin/<pluginName>

        configfile.getParentFile().mkdirs();
        copy(jp.getResource("config.yml"), configfile);

        warpfile.getParentFile().mkdirs();
        copy(jp.getResource("warps.yml"), warpfile);
    }

    public static void saveData() throws Exception
    {
        config.save(configfile);
        userConfig.save(userfile);
        warpConfig.save(warpfile);
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Location getWarp(String name)
    {
        for(int i=0;i<warps.size();i++)
        {
            if(warps.get(i).getName().equals(name))
            {
                Location loc = new Location(null,0,0,0);
                loc.setX(warps.get(i).getX());
                loc.setY(warps.get(i).getY());
                loc.setZ(warps.get(i).getZ());
                return loc;
            }
        }
        return null;
    }
    public static boolean configPlayerExists(Player player)
    {
        try {
            return Database.getInstance().getDocuments(Database.Table_User, Database.Field_UUID, player.getUniqueId().toString()).size() > 0;
        }catch(Exception e){
            System.out.println("Unable to check if player's config exists!!! Reason:");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        /** Used only if files are used, not if using database.
         * TODO Implement ability to use files without Database connection required (for local testing)
        if(userConfig.contains("users."+player.getUniqueId()+".permission")) {
            userConfig.set("users."+player.getUniqueId()+".username", player.getName());
            return true;
        }
        return false;*/


    }
    public static void addNewPlayer(Player p)
    {
        try{
            Database.getInstance().insertDocument(Database.Table_User, new Document(Database.Field_UUID, p.getUniqueId().toString()).append(Database.Field_Username, p.getName()).append(Database.Field_Permission, PermissionTemplate.DEFAULT.getName()));
            p.sendMessage(Prefix + "Your profile has been created.");
        }catch(Exception e){
            System.out.println("Unable to create player's configuration!!! Reason:");
            System.out.println(e.getMessage());
            e.printStackTrace();
            p.sendMessage(Prefix + MessageErrorUnknown);
        }
/*
        try{
            int t_res = Database.getInstance().setQuery("INSERT INTO `users` (uuid, username) VALUES (\"" + p.getUniqueId() + "\", \"" + p.getName() + "\");");
            if(t_res < 1)
                throw new SQLDataException("UNABLE TO CREATE PROFILE");
            else
                p.sendMessage(Prefix + "Your profile has been created.");
        }catch(Exception e){
            e.printStackTrace();
            p.sendMessage(Prefix + MessageErrorUnknown);
        }
        /** No longer used. This was for files.
        userConfig.set("users."+p.getUniqueId(), (userConfig.getConfigurationSection("users.new").getKeys(true)));
        userConfig.set("users."+p.getUniqueId(), (userConfig.getConfigurationSection("users.new").getValues(true)));

        p.sendMessage(Prefix+"Your profile has been created.");*/
    }

    public static void savePlayerData(Main.PlayerData a_pd){

        try{
            Database.getInstance().updateDocument(Database.Table_User, Database.Field_UUID, a_pd.getUniqueId().toString(), Database.Field_Permission, a_pd.getPermissions().getName());
        }catch(Exception e){
            System.out.println("Unable to save player's configuration!!! Reason:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        /*
        try{
            int t_res = Database.getInstance().setQuery("UPDATE users SET permission=\"" + a_pd.getPermissions().getName() + "\" WHERE uuid=\"" + a_pd.getUniqueId() + "\";");
            if(t_res < 1)
                throw new SQLDataException("UNABLE TO SAVE PLAYER DATA");
        }catch(Exception e){
            e.printStackTrace();
            // MESSAGE ADMINISTRATORS / DEVELOPERS ABOUT THIS
        }

        /** No longer used, this was for files
        // Set the permissions template that the player uses.
        userConfig.set("users." + a_pd.getUniqueId() + ".permission", a_pd.getPermissions().getName());
        System.out.println("Saved user permission, " + a_pd.getPermissions().getName() + " for user, '" + a_pd.getUniqueId() + "'");*/
    }

    public static void setPlayerData(Player a_p, String a_field, String a_value){

        try{
            Database.getInstance().updateDocument(Database.Table_User, Database.Field_UUID, a_p.getUniqueId().toString(), a_field, a_value);
        }catch(Exception e){
            System.out.println("Unable to save player data, Field: " + a_field + ", Value: " + a_value + " Reason:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        /*
        try{
            int t_res = Database.getInstance().setQuery("UPDATE users SET " + a_field + "=\"" + a_value + "\" WHERE uuid=\"" + a_p.getUniqueId() + "\";");
            if(t_res < 1)
                throw new SQLDataException("UNABLE TO SET PLAYER DATA, " + a_p.getUniqueId() + ", " + a_field + " = " + a_value);
        }catch(Exception e){
            e.printStackTrace();
            // MESSAGE ADMINISTRATORS / DEVELOPERS ABOUT THIS
        }*/
    }

    public static Document getPlayerDataDocument(Player p){

        try{
            return Database.getInstance().getDocument(Database.Table_User, Database.Field_UUID, p.getUniqueId().toString());
        }catch(Exception e){
            System.out.println("Unable to get player data, " + " Reason:");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new Document();
        }

        /*try{
            ResultSet t_res = Database.getInstance().getQuery("SELECT * FROM users WHERE uuid=\"" + p.getUniqueId() + "\";");
            if(!t_res.first())
                throw new SQLDataException("No rows returnd!!");
            return t_res.getString(a_field);
        }catch(Exception e){
            e.printStackTrace();
            p.sendMessage(Prefix + MessageErrorUnknown);
            return "";
        }/**/
    }

}
