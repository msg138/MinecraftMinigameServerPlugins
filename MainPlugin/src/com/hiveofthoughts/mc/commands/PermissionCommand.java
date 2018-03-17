package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.permissions.PermissionTemplate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Michael George on 11/20/2017.
 */
public class PermissionCommand extends CommandTemplate {

    public PermissionCommand(Main pl){
        super(pl, "permission");

        m_requiredArgs = 1;
    }


    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        switch(args[0]){
            case "reset":
                if(args.length > 1){
                    Player p = Bukkit.getServer().getPlayer(args[1]);
                    if(p == null)
                    {
                        sender.sendMessage(Config.Prefix + "Player does not exist!");
                        return true;
                    }
                    String message = p.getName() + " - " + PermissionTemplate.DEFAULT.getName();
                    if(m_plugin.getPlayerList().get(p.getName()) != null){
                        m_plugin.getPlayerList().get(p.getName()).setPermissions(PermissionTemplate.DEFAULT);
                    }else{
                        if(Config.configPlayerExists(p))
                            Config.setPlayerData(p, "permission", PermissionTemplate.DEFAULT.getName());
                            // Config.getUserConfig().set("users."+p.getUniqueId()+".permission", PermissionTemplate.DEFAULT.getName());
                    }
                    sender.sendMessage(Config.Prefix + message);
                }else
                    sender.sendMessage(Config.Prefix + "Requires player name as an argument.");
                break;
            case "view":
                if(args.length > 1){
                    Player p = Bukkit.getServer().getPlayer(args[1]);
                    if(p == null)
                    {
                        sender.sendMessage(Config.Prefix + "Player does not exist!");
                        return true;
                    }
                    String message = p.getName() + " - ";
                    if(m_plugin.getPlayerList().get(p.getName()) != null){
                        message += m_plugin.getPlayerList().get(p.getName()).getPermissions().getName();
                    }else{
                        if(Config.configPlayerExists(p))
                            message += Config.getPlayerDataString(p, "permission");
                        else
                            message += PermissionTemplate.DEFAULT.getName();
                    }
                    sender.sendMessage(Config.Prefix + message);
                }else
                    sender.sendMessage(Config.Prefix + "Requires player name as an argument.");
                break;
            case "set":
                if(args.length > 2){
                    Player p = Bukkit.getServer().getPlayer(args[1]);
                    if(p == null)
                    {
                        sender.sendMessage(Config.Prefix + "Player does not exist!");
                        return true;
                    }
                    String message = p.getName() + " - " + PermissionTemplate.getPermission(args[2]).getName();
                    if(m_plugin.getPlayerList().get(p.getName()) != null){
                        m_plugin.getPlayerList().get(p.getName()).setPermissions(PermissionTemplate.getPermission(args[2]));
                    }
                    if(Config.configPlayerExists(p))
                        Config.setPlayerData(p, "permission", PermissionTemplate.getPermission(args[2]).getName());// .getUserConfig().set("users."+p.getUniqueId()+".permission", PermissionTemplate.getPermission(args[2]).getName());
                    sender.sendMessage(Config.Prefix + message);
                }else
                    sender.sendMessage(Config.Prefix + "Requires permission name, then player name as arguments.");
                break;
            default:
                sender.sendMessage(Config.Prefix + "Invalid Arguments. Options are: set, view, or reset.");
                break;
        }
        return true;
    }
}
