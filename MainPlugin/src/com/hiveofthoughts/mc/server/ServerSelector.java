package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;

/**
 * Created by Michael George on 3/18/2018.
 */
public class ServerSelector {
    private static ServerSelector m_instance = null;

    public static ServerSelector getInstance(){
        if(m_instance == null)
            try {
                m_instance = new ServerSelector();
            }catch(Exception e){
                e.printStackTrace();
                m_instance = null;
            }
        return m_instance;
    }

    private ItemStack m_selectorItem = null;

    private ServerSelector() throws Exception{
        // Make calls to the database to get information about what the selector item should be
        String t_itemId = Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorItem).getString(Database.Field_Value);
        m_selectorItem = new ItemStack(Material.getMaterial(t_itemId.toUpperCase()), 1);
        ItemMeta t_im = m_selectorItem.getItemMeta();
        t_im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorItemName).getString(Database.Field_Value)));
        String[] t_desc = Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorItemDescription).getString(Database.Field_Value).split("\\|");
        ArrayList<String> t_newLore = new ArrayList<>();
        for(String t_d : t_desc)
            t_newLore.add(ChatColor.translateAlternateColorCodes('&', t_d));
        t_im.setLore(t_newLore);
        m_selectorItem.setItemMeta(t_im);
    }

    public String getSelectorName(){
        return m_selectorItem.getItemMeta().getDisplayName();
    }

    public String getSelectorInventoryName(){
        return getSelectorName();
    }

    public boolean teleportToServer(Player a_p, String a_msg, String a_serverName){

        a_p.sendMessage(Config.Prefix + "Sending you to server '" + a_serverName + "' ");

        try{
            ByteArrayOutputStream t_b = new ByteArrayOutputStream();
            DataOutputStream t_out = new DataOutputStream(t_b);

            t_out.writeUTF(Config.BungeeConnect);
            t_out.writeUTF(a_serverName);

            a_p.sendPluginMessage(Main.GlobalMain, Config.BungeeCord, t_b.toByteArray());
            t_b.close();
            t_out.close();
        }catch(Exception e){
            e.printStackTrace();
            a_p.sendMessage(Config.Prefix + Config.MessageErrorUnknown);
        }

        return true;
    }

    public boolean giveSelector(Player a_p){
        if(hasSelector(a_p))
            return false;
        a_p.getInventory().addItem(m_selectorItem);
        return true;
    }

    public boolean holdingSelector(Player a_p){
        return a_p.getInventory().getItemInMainHand().isSimilar(m_selectorItem);
    }

    public boolean hasSelector(Player a_p){
        Inventory t_playerInventory = a_p.getInventory();
        return t_playerInventory.contains(m_selectorItem);
    }

    private Inventory getInventory(Player a_p){

        try {
            Inventory r_main = Bukkit.createInventory(null, Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorMenuSize).getDouble(Database.Field_Value).intValue(), getSelectorInventoryName());
            List<String> t_serversList = null;
            t_serversList = (List<String>) Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelectorArray).get(Database.Field_Value);
            for(String t_d : t_serversList){
                ItemStack t_item = new ItemStack(1, 1);
                ItemMeta t_im = t_item.getItemMeta();
                t_im.setDisplayName(t_d);
                ArrayList<String> t_newLore = new ArrayList<>();
                t_newLore.add(Config.InventoryServerItem);
                t_newLore.add("Connect to the '" + t_d + "' server.");
                t_im.setLore(t_newLore);
                t_item.setItemMeta(t_im);
                r_main.addItem(t_item);
            }
            return r_main;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean openMenu(Player a_p){
        a_p.sendMessage(Config.Prefix + "Opening server selector...");
        Inventory t_inv = getInventory(a_p);
        if(t_inv == null)
            return false;
        a_p.openInventory(t_inv);
        return true;
    }

    public String getServerNameFromItem(ItemStack a_item){
        return a_item.getItemMeta().getDisplayName();
    }
}
