package com.hiveofthoughts.mc.listeners.rpg;

import com.hiveofthoughts.mc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;


public class EntityListener implements Listener {
	
	public static double HEALTH_PER_LEVEL = 2.0;
	
	public EntityListener(Main jp)
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, jp);
	}
	@EventHandler
	public void spawnMob(CreatureSpawnEvent evt)
	{
		LivingEntity e = evt.getEntity();
		boolean townFound = false;
		for(String spawnz:Data.townConfig.getString("towns.list").split(","))
		{
			//else
				//System.out.println("IN TOWN");
			String[] args = Data.townConfig.getString("towns."+spawnz+".args").split(",");
			if(args.length<4 && !evt.getSpawnReason().equals(SpawnReason.SPAWNER_EGG))
			{
				//System.out.println("IN TOWN NO ARGS");
				evt.setCancelled(true);
				return;
			}
			boolean foundCreature = false;
			String[] cre = (args[2]).split("\\.");
			//System.out.println(cre.length+" "+args[2]+"Type: "+e.getType().getName());
			for(String c : cre)
			{
				if(e.getType().equals(EntityType.fromName(c)))
				{
					//System.out.println("FOUND CREATURE");
					foundCreature = true;
				}//else
					//System.out.println("IS NOT "+c);
			}
			if(!foundCreature && cre.length>0 && !evt.getSpawnReason().equals(SpawnReason.SPAWNER_EGG))
			{
				//System.out.println("NOT FOUND");
				evt.setCancelled(true);
				return;
			}
			
			
			int level = 0;
			level = (int)(Integer.parseInt(args[0]));

			e.setMaxHealth(level * HEALTH_PER_LEVEL);
			e.setHealth(level * HEALTH_PER_LEVEL);
			
			
			e.getEquipment().clear();
			
			String name = args[3];
			if(name.indexOf("$") != -1)
			{
				name = name.substring(0,name.indexOf("$")) + e.getType().getName()+name.substring(name.indexOf("$")+1);
			}
			name = name.replaceAll("_", " ");
			
			e.setCustomName(ChatColor.BLUE+"[Lvl "+level+"] "+ChatColor.RESET + name);
			e.setCustomNameVisible(true);
			
			
			//e.setMetadata("level", new FixedMetadataValue(main.getPlugin(null), level));
			
			townFound = true;
		}
		if(Data.townConfig.getString("towns.list").split(",").length<=0 || townFound == false)
		{
			evt.setCancelled(true);
		}
		/*if(.getType().equals(EntityType.CHICKEN))
			e.setCancelled(true);
		else{
			e.getEntity().setCustomName(ChatColor.BLUE+"[Lvl 1] "+ChatColor.WHITE+e.getEntityType().getName());
			e.getEntity().setCustomNameVisible(true);
		}*/
	}
	@EventHandler
	public void preventFireKillingMobs(EntityCombustEvent evt)
	{
		Entity e = evt.getEntity();
		if((e instanceof Arrow))
		{
			
		}
		else
			evt.setCancelled(true);
	}
	@EventHandler
	public void entityHealth(EntityRegainHealthEvent evt)
	{
		Entity e = evt.getEntity();
		if(e instanceof Player)
		{
			//evt.setCancelled(true);
		}
	}
	@EventHandler
	public void entityDeathEvent(EntityDeathEvent evt)
	{
		evt.setDroppedExp(0);
		evt.getDrops().clear();
		evt.getDrops().addAll(MobDrop.getDrops(evt.getEntity().getCustomName(), evt.getEntityType()));
	}
	
	//handle the damage for entities
	@EventHandler
	public void entityDamageHandle(EntityDamageByEntityEvent evt)
	{
		if(evt.getDamager() instanceof Player)
		{
			int x = 0;
			Player p = (Player)evt.getDamager();
			ItemStack i = p.getItemInHand();
			if(i.getType()==Material.WOOD_SWORD || i.getType()==Material.STONE_SWORD || i.getType()==Material.IRON_SWORD||i.getType()==Material.GOLD_SWORD||i.getType()==Material.DIAMOND_SWORD)
			{
				x = 1;
				if(i.hasItemMeta())
				{
					ItemMeta im = i.getItemMeta();
					if(im.hasLore())
					{
						x = Integer.parseInt(im.getLore().get(im.getLore().size()-1));
					}
				}
				evt.setDamage(x);
				p.sendMessage("You deal "+x+" damage.");
			}
		}
	}
	
	//Handle the mobs talking
	@EventHandler
	public void entityTalkOnTarget(EntityTargetLivingEntityEvent evt)
	{
		if(evt.getTarget() instanceof Player && evt.getEntity() instanceof org.bukkit.entity.LivingEntity)
		{
			evt.getTarget().sendMessage(ChatColor.GOLD + evt.getEntity().getType().toString()+ChatColor.RESET+" "+Dialog.getDialog(evt.getEntity().getType()));
		}
	}
	@EventHandler
	public void entityTalkOnInteract(PlayerInteractEntityEvent evt)
	{
		if(evt.getRightClicked() instanceof LivingEntity)
		{
			evt.getPlayer().sendMessage(ChatColor.GOLD + evt.getRightClicked().getCustomName()+ChatColor.RESET+" "+Dialog.getDialog(evt.getRightClicked().getType()));
			if(evt.getRightClicked() instanceof Villager)
				evt.setCancelled(true);
		}
	}
}
