package com.hiveofthoughts.mc.listeners.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Style;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleCollisionEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


public class PlayerListener implements Listener{
	public PlayerListener(Main jp)
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, jp);
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player player = evt.getPlayer();
		
		if(Data.playerExists(player.getUniqueId())){
			Data.players.add(Data.loadPlayer(player));
			if(Data.getPlayerData(player.getUniqueId()).flying)
			{
				player.setAllowFlight(true);
				player.setFlying(true);
			}
			
			evt.setJoinMessage(Data.getChatColor(player)+Data.getFullName(player.getUniqueId())+ChatColor.RESET+" "+Data.joinMessage);
			player.setCustomName(Data.getFirstName(player.getUniqueId()));
			player.setCustomNameVisible(true);
			player.setDisplayName(Data.getFullName(player.getUniqueId()));
			player.setPlayerListName("("+player.getName()+") "+Data.getFirstName(player.getUniqueId()));
			
		}else{
			evt.setJoinMessage(ChatColor.GREEN+player.getName()+ChatColor.RESET+" "+Data.joinMessage);
			Data.addNewPlayer(player, player.getName(), "", 0);
			Data.players.add(Data.loadPlayer(player));
		}
		
		//player.sendMessage(Data.realityTag+"Welcome back to The Reality Minecraft Server.");
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent evt)
	{
		Player player = evt.getPlayer();
		
		Data.savePlayer(Data.getPlayerData(player.getUniqueId()));
		
		Data.players.remove(Data.getPlayerData(player.getUniqueId()));
		
		if(Data.playerExists(player.getUniqueId()))
			evt.setQuitMessage(Data.getChatColor(player)+Data.getFullName(player.getUniqueId())+ChatColor.RESET+" "+Data.quitMessage);
		else
			evt.setQuitMessage(ChatColor.GREEN+player.getName()+ChatColor.RESET+" "+Data.quitMessage);
	}
	@EventHandler
	public void onDropItem(PlayerDropItemEvent evt)
	{
		Player p = evt.getPlayer();
		
		
		
		//evt.setCancelled(true);
	}
	@EventHandler
	public void onBanPlace(BlockPlaceEvent evt)
	{
		if(!evt.getPlayer().isOp() && (evt.getBlock().getType().equals(Material.BEDROCK)||evt.getBlock().getType().equals(Material.COAL_ORE)||evt.getBlock().getType().equals(Material.EMERALD_ORE)||evt.getBlock().getType().equals(Material.DIAMOND_ORE)||evt.getBlock().getType().equals(Material.GOLD_ORE)||evt.getBlock().getType().equals(Material.LAPIS_ORE)||evt.getBlock().getType().equals(Material.IRON_ORE)))
			evt.setCancelled(true);
	}
	@EventHandler
	public void onCraftItem(CraftItemEvent evt)
	{
		List<HumanEntity> ps = evt.getViewers();
		String crafters = "";
		for(HumanEntity p : ps)
		{
			crafters+=p.getName()+" ";
		}
		//Get recipe
		CraftingInventory ci = evt.getInventory();
		
		if(ci.contains(Material.WOOD) || ci.contains(Material.LOG) || ci.contains(Material.LOG_2))
		{
			if(ci.getTitle().equalsIgnoreCase("saw mill"))
			{
				ci.getResult().setAmount(ci.getResult().getAmount()*2);
			}
		}
		
		// Check if item is not permitted like swords.
		/*if(item.getType().equals(Material.IRON_SWORD) || item.getType().equals(Material.IRON_SPADE)||item.getType().equals(Material.IRON_AXE)||item.getType().equals(Material.IRON_HOE) || 
				item.getType().equals(Material.DIAMOND_SWORD) || item.getType().equals(Material.DIAMOND_SPADE)||item.getType().equals(Material.DIAMOND_AXE)||item.getType().equals(Material.DIAMOND_HOE)||	
				item.getType().equals(Material.GOLD_SWORD) || item.getType().equals(Material.GOLD_SPADE)||item.getType().equals(Material.GOLD_AXE)||item.getType().equals(Material.GOLD_HOE))
		*/
		boolean hasMolten = false;
		String type = "none";
		ItemStack[] items = ci.getContents();
		for(ItemStack item : items){
			if(item == null)
				continue;
			ItemMeta im = item.getItemMeta();
			if(im != null && im.getLore()!=null){
				List<String> l = im.getLore();
				for(String lore : l){
					if(lore.indexOf("MOLTEN") != -1)
						hasMolten = true;
					if(lore.indexOf("RECIPE") != -1)
					{
						type = im.getLore().get(1);
					}
				}
			}
		}
		if(((ci.contains(Material.IRON_INGOT) || ci.contains(Material.DIAMOND) || ci.contains(Material.GOLD_INGOT) || ci.contains(Material.COBBLESTONE) || ci.contains(Material.WOOD)) &&
				hasMolten==false && ci.contains(Material.LAVA_BUCKET) == false) || (hasMolten == true && !ci.contains(Material.BOOK))){
			evt.setCancelled(true);
		}else if(hasMolten == true && ci.contains(Material.BOOK) && type.equals("none") == false)
		{
			String mat = "";
			if(ci.contains(Material.IRON_INGOT))
				mat = "IRON";
			if(ci.contains(Material.DIAMOND))
				mat = "DIAM";
			if(ci.contains(Material.GOLD_INGOT))
				mat = "GOLD";
			if(ci.contains(Material.COBBLESTONE))
				mat = "COBB";
			if(ci.contains(Material.WOOD))
				mat = "WOOD";
			ItemStack retItem = null;
			switch(type)
			{
				case "SWORD":
					switch(mat){
					case "IRON":
						retItem = new ItemStack(Material.IRON_SWORD);
						break;
					case "GOLD":
						retItem = new ItemStack(Material.GOLD_SWORD);
						break;
					case "COBB":
						retItem = new ItemStack(Material.STONE_SWORD);
						break;
					case "WOOD":
						retItem = new ItemStack(Material.WOOD_SWORD);
						break;
					case "DIAM":
						retItem = new ItemStack(Material.DIAMOND_SWORD);
						break;
					}
					break;
			}
			ItemMeta im = retItem.getItemMeta();
			im.setLore(Arrays.asList("Forged by "+crafters));
			im.setDisplayName("Sword");
			retItem.setItemMeta(im);
			
			
			ci.setResult(retItem);
		}
	}
	@EventHandler
	public void onSignClick(PlayerInteractEvent evt)
	{
		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(evt.getClickedBlock().getType().equals(Material.SIGN_POST)||evt.getClickedBlock().getType().equals(Material.SIGN)||evt.getClickedBlock().getType().equals(Material.WALL_SIGN)||evt.getClickedBlock().getType().equals(Material.EMERALD_ORE))
			{
				Location l = evt.getClickedBlock().getLocation();
				l.setY(l.getY()-3);
				Block bl = l.getBlock();
				Sign sign = null;
				if(bl.getType().equals(Material.SIGN)||bl.getType().equals(Material.WALL_SIGN)||bl.getType().equals(Material.SIGN_POST))
				{
					sign = (Sign)bl.getState();
				}else if(evt.getClickedBlock().getType().equals(Material.SIGN)||evt.getClickedBlock().getType().equals(Material.WALL_SIGN)||evt.getClickedBlock().getType().equals(Material.SIGN_POST)){
					sign = (Sign)evt.getClickedBlock().getState();
				}
				l = evt.getClickedBlock().getLocation();
				String a = sign.getLine(0);
				String b = sign.getLine(1);
				String c = sign.getLine(2);
				String d = sign.getLine(3);
				
				switch(a)
				{
					case "[HEAL]":///b=cost c=healing
						if(Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(c)<0)
							evt.getPlayer().sendMessage(Data.realityTag+"You do not have enough money to purchase a heal.");
						else if(evt.getPlayer().getHealth()>=evt.getPlayer().getMaxHealth())
						{
							evt.getPlayer().sendMessage(Data.realityTag+"You already have maximum health. No need to buy a heal.");
						}
						else
						{
							Data.userConfig.set("users."+evt.getPlayer().getName()+".money", Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(c));
							if(evt.getPlayer().getHealth()+Integer.parseInt(b)>evt.getPlayer().getMaxHealth())
								evt.getPlayer().setHealth(evt.getPlayer().getMaxHealth());
							else
								evt.getPlayer().setHealth(evt.getPlayer().getHealth()+Integer.parseInt(b));
							evt.getPlayer().sendMessage(Data.realityTag+"You spent"+c+" for "+b+" health.");
						}
						break;
					case "[ELEVATE]":
						Location loc = Data.getWarp(b);
						if(l!=null)
						{try{
							Location l2=evt.getPlayer().getLocation();
							l2.setX(loc.getX());
							l2.setY(loc.getY());
							l2.setZ(loc.getZ());
							
							
							evt.getPlayer().teleport(l2);
							}catch(Exception e)
							{
								e.printStackTrace();
							}
							evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,15,6));
						}else
						{
							evt.getPlayer().sendMessage(Data.realityTag+"Invalid warp sign.");
						}
						break;
					case "[PURCHASE CAR]":
						if(!b.equals(""))
						{
							///Charge for the car.
							if(Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(b)<0)
							{
								evt.getPlayer().sendMessage(Data.realityTag+"You do not have enough money to purchase a car.");
								return;
							}else
								Data.userConfig.set("users."+evt.getPlayer().getName()+".money",(int)(Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(b)));
						}
						
						ItemStack carItem = new ItemStack(Material.NAME_TAG,1);
						ItemMeta im = carItem.getItemMeta();
						im.setDisplayName("car");
						if(!c.equals("")){
							ArrayList<String> lore = new ArrayList<>();
							lore.add(c);
							im.setLore(lore);
						}
						carItem.setItemMeta(im);
						evt.getPlayer().getInventory().addItem(carItem);
						evt.getPlayer().sendMessage(Data.realityTag + "You have purchased a car");
						break;
					case "[BUY HORSE]":
						if(Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(b)<0)
							Data.userConfig.set("users."+evt.getPlayer().getName()+".money",(int)(Data.userConfig.getInt("users."+evt.getPlayer().getName()+".money")-Integer.parseInt(b)));
						
						Horse e =(Horse)evt.getPlayer().getWorld().spawnEntity(l, EntityType.HORSE);
						Horse.Color co = Horse.Color.CREAMY;
						
						switch(c){
						case "CHESTNUT":
							co = Horse.Color.CHESTNUT;
							break;
						case "GRAY":
							co = Horse.Color.GRAY;
							break;

						case "BROWN":
							co = Horse.Color.BROWN;
							break;
						case "BLACK":
							co = Horse.Color.BLACK;
							break;
						case "WHITE":
							co = Horse.Color.WHITE;
							break;
						case "DARK_BROWN":
							co = Horse.Color.DARK_BROWN;
							break;
						}
						e.setColor(co);
						e.setTamed(true);
						e.setStyle(Style.NONE);
						e.setCustomName(evt.getPlayer().getName()+"'s Horse");
						
						
						
						e.getInventory().setSaddle(new ItemStack(Material.SADDLE));;
						
						break;
				}
			}
		}
	}
	@EventHandler
	public void onWarpClick(PlayerInteractEvent evt)
	{
		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(evt.getClickedBlock().getType().equals(Material.IRON_DOOR_BLOCK))
			{
				Location loc = evt.getClickedBlock().getLocation();
				loc.setY(loc.getY()-2);
				Block b = null;
				if(loc.getBlock().getType().equals(Material.SIGN_POST)||loc.getBlock().getType().equals(Material.WALL_SIGN)||loc.getBlock().getType().equals(Material.SIGN))
				{
					b = loc.getBlock();
				}else
				{
					loc.setY(loc.getY()-1);
					if(loc.getBlock().getType().equals(Material.SIGN_POST)||loc.getBlock().getType().equals(Material.WALL_SIGN)||loc.getBlock().getType().equals(Material.SIGN))
					{
						b = loc.getBlock();
					}
				}
				if(b==null)
				{
					evt.getPlayer().sendMessage(Data.realityTag+"Just a regular door. Nothing special.");
				}else
				{
					Sign s = (Sign)b.getState();
					String a = s.getLine(0);
					String bb = s.getLine(1);
					String c = s.getLine(2);
					String d = s.getLine(3);
					
					if(!(a.equals("[WARP]")||a.equals("[DOOR]")) && !(a.equals("WARP") || a.equals("DOOR")))
						return;
					boolean unlock = true;
					if(!c.equals(""))
					{
						unlock = false;
						if(evt.getPlayer().getItemInHand().hasItemMeta()){
							unlock=false;
							ItemMeta im = evt.getPlayer().getItemInHand().getItemMeta();
							if(!im.hasLore())
								unlock=false;
							else if(evt.getPlayer().getItemInHand().getItemMeta().getLore().contains(c))
								unlock=true;
						}
						else
						{
							evt.getPlayer().sendMessage(Data.realityTag+"This door is locked. Come back with key: "+c);
						}
					}
					
					if(unlock)
					{
						if(a.equals("[WARP]"))
							evt.getPlayer().sendMessage(Data.realityTag+"Warping...");
						else if(a.equals("[DOOR]"))
							evt.getPlayer().sendMessage(Data.realityTag+"Entering...");
						Location l = Data.getWarp(bb);
						if(l!=null)
						{try{
							Location l2=evt.getPlayer().getLocation();
							l2.setX(l.getX());
							l2.setY(l.getY());
							l2.setZ(l.getZ());
							
							
							evt.getPlayer().teleport(l2);
							}catch(Exception e)
							{
								e.printStackTrace();
							}
							evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,15,6));
						}else
						{
							evt.getPlayer().sendMessage(Data.realityTag+"Invalid warp sign.");
						}
					}
				}
			}
			if(evt.getClickedBlock().getType().equals(Material.SIGN)||evt.getClickedBlock().getType().equals(Material.SIGN_POST)||evt.getClickedBlock().getType().equals(Material.WALL_SIGN))
			{
				Sign s = (Sign)evt.getClickedBlock().getState();
				String a = s.getLine(0);
				String bb = s.getLine(1);
				String c = s.getLine(2);
				String d = s.getLine(3);
				
				if(!(a.equals("[WARP]")||a.equals("[DOOR]")))
					return;
				boolean unlock = true;
				if(!c.equals(""))
				{
					unlock = false;
					if(evt.getPlayer().getItemInHand().hasItemMeta()){
						unlock=false;
						ItemMeta im = evt.getPlayer().getItemInHand().getItemMeta();
						if(!im.hasLore())
							unlock=false;
						else if(evt.getPlayer().getItemInHand().getItemMeta().getLore().contains(c))
							unlock=true;
					}
					else
					{
						evt.getPlayer().sendMessage(Data.realityTag+"This door is locked. Come back with key: "+c);
					}
				}
				
				if(unlock)
				{
					if(a.equals("[WARP]"))
						evt.getPlayer().sendMessage(Data.realityTag+"Warping...");
					else if(a.equals("[DOOR]"))
						evt.getPlayer().sendMessage(Data.realityTag+"Entering...");
					Location l = Data.getWarp(bb);
					if(l!=null)
					{try{
						Location l2=evt.getPlayer().getLocation();
						l2.setX(l.getX());
						l2.setY(l.getY());
						l2.setZ(l.getZ());
						
						
						evt.getPlayer().teleport(l2);
						}catch(Exception e)
						{
							e.printStackTrace();
						}evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,15,6));
				
					}else
					{
						evt.getPlayer().sendMessage(Data.realityTag+"Invalid warp sign.");
					}
				}
			}
		}
	}
	@EventHandler
	public void potionSplash(PotionSplashEvent evt)
	{
		//if(evt.getPotion().getShooter() instanceof Player)
			//evt.setCancelled(true);
	}
	@EventHandler
	public void dmg(EntityDamageEvent evt)
	{
		/*Entity e = evt.getEntity();
		if(e instanceof Player)
		{
			Player player = (Player)e;
			if(player.getHealth()-evt.getDamage()<=0)
			{
				player.setHealth(1);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1500,2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,1500,3));
				evt.setCancelled(true);
			}
		}*/
	}/*
	@EventHandler
	public void respawn(final PlayerRespawnEvent evt)
	{
		int respawnsend = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable(){
			public void run(){
				try{
					Thread.sleep(500);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				Location l = Data.getWarp(Data.userConfig.getString("users."+evt.getPlayer().getName()+".lasthospital"));
				if(l!=null)
				{
					try{
						Location l2=evt.getPlayer().getLocation();
						l2.setX(l.getX());
						l2.setY(l.getY());
						l2.setZ(l.getZ());
						
						
						evt.getPlayer().teleport(l2);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}else
					evt.getPlayer().sendMessage(Data.realityTag+"There was an error sending you to the hospital.");
			}
		});
		evt.getPlayer().sendMessage(Data.realityTag+"You have died and have recovered at the most recent hospital you have visited. Be more careful.");
	}*/
	@EventHandler
	public void die(PlayerDeathEvent evt)
	{
		evt.setDeathMessage(evt.getEntity().getDisplayName()+" has died.");
		if(evt.getEntity().getLevel()>=30)
		{
			evt.setKeepInventory(true);
			//evt.setNewLevel(evt.getEntity().getLevel()-10);
		}
		//evt.setDroppedExp((int) (.3*evt.getEntity().getExp()));
		evt.setDroppedExp(0);
	}

	@EventHandler
	public void formatChat(PlayerChatEvent evt)
	{
		UUID username = evt.getPlayer().getUniqueId();
		if(Data.playerExists(evt.getPlayer().getUniqueId())){
			if(evt.getMessage().toLowerCase().contains("fuck")||evt.getMessage().toLowerCase().contains("shit")||evt.getMessage().toLowerCase().contains("ass")||evt.getMessage().toLowerCase().contains("nigger")||evt.getMessage().toLowerCase().contains("nigga")||evt.getMessage().toLowerCase().contains("penis")||evt.getMessage().toLowerCase().contains("vagina")||evt.getMessage().toLowerCase().contains("bitch")||evt.getMessage().toLowerCase().contains("cunt")||evt.getMessage().toLowerCase().contains("pussy")||evt.getMessage().toLowerCase().contains("douche")){
				evt.getPlayer().sendMessage(Data.realityTag+"No curse words please. This is warning: "+(Data.userConfig.getInt("users."+username+".warning")+1));
				Data.userConfig.set("users."+username+".warning", (Data.userConfig.getInt("users."+username+".warning")+1));
				handleWarning(evt.getPlayer());
				evt.setCancelled(true);
			}
			evt.setFormat(Data.getChatColor(evt.getPlayer())+""+Data.getFullName(username)+ChatColor.WHITE+" "+evt.getMessage());
		}else{
			evt.getPlayer().sendMessage(Data.realityTag+"You must create a profile before you can chat. Not hard. Just type '/profile create <firstname> <lastname>' Names are not permanent.");
			evt.setCancelled(true);
		}
	}
	
	public void handleWarning(Player p)
	{
		if(Data.userConfig.getInt("users."+p.getName()+".warning")>=4)
			p.kickPlayer("You have recieved at least 3 warnings. If you reach 7 warnings, you will be temp banned. If you reach 10 warnings you will be banned with chance of appeal. If you reach 20 you will be banned permanently no pleas.");
		if(Data.userConfig.getInt("users."+p.getName()+".warning")>=7)
		{
			///Temp ban the player for 1 day.
		}
	}
	@EventHandler
	public void farmClick(PlayerInteractEvent evt)
	{
		Player p = evt.getPlayer();
		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block b = evt.getClickedBlock();
			//b.setData((byte) (b.getData()+1));
		}
	}
	@EventHandler
	public void handleForgeOpen(PlayerInteractEvent evt)
	{
		
		Data.getPlayerData(evt.getPlayer().getUniqueId()).idleSince = 0;
		

		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getClickedBlock().getType() == Material.ANVIL)
		{
			Block b = evt.getClickedBlock().getRelative(0, -3, 0);
			if(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
			{
				Sign s = (Sign)b.getState();
				if(s.getLine(1).equalsIgnoreCase("[anvil]"))
				{
					evt.setCancelled(true);
					
					InventoryView i = evt.getPlayer().openInventory(Bukkit.createInventory(evt.getPlayer(), 27,"Anvil"));
					ItemStack is = new ItemStack(Material.WOOL,1);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("CRAFT");
					is.setItemMeta(im);
					i.getTopInventory().addItem(is);
					
					evt.getPlayer().updateInventory();
				}
			}
		}
		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getClickedBlock().getType() == Material.WORKBENCH)
		{
			Block b = evt.getClickedBlock().getRelative(0, -3, 0);
			if(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
			{
				Sign s = (Sign)b.getState();
				if(s.getLine(1).equalsIgnoreCase("[sawmill]"))
				{
					evt.setCancelled(true);
					CraftPlayer cp = (CraftPlayer)evt.getPlayer();
					Inventory i = Bukkit.createInventory(null, InventoryType.WORKBENCH, "Saw Mill");
					
					cp.openInventory(i);
				}
			}
		}
		
		if(evt.hasBlock() && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (evt.getClickedBlock().getType().equals(Material.WALL_SIGN) || evt.getClickedBlock().getType().equals(Material.SIGN_POST) || evt.getClickedBlock().getType().equals(Material.SIGN)))
		{
			Block b = evt.getClickedBlock();
			Sign s = (Sign)b.getState();
			
			Player p = evt.getPlayer();
			
			if(s.getLine(1).equalsIgnoreCase("[forge]")){
				Inventory i = Bukkit.createInventory(evt.getPlayer(), 9, "Forge");
				{
					ItemStack is = new ItemStack(Material.IRON_INGOT);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("Melt Ingot");
					is.setItemMeta(im);
					i.setItem(0, is);
					
				}
				evt.getPlayer().openInventory(i);
			}/*else if(s.getLine(1).equalsIgnoreCase("[smelter]"))
			{
				if(Data.forgePlayers.contains(p.getName()) == false){
					FurnaceInventory i = Bukkit.createInventory(p, InventoryType.FURNACE,"Smelter");
					i.setFuel(new ItemStack(Material.LAVA_BUCKET,99));
					Data.forgePlayers.add(p.getName());
					Data.forgeViews.add(new Smelter(i));
					p.openInventory(i);	
				}
				else{
					p.openInventory(Data.forgeViews.get(Data.forgePlayers.indexOf(p.getName())).getInventory());
				}
			}*/
			
		}
	}
	@EventHandler
	public void handleForgeClose(InventoryCloseEvent evt)
	{
		
		Data.getPlayerData(evt.getPlayer().getUniqueId()).idleSince = 0;
		
		if(evt.getInventory().getTitle().equalsIgnoreCase("Anvil"))
		{
			for(ItemStack i : evt.getInventory().getContents())
			{
				if(i == null || i.getType().equals(Material.AIR))
					continue;
				if(i.hasItemMeta() && i.getItemMeta().getDisplayName().equalsIgnoreCase("CRAFT"))
				{
					
				}else
					evt.getPlayer().getWorld().dropItem(evt.getPlayer().getLocation(),i);
			}
		}
		if(evt.getInventory().getTitle().equalsIgnoreCase("smelter"))
		{
			if((evt.getInventory().getItem(0)!=null && evt.getInventory().getItem(0).getType() != Material.AIR) || (evt.getInventory().getItem(2) != null &&evt.getInventory().getItem(2).getType() != Material.AIR))
			{
				if(Data.forgePlayers.contains(evt.getPlayer()) == false)
				{
					Data.forgeViews.add(new Smelter((FurnaceInventory)evt.getInventory()));
					Data.forgePlayers.add(evt.getPlayer().getName());
				}
			}else if(Data.forgeViews.contains(evt.getInventory()) == true)
			{
				int i = Data.forgeViews.indexOf(evt.getInventory());
				Data.forgeViews.remove(i);
				Data.forgePlayers.remove(i);
			}
		}
	}
	
	@EventHandler
	public void handleForge(InventoryClickEvent evt)
	{
		
		Data.getPlayerData(evt.getWhoClicked().getUniqueId()).idleSince = 0;
			ItemStack i = evt.getCurrentItem();
		if(i == null || i.getType()== Material.AIR)
			return;
			if(i.hasItemMeta() && i.getItemMeta().getDisplayName().equalsIgnoreCase("CRAFT"))
			{
				ItemStack recipe = null;
				//First find the recipe.
				for(ItemStack item : evt.getInventory().getContents())
				{
					if(item == null || item.getType().equals(Material.AIR))
						continue;
					if(item.hasItemMeta() && item.getItemMeta().getDisplayName().indexOf("RECIPE: ") != -1)
					{
						recipe = item;
						break;
					}
				}
				if(recipe != null)
				{
					boolean crafted = true;
					
					boolean destroy = false;
					
					ItemStack result = new ItemStack(Material.getMaterial(recipe.getItemMeta().getDisplayName().substring(recipe.getItemMeta().getDisplayName().indexOf("RECIPE: ")+8)));
					
					for(String l : recipe.getItemMeta().getLore())
					{
						if(l.startsWith("AMOUNT: "))
						{
							result.setAmount(Integer.parseInt(l.substring(8)));
						}
						if(l.startsWith("TOOL: "))
						{
							if(!evt.getInventory().getName().equalsIgnoreCase(l.substring(6)))
							{
								crafted = false;
							}
						}
						if(l.startsWith("NAME: "))
						{
							ItemMeta im = result.getItemMeta();
							im.setDisplayName(l.substring(6));
							result.setItemMeta(im);
						}
						if(l.startsWith("LORE: "))
						{
							ItemMeta im = result.getItemMeta();
							ArrayList<String> lores = new ArrayList<>();
							
							for(String s : l.substring(6).split("&"))
								lores.add(s);
							im.setLore(lores);
							result.setItemMeta(im);
						}
						if(l.startsWith("ONEUSE"))
							destroy = true;
						if(l.startsWith("REQ: "))
						{
							String[] reqs = l.substring(5).split(",");
							for(String r : reqs)
							{
								boolean found = false;
								for(ItemStack item : evt.getInventory().getContents())
								{
									if(item == null || item.getType().equals(Material.AIR))
										continue;
									if(item.getType().toString().equals(r))
									{
										found = true;
										break;
									}
								}
								if(found == false)
								{
									crafted = false;
									break;
								}
							}
							
							if(crafted)
							{
								for(String r : reqs)
								{
									boolean found = false;
									for(ItemStack item : evt.getInventory().getContents())
									{
										if(item == null || item.getType().equals(Material.AIR))
											continue;
										if(item.getType().toString().equals(r))
										{
											if(item.getAmount()<=1)
												evt.getInventory().removeItem(item);
											else {
												item.setAmount(item.getAmount()-1);
											}
											break;
										}
									}
								}
								if(destroy)
								{
									evt.getInventory().removeItem(recipe);
								}
							}
						}
					}
					if(crafted)
					{
						evt.setCurrentItem(result);

						ItemStack is = new ItemStack(Material.WOOL,1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName("CRAFT");
						is.setItemMeta(im);
						evt.getInventory().addItem(is);
						
						if(evt.getWhoClicked() instanceof Player)
						{
							((Player)evt.getWhoClicked()).updateInventory();
						}
						
					}else
						evt.setCancelled(true);
				}else
				{
					evt.setCancelled(true);
				}
			}
		
		
		/*
		if(evt.getInventory().getTitle().equalsIgnoreCase("smelter"))
		{
			ItemStack b = evt.getCurrentItem();
			
			if(b == null || (b.getType() == Material.REDSTONE_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.COAL_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.LAPIS_ORE))
			{
				evt.setCancelled(false);
				if(b != null)
				b.setDurability((short)100);
			}
			else
				evt.setCancelled(true);
				
			/*Player p = (Player) evt.getWhoClicked();
			
			if(b.getType() == Material.REDSTONE_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.COAL_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.LAPIS_ORE)
			{
				if(b != evt.getInventory().getItem(0) && b != evt.getInventory().getItem(1) && b != evt.getInventory().getItem(2)){
					ItemStack item = new ItemStack(b.getType(),1);
					item.setDurability((short) 100);
					evt.getInventory().setItem(0, item);
					if(b.getAmount() > 1){
						b.setAmount(b.getAmount() -1);
					}else{
						b.setType(Material.AIR);
					}
				}else if(b == evt.getInventory().getItem(0))
				{
					evt.getWhoClicked().getInventory().addItem(b);
					evt.getInventory().setItem(0, new ItemStack(Material.AIR,1));
				}else if(b == evt.getInventory().getItem(2))
				{
					evt.getWhoClicked().getInventory().addItem(b);
					evt.getInventory().setItem(2, new ItemStack(Material.AIR,1));
				}
			}*
		}else
			evt.setCancelled(false);*/
	}
	@EventHandler
	public void handleCastAbility(PlayerInteractEvent evt)
	{
		if((evt.getAction().equals(Action.LEFT_CLICK_AIR) || evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) && (evt.getPlayer().getItemInHand().getType() == Material.BOOK || evt.getPlayer().getItemInHand().getType() == Material.BOOK_AND_QUILL || evt.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK))
		{
			Player p = evt.getPlayer();
			ItemStack item = p.getItemInHand();
			BookMeta im = (BookMeta)item.getItemMeta();
				
		    if(item.getType() == Material.WRITTEN_BOOK && im.getTitle().contains("SPELL"))
			{
				//Bukkit.broadcastMessage(Data.userConfig.getString("users."+p.getName()+".firstname")+" Uba Luba Suba Duba Dun");
				String spellname = im.getLore().get(0);
				boolean casted = false;
				
				if(Data.abilConfig.getInt(spellname+".level")<= Data.getUserLevel(p.getUniqueId(), "magic") && Data.abilConfig.getString(spellname+".class").indexOf((String)Data.getUserData(p.getUniqueId(),"job")) != -1 && Data.getPlayerData(p.getUniqueId()).mana >= Data.abilConfig.getInt(spellname+".cost"))
					for(int i=0;i<Data.abilConfig.getInt(spellname+".effects.amount");i++)
					{
						String effectName = Data.abilConfig.getString(spellname+".effects."+i+".effecttype");
						int amp = Data.abilConfig.getInt(spellname+".effects."+i+".amplifier");
						
						if(Data.abilConfig.getString(spellname+".effects."+i+".target").equalsIgnoreCase("self"))
						{
							casted = true;
							Spell.applySpell(effectName, amp, p);
						}else if(Data.abilConfig.getString(spellname+".effects."+i+".target").equalsIgnoreCase("entity"))
						{
							casted = true;
							LivingEntity en = Data.getEntityLook(p,Data.abilConfig.getInt(spellname+".range"));
							if(en != null)
								Spell.applySpell(effectName, amp, en);
							else{
								casted = false;
								p.sendMessage("No target found.");
							}
						}
						
						
					}
				else if(Data.abilConfig.getInt(spellname+".level")> Data.getUserLevel(p.getUniqueId(), "magic"))
					p.sendMessage("Magic level not high enough.");
				else if(Data.abilConfig.getString(spellname+".class").indexOf((String)Data.getUserData(p.getUniqueId(), "job")) == -1)
					p.sendMessage("Not the right class.");
				else
					p.sendMessage("Not enough mana.");
				if(casted)
				{
					p.sendMessage("Successfully casted the spell.");
					Data.getPlayerData(p.getUniqueId()).mana -= Data.abilConfig.getInt(spellname+".cost");
					if(Data.abilConfig.getString(spellname+".destroy").equals("true"))
					{
						if(item.getAmount() <= 1)
							p.setItemInHand(new ItemStack(Material.AIR,1));
						else
							item.setAmount(item.getAmount()-1);
						p.sendMessage("The scroll crumbles.");
					}
					
					Data.addUserExperience(p.getUniqueId(), "magic", Data.abilConfig.getInt(spellname+".exp"));
				}else
				{
					p.sendMessage("You failed to cast the spell.");
				}
			}
		}
	}
	/*@EventHandler
	public void onTownyChangePlot(PlayerChangePlotEvent evt)
	{
		Player p = evt.getPlayer();
		TownBlock tb;
		try {
			tb = evt.getTo().getTownBlock();
			if(tb.hasTown()){
				String townName = tb.getTown().getFormattedName();
				if(Data.getPlayerData(p.getUniqueId()).lastTown.equalsIgnoreCase(townName) == false){
					Title t = new Title(townName.replace('_', ' ')," ");
					t.fadeIn = 50;
					t.fadeOut = 30;
					t.send(p);
					Data.getPlayerData(p.getUniqueId()).lastTown = townName;
				}
			}else if(Data.getPlayerData(p.getUniqueId()).lastTown.equals("wild") == false)
			{
				Title t = new Title(main.wilderTitle.replace('_', ' ')," ");
				t.fadeIn = 50;
				t.fadeOut = 30;
				t.send(p);
				Data.getPlayerData(p.getUniqueId()).lastTown = "wild";
			}
		} catch (NotRegisteredException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(Data.getPlayerData(p.getUniqueId()).lastTown.equals("wild") == false)
			{
				Title t = new Title(main.wilderTitle.replace('_', ' ')," ");
				t.fadeIn = 50;
				t.fadeOut = 30;
				t.send(p);
				Data.getPlayerData(p.getUniqueId()).lastTown = "wild";
			}
		}
	}*/
	@EventHandler
	public void checkIfAbilityActivated(PlayerMoveEvent evt)
	{
		
		boolean swimming = false;
		//Get player object
		///System.out.println("checking");
		Player p = evt.getPlayer();
		Data.getPlayerData(p.getUniqueId()).idleSince = 0;
		// Check if there is an race buff event that involves touching a block
		if(Data.raceConfig.contains("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.onblocktouch"))
		{
			///System.out.println("checking1");
			// Now get the block type
			if(Data.raceConfig.getString("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.onblocktouch.blockids").equals("WATER"))
			{
				///System.out.println("checking2");
				// Checkk if we touch water.
				Location loc = p.getLocation();
				World w = loc.getWorld();
				if(w.getBlockAt(loc).getType().equals(Material.WATER) || w.getBlockAt(loc).getType().equals(Material.STATIONARY_WATER))
				{
					//p.sendMessage("you feel a surge of energy as you touch the water.");
					
					//PotionEffectType pet = PotionEffectType.getByName(Data.raceConfig.getString("races."+Data.userConfig.get("users."+p.getName()+".raceName")+".eventbuff.onblocktouch.effecttype"));
					//PotionEffect pot = new PotionEffect(pet,24,Data.raceConfig.getInt("races."+Data.userConfig.get("users."+p.getName()+".raceName")+".eventbuff.onblocktouch.amplifier"));
					//p.addPotionEffect(pot);
					p.setAllowFlight(true);
					p.setFlying(true);
					swimming = true;
				}else if(p.isFlying() && Data.getPlayerData(p.getUniqueId()).flying == false)
					{
						p.setFlying(false);
						p.setAllowFlight(false);
					}
				
			}
		}
		if(p.isFlying() && Data.getPlayerData(p.getUniqueId()).lastTown.equals("wild") && swimming == false && !p.isOp())
		{
			p.setFlying(false);
		}
	}
}
