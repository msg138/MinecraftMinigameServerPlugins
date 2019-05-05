package com.hiveofthoughts.mc;


import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.data.Warp;
import com.hiveofthoughts.mc.listeners.global.player.PreventBlockPlaceAndBreak;
import com.hiveofthoughts.mc.permissions.PermissionTemplate;
import com.hiveofthoughts.mc.server.ItemBuilder;
import com.hiveofthoughts.mc.server.ServerInfo;
import com.hiveofthoughts.mc.server.ServerType;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by Michael George on 11/20/2017.
 */
public class Config {
    /**
     * VARIABLES HERE SHOULD ONLY BE CHANGED FROM OTHER PLUGINS IN THE ONLOAD FUNCTION OF THE PLUGIN TO ENSURE THAT THESE ARE CHANGED BEFORE THEY ARE RELEVANT.
     */
    public static ServerType ServerType = com.hiveofthoughts.mc.server.ServerType.DEFAULT;
    public static int ServerNumber = 0;

    public static boolean EnforceServerRestriction = true;

    // If this is too large, and there is a large amount of servers being checked, this can cause issues and crash the server.
    public static int ServerPingTimeout = 2000;
    public static int ServerStartUpTime = 30000;
    public static int ServerNameNumberStart = 1;
    public static int ServerNameNumberFinish = 10;
    public static String ServerNameMiddle = "-";
    public static String ServerHostName = "mc.kaspyre.com";

    public static int ServerMinDefault = 0;
    public static int ServerPlayersMaxDefault = 10;
    public static float ServerFullRatioDefault = 0.75f;
    public static int ServerCountOkayDefault = 2;

    public static String ServerDefault = "main";
    public static String Server_Main = "main";
    public static String Server_None = "limbo";

    public static String Prefix = ChatColor.DARK_GREEN.toString() + "[HOT] " + ChatColor.WHITE.toString();
    public static String MessageUnknown = "Unknown Command. Type /help to see possible commands.";
    public static String MessagePermission = "You do not have permission to do that.";
    public static String MessageErrorUnknown = "Something went wrong on the server. (You should report this)";
    public static String MessageErrorKnown = "Something went wrong on the server. (We are working on this)";
    public static String MessageMustBePlayer = "You must be a player to be able to do that.";
    public static String MessageMustBeConsole = "You must do that from the console.";

    public static String MessageServerStartTypeRequired = "Requires a type of the server to start.";
    public static String MessageServerStartPermanentRequired = "Requires server number for permanent servers.";
    public static String MessageServerStartMaxReached = "Maximum amount of servers for that type has already been reached.";
    public static String MessageServerStartAlreadyOn = "That server is already on.";
    public static String MessageServerStartSuccess = "Server was started successfully.";
    public static String MessageServerTypeUnknown = "That is not a known server type to start.";
    public static String MessageServerStartFail = "Server could not start. Please report this error.";

    public static String MessageRainDisabled = "Rain / Weather has been disabled.";
    public static String MessageRainEnabled = "Rain / Weather has been enabled.";

    public static boolean DisplayLoginMessage = true;
    public static String LoginMessage = ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] $P";// $P is Player Name.
    public static boolean DisplayLogoutMessage = true;
    public static String LogoutMessage = ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] $P";// $P is Player Name.

    public static String GlobalChatPrefix = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "GC" + ChatColor.GRAY + "]" +ChatColor.WHITE;
    public static String GlobalChatSuffix = " > ";

    public static String StaffChatPrefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "SC" + ChatColor.GRAY + "]" +ChatColor.WHITE;
    public static String StaffChatSuffix = " > ";

    // More generic permissions
    public static String PermissionBuild = "action.build";
    public static String PermissionDig = "action.dig";
    public static String PermissionStaffChat = "chat.staff";
    public static String PermissionGlobalChat = "chat.global";
    public static String PermissionServerChange = "serverchange";
    public static String PermissionServerChangeAll = "serverchangeall";

    public static String PermissionServerAll = "all";

    public static String StatusActive = "&2Active";
    public static String StatusInactive = "&o&7Inactive";
    public static String StatusOffline = "&4Offline";
    public static final String StatusInProgress = "&6In Progress";

    public static String BungeeCord = "BungeeCord";
    public static String BungeeConnect = "Connect";

    public static String InventoryServerItem = "Server";

    public static ItemStack BlankSpace = null;

    public static Set<String> DisabledCommands = new HashSet<String>(){
        {
            add("stop");
            add("reload");
        }
    };

    // Eventually move these to be loaded from the Network configuration database.
    // If adding servers here, remember the server will not show / start if not listed in server_list in networkConfig collection
    // For now, comment out the unneeded quantities of servers.
    public static final HashMap<String, Integer > ServerPorts = new HashMap<String, Integer>(){
        {
            // Main servers / hub / lobby
            put("main-1", 25600);
            put("main-2", 25601);
            put("main-3", 25602);
            put("main-4", 25603);
            put("main-5", 25604);
            put("main-6", 25605);
            put("main-7", 25606);
            put("main-8", 25607);
            put("main-9", 25608);
            put("main-10", 25609);

            // Testing servers.
            put("test-1", 25610);
            put("test-2", 25611);
            put("test-3", 25612);
            put("test-4", 25613);
            put("test-5", 25614);
            put("test-6", 25615);
            put("test-7", 25616);
            put("test-8", 25617);
            put("test-9", 25618);
            put("test-10", 25619);

            // Build servers.
            put("build-1", 25620);
            put("build-2", 25621);
            put("build-3", 25622);
            put("build-4", 25623);
            put("build-5", 25624);
            put("build-6", 25625);
            put("build-7", 25626);
            put("build-8", 25627);
            put("build-9", 25628);
            put("build-10", 25629);

            // RPG Plugin servers.
            put("rpg-1", 25630);
            put("rpg-2", 25631);
            put("rpg-3", 25632);
            put("rpg-4", 25633);
            put("rpg-5", 25634);
            put("rpg-6", 25635);
            put("rpg-7", 25636);
            put("rpg-8", 25637);
            put("rpg-9", 25638);
            put("rpg-10", 25639);

            // Adventure Map Servers
            put("adventure-1", 25690);
        }
    };




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
        /**config.save(configfile);
        userConfig.save(userfile);
        warpConfig.save(warpfile);*/

        for(Main.PlayerData t_pd : Main.GlobalMain.getPlayerList().values()) {
            if(t_pd != null)
                t_pd.save();
        }
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
            Database.getInstance().updateDocument(Database.Table_User, Database.Field_UUID, a_pd.getUniqueId().toString(), Database.Field_LastSeenServer, ServerInfo.getInstance().getServerName() + Config.ServerNameMiddle + ServerInfo.getInstance().getServerNumber());
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
    }
}
