package com.hiveofthoughts.mc.rpgold;

import org.bukkit.entity.EntityType;

public class Dialog {
	public static final String[] dDialog = {"Hello.","How may I help you?"};
	public static final String[] spiderDialog = {"Tssk.","Tssssssk"};
	public static final String[] creeperDialog = {"Sssss?","SSSSSSSSSSS!"};
	public static final String[] zombieDialog = {"Bragggghhhh","Uroooooaghgh"};
	
	
	public static String getDialog(EntityType e)
	{
		switch(e){
		case VILLAGER:
			return dDialog[(int)Math.floor(dDialog.length*Math.random())];
		case SPIDER:
			return spiderDialog[(int)Math.floor(spiderDialog.length*Math.random())];
		case CREEPER:
			return spiderDialog[(int)Math.floor(creeperDialog.length*Math.random())];
		case ZOMBIE:
			return spiderDialog[(int)Math.floor(spiderDialog.length*Math.random())];
		}
		return "";
		
	}
}
