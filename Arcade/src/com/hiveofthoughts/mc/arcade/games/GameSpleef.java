package com.hiveofthoughts.mc.arcade.games;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import com.hiveofthoughts.mc.arcade.game.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GameSpleef extends BaseGame {

    public GameSpleef(){
        super();
        m_gameName = "Spleef";
        m_gameDescription = "Destroy blocks under others to be the last man standing.";

        m_gameScoreboard = "&gt\n \n&team\n \nKit: &kit\n \nPlayers left: &ppc";

        m_allowViolence = true;

        m_teams.add(new TeamDefault());
        m_kits.add(new KitDefault());

        m_defaultGameMode = GameMode.SURVIVAL;

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
        // Kill the rest of the players online.
        for(Player t_p : Bukkit.getOnlinePlayers()){
            t_p.setHealth(0);
        }
    }

    @Override
    public void onCleanup() {

    }

    @Override
    public void onRun() {

    }

    @Override
    public boolean checkWinCondition() {
        return getPlayerStatusCount(PlayerStatus.PLAYING) <= 1;
    }

    @Override
    public void finalCheckScore() {
        List<PlayerInfo > t_players = getAllPlayerInfo();
        if(t_players.size() > 0){
            t_players.get(t_players.size() - 1).setScore(" For surviving the longest.");
        }
        if(t_players.size() > 1){
            t_players.get(t_players.size() - 2).setScore(" For almost surviving the longest.");
        }
        if(t_players.size() > 2){
            t_players.get(t_players.size() - 3).setScore(" For not nearly surviving the longest.");
        }
    }

    @EventHandler
    public void onBurn(EntityDamageEvent a_event) {
        if(!(a_event.getEntity() instanceof Player))
            return;
        if(a_event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)){
            a_event.setCancelled(true);
            ((Player) a_event.getEntity()).setHealth(0);
            getPlayerInfo((Player) a_event.getEntity()).setStatus(PlayerStatus.SPECTATOR);
            addLoss((Player)a_event.getEntity());
        }

        if(a_event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            a_event.setCancelled(false);
        } else
            a_event.setCancelled(true);
    }

    @EventHandler
    public void snowballHit(ProjectileHitEvent a_event) {
        if(a_event.getHitBlock() != null) {
            if(a_event.getHitBlock().getType().equals(Material.SNOW_BLOCK)) {
                a_event.getHitBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent a_event) {
        if(!(a_event.getEntity() instanceof Player))
            return;
        getPlayerInfo((Player) a_event.getEntity()).setStatus(PlayerStatus.SPECTATOR);
        addLoss((Player)a_event.getEntity());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent a_event) {
        if (a_event.getClickedBlock() == null && !a_event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;
        if (a_event.getClickedBlock().getType().equals(Material.SNOW_BLOCK)) {
            a_event.getClickedBlock().breakNaturally(new ItemStack(Material.OBSIDIAN));
            a_event.getPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 3));
        } else
            a_event.setCancelled(true);
    }

    @EventHandler
    public void onDigBlock(BlockBreakEvent a_event){
        if(a_event.getBlock() == null || (a_event.getPlayer() == null))
            return;
        if(a_event.getBlock().getType().equals(Material.SNOW_BLOCK)) {
            a_event.getBlock().setType(Material.AIR);
        } else
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
