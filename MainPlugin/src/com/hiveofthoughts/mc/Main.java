/**
 * Created by Michael George on 11/14/2017.
 */
package com.hiveofthoughts.mc;

import com.hiveofthoughts.mc.commands.*;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.listeners.global.server.BungeePluginListener;
import com.hiveofthoughts.mc.permissions.PermissionTemplate;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin{
    private HashMap<String, PlayerData> m_playerList;
    private ArrayList<CommandTemplate> m_commandList;

    public static Main GlobalMain = null;

    @Override
    public void onEnable(){
        GlobalMain = this;

        Bukkit.getPluginManager().registerEvents(new BungeePluginListener(), this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getLogger().info("Hive Of Thoughts starting up for Server of Type: " + Config.ServerType.getName());
        // Do first initialization of the Database,
        try{
            System.out.println( Database.getInstance().getDocument(Database.Table_ServerConfig, Database.Field_Name, "default_permission").get(Database.Field_Value));
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            Config.loadFiles(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        initializeCommands();
        if(m_playerList == null)
            m_playerList = new HashMap<>();
        try {
            for (Class t_listen : InitClasses.EventListeners){
                boolean t_isDenied = false;
                // Check through the exclusion list for classes that should not be loaded with the servertype of this server.
                for(Class t_check : InitClasses.ServerExclusive.get(Config.ServerType))
                    if(t_check.equals(t_listen)){
                        t_isDenied = true;
                        break;
                    }
                if(t_isDenied)
                    continue;
                Constructor t_cons = t_listen.getConstructor(Main.class);
                Bukkit.getPluginManager().registerEvents((Listener) t_cons.newInstance(this), this);
            }
            for (Class t_listen : InitClasses.ServerInclusive.get(Config.ServerType)){
                // Load each and every one, because these should not be excluded, if they are in the MUST include list.
                Constructor t_cons = t_listen.getConstructor(Main.class);
                Bukkit.getPluginManager().registerEvents((Listener) t_cons.newInstance(this), this);
            }
                // WRONG Bukkit.getPluginManager().registerEvents(( (Listener) t_listen.newInstance()).getDeclaredConstructor(t_listen).newInstance(new Object[]{ this }), this);
        }catch(Exception e){
            // Error thrown with creating an instance of the listener
            e.printStackTrace();
        }

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            m_playerList.put(player.getName(), new PlayerData(player));
        }

        // Set up Server Info repeater.
        ServerInfo.getInstance().setScheduler(this);
    }

    @Override
    public void onDisable(){
        getLogger().info("Saving player information");
        try {
            Config.saveData();
        }catch(Exception e){
            e.printStackTrace();
        }
        getLogger().info("Hive Of Thoughts shutting down.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        return true;
    }

    private void initializeCommands(){
        if(m_commandList == null)
            m_commandList = new ArrayList<>();
        m_commandList.add(new TestCommand(this));
        m_commandList.add(new HelpCommand(this));
        m_commandList.add(new PermissionCommand(this));
        m_commandList.add(new ServerMenuCommand(this));
        m_commandList.add(new ServerCommand(this));
    }

    public HashMap<String, PlayerData> getPlayerList(){
        return m_playerList;
    }
    public ArrayList<CommandTemplate> getCommandList(){
        return m_commandList;
    }

    public PlayerData getPlayerData(Player a_playerObject){
        PlayerData r_pd = getPlayerList().get(a_playerObject.getName());
        if(r_pd == null){
            r_pd = new PlayerData(a_playerObject);
            getPlayerList().put(a_playerObject.getName(), r_pd);
        }
        return r_pd;
    }

    public static class PlayerData{
        private PermissionTemplate m_permissions;

        // Hold the player id, to easily save to their config file.
        private UUID m_playerId;

        // Use this class to generate player data that pertains to the plugin.
        public PlayerData(Player player){
            // Set the localized player id for later use if needed.
            m_playerId = player.getUniqueId();
            // Set the default permissions so that if they don't have a config, when it's created, we need not worry.
            m_permissions = PermissionTemplate.DEFAULT;
            if(Config.configPlayerExists(player)){
                try {
                    m_permissions = PermissionTemplate.getPermission(Config.getPlayerDataDocument(player).getString(Database.Field_Permission));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else
                Config.addNewPlayer(player);
        }

        public void save(){
            // Save to a config file.
            Config.savePlayerData(this);
        }

        // Could be more secure. Limit access to the object itself.
        public PermissionTemplate getPermissions(){
            return m_permissions;
        }
        public void setPermissions(PermissionTemplate pt){
            m_permissions = pt;
        }

        // Get the unique id of the player.
        public UUID getUniqueId(){ return m_playerId; }

        public static HashMap<String, String> loadPlayerConfig(){
            HashMap<String, String> ret = new HashMap<>();
            return ret;
        }
    }


}
