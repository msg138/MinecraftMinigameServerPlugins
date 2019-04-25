package com.hiveofthoughts.mc.rpg.listeners;

import com.hiveofthoughts.mc.rpg.Main;
import com.hiveofthoughts.mc.rpg.skills.BuildingMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Michael George on 3/22/2018.
 */
public class BuildingInventoryListener implements Listener {
    private com.hiveofthoughts.mc.Main m_main;

    public BuildingInventoryListener(com.hiveofthoughts.mc.Main a_m){
        m_main = a_m;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent t_event){
        Inventory t_inv = t_event.getClickedInventory();
        if(t_inv != null){
            if(t_inv.getName().contains(BuildingMenu.MenuKeyword)){
                t_event.setCancelled(true);
                ItemStack t_item = t_event.getCurrentItem();
                if(t_item != null){
                    // Now that we know they have clicked an item in the required inventory, we will send it off to the appropriate BuildingMenu.
                    if(t_event.getWhoClicked() instanceof Player) {
                        BuildingMenu.findMenu(t_inv.getName()).doAction((Player) t_event.getWhoClicked(), t_item.getItemMeta().getDisplayName());
                    }
                }
            }
        }
    }
}
