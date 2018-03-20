package com.hiveofthoughts.mc.rpgold;
/*
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.msg138.NickelLilac.BlockListener;import me.msg138.NickelLilac.Data;import net.minecraft.server.v1_8_R1.EntitySkeleton;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.google.common.collect.Iterables;
import com.palmergames.bukkit.towny.object.TownBlock;



/**
 * 
 * @author Michael George
 */

public class main {
	/*private EntityListener entityListener;
	private WorldListener worldListener;
	private PlayerListener playerListener;
	private BlockListener blockListener;
	
	public static final boolean debug = true;
	
	
	private ScoreboardManager sbm;
	
	
	public static int uPlayerCheckTown = -1, uVehicleDirection=-1, uRacialBuff = -1, uScoreboard = -1, uManaRegen = -1, uMineralSpawn = -1, uForge = -1,
			uMonsterSpawn = -1, uAfkKick;
	
	public static final String wilderTitle = "Wilderness";
	
	public void onEnable(){
		getLogger().info("Loading plugin data...");
		///Set join and quit messages.
		Data.joinMessage = "has entered the world.";
		Data.quitMessage = "has abandoned the world.";
		
		//load data
		try {
			Data.loadFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Set game update loop
		uPlayerCheckTown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updatePlayerCheckTown();
			}
		}, 0, 5);
		uRacialBuff = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateRacialBuffs();
			}
		}, 0, 1);
		uScoreboard = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateScoreboard();
			}
		}, 0, 30);
		uManaRegen = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateManaRegen();
			}
		}, 0, 100);
		uMineralSpawn = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateMineralSpawn();
			}
		}, 0, 1);
		uForge = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateForge();
			}
		}, 0, 10);
		uMonsterSpawn = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateMonsterSpawn(Bukkit.getWorld("rpmakestuff"));
			}
		}, 0, 1);
		uAfkKick = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				updateAfkKick();
			}
		}, 0, 1);
		

		playerListener = new PlayerListener(this);
		worldListener = new WorldListener(this);
		entityListener = new EntityListener(this);
		blockListener = new BlockListener(this);
		
		// Add players that were already logged in.
		for(World w : Bukkit.getWorlds())
		{
			for(Player p : w.getPlayers())
			{
				Data.players.add(new PlayerData(p.getUniqueId()));
			}
		}
		
		sbm = Bukkit.getScoreboardManager();

		MobDrop.mDrops.add(new MobDrop("The Forbidden",10,EntityType.ZOMBIE,Spell.createSpell("spawnfish"),100));
		MobDrop.mDrops.add(new MobDrop("The Forbidden",10,EntityType.ZOMBIE,Item.setLore(new ItemStack(Material.IRON_SWORD,1), "Swift Edge", "Wielded by only the worthiest of warriors.\n6"),100));
		MobDrop.mDrops.add(new MobDrop("The Forbidden",11,EntityType.PIG_ZOMBIE,Item.setLore(new ItemStack(Material.IRON_SWORD,1), "Swift Edge", "Wielded by only the worthiest of warriors.\n6"),100));
		MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: PAPER", "TOOL: ANVIL\nONEUSE\nNAME: Legendary Paper\nLORE: Used by kings.\nAMOUNT: 2\nREQ: DIAMOND"),100));
		MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: DIAMOND_SWORD", "TOOL: ANVIL\nONEUSE\nNAME: "+ChatColor.GOLD+"King Slayer\nLORE: Killed many kings.&999\nAMOUNT: 1\nREQ: DIAMOND,DIAMOND"),100));
		MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: WRITTEN_BOOK", "TOOL: ANVIL\nNAME: SPELL:spawnfish\nLORE: spawnfish\nAMOUNT: 1\nREQ: PAPER,PAPER"),100));
	}
	public void onDisable(){
		getLogger().info("Plugin being disabled.");
		Bukkit.getServer().getScheduler().cancelTask(uPlayerCheckTown);
		Bukkit.getServer().getScheduler().cancelTask(uVehicleDirection);
		Bukkit.getServer().getScheduler().cancelTask(uRacialBuff);
		
		// kick players.
		for(PlayerData pd : Data.players)
		{
			Player p = Bukkit.getServer().getPlayer(pd.uuid);
			//p.kickPlayer("Server closed. Try again soon.");
		}
		for(int i=0;i<Data.mineralBlocks.size();i++){
			Data.mineralBlocks.get(i).setType(Data.mineralType.get(i));
		}
		
		try {
			Data.saveData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateMonsterSpawn(World w)
	{
		if(w == null || Data.townConfig.getString("towns.list").isEmpty() || Data.townConfig.getString("towns.list").split(",").length<=0)
			return;
		for(String ss:Data.townConfig.getString("towns.list").split(","))
		{

			Location loc1 = new Location(w, 0, 0, 0);
			loc1.setX(Data.townConfig.getDouble("towns."+ss+".x1"));
			loc1.setY(Data.townConfig.getDouble("towns."+ss+".y1"));
			loc1.setZ(Data.townConfig.getDouble("towns."+ss+".z1"));
			String[] args = Data.townConfig.getString("towns."+ss+".args").split(",");
			String[] cre = (args[2]).split("\\.");
			
			int entsInChunk = 0;
			for(Entity e : loc1.getChunk().getEntities())
			{
				if(args[2].indexOf(e.getType().toString()) != -1)
					entsInChunk++;
				
			}
			if(args.length<4)
			{
				//System.out.println("IN TOWN NO ARGS");
				continue;
			}
			if(entsInChunk >= Integer.parseInt(args[1]))
			{
				continue;
			}else
			{
				//Spawn in a random entity from the wanted list.

				loc1.setY(loc1.getY()+2);
				EntityType n = EntityType.fromName(cre[(int)Math.floor(cre.length*Math.random())]);
				if(n == null)
					n = EntityType.CREEPER;
				LivingEntity ll = (LivingEntity)w.spawnEntity(loc1,n);
				
				if(args.length>8)// nine for equipment
				{
					if(args[4].equals("0") == false)
					{
						ll.getEquipment().setHelmet(new ItemStack(Material.getMaterial(Integer.parseInt(args[4]))));
						ll.getEquipment().setHelmetDropChance(0);
					}if(args[5].equals("0") == false)
					{
						ll.getEquipment().setChestplate(new ItemStack(Material.getMaterial(Integer.parseInt(args[5]))));
						ll.getEquipment().setChestplateDropChance(0);
					}if(args[6].equals("0") == false)
					{
						ll.getEquipment().setLeggings(new ItemStack(Material.getMaterial(Integer.parseInt(args[6]))));
						ll.getEquipment().setLeggingsDropChance(0);
					}if(args[7].equals("0") == false)
					{
						ll.getEquipment().setBoots(new ItemStack(Material.getMaterial(Integer.parseInt(args[7]))));
						ll.getEquipment().setBootsDropChance(0);
					}if(args[8].equals("0") == false)
					{
						ll.getEquipment().setItemInHand(new ItemStack(Material.getMaterial(Integer.parseInt(args[8]))));
						ll.getEquipment().setItemInHandDropChance(0);
					}
				}
				
				if(ll != null)
					return;
				
			}
		}
	}
	
	public void updateForge()
	{
	}
	
	public void updateAfkKick()
	{
		for(PlayerData pd : Data.players)
		{
			pd.idleSince++;
			
			if(pd.idleSince>=Data.KICK_AFTER_SEC*24)
			{
				Bukkit.getPlayer(pd.uuid).kickPlayer("Kicked for being afk.");
			}else if(pd.idleSince>=Data.AFK_AFTER_SEC*24 && pd.afk == false)
			{
				pd.afk = true;
				Bukkit.broadcastMessage(pd.uuid+" is afk.");
			}else if(pd.idleSince<Data.AFK_AFTER_SEC*24)
				pd.afk = false;
		}
	}
	
	public void updateMineralSpawn()
	{
		for(int i=0;i<Data.mineralBlocks.size();i++)
		{
			Data.mineralTimes.set(i, Data.mineralTimes.get(i)+1);
			
			boolean isDone = false;
			int curTime = Data.mineralTimes.get(i);
			switch(Data.mineralType.get(i))
			{
				case COAL_ORE:
					if(curTime >= Data.MINE_COAL_RESPAWN_TIME)
						isDone = true;
				break;
				case DIAMOND_ORE:
					if(curTime >= Data.MINE_DIAMOND_RESPAWN_TIME)
						isDone = true;
				break;
				case IRON_ORE:
					if(curTime >= Data.MINE_IRON_RESPAWN_TIME)
						isDone = true;
				break;
				case EMERALD_ORE:
					if(curTime >= Data.MINE_EMERALD_RESPAWN_TIME)
						isDone = true;
				break;
				case LAPIS_ORE:
					if(curTime >= Data.MINE_LAPIS_RESPAWN_TIME)
						isDone = true;
				break;
				case GOLD_ORE:
					if(curTime >= Data.MINE_GOLD_RESPAWN_TIME)
						isDone = true;
				break;
				case REDSTONE_ORE:
					if(curTime >= Data.MINE_REDSTONE_RESPAWN_TIME)
						isDone = true;
				break;
				case GLOWING_REDSTONE_ORE:
					if(curTime >= Data.MINE_REDSTONE_RESPAWN_TIME)
						isDone = true;
				break;
			}
			
			if(isDone)
			{
				Data.mineralBlocks.get(i).setType(Data.mineralType.get(i));
				Data.mineralBlocks.remove(i);
				Data.mineralTimes.remove(i);
				Data.mineralType.remove(i);
				i--;
			}
		}
	}
	
	public void updateManaRegen()
	{
		for(PlayerData pd : Data.players)
		{
			Player p = Bukkit.getServer().getPlayer(pd.uuid);
			pd.mana+= pd.manaRegen;
			if(pd.mana>pd.maxmana)
			{
				pd.mana = pd.maxmana;
			}
		}
	}
	
	public void updateScoreboard()
	{
		for(int i=0;i<Data.players.size();i++)
		{
			Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);
			Scoreboard s = sbm.getNewScoreboard();
			Team t = s.registerNewTeam(p.getName());
			t.addPlayer(p);
			t.setDisplayName(p.getName());
			// The race of the player.
			Objective score = s.registerNewObjective("scoreboard", "dummy");
			score.setDisplayName(p.getName());
			score.setDisplaySlot(DisplaySlot.SIDEBAR);
			score.getScore((String)Data.getUserData(p.getUniqueId(),"raceName")).setScore(-2);
			// The last town of tah player.
			score.getScore(Data.players.get(i).lastTown).setScore(-1);
			// The mana of tah player.
			score.getScore("Mana").setScore(Data.players.get(i).mana);
			
			
			p.setScoreboard(s);
		}
	}
	
	public void updateRacialBuffs()
	{
		for(int i=0;i<Data.players.size();i++)
		{
			Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);
			p.setExp(0);
			
			if(Data.players.get(i).mineMaterial != null)
				p.setLevel(Data.getHitsRequired(Data.players.get(i).mineMaterial.getType(),Data.players.get(i).getCurrentLevelSkill()) - Data.players.get(i).mineHits);
			else
				p.setLevel(100);
			
			if(Data.playerExists(p.getUniqueId()) == false || Data.raceConfig.contains("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff.amount") == false)
			{
				continue;
			}
			int l = Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff.amount");
			for(int j=0;j<l;j++)
			{
				PotionEffectType pet = PotionEffectType.getByName(Data.raceConfig.getString("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff."+j+".effecttype"));
				PotionEffect pot = new PotionEffect(pet,1000,Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff."+j+".amplifier"));
				p.addPotionEffect(pot);
			}
			if(Data.raceConfig.contains("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch"))
			{
				///System.out.println("checking1");
				// Now get the block type
					if(p.isSneaking())
					{
						//p.sendMessage("you feel a surge of energy as you touch the water.");
						
						PotionEffectType pet = PotionEffectType.getByName(Data.raceConfig.getString("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch.effecttype"));
						PotionEffect pot = new PotionEffect(pet,30,Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch.amplifier"));
						p.addPotionEffect(pot);
						
						
					}
					
			}
		
		}
	}
	
	public void updatePlayerCheckTown()
	{
		for(int i=0;i<Data.players.size();i++)
		{
			Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);
			
			/**
			
		}
	}
	
	public void updateVehicleDirection()
	{
		for(int i=0;i<Data.vehicles.size();i++)
		{
			if(Data.vehicles.get(i).running==false)
				continue;
			Minecart mc = Data.vehicles.get(i).minecart;
			//Block b = Data.vehicles.get(i).player.getTargetBlock(null, 100);
			Vector vel = Data.getLookAt(Data.vehicles.get(i).player);
			vel.multiply(Data.vehicles.get(i).speed);
			vel.setY(0);
			mc.setVelocity(vel);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args)
	{
		String cmdName = cmd.getName().toLowerCase();
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			/*if(Data.hasPermission(player.getName(), cmdName, args) == false)
			{
				player.sendMessage(Data.realityTag+"You do not have permission for that command.");
				return false;
			}*
			if(cmdName.equals("mo") && args.length>1 && args[0].equals("get"))
			{
				if(com.iConomy.iConomy.hasAccount(player.getName()) && com.iConomy.iConomy.getAccount(player.getName()).getHoldings().balance()>=Integer.parseInt(args[1]))
				{
					com.iConomy.iConomy.getAccount(player.getName()).getHoldings().add(-Integer.parseInt(args[1]));
					player.getInventory().addItem(new ItemStack(Material.BEDROCK,Integer.parseInt(args[1])));
					player.sendMessage("you got some physical monies.");
				}
			}
			if(cmdName.equals("mo") && args.length>1 && args[0].equals("deposit"))
			{
				if(com.iConomy.iConomy.hasAccount(player.getName()) && com.iConomy.iConomy.getAccount(player.getName()).getHoldings().balance()>=Integer.parseInt(args[1]))
				{
					
					if(player.getInventory().getItemInHand().getType().equals(Material.BEDROCK)){
						int amount = player.getInventory().getItemInHand().getAmount();
						com.iConomy.iConomy.getAccount(player.getName()).getHoldings().add(amount);
						player.getInventory().setItemInHand(new ItemStack(Material.AIR));
						player.sendMessage("you return some physical monies.");
					}
				}
			}
			if(cmdName.equals("about")){
				player.sendMessage(Data.realityTag+"This is a plugin programmed by Michael George(msg138) thought of by Sean Lewis(catguy54), Colyn Savage(ElectricBeastly) and Jaimie(foxtailx)");
				return true;
			}if(cmdName.equals("get"))
			{
				if(args.length < 1)
				{
					sender.sendMessage(Data.realityTag+"Not enough arguments.");
					return false;
				}
				int amount = 1;
				if(args.length >= 2)
					amount = Integer.parseInt(args[1]);
				PlayerInventory i = player.getInventory();
				i.addItem(new ItemStack(Material.getMaterial(Integer.parseInt(args[0])),amount));
				return true;
			}
			if(cmdName.equals("broadcast"))
			{
				if(args.length < 1)
				{
					sender.sendMessage(Data.realityTag+"Not enough arguments.");
					return false;
				}
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN+""+ChatColor.ITALIC+"[Broadcast] "+ChatColor.WHITE+StringUtils.join(args," "));
				return true;
				}
			if(cmdName.equals("suicide"))
			{
				player.setFireTicks(1);
				player.setHealth(0);
				return true;
				}
			if(cmdName.equals("reloadreality") && player.isOp())
			{
				Bukkit.getServer().broadcastMessage(Data.realityTag+"Reloading reality plugin...");
				boolean save = true;
				if(args.length>0)
				{
					if(args[0].equals("false"))
						save = false;
				}
					try{
						if(save)
							Data.saveData();
						Data.loadFiles(this);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					Bukkit.getServer().broadcastMessage(Data.realityTag+"Reloaded reality plugin.");
					return true;
					}
			if(cmdName.equals("profile"))
			{
				if(args.length < 1)
				{
					sender.sendMessage(Data.realityTag+(String)(Data.userConfig.getString("users."+player.getName())));
					return false;
				}
				if(args[0].equals("list"))
				{
					String list = "Your profiles:\n";
					int am=0;
					am = (int)Integer.parseInt(Data.userConfig.getString("users."+player.getName()+".cc"));
					
					for(int i=0;i<am;i++)
					{
						if(Data.userConfig.contains("users."+player.getUniqueId()+"."+i))
						{
							list+=" "+i+" "+Data.userConfig.getString("users."+player.getUniqueId()+"."+i+".firstname")+" "+Data.userConfig.getString("users."+player.getUniqueId()+"."+i+".lastname") + " \n";
						}
					}
					list += "Out of "+Data.userConfig.getString("users."+player.getName()+".cc");
					sender.sendMessage(Data.realityTag+list);
					return true;
				}
				if(args[0].equals("set"))
				{
					if(args.length<2)
					{
						sender.sendMessage(Data.realityTag+"Requires the profile number.");
						return true;
					}
					if(Data.userConfig.contains("users."+player.getName()+"."+args[1]))
					{

						Data.players.set(Data.players.indexOf(Data.getPlayerData(player.getUniqueId())),Data.loadPlayer(player,Integer.parseInt(args[1])));
						player.setPlayerListName("("+player.getName()+") "+Data.getFirstName(player.getUniqueId()));
						return true;
					}else
					{
						sender.sendMessage(Data.realityTag+"Invalid profile number. Type '/profile list' to see them.");
						return true;
					}
				}
				if(args[0].equals("stats"))
				{
					String endResult = "Stats: \n";
					for(String skill : Data.skills)
					{
						endResult+=skill.toUpperCase() + ": Lvl"+Data.getUserLevel(player.getUniqueId(), skill)+" - ";
						endResult += Data.getUserExperience(player.getUniqueId(),skill)+" / ";
						endResult += Data.getUserLevel(player.getUniqueId(), skill) * Data.EXP_PER_LEVEL + "\n";
					}
					sender.sendMessage(endResult);
					return true;
				}
				if(args[0].equals("setcharactercount") && sender.isOp() && args.length >2)
				{
					Data.userConfig.set("users."+args[1]+".cc", args[2]);
					sender.sendMessage(Data.realityTag+(String)(Data.userConfig.getString("users."+player.getName()+".cc"))+" character slots now for "+args[1]);
					return true;
				}
				if(args[0].equals("create"))
				{
					if(args.length < 3)
					{
						sender.sendMessage(Data.realityTag+"Not enough arguments.");
						return false;
					}
					int slot = 0;
					
					try{
						if(args.length>=4)
							slot = Integer.parseInt(args[3]);
						if(slot!=0 && Data.userConfig.getInt("users."+player.getName()+".cc")<slot)
							slot = 0;
					}catch(NumberFormatException e)
					{}
					Data.addNewPlayer(player, args[1],  args[2], slot);
					player.setCustomName(Data.getFirstName(player.getUniqueId()));
					player.setCustomNameVisible(true);
					player.setPlayerListName("("+player.getName()+") "+Data.getFirstName(player.getUniqueId()));
					Data.players.set(Data.players.indexOf(Data.getPlayerData(player.getUniqueId())),Data.loadPlayer(player));
					return true;
				}
				if(args[0].equals("class"))
				{
					if(args.length<2)
					{
						sender.sendMessage(Data.realityTag+"/profile class set <classname> \n/profile class list");
						return true;
					}else if(args.length<3)
					{
						if(args[1].equals("set"))
						{
							sender.sendMessage(Data.realityTag+"Requires class.");
							return true;
						}
					}
					if(args[1].equals("list"))
					{
						sender.sendMessage(Data.realityTag+"Classes: "+Data.classConfig.getString("list"));
						return true;
					}
					if(args[1].equals("set") && Data.classConfig.getString("list").contains(args[2]+","))
					{
						Object race = Data.getUserData(player.getUniqueId(),".raceName");
						Data.resetPlayer(player);

						Data.setUserData(player.getUniqueId(),"job", args[2]);
						Data.setUserData(player.getUniqueId(),"raceName", race);

						Data.setUserData(player.getUniqueId(),"maxmana", Data.classConfig.get(args[2]+".maxmana"));
						Data.setUserData(player.getUniqueId(),"manaregen", Data.classConfig.get(args[2]+".manaregen"));
						
						Data.players.set(Data.players.indexOf(Data.getPlayerData(player.getUniqueId())),Data.loadPlayer(player));
						return true;
					}
				}
				if(args[0].equals("race"))
				{
					if(args.length<2 || args[1].equals("list"))
					{
						sender.sendMessage(Data.realityTag+"Requires race.");
						sender.sendMessage(Data.realityTag+"Races: "+Data.raceConfig.getString("races.list").replace(',',' '));
						
						return true;
					}else if(Data.userConfig.contains("users."+player.getName()) == false ){
						sender.sendMessage(Data.realityTag+"Must create profile first.");
						return true;
					}else if(Data.raceConfig.contains("races."+args[1]) == false || (args[1].equals("god") && player.isOp() == false))
					{
						sender.sendMessage(Data.realityTag+"Race does not exist.");
						return true;
					}
					//string classn
					Object job = Data.getUserData(player.getUniqueId(),"job");
					
					Data.resetPlayer(player);
					Data.setUserData(player.getUniqueId(),"raceName",args[1]);
					Data.setUserData(player.getUniqueId(),"raceID", Data.raceConfig.get("races."+args[1]+".id"));
					
					
					Data.setUserData(player.getUniqueId(),"job", job);
					
					Data.setUserData(player.getUniqueId(),"maxmana", Data.classConfig.get(job+".maxmana"));
					Data.setUserData(player.getUniqueId(),"manaregen", Data.classConfig.get(job+".manaregen"));
					
					Data.players.set(Data.players.indexOf(Data.getPlayerData(player.getUniqueId())),Data.loadPlayer(player));
					
				}
			}
			if(cmdName.equals("fly"))
			{
				boolean fly = false;
				try{
					fly = Data.getPlayerData(player.getUniqueId()).flying;
					if(fly)
					{
						player.setAllowFlight(false);
					}else
						player.setAllowFlight(true);
					Data.getPlayerData(player.getUniqueId()).flying = !fly;
				}catch(Exception e){ e.printStackTrace(); }
				return true;
			}
			if(cmdName.equals("savereality") && player.isOp())
			{
				Bukkit.getServer().broadcastMessage(Data.realityTag+"Saving reality plugin...");
				try {
					Data.saveData();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Bukkit.getServer().broadcastMessage(Data.realityTag+"Finished saving reality plugin!");
				return true;
			}
			if(cmdName.equals("resetreality") && player.isOp())
			{
				Bukkit.getServer().broadcastMessage(Data.realityTag+"RESETING REALITY!");
				try {
					Data.resetData(this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bukkit.getServer().broadcastMessage(Data.realityTag+"Reality has been reset. Enjoy.");
			}
			if(cmdName.equals("warp"))
			{
				if(args.length<1)
					return false;
				if(args[0].equals("list"))
				{
					String ret="Warps: ";
					for(int i=0;i<Data.warps.size();i++)
					{
						ret+=Data.warps.get(i).name+", ";
					}
					player.sendMessage(Data.realityTag+ret);
				}else if(args[0].equals("delete")){
					if(args.length<2)
					{
						player.sendMessage(Data.realityTag+"Delete command requires a name.");
						return true;
					}
					for(int i=0;i<Data.warps.size();i++)
					{
						if(Data.warps.get(i).name.equals(args[1])){
							Data.warps.remove(i);
							i--;
						}
					}
					Data.warpConfig.set("warps."+args[1], null);
				}	
				else if(args[0].equals("create"))
				{
					if(args.length<2)
					{
						player.sendMessage(Data.realityTag+"Create command requires a name.");
						return true;
					}
					boolean exists = false;
					if(Data.warpConfig.get("warps."+args[1]+".x") != null)
						exists=true;
					Data.warpConfig.set("warps."+args[1]+".x", player.getLocation().getX());
					Data.warpConfig.set("warps."+args[1]+".y", player.getLocation().getY());
					Data.warpConfig.set("warps."+args[1]+".z", player.getLocation().getZ());
					if(exists)
						player.sendMessage(Data.realityTag+"Edited warp");
					else
						player.sendMessage(Data.realityTag+"Created warp");
					
					try {
						if(exists)
						{
							for(int i=0;i<Data.warps.size();i++)
							{
								if(Data.warps.get(i).name.equals(args[1])){
									Data.warps.remove(i);
									i--;
								}
							}
						}
							Data.warps.add(new Warp(args[1],player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else
				{
					if(Data.getWarp(args[0])!=null){
						
						try{
						Location l1=Data.getWarp(args[0]);
						Location l2=player.getLocation();
						l2.setX(l1.getX());
						l2.setY(l1.getY());
						l2.setZ(l1.getZ());
						
						
						player.teleport(l2);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,15,6));
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						
					}else
					{
						player.sendMessage(Data.realityTag+"Invalid warp.");
					}
					return true;
				}
				
			}if(cmdName.equals("ability") && player.isOp())
			{
				if(args.length<2)
				{
					player.sendMessage(Data.realityTag+"Not enough arguments.");
					return true;
				}
				if(args[0].equals("get") && Data.abilConfig.contains(args[1].toLowerCase()))
				{
					player.sendMessage(Data.realityTag+"Giving you the ability book.");
					
					player.getInventory().addItem(Spell.createSpell(args[1]));
					
					return true;
				}
				return false;
			}
			if(cmdName.equals("spawnzone") && player.isOp())
			{
				if(args.length<2 && !(args.length>0 && args[0].equalsIgnoreCase("list")))
				{
					player.sendMessage(Data.realityTag+"set <spawnname> <args>  \n rename <spawnname> <newspawnname>\n remove <spawnname>\n list");
					return true;
				}
				if(args[0].equals("list"))
				{
					player.sendMessage(Data.realityTag+"All spawns: "+Data.townConfig.getString("towns.list"));
					return true;
				}
				if(args[0].equals("set") && args.length>=3)
				{
					if(Data.townConfig.contains("towns."+args[1]) == false)
					{
						Data.townConfig.set("towns.list", Data.townConfig.getString("towns.list")+args[1]+",");
					}
					
					Location loc = player.getLocation();
					
					Data.townConfig.set("towns."+args[1]+".x1", loc.getX());
					Data.townConfig.set("towns."+args[1]+".y1", loc.getY());
					Data.townConfig.set("towns."+args[1]+".z1", loc.getZ());
					
					Data.townConfig.set("towns."+args[1]+".args", args[2]);
					player.sendMessage("Done.");
					return true;
				}else if(args[0].equals("rename"))
				{
					Data.townConfig.set("towns."+args[2],Data.townConfig.get("towns."+args[1]));
					Data.townConfig.set("towns."+args[1], null);

					String list  = Data.townConfig.getString("towns.list");
					list = list.replaceAll(args[1], args[2]);
					Data.townConfig.set("towns.list", list);
				}else if(args[0].equals("remove"))
				{
					Data.townConfig.set("towns."+args[1], "");
					String list  = Data.townConfig.getString("towns.list");
					list = list.replaceAll(args[1]+",", "");
					Data.townConfig.set("towns.list", list);
					player.sendMessage("Done. ");
				}
				return true;
				
			}
			if(cmdName.equals("key") && player.isOp())
			{
				if(args.length<1)
				{
					player.sendMessage(Data.realityTag+"Invalid Syntax. Requires Combination.");
					return true;
				}
				ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
				ItemMeta im = item.getItemMeta();
				ArrayList<String> lores = new ArrayList<>();
				lores.add(args[0]);
				im.setLore(lores);
				im.setDisplayName("KEY");
				item.setItemMeta(im);
				player.getInventory().addItem(item);
			}
		}
		if(cmdName.equals("permission") && sender.isOp())
		{
			if(args.length<3)
			{
				sender.sendMessage(Data.realityTag+"Not enough arguments.");
				return false;
			}
			if(args[0].equals("add"))
			{
				String user = args[1];
				String perm = args[2];
				for(int i=3;i<args.length;i++)
					perm+=" "+args[i];
				
				Data.addPermission(perm,user);
			}
			if(args[0].equals("remove"))
			{
				String user = args[1];
				String perm = args[2];
				for(int i=3;i<args.length;i++)
					perm+=" "+args[i];
				
				Data.removePermission(perm,user);
			}
		}
		return false;
	}
	
	public static boolean inTown(Entity p, String townName)
	{
		Location loc = p.getLocation();
		
		
		
		Location loc1 = new Location(loc.getWorld(), 0, 0, 0);
		loc1.setX(Data.townConfig.getDouble("towns."+townName+".x1"));
		loc1.setY(Data.townConfig.getDouble("towns."+townName+".y1"));
		loc1.setZ(Data.townConfig.getDouble("towns."+townName+".z1"));
		
		Location loc2 = new Location(loc.getWorld(), 0, 0, 0);
		loc2.setX(Data.townConfig.getDouble("towns."+townName+".x2"));
		loc2.setY(Data.townConfig.getDouble("towns."+townName+".y2"));
		loc2.setZ(Data.townConfig.getDouble("towns."+townName+".z2"));
		
		
		if(p.getLocation().getX() == loc1.getX() && p.getLocation().getZ() == loc1.getZ())
			return true;
		else
			return false;
	}
	*/
}
