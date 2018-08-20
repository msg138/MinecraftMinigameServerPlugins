package com.hiveofthoughts.mc.rpg;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.InitClasses;
import com.hiveofthoughts.mc.rpg.calculator.MiningCalculator;
import com.hiveofthoughts.mc.rpg.config.MiningConfig;
import com.hiveofthoughts.mc.rpg.listeners.*;
import com.hiveofthoughts.mc.rpg.skills.CarpenterDeskTemplate;
import com.hiveofthoughts.mc.server.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Michael George on 3/19/2018.
 */
public class Main extends JavaPlugin{

    public static Main GlobalMain;

    @Override
    public void onEnable(){
        GlobalMain = this;

        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Starting up...");

        TimerContainer.getInstance().addTimer(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                MiningCalculator.updateMineralSpawn();
            }
        }, 0, 5));

        // Add commands
        com.hiveofthoughts.mc.Main.GlobalMain.getCommandList().add(new RPGCommand(com.hiveofthoughts.mc.Main.GlobalMain));

        // Add Skill Buildings
        new CarpenterDeskTemplate();

        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Started up.");
    }

    @Override
    public void onLoad(){
        Bukkit.getLogger().info("ADDED LISTENERS");

        Config.ServerType = ServerType.RPG;
        // Add the server inclusive listeners here.
        InitClasses.ServerInclusive.put(ServerType.RPG, new Class[]{
                PlayerPlaceListener.class,
                MiningListener.class,
                WoodcuttingListener.class,

                BuildingInventoryListener.class,
                BuildingOpenListener.class,
                BuildingRemoveListener.class,
        });
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info(RPGConfig.Prefix + "Hive Of Thoughts RPG Shutting Down...");
        // TimerContainer.getInstance().deleteAllTimers();
        MiningCalculator.updateMineralSpawn(MiningConfig.MINE_DIAMOND_RESPAWN_TIME);
    }
}
