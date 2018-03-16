package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Michael George on 11/15/2017.
 */
public class CommandTemplate implements CommandExecutor {
    protected final Main m_plugin;
    protected final String m_commandName;

    // Flags for the command.
    protected boolean m_playerOnly = false;
    protected boolean m_consoleOnly = false;
    protected boolean m_playerOnline = true;
    protected int m_requiredArgs = 0;

    protected String m_description = "nil description";
    protected String m_usage = "/<command>";

    public CommandTemplate(Main plugin, String commandName){
        this.m_plugin = plugin;
        this.m_plugin
                .getCommand(commandName)
                .setExecutor(this);
        m_commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(m_requiredArgs > args.length)
            return true;
        if(m_playerOnly && !(sender instanceof Player))
            return true;
        if(m_consoleOnly && sender instanceof Player)
            return true;

        return act(sender, cmd, label, args);
    }

    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        return true;
    }

    public String getCommandName(){
        return m_commandName;
    }
    public String getDescription(){
        return m_description;
    }
}
