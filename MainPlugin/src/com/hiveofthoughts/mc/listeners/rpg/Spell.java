package com.hiveofthoughts.mc.listeners.rpg;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class Spell {
	
	public static void applySpell(String effect, int amp, LivingEntity e)
	{
		switch(effect)
		{
			case "HEAL":
				e.setHealth(e.getHealth()+amp);
				break;
			case "HARM":
				e.setHealth(e.getHealth()-amp);
				break;
			case "SPAWNITEM":
				e.getWorld().dropItem(e.getLocation(), new ItemStack(Material.getMaterial(amp)));
				break;
		}
	}
	
	public static ItemStack createSpell(String spellname)
	{
		String info = "";
		info+="C:"+Data.abilConfig.getString(spellname+".class").replace(',',' ');
		info+="COST:"+Data.abilConfig.getString(spellname+".level");
		if(Data.abilConfig.getString(spellname+".destroy").equals("true"))
			info+="ONE TIME USE";
		return Data.createBook("SPELL:"+spellname,Data.abilConfig.getString(spellname+".description"),info,spellname,"Unknown");
	}
}
