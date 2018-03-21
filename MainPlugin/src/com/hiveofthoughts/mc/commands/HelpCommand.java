package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Michael George on 11/20/2017.
 */
public class HelpCommand extends CommandTemplate {

    private static final int AMOUNT_PER_PAGE = 6;

    public HelpCommand(Main pl){
        super(pl, "help");
    }

    public static void sendHelp(Main pl, CommandSender sender) {
        sendHelp(pl, sender, 0);
    }
    public static void sendHelp(Main pl, CommandSender sender, int page) {
        sender.sendMessage(ChatColor.GOLD + "/----------------HELP----------------/");
        for(int i = 0 + page*AMOUNT_PER_PAGE;i<(page+1)*AMOUNT_PER_PAGE && i < pl.getCommandList().size();i++) {
            Main.PlayerData pd = pl.getPlayerList().get(sender.getName());
            if(pd != null && pd.getPermissions().hasPermission(pl.getCommandList().get(i).getCommandName()))
                sender.sendMessage(ChatColor.GOLD + "/" + pl.getCommandList().get(i).getCommandName() + " - " + pl.getCommandList().get(i).getDescription() + "/");
        }
        sender.sendMessage(ChatColor.GOLD + "/------PAGE " + (page+1) + " / " + (int)Math.ceil(pl.getCommandList().size()/AMOUNT_PER_PAGE) + "------/");
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        int page = 0;
        try{
            page = Integer.parseInt(args[0]);
        }catch(Exception e){
        }
        sendHelp((Main)m_plugin, sender, page);
        return true;
    }
}
