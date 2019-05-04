package com.hiveofthoughts.mc.arcade;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.InitClasses;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.arcade.commands.CommandArcade;
import com.hiveofthoughts.mc.arcade.game.GameManager;
import com.hiveofthoughts.mc.server.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ArcadeServer extends JavaPlugin {

    public static ArcadeServer GlobalMain;

    @Override
    public void onEnable(){
        GlobalMain = this;
        Bukkit.getLogger().info(ArcadeConfig.Prefix + "Hive Of Thoughts Arcade Starting up... ");

        GameManager.getInstance();
        // Add commands
        //      com.hiveofthoughts.mc.Main.GlobalMain.getCommandList().add(new RPGCommand(com.hiveofthoughts.mc.Main.GlobalMain));
        Main.GlobalMain.addCommand(new CommandArcade(Main.GlobalMain));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                GameManager.getInstance().onRun();
            }
        }, 0, 1);

        Bukkit.getLogger().info(ArcadeConfig.Prefix + "Hive Of Thoughts Arcade Started up.");
    }

    @Override
    public void onLoad(){
        Bukkit.getLogger().info("ADDED LISTENERS");

        Config.ServerType = ServerType.TEST;
        // Add the server inclusive listeners here.
        InitClasses.ServerInclusive.put(Config.ServerType, new Class[]{
            GameManager.class
        });
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info(ArcadeConfig.Prefix + "Hive Of Thoughts Arcade Shutting Down...");
        // TimerContainer.getInstance().deleteAllTimers();
    }
}
