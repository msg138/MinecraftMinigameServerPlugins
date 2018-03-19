package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.permissions.PermissionTemplate;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerMenuCommand extends CommandTemplate {

    public ServerMenuCommand(Main pl){
        super(pl, "serverlist");

        m_requiredArgs = 0;
    }


    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        Player t_p = Bukkit.getPlayer(sender.getName());
        if(t_p != null)
            ServerSelector.getInstance().openMenu(t_p);
        return true;
    }
}
