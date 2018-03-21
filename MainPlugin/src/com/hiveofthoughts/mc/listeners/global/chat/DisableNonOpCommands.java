package com.hiveofthoughts.mc.listeners.global.chat;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Michael George on 3/15/2018.
 */
public class DisableNonOpCommands implements Listener {
    private Main m_main;

    // Default constructor.
    public DisableNonOpCommands(){
    }

    public DisableNonOpCommands(Main pl){
        super();
        m_main = pl;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event)
    {
            /*
            String label = event.getMessage().split(" ")[0].substring(1);
            boolean found = false;
            for(CommandTemplate c : m_main.getCommandList()){
                if(label.equals(c.getCommandName())){
                    found = true;
                    break;
                }
            }
            if(!found){
                if(!event.getPlayer().isOp()){
                    event.getPlayer().sendMessage(m_main.getPrefix() + m_main.getUnknownMessage());
                    Bukkit.getLogger().info("User: " + event.getPlayer().getName() + " tried to use command '" + label + "'");
                    event.setCancelled(true);
                }
            }*/
        Main.PlayerData pd = m_main.getPlayerData(event.getPlayer());
        if(pd != null){
            String[] oargs = event.getMessage().split(" ");
            String[] oldargs = new String[oargs.length-1];
            String label = (oargs[0].substring(1));
            if(Config.DisabledCommands.contains(label))
                event.setCancelled(true);
            for(int i=1;i<oargs.length;i++){
                oldargs[i-1] = oargs[i];
            }
            if(!pd.getPermissions().hasPermission(label, oldargs)) {
                event.getPlayer().sendMessage(Config.Prefix + Config.MessageUnknown);
                Bukkit.getLogger().info("User: " + event.getPlayer().getName() + " tried to use command '" + label + "'");
                event.setCancelled(true);
            }
        }else{
            Bukkit.getLogger().info("PlayerData does not exist for: " + event.getPlayer().getName() + ". Creating now.");
            m_main.getPlayerList().put(event.getPlayer().getName(), new Main.PlayerData(event.getPlayer()));
            event.getPlayer().sendMessage(Config.Prefix + Config.MessageErrorUnknown);
            event.setCancelled(true);
        }
    }
}