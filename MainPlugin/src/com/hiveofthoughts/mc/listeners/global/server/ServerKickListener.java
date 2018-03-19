package com.hiveofthoughts.mc.listeners.global.server;

import com.hiveofthoughts.mc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerKickListener implements Listener {
    private Main m_main;

    public ServerKickListener(Main a_main){
        m_main = a_main;
    }

    @EventHandler
    public onServerKick(Server)

}
