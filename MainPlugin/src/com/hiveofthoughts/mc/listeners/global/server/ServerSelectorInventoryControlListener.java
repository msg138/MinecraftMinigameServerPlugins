package com.hiveofthoughts.mc.listeners.global.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.server.ServerSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerSelectorInventoryControlListener implements Listener {
    private Main m_main;

    public ServerSelectorInventoryControlListener(Main main){
        m_main = main;
    }

    @EventHandler
    public void onSelectorClick(InventoryClickEvent t_event){
        if(t_event.getWhoClicked() instanceof Player){
            Player t_p = (Player) t_event.getWhoClicked();
            if(t_event.getInventory() != null){
                Inventory t_inv = t_event.getInventory();
                if(t_inv.getName().equals(ServerSelector.getInstance().getSelectorInventoryName()))
                {
                    t_event.setCancelled(true);

                    if(t_event.getCurrentItem() != null)
                    {
                        ItemStack t_item = t_event.getCurrentItem();
                        if(t_item.getItemMeta() != null){
                            ItemMeta t_im = t_item.getItemMeta();
                            if(t_im.getLore() != null){
                                List<String> t_lore = t_im.getLore();
                                if(t_lore.size() > 0 && t_lore.get(0).equals(Config.InventoryServerItem)){
                                    ServerSelector.getInstance().teleportToServer(t_p, "SERVER CHANGE", t_im.getDisplayName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
