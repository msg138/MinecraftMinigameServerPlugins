package com.hiveofthoughts.mc.rpg;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.InitClasses;
import com.hiveofthoughts.mc.rpg.calculator.MiningCalculator;
import com.hiveofthoughts.mc.rpg.config.MiningConfig;
import com.hiveofthoughts.mc.rpg.listeners.MiningListener;
import com.hiveofthoughts.mc.server.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Michael George on 3/19/2018.
 */
public class Main extends JavaPlugin{

    @Override
    public void onEnable(){
        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Starting up...");

        TimerContainer.getInstance().addTimer(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                MiningCalculator.updateMineralSpawn();
            }
        }, 0, 5));

        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Started up.");
    }

    @Override
    public void onLoad(){
        Bukkit.getLogger().info("ADDED LISTENERS");

        Config.ServerType = ServerType.RPG;
        // Add the server inclusive listeners here.
        InitClasses.ServerInclusive.put(ServerType.RPG, new Class[]{
                MiningListener.class
        });
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Shutting Down...");
        // TimerContainer.getInstance().deleteAllTimers();
        MiningCalculator.updateMineralSpawn(MiningConfig.MINE_DIAMOND_RESPAWN_TIME);
    }
}
