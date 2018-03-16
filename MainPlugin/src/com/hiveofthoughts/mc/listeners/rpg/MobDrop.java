package com.hiveofthoughts.mc.listeners.rpg;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobDrop {
	public static ArrayList<MobDrop> mDrops = new ArrayList<>();
	
	public String mobName;
	public ItemStack drop;
	
	public EntityType mobType;
	
	public double chance = 0;
	
	public MobDrop(String mobName,int ml,EntityType mt, ItemStack drop, double chance)
	{
		this.mobName = ChatColor.BLUE+"[Lvl "+ml+"] "+ChatColor.RESET+mobName;
		this.drop = drop;
		this.mobType = mt;
		this.chance =chance;
	}
	
	public static ArrayList<ItemStack> getDrops(String mn, EntityType mt)
	{
		ArrayList<ItemStack> items = new ArrayList<>();
		
		for(MobDrop m : mDrops)
		{
			if(m.mobName.equals(mn) && m.mobType == mt){
				int isGet = (int)Math.floor(Math.random()*100);
				if(isGet<= m.chance)
					items.add(m.drop);
			}
		}
		
		return items;
	}
}
