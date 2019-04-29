package com.hiveofthoughts.mc.listeners.global.world;

import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DisableMobsListener implements Listener {
    private Main m_main;

    public DisableMobsListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent t_event){
        t_event.setCancelled(true);
    }


}
