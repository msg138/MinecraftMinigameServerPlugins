package com.hiveofthoughts.mc.rpg.skills;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael George on 3/22/2018.
 */
public class BuildingMenu {

    public static final String ActionProduce = "Produce: ";
    public static final String ActionMenu = "Menu: ";

    public static final String MenuKeyword = "Menu";

    public static final String MessageActionFail = "Action has failed.";
    public static final String MessageActionSuccess = "You have successfully completed the action.";

    public static final int DefaultMenuSize = 27;

    private List<ItemStack> m_options;
    private List<Integer> m_slots;
    private List<String> m_actions;

    private List<BuildingMenu> m_submenu;

    private List<BuildingExchange> m_exchanges;

    private int m_menuSize;

    private boolean m_fillMenu;

    private String m_menuName;

    public BuildingMenu(){
        m_options = new ArrayList<>();
        m_slots = new ArrayList<>();
        m_actions = new ArrayList<>();

        m_submenu = new ArrayList<>();

        m_exchanges = new ArrayList<>();

        m_menuSize = DefaultMenuSize;

        m_fillMenu = true;

        m_menuName = "Sample Menu";
    }

    public void addExchange(BuildingExchange a_be){
        m_exchanges.add(a_be);
    }

    public void addSubMenu(BuildingMenu a_bm){
        m_submenu.add(a_bm);
    }

    public boolean getFillMenu(){
        return m_fillMenu;
    }
    public void setFillMenu(boolean a_fm){
        m_fillMenu = a_fm;
    }

    public String getMenuName(){
        return m_menuName;
    }
    public void setMenuName(String a_mn){
        m_menuName = a_mn;
    }

    public int getMenuSize(){
        return m_menuSize;
    }
    public void setMenuSize(int a_ms){
        m_menuSize = a_ms;
    }

    public void addOption(ItemStack a_i, int a_slot, String a_action){
        m_options.add(a_i);
        m_slots.add(a_slot);
        m_actions.add(a_action);
    }

    public Inventory generateGui(){
        Inventory r_res = Bukkit.createInventory(null, getMenuSize(), getMenuName());

        if(getFillMenu()){
            for(int i=0;i<getMenuSize();i++)
                r_res.setItem(i, Config.BlankSpace);
        }

        for(int i=0;i<m_options.size();i++){
            ItemStack t_i = m_options.get(i);
            ItemMeta t_im = t_i.getItemMeta();
            t_im.setDisplayName(m_actions.get(i));
            t_i.setItemMeta(t_im);

            r_res.setItem(m_slots.get(i), t_i);
        }

        return r_res;
    }

    public boolean doAction(Player a_p, String a_action){
        if(a_action.startsWith(ActionMenu)){
            String t_nm = a_action.substring(ActionMenu.length());
            BuildingMenu t_nbm = findMenu(t_nm);
            if(t_nbm == null)
                return false;
            a_p.openInventory(t_nbm.generateGui());
            return true;
        }else if(a_action.startsWith(ActionProduce)){
            String t_nm = a_action.substring(ActionProduce.length());
            for(BuildingExchange t_be : m_exchanges){
                if(t_be.getExchangeName().equals(t_nm)){
                    if(t_be.processRecipe(a_p))
                        a_p.sendMessage(RPGConfig.Prefix + MessageActionSuccess);
                    else
                        a_p.sendMessage(RPGConfig.Prefix + MessageActionFail);
                    break;
                }
            }
        }
        return false;
    }

    public static BuildingMenu findMenu(String a_menuName){
        for(BuildingMenu t_bm : WorldData.BuildingMenus){
            if(t_bm.getMenuName().equals(a_menuName)){
                return t_bm;
            }
        }
        return null;
    }
}
