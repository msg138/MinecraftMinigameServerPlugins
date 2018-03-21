package com.hiveofthoughts.mc.listeners.global.chat;

import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Michael George on 3/20/2018.
 */
public class PlayerChatHandler implements Listener {
    private Main m_main;

    // Default constructor.
    public PlayerChatHandler(){
    }

    public PlayerChatHandler(Main pl){
        super();
        m_main = pl;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try{
            String dn = m_main.getPlayerData(event.getPlayer()).getPermissions().getPrefix() + " " +
                    event.getPlayer().getName() + " " + m_main.getPlayerData(event.getPlayer()).getPermissions().getSuffix();
            event.setFormat(dn + " > %2$s");
        }catch(Exception e){
            e.printStackTrace();
            Bukkit.getLogger().info("MESSAGE : " + event.getMessage() + ",,," + event.getPlayer().getDisplayName());
            event.setCancelled(true);
        }
        event.setMessage(ChatColor.WHITE + event.getMessage());
    }
}
