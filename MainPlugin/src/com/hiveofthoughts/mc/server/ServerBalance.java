package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collection;

import com.hiveofthoughts.connector.*;

/**
 * Created by Michael George on 3/19/2018.
 */
public class ServerBalance {

    private static String BuildScript = "/home/michaelg/hot/startbuild.sh";
    private static String TestScript = "/home/michaelg/hot/starttest.sh";
    private static String MainScript = "/home/michaelg/hot/startmain.sh";
    private static String AdvScript = "/home/michaelg/hot/startadv.sh";

    private static String StopScript = "/home/michaelg/hot/stopserver.sh";

    private static String ShellCommand = "/bin/sh";

    private static int ServerNumberNil = -1;

    public static String startServer(String a_serverName){
        return startServer(a_serverName, ServerNumberNil);
    }

    public static String startServer(String a_serverName, int a_number){

        String t_firstAvailable = "";
        String[] t_serversOfTypeAvailable = ServerInfo.getInstance().getServerOfflineListOfType(a_serverName);
        if(t_serversOfTypeAvailable.length < 1){
            return Config.MessageServerStartMaxReached;
        }else {
            if (a_number != ServerNumberNil){
                for (String a_sn : t_serversOfTypeAvailable) {
                    if (a_sn.equals(a_serverName + Config.ServerNameMiddle + a_number)) {
                        t_firstAvailable = a_sn;
                    }
                }
                if(t_firstAvailable.isEmpty())
                    a_number = ServerNumberNil;
            }
            else{
                t_firstAvailable = t_serversOfTypeAvailable[0];
                a_number = getServerNumber(t_firstAvailable);
            }
        }
        if(a_number == ServerNumberNil)
            return Config.MessageServerStartAlreadyOn;
        /** No longer used with the addition of the connector ..
        switch(a_serverName.toLowerCase()){
            case "build":
                if(a_number < 1)
                    return Config.MessageServerStartPermanentRequired;
                if(Config.executeScript(ShellCommand, new String[]{BuildScript, "" + a_number}))
                    return Config.MessageServerStartSuccess;
                break;
            case "test":
                if(Config.executeScript(ShellCommand, new String[]{TestScript, "" + a_number}))
                    return Config.MessageServerStartSuccess;
                break;
            case "adventure":
                if(Config.executeScript(ShellCommand, new String[]{AdvScript, "" + a_number}))
                    return Config.MessageServerStartSuccess;
            case "main":
                if(Config.executeScript(ShellCommand, new String[]{MainScript, "" + a_number}))
                    return Config.MessageServerStartSuccess;
                break;
            default:
                return Config.MessageServerTypeUnknown;
        }*/

        if(Connector.submitAction(new Connector.ActionStartServer(a_serverName.toLowerCase(), a_number)))
            return Config.MessageServerStartSuccess;

        return Config.MessageServerStartFail;
    }

    public static String getServerFolderName(String a_serverName){
        switch(a_serverName.toLowerCase()){
            case "build":
                return "Build";
            case "test":
                return "TestServer";
            case "adventure":
                return "Adv";
            case "main":
                return "Server";
            default:
                return a_serverName;
        }
    }

    public static boolean stopServer(String a_reason){

        // Run the stop server script before we actually stop the server, to prevent it from coming back up.
        //if(!Config.executeScript(ShellCommand, new String[]{StopScript, getServerFolderName(getMainServer(ServerInfo.getInstance().getServerName())), ""+(ServerInfo.getInstance().getServerNumber() - 1)}))
            //return false;

        kickAll(Config.Server_Main, a_reason, ServerInfo.getInstance().getServerName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.GlobalMain, new Runnable() {
            @Override
            public void run() {
                // NO LONGER NEEDED WITH CONNECTOR Bukkit.getServer().shutdown();
                Connector.submitAction(new Connector.ActionStopServer(Config.ServerType.getName(), Config.ServerNumber));
                Bukkit.getServer().shutdown();
            }
        },25);
        // Bukkit.getServer().shutdown();
        return true;
    }

