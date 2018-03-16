package com.hiveofthoughts.mc.listeners.rpg;

import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldListener implements Listener {
	public WorldListener(Main jp)
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, jp);
	}
	@EventHandler
	public void stopweatherChange(WeatherChangeEvent evt)
	{
		if(!Data.config.getBoolean("reality.options.allow-rain")&&evt.toWeatherState())
			evt.setCancelled(true);
	}
	@EventHandler
	public void stopTreeFromGrowingWithBonemeal(StructureGrowEvent evt)
	{
		//Bukkit.getServer().broadcastMessage(evt.getPlayer().getName()+" is growing a something.");
	}
	@EventHandler
	public void stopPlantFromGrowing(BlockGrowEvent evt)
	{
		//evt.setCancelled(true);
		//Bukkit.getServer().broadcastMessage("Not going to grow mate.");
	}
}
