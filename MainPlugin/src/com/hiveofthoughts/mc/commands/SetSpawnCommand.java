package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Michael George on 3/21/2018.
 */
public class SetSpawnCommand extends CommandTemplate{
    public SetSpawnCommand(Main plugin){
        super(plugin, "setspawn");
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player) {
            Player t_p = ((Player) sender);
            t_p.getWorld().setSpawnLocation(t_p.getLocation().getBlockX(), t_p.getLocation().getBlockY(), t_p.getLocation().getBlockZ());
            sender.sendMessage(Config.Prefix + "World spawn location has been set to your current location." + t_p.getWorld().getSpawnLocation().toString());
        }else
            sender.sendMessage(Config.Prefix + Config.MessageMustBePlayer);
        return true;
    }
}
