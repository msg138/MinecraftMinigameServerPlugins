package com.hiveofthoughts.mc.listeners.rpg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.*;

public class Vehicle {
	public Minecart minecart;
	public Player player;
	public static Minecart entityType;
	
	public boolean running;
	
	public double speed;
	
	public Vehicle(Minecart mc,Player p)
	{
		minecart = mc;
		player = p;
		running = false;
		
		speed = 0.25;
		minecart.setMaxSpeed(speed);
	}
	
	public Vehicle(Minecart mc, Player p, ItemStack i)
	{
		minecart = mc;
		player = p;
		running = false;
		
		ItemMeta im = i.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.addAll(im.getLore());
		speed = Double.parseDouble(lore.get(0));
		minecart.setMaxSpeed(speed);
	}
}