    public static boolean reloadServer(String a_reason){
        kickAll(Config.Server_Main, a_reason, ServerInfo.getInstance().getServerName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.GlobalMain, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        },25);

        return true;
    }

    public static boolean kickAll(String a_reason, String a_curServer){
        return kickAll(Config.ServerDefault, a_reason, a_curServer);
    }

    public static boolean kickAll(String a_serverPreference, String a_reason, String a_curServer){
        System.out.println("Kicking all players to ~" + a_serverPreference + " for reason " + a_reason);
        Collection<Player> t_playerList = (Collection<Player> )Bukkit.getServer().getOnlinePlayers();
        for(Player t_p : t_playerList){
            String t_nextAvail = getNextAvailable(a_serverPreference, a_curServer);
            if(t_nextAvail.equals(Config.Server_None))
                t_p.kickPlayer(a_reason);
            else
                ServerSelector.getInstance().teleportToServer(t_p, a_reason, t_nextAvail);
        }
        return true;
    }

    public static boolean kickAllSpecific(String a_serverName){
        return kickAllSpecific(a_serverName, "Default Reason");
    }
    public static boolean kickAllSpecific(String a_serverName, String a_reason){
        System.out.println("Kicking all players to '" + a_serverName + "' for reason " + a_reason);
        Collection<Player> t_playerList = (Collection<Player> )Bukkit.getServer().getOnlinePlayers();
        for(Player t_p : t_playerList)
            ServerSelector.getInstance().teleportToServer(t_p, a_reason, a_serverName);
        return true;
    }

    public static String getMainServer(String a_serverName){
        String r_ret = a_serverName;
        if(a_serverName.indexOf(Config.ServerNameMiddle) != -1)
            r_ret = a_serverName.substring(0, a_serverName.indexOf(Config.ServerNameMiddle));
        return r_ret;
    }

    public static boolean isMainServer(String a_serverName){
        return a_serverName.indexOf(Config.ServerNameMiddle) == -1;
    }

    public static int getServerNumber(String a_serverName){
        return Integer.parseInt(getServerNumberString(a_serverName));
    }
    public static String getServerNumberString(String a_serverName){
        if(isMainServer(a_serverName))
            return "0";
        return a_serverName.substring(a_serverName.indexOf(Config.ServerNameMiddle)+1);
    }

    public static String getNextAvailable(String a_serverPrefix){
        // If it has the server name middle, we will split to get the beginning part.
        String t_ret = getMainServer(a_serverPrefix);

        String[] t_servList = ServerInfo.getInstance().getServerOnlineListOfType(t_ret);
        int t_lowestCount = Integer.MAX_VALUE;
        String r_lowestServerName = Config.Server_None;
        for(String t_s : t_servList){
            try{
                int t_ci = Integer.parseInt(Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, ""+Config.ServerPorts.get(t_s)).getString(Database.Field_PlayerCount));
                if(t_ci < t_lowestCount){
                    t_lowestCount = t_ci;
                    r_lowestServerName = t_s;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return r_lowestServerName;
    }
    // Used if we don't want to connect to the same as the current server.
    public static String getNextAvailable(String a_serverPrefix, String a_curServer){
        // If it has the server name middle, we will split to get the beginning part.
        String t_ret = getMainServer(a_serverPrefix);

        String[] t_servList = ServerInfo.getInstance().getServerOnlineListOfType(t_ret);
        int t_lowestCount = Integer.MAX_VALUE;
        String r_lowestServerName = Config.Server_None;
        for(String t_s : t_servList){
            if(t_s.equals(a_curServer))
                continue;
            try{
                int t_ci = Integer.parseInt(Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, ""+Config.ServerPorts.get(t_s)).getString(Database.Field_PlayerCount));
                if(t_ci < t_lowestCount){
                    t_lowestCount = t_ci;
                    r_lowestServerName = t_s;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return r_lowestServerName;
    }
}
