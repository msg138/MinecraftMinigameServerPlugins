package com.hiveofthoughts.mc.commands;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.skills.BuildingMenu;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hiveofthoughts.mc.rpg.skills.CarpenterDeskTemplate;

/**
 * Created by Michael George on 3/21/2018.
 */
public class DevCommand extends CommandTemplate {
    public DevCommand(Main plugin){
        super(plugin, "dev");
        m_playerOnly = true;
    }

    @Override
    public boolean act(CommandSender sender, Command cmd, String label, String[] args){
        Player t_p = (Player) sender;
        if(args.length > 0)
        {
            switch(args[0]){
                case "blockinfo":
                    Block t_b = t_p.getTargetBlock(null, 10);
                    if(t_b != null)
                        sender.sendMessage("Block Info, Material: " + t_b.getType().toString() +", Data: " + t_b.getData() + " Location: " + t_b.getLocation().toString());
                    else
                        sender.sendMessage("Block is null.");
                    break;
                case "blockmeta":
                    if(args.length > 1){
                        t_b = t_p.getTargetBlock(null, 10);
                        if(t_b != null && t_b.hasMetadata(args[1]))
                            sender.sendMessage("Block Meta for '" + args[1] + "', Meta: " + t_b.getMetadata(args[1]).get(0).value() + ", " + t_b.getMetadata(args[1]).get(0).value().toString());
                        else
                            sender.sendMessage("Block is null or does not have metadata '" + args[1] + "'.");
                    }
                    break;
                case "rtest":
                    t_b = t_p.getTargetBlock(null, 10);
                    CarpenterDeskTemplate cdt = new CarpenterDeskTemplate();
                    sender.sendMessage("The carpenter recipe " + (cdt.buildTemplate(t_b) ? "did" : "did not") + " work.");
                    break;
                case "openmenu":
                    if(args.length > 1){
                        String t_con = args[1];
                        for(int i=2;i<args.length;i++)
                            t_con += args[i];
                        BuildingMenu bm = BuildingMenu.findMenu(t_con);
                        if(bm != null)
                            t_p.openInventory(bm.generateGui());
                        else
                            t_p.sendMessage("Menu is null.");
                        t_p.sendMessage(WorldData.BuildingMenus.toString() +" " + WorldData.TemplateMenuMap.keySet().toString());
                        t_p.sendMessage(WorldData.BuildingMenus.size() +" " + WorldData.TemplateMenuMap.keySet().size());
                    }
                    break;
            }
        }
        return true;
    }
}
