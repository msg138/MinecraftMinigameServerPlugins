package com.hiveofthoughts.mc.arcade.commands;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import com.hiveofthoughts.mc.arcade.game.GameManager;
import com.hiveofthoughts.mc.arcade.game.GameState;
import com.hiveofthoughts.mc.arcade.game.PlayerStatus;
import com.hiveofthoughts.mc.commands.CommandTemplate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class ArcadeCommand extends CommandTemplate {


    public ArcadeCommand(JavaPlugin plugin) {
        super(plugin, "arcade");
    }


    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "setstate":
                    if(args.length >= 2) {
                        GameManager.getInstance().getCurrentGame().setGameState(GameState.getFromName(args[1]));
                        sender.sendMessage(ArcadeConfig.MessageGameStateSet);
                        return true;
                    }else
                        sender.sendMessage(ArcadeConfig.MessageGameStateRequired);
                    break;
                case "info":
                    sender.sendMessage(ArcadeConfig.Prefix + "Game: " + GameManager.getInstance().getCurrentGame().getName());
                    sender.sendMessage(ArcadeConfig.Prefix + "Status: " + GameManager.getInstance().getCurrentGame().getGameState().getState());
                    sender.sendMessage(ArcadeConfig.Prefix + "Total Players: " + GameManager.getInstance().getCurrentGame().getPlayerCount());
                    sender.sendMessage(ArcadeConfig.Prefix + "Spectator Count: " + GameManager.getInstance().getCurrentGame().getPlayerStatusCount(PlayerStatus.SPECTATOR));

                    sender.sendMessage(ArcadeConfig.Prefix + "Current Loss Order: ");

                    GameManager.getInstance().getCurrentGame().getLossOrder().forEach(t_p -> {
                        sender.sendMessage("\t" + t_p.getName());
                    });

                    sender.sendMessage(ArcadeConfig.Prefix + "Current Players: ");

                    GameManager.getInstance().getCurrentGame().getAllPlayerInfo().forEach(t_p -> {
                        sender.sendMessage("\t" + t_p.getPlayer().getName() + " - " + t_p.getScore() + ", " + t_p.getStatus().getId() + " - " + t_p.getTeam().getTeamName() + ", " + t_p.getKit().getKitName());
                    });

                    return true;
            }
        }
        return false;
    }
}
