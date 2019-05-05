package com.hiveofthoughts.mc.arcade.games;

import com.hiveofthoughts.mc.arcade.game.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameSpleef extends BaseGame {

    public GameSpleef(){
        super();
        m_gameName = "Spleef";
        m_gameDescription = "Destroy blocks under others to be the last man standing.";

        m_teams.add(new TeamDefault());
        m_kits.add(new KitDefault());

        // Setup a map
        {
            GameMap t_map = new GameMap();
            t_map.setWorldName("spleef");

            t_map.setSetting("spawn", new Location(Bukkit.getWorld(t_map.getWorldName()), 2035.5, 9, 503.5));
            t_map.setSetting("lowest", 3.0);

            this.m_map = t_map;
        }
    }

    @Override
    public void onSetup() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onCleanup() {

    }

    @Override
    public void onRun() {

    }

    @Override
    public boolean checkWinCondition() {
        return getPlayerStatusCount(PlayerStatus.SPECTATOR) > 0;
    }

    @Override
    public void finalCheckScore() {
    }

    @EventHandler
    public void onBurn(EntityDamageEvent a_event) {
        if(!(a_event.getEntity() instanceof Player))
            return;
        if(a_event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)){
            a_event.setCancelled(true);

            getPlayerInfo((Player) a_event.getEntity()).setStatus(PlayerStatus.SPECTATOR);
            m_lossOrder.add((Player) a_event.getEntity());
        }
    }

    @EventHandler
    public void onDig(PlayerInteractEvent a_event){
        if(a_event.getClickedBlock().getType().equals(Material.SNOW_BLOCK))
            a_event.getClickedBlock().breakNaturally(new ItemStack(Material.OBSIDIAN));
        else
            a_event.setCancelled(true);
    }

    private class TeamDefault extends Team{

        public TeamDefault(){
            super();
            m_teamName = "Free For All";
            m_teamDescription = "Free For All";

            m_givenItems.add(new ItemStack(Material.BLAZE_POWDER, 3));
        }
    }

    private class KitDefault extends Kit {

        public KitDefault(){
            super();
            m_kitName = "Shoveler";
            m_kitDescription = "You have a shovel. Happy?";

            m_givenItems.add(new ItemStack(Material.GOLDEN_SHOVEL, 1));
        }
    }
}
