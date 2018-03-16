package com.hiveofthoughts.mc.listeners.rpg;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	public String name;
	public String description;
	public int damage;
	
	public Item(String n, String d, int dm)
	{
		name = n;
		description = d;
		damage = dm;
	}
	
	public static ItemStack setLore(ItemStack i, String n, String d)
	{
		ItemMeta im = i.getItemMeta();
		String[] lores = d.split("\n");
		ArrayList<String> loresList = new ArrayList<>();
		for(String s : lores)
			loresList.add(s);
		im.setLore(loresList);
		im.setDisplayName(n);
		i.setItemMeta(im);
		
		return i;
	}
}
