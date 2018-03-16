package com.hiveofthoughts.mc.listeners.global.player;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Michael George on 11/18/2017.
 */
public class PlayerJoinQuitListener implements Listener{
    private Main m_main;

    public PlayerJoinQuitListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        m_main.getPlayerList().put(event.getPlayer().getName(), new Main.PlayerData(event.getPlayer()));
        Bukkit.getLogger().info("Player: " + event.getPlayer().getName() + " logged in with permission: " + m_main.getPlayerList().get(event.getPlayer().getName()).getPermissions().getName());
        if(Config.DisplayLoginMessage){
            event.setJoinMessage(Config.LoginMessage.replaceAll("\\$P", event.getPlayer().getName()));
        }else
            event.setJoinMessage("");
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(Config.DisplayLogoutMessage){
            event.setQuitMessage(Config.LogoutMessage.replaceAll("\\$P", event.getPlayer().getName()));
        }else
            event.setQuitMessage("");
    }
}
