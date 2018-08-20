package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by Michael George on 3/19/2018.
 */
public class ServerCommand extends CommandTemplate {

    public ServerCommand(Main pl){
        super(pl, "server");
        m_requiredArgs = 0;
        m_usage = "server <list:connect:status:start:stop:reload:block> [<servername>] [option]";
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length < 1){
            sender.sendMessage(Config.Prefix + "Current Server: " + ServerInfo.getInstance().getServerName());
        }else
        switch(args[0].toLowerCase()){
            case "list":
                if(args.length > 1)
                    sender.sendMessage(Config.Prefix + "Servers of Type '" + args[1] + "':" + ServerInfo.getInstance().getServerOnlineListOfType(args[1]));
                else
                    sender.sendMessage(Config.Prefix + String.join(",", ServerInfo.getInstance().getServerCompleteOnlineList()));
                break;
            case "load":
            case "join":
            case "connect":
                if(args.length < 2)
                    sender.sendMessage(Config.Prefix + "Command requires server name as an argument.");
                else if(sender instanceof Player) {
                    if(ServerBalance.isMainServer(args[1]))
                        ServerSelector.getInstance().teleportToServer((Player) sender, "Connecting to server.", ServerBalance.getNextAvailable(args[1]));
                    else
                        ServerSelector.getInstance().teleportToServer((Player) sender, "Connecting to server.", args[1]);
                }else
                    sender.sendMessage(Config.Prefix + "Must be a player for that command!");
                break;
            case "block":
                if(args.length < 2)
                    try {
                        sender.sendMessage(Config.Prefix + "Current Server Block: " + Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, "" + Config.ServerPorts.get(ServerInfo.getInstance().getServerName())).getString(Database.Field_ServerBlock));
                    }catch(Exception e){
                        sender.sendMessage(Config.Prefix + Config.MessageErrorUnknown);
                        e.printStackTrace();
                    }
                else{
                    if(Config.ServerPorts.get(args[1]) != null)
                        try {
                            sender.sendMessage(Config.Prefix + "Current Server Block: " + Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, "" + Config.ServerPorts.get(args[1])).getString(Database.Field_ServerBlock));
                        }catch(Exception e){
                            sender.sendMessage(Config.Prefix + Config.MessageErrorUnknown);
                            e.printStackTrace();
                        }
                    else {
                        ServerInfo.getInstance().setServerBlock(args[1]);
                        sender.sendMessage(Config.Prefix + "Server block changed!");
                    }
                }
                break;
            case "status":
                try {
                    if (args.length < 2)
                        sender.sendMessage(Config.Prefix + "Command requires server name as an argument.");
                    else if(args.length >= 2){
                        if(args.length >= 3 && args[2].toLowerCase().equals("view")){
                            if (ServerBalance.isMainServer(args[1]))
                                sender.sendMessage(Config.Prefix + args[1] + " status: " + Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, "" + Config.ServerPorts.get(ServerBalance.getNextAvailable(args[1]))));
                            else
                                sender.sendMessage(Config.Prefix + args[1] + " status: " + Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, "" + Config.ServerPorts.get(args[1])));
                        }else {
                            String t_newStatus = "";
                            for (int i = 1; i < args.length; i++)
                                t_newStatus += args[i] + " ";
                            ServerInfo.getInstance().setServerStatus(t_newStatus);
                            sender.sendMessage(Config.Prefix + "Server status changed!");
                        }
                    }
                }catch(Exception e){
                    sender.sendMessage(Config.Prefix + Config.MessageErrorUnknown);
                    e.printStackTrace();
                }
                break;
            case "togglerainperm":
                ServerInfo.getInstance().setRainTemplateDisabled(!ServerInfo.getInstance().getRainDisabled());
            case "togglerain":
                ServerInfo.getInstance().setRainDisabled(!ServerInfo.getInstance().getRainDisabled());
                sender.sendMessage(Config.Prefix + (ServerInfo.getInstance().getRainDisabled() ? Config.MessageRainDisabled : Config.MessageRainEnabled));
                break;
            case "stop":
                String t_stopMessage = "Server is shutting down.";
                if(args.length > 1){
                    t_stopMessage = "";
                    for(int i=1;i<args.length;i++){
                        t_stopMessage += args[i] + " ";
                    }
                }
                ServerBalance.stopServer(t_stopMessage);
                break;
            case "start":
                if(args.length > 1){
                    if(args.length > 2){
                        sender.sendMessage(Config.Prefix + ServerBalance.startServer(args[1], Integer.parseInt(args[2])));
                    }else
                        sender.sendMessage(Config.Prefix + ServerBalance.startServer(args[1]));
                }else
                    sender.sendMessage(Config.Prefix + Config.MessageServerStartTypeRequired);
                break;
            case "reload":
                String t_reloadMessage = "Server is reloading.";
                if(args.length > 1){
                    t_reloadMessage = "";
                    for(int i=1;i<args.length;i++){
                        t_reloadMessage += args[i] + " ";
                    }
                }
                ServerBalance.reloadServer(t_reloadMessage);
                break;
        }
        return true;
    }
}
