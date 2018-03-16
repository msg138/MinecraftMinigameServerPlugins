package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Michael George on 11/15/2017.
 */
public class TestCommand extends CommandTemplate {
    public TestCommand(Main plugin){
        super(plugin, "test");
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        sender.sendMessage(Config.Prefix + "You have entered the test command. Are you proud?");
        return true;
    }
}
