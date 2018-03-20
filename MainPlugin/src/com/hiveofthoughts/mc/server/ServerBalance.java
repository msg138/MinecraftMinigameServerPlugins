package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collection;

/**
 * Created by Michael George on 3/19/2018.
 */
public class ServerBalance {

    public static boolean stopServer(String a_reason){
        kickAll(Config.Server_Main, a_reason, ServerInfo.getInstance().getServerName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.GlobalMain, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        },25);
        // Bukkit.getServer().shutdown();
        return true;
    }

    public static boolean reloadServer(String a_reason){
        kickAll(Config.Server_Main, a_reason, ServerInfo.getInstance().getServerName());
        Bukkit.getServer().reload();
        Bukkit.getServer().reloadData();
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
