package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.connector.Connector;
import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;

/**
 * Created by Michael George on 3/19/2018.
 */
public class ServerInfo {

    private static ServerInfo m_instance = null;

    private String m_serverStatus = "";

    private String m_currentServer = "";

    private String m_currentServerBlock = "";

    private int m_currentServerNumber = 0;

    private boolean m_disableRain = false;

    private int m_scheduler = 0;

    public static ServerInfo getInstance(){
        if(m_instance == null)
            try {
                m_instance = new ServerInfo();
            }catch(Exception e){
                e.printStackTrace();
                m_instance = null;
            }
        return m_instance;
    }

    private ServerInfo(){
        // Load from db, defaults.
        m_serverStatus = Config.StatusInactive;

        m_currentServerBlock = Material.DIRT.toString();

        // Look through our loaded list of ports to server names to find which server this is.
        for(String t_s : Config.ServerPorts.keySet()){
            if(Config.ServerPorts.get(t_s) == Bukkit.getServer().getPort()){
                m_currentServer = t_s;
                break;
            }
        }

        // Load the defaults from the database
        try{
            Document t_doc = Database.getInstance().getDocument(Database.Table_ServerConfig, Database.Field_Name, ServerBalance.getMainServer(m_currentServer));
            if(t_doc.get(Database.Field_ServerBlock) != null)
                m_currentServerBlock = t_doc.getString(Database.Field_ServerBlock);
            if(t_doc.get(Database.Field_ServerStatus) != null)
                m_serverStatus = t_doc.getString(Database.Field_ServerStatus);
            if(t_doc.get(Database.Field_DisableRain) != null)
                m_disableRain = t_doc.getBoolean(Database.Field_DisableRain);
        }catch(Exception e){
            System.out.println("No server defaults exist for " + Config.ServerType);
        }
    }

    public String getServerName(){
        return Config.ServerType.getName().toLowerCase();
    }

    public int getServerNumber(){
        return Config.ServerNumber;
    }

    public boolean getRainDisabled(){
        return m_disableRain;
    }

    public void setRainDisabled(boolean a_d){
        m_disableRain = a_d;
    }

