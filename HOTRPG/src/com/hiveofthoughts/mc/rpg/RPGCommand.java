package com.hiveofthoughts.mc.rpg;

import com.hiveofthoughts.mc.*;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.commands.CommandTemplate;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Michael George on 3/20/2018.
 */
public class RPGCommand extends CommandTemplate {
    public RPGCommand(JavaPlugin a_main){
        super(a_main, "rpg");
        m_requiredArgs = 1;
    }


    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length > 0){
            switch(args[0].toLowerCase()){
                case "stats":
                    if(sender instanceof Player)
                        RPGConfig.sendStats((Player) sender);
                    else
                        sender.sendMessage(RPGConfig.Prefix + "Unable to do that.");
                    break;
                default:
                    sender.sendMessage(RPGConfig.Prefix + Config.MessageUnknown);
            }
        }else
            sender.sendMessage(RPGConfig.Prefix + Config.MessageUnknown);
        return true;
    }
}
