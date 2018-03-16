package com.hiveofthoughts.mc.listeners.rpg;

import java.util.UUID;

import org.bukkit.block.Block;

/*
 * TODO::
 * - Global, Admin, Local, Yell Chats
 * - Forge (Delayed)
 * - Better money system
 *   - allow physical money (Bedrock)
 * - // DONE(Pending) Towny sort of thing.
 * - 
 * 
 */


public class PlayerData {
	public UUID uuid;
	
	public String lastTown;
	
	public boolean flying = false;
	
	public int mana=0,maxmana=0;
	
	public int manaRegen = 0;
	
	public int idleSince = 0;
	public boolean afk = false;
	
	public int mineHits = 0;
	public Block mineMaterial;
	public String currentskill;
	
	public int getCurrentLevelSkill()
	{
		return Data.getUserLevel(uuid, currentskill);
	}
	
	public PlayerData(UUID a)
	{
		uuid = a;
		lastTown = "";
		flying = false;
	}
	
	public void updateThings()
	{
	}
}