    public void setRainTemplateDisabled(boolean a_d){
        try{
            Database.getInstance().updateDocument(Database.Table_ServerConfig, Database.Field_Name, ServerBalance.getMainServer(getServerName()), new Document(Database.Field_DisableRain, getRainDisabled()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getServerStatus(){
        return m_serverStatus;
    }
    public void setServerStatus(String a_status){
        m_serverStatus = a_status;
    }

    public String getServerBlock(){
        return m_currentServerBlock;
    }
    public void setServerBlock(String a_s){
        m_currentServerBlock = a_s;
    }

    // Method to update the mongo database with the necessary information that pertains to this server.
    public void updateDatabase(){
        try{
            try{
                Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, getPort() + "");
            }catch(Exception e) {
                Database.getInstance().insertDocument(Database.Table_ServerInfo, new Document(Database.Field_Port, getPort() + ""));
            }
            Database.getInstance().updateDocument(Database.Table_ServerInfo, Database.Field_Port, getPort()+"",
                    new Document(Database.Field_PlayerCount, Bukkit.getServer().getOnlinePlayers().size() + "")
                        .append(Database.Field_ServerStatus, getServerStatus())
                        .append(Database.Field_ServerBlock, m_currentServerBlock)
                        .append(Database.Field_ServerType, Config.ServerType.getName())
                        .append(Database.Field_ServerVersion, Config.ServerType.getVersion()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeDatabase(){
        try{
            Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Port, getPort() + "");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getPort(){
        try {
            return Config.ServerPorts.get((Config.ServerType.getName() + Config.ServerNameMiddle + Config.ServerNumber).toLowerCase());
        } catch(Exception e){
            return 0;
        }
    }

    public void setScheduler(Main a_m){
        updateDatabase();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(a_m, new Runnable() {
            @Override
            public void run() {
                ServerInfo.getInstance().updateDatabase();
                List<String > t_actions = Connector.getServerActions();
                if(t_actions != null)
                    for(String t_action : t_actions){
                        a_m.getLogger().info("Running action: " + t_action);
                        Connector.convertStringToAction(t_action).runServer(a_m);
                    }
            }
        }, 0, 20);
    }

    public static int getPlayerCountOnServer(String a_serverName, int a_serverNumber){
        return getPlayerCountOnServer(a_serverName + Config.ServerNameMiddle + a_serverNumber);
    }
    public static int getPlayerCountOnServer(String a_serverName){
        try{
            return  Integer.parseInt((String) Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, Config.ServerPorts.get(a_serverName) + "").get(Database.Field_PlayerCount));
        } catch(Exception e){
            return -1;
        }
    }

    public static String[] getServerCompleteList(){
        try {
            String[] r_serverList = Config.ServerPorts.keySet().toArray(new String[Config.ServerPorts.size()]);// ((List<String>) Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorArray).get(Database.Field_Value)).toArray(new String[0]);
            return r_serverList;
        }catch(Exception e){
            return new String[]{};
        }
    }
    public static String[] getServerCompleteOnlineList(){
        try {
            String[] t_servers = Config.ServerPorts.keySet().toArray(new String[Config.ServerPorts.size()]);
            List<String> t_onlineServers = new ArrayList<>();
            for(String t_d : t_servers) {
                boolean t_serverUp = true;
                int t_portNum = Config.ServerPorts.get(t_d);

                // Check database to see if server is showing as offline, if so, it is not up.
                try{
                    // if(((String)Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "").get(Database.Field_ServerStatus)).equals(Config.StatusInProgress))
                    Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "");
                }catch(Exception e) {
                    // Doesn't exist, so is not up.
                    t_serverUp = false;
                }
                if(t_serverUp)
                    t_onlineServers.add(t_d);
            }
            String[] r_serverList = t_onlineServers.toArray(new String[0]);
            return r_serverList;
        }catch(Exception e){
            return new String[]{};
        }
    }
    public static String[] getServerCompleteOfflineList(){
        try {
            String[] t_servers = getServerCompleteList();
            String[] t_onlineServers = getServerCompleteOnlineList();
            List<String> t_offlineServers = new ArrayList<>();
            for(String t_d : t_servers) {
                boolean t_isOnline = false;
                for(String t_od : t_onlineServers){
                    if(t_d.equals(t_od)){
                        t_isOnline = true;
                        break;
                    }
                }
                if(!t_isOnline)
                    t_offlineServers.add(t_d);
            }
            String[] r_serverList = t_offlineServers.toArray(new String[0]);
            return r_serverList;
        }catch(Exception e){
            return new String[]{};
        }
    }
    public static String[] getServerList(){
        Set<String> t_singleServers = new HashSet<>();
        String[] t_serversCompleteList = getServerCompleteList();
        for(String t_s : t_serversCompleteList)
            t_singleServers.add(ServerBalance.getMainServer(t_s));
        return t_singleServers.toArray(new String[0]);
    }
    public static String[] getServerOnlineList(){
        Set<String> t_singleServers = new HashSet<>();
        String[] t_serversCompleteList = getServerCompleteOnlineList();
        for(String t_s : t_serversCompleteList)
            t_singleServers.add(ServerBalance.getMainServer(t_s));
        return t_singleServers.toArray(new String[0]);
    }
    public static String[] getServerOfflineList(){
        Set<String> t_singleServers = new HashSet<>();
        String[] t_serversCompleteList = getServerCompleteOfflineList();
        for(String t_s : t_serversCompleteList)
            t_singleServers.add(ServerBalance.getMainServer(t_s));
        return t_singleServers.toArray(new String[0]);
    }

    public static String[] getServerListOfType(String a_serverPrefix){
        List<String> t_singleServers = new ArrayList<>();
        String[] t_serversCompleteList = getServerCompleteList();
        for(String t_s : t_serversCompleteList)
            if(ServerBalance.getMainServer(t_s).equalsIgnoreCase(a_serverPrefix))
                t_singleServers.add(t_s);
        return t_singleServers.toArray(new String[0]);
    }

    public static String[] getServerOnlineListOfType(String a_serverPrefix){
        List<String> t_singleServers = new ArrayList<>();
        String[] t_serversCompleteList = getServerCompleteOnlineList();
        for(String t_s : t_serversCompleteList)
            if(ServerBalance.getMainServer(t_s).equalsIgnoreCase(a_serverPrefix))
                t_singleServers.add(t_s);
        return t_singleServers.toArray(new String[0]);
    }
    public static String[] getServerOfflineListOfType(String a_serverPrefix){
        List<String> t_singleServers = new ArrayList<>();
        String[] t_serversCompleteList = getServerCompleteOfflineList();
        for(String t_s : t_serversCompleteList)
            if(ServerBalance.getMainServer(t_s).equalsIgnoreCase(a_serverPrefix))
                t_singleServers.add(t_s);
        return t_singleServers.toArray(new String[0]);
    }
}
