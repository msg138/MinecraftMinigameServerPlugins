package com.hiveofthoughts.mc.listeners.global.world;

import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Michael George on 3/21/2018.
 */
public class DisableRainListener implements Listener{
    private Main m_main;

    public DisableRainListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onRainStart(WeatherChangeEvent t_event){
        if(ServerInfo.getInstance().getRainDisabled() && t_event.toWeatherState()){
            t_event.setCancelled(true);
        }
    }
}
