package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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
        String t_itemId = Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelector).getString(Database.Field_ServerSelectorItem);
        m_selectorItem = new ItemStack(Material.getMaterial(t_itemId.toUpperCase()), 1);
        ItemMeta t_im = m_selectorItem.getItemMeta();
        t_im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelector).getString(Database.Field_ServerSelectorItemName)));
        String[] t_desc = Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelector).getString(Database.Field_ServerSelectorItemDescription).split("\\|");
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
        if(a_msg == null || a_msg.isEmpty())
            a_p.sendMessage(Config.Prefix + "Sending you to server '" + a_serverName + "' ");
        else
            a_p.sendMessage(Config.Prefix + a_msg);

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
            int t_menuSize = Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, Database.Field_ServerSelector).getDouble(Database.Field_ServerSelectorMenuSize).intValue();
            Inventory r_main = Bukkit.createInventory(null, t_menuSize, getSelectorInventoryName());

            // Fill the inventory up with all stained glass.
            for(int i=0;i<t_menuSize;i++){
                r_main.setItem(i, Config.BlankSpace);
            }

            String[] t_serversList = ServerInfo.getInstance().getServerCompleteOnlineList();
            int t_nextSlot = 0;
            for(String t_d : t_serversList){
                boolean t_serverUp = true;

                ItemStack t_item = null;
                Document t_serverInfo = null;
                if(t_serverUp) {
                    t_serverInfo = Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, Config.ServerPorts.get(t_d)+"");

                    t_item = new ItemStack(Material.getMaterial(t_serverInfo.getString(Database.Field_ServerBlock)), (Integer.parseInt(t_serverInfo.getString(Database.Field_PlayerCount)) % 64) + 1);
                }else
                    t_item = new ItemStack(Material.BEDROCK, 1);
                ItemMeta t_im = t_item.getItemMeta();
                t_im.setDisplayName(t_d);
                ArrayList<String> t_newLore = new ArrayList<>();
                t_newLore.add(Config.InventoryServerItem);
                if(t_serverUp) {
                    t_newLore.add("Connect to the '" + t_d + "' server.");
                    t_newLore.add("Players Online: " + t_serverInfo.getString(Database.Field_PlayerCount));
                    t_newLore.add(ChatColor.translateAlternateColorCodes('&', t_serverInfo.getString(Database.Field_ServerStatus)));
                }else
                    t_newLore.add(ChatColor.translateAlternateColorCodes('&', "Server is currently " + Config.StatusOffline));
                t_im.setLore(t_newLore);
                t_item.setItemMeta(t_im);
                r_main.setItem(t_nextSlot, t_item);
                t_nextSlot ++;
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

        SelfCancelTask t_sct = new SelfCancelTask() {
            @Override
            public void run() {
                System.out.println("Checking task, for inventory...");
                boolean t_invUpdated = false;
                if(a_p != null && a_p.isOnline()) {
                    InventoryView t_iv = a_p.getOpenInventory();
                    if (t_iv != null) {
                        Inventory t_inv = t_iv.getTopInventory();
                        if (t_inv != null) {
                            if (t_inv.getName().equals(getSelectorInventoryName())) {
                                System.out.println("Updating inventory...");
                                a_p.openInventory(getInventory(a_p));
                                t_invUpdated = true;
                            }
                        }
                    }
                }
                if(!t_invUpdated) {
                    System.out.println("Deleting task, as inventory no longer open.");
                    this.cancelTask();
                }
            }
        };
        int t_schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.GlobalMain, t_sct, 0, 50);
        t_sct.setSchedulerId(t_schedule);

        return true;
    }

    public String getServerNameFromItem(ItemStack a_item){
        return a_item.getItemMeta().getDisplayName();
    }
}
