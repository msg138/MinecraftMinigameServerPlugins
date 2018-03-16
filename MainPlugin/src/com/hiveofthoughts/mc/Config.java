package com.hiveofthoughts.mc;


import com.hiveofthoughts.mc.data.Warp;
import com.hiveofthoughts.mc.server.ServerType;
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
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Michael George on 11/20/2017.
 */
public class Config {

    public static final ServerType ServerType = com.hiveofthoughts.mc.server.ServerType.RPG;

    public static final String Prefix = ChatColor.DARK_GREEN.toString() + "[HOT] " + ChatColor.WHITE.toString();
    public static final String MessageUnknown = "Unknown Command. Type /help to see possible commands.";
    public static final String MessagePermission = "You do not have permission to do that.";

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
        if(userConfig.contains("users."+player.getUniqueId()+".permission")) {
            userConfig.set("users."+player.getUniqueId()+".username", player.getName());
            return true;
        }
        return false;
    }
    public static void addNewPlayer(Player p)
    {
        userConfig.set("users."+p.getUniqueId(), (userConfig.getConfigurationSection("users.new").getKeys(true)));
        userConfig.set("users."+p.getUniqueId(), (userConfig.getConfigurationSection("users.new").getValues(true)));

        p.sendMessage(Prefix+"Your profile has been created.");
    }

    public static void savePlayerData(Main.PlayerData a_pd){
        // Set the permissions template that the player uses.
        userConfig.set("users." + a_pd.getUniqueId() + ".permission", a_pd.getPermissions().getName());
        System.out.println("Saved user permission, " + a_pd.getPermissions().getName() + " for user, '" + a_pd.getUniqueId() + "'");
    }

}
