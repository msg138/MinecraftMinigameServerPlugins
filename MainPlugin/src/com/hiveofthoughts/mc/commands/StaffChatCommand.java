package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.connector.Connector;
import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand extends CommandTemplate {

    public StaffChatCommand(Main plugin){
        super(plugin, "sc");
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        String t_message = "";
        if(Bukkit.getPlayer(sender.getName()) != null)
            t_message = Config.StaffChatPrefix + ((Main)m_plugin).getPlayerData((Player) sender).getPermissions().getPrefix() + " " +
                    sender.getName() + " " + ((Main)m_plugin).getPlayerData((Player) sender).getPermissions().getSuffix() + Config.StaffChatSuffix;

        for(int t_i = 0; t_i < args.length; t_i++)
            t_message += args[t_i] + " ";
        Connector.submitAction(new Connector.ActionStaffMessage(t_message));
        return true;
    }
}
