package com.hiveofthoughts.mc.rpg.skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael George on 3/21/2018.
 */
public class BuildingExchange {

    private List<ItemStack> m_required;
    private List<ItemStack> m_produced;

    private String m_exchangeName;

    public BuildingExchange(String a_ename){
        m_required = new ArrayList<>();
        m_produced = new ArrayList<>();

        m_exchangeName = a_ename;
    }

    public String getExchangeName(){
        return m_exchangeName;
    }

    public void addRequirement(ItemStack a_i){
        m_required.add(a_i);
    }
    public void addResult(ItemStack a_i){
        m_produced.add(a_i);
    }

    public boolean processRecipe(Player a_p){
        if(hasRecipe(a_p)) {
            removeRecipe(a_p);
            for (ItemStack t_i : m_produced)
                a_p.getWorld().dropItem(a_p.getLocation(), t_i);
        }else
            return false;
        return true;
    }

    public boolean hasRecipe(Player a_p){
        Inventory t_inv = a_p.getInventory();
        for(ItemStack t_i : m_required){
            boolean t_found = false;
            int t_itemCount = 0;
            for(ItemStack t_i2 : t_inv.getContents()){
                if(t_i2 == null || t_i == null)
                    continue;
                if(t_i.getType().equals(t_i2.getType()) &&
                        t_i.getItemMeta().hasLore() == t_i2.getItemMeta().hasLore() &&
                        t_i.getItemMeta().getDisplayName().equals(t_i2.getItemMeta().getDisplayName())) {
                    t_found = true;
                    if(t_i.getItemMeta().hasLore()){
                        List<String> t_lore = t_i.getItemMeta().getLore();
                        List<String> t_lore2 = t_i2.getItemMeta().getLore();

                        for(int i=0;i<t_lore.size();i++){
                            if(!t_lore.get(i).equals(t_lore2.get(i)))
                                t_found = false;
                        }
                    }
                    if(t_found)
                        t_itemCount += t_i2.getAmount();
                }
            }
            if(!t_found || t_itemCount < t_i.getAmount())
                return false;
        }
        return true;
    }

    // Direct copy of hasRecipe, except removes the items.
    public boolean removeRecipe(Player a_p){
        Inventory t_inv = a_p.getInventory();
        for(ItemStack t_i : m_required){
            boolean t_found = false;
            int t_itemsNeeded = t_i.getAmount();
            for(ItemStack t_i2 : t_inv.getContents()){
                if(t_i2 == null || t_i == null)
                    continue;
                if(t_i.getType().equals(t_i2.getType()) &&
                        t_i.getItemMeta().hasLore() == t_i2.getItemMeta().hasLore() &&
                        t_i.getItemMeta().getDisplayName().equals(t_i2.getItemMeta().getDisplayName())) {
                    t_found = true;
                    if(t_i.getItemMeta().hasLore()){
                        List<String> t_lore = t_i.getItemMeta().getLore();
                        List<String> t_lore2 = t_i2.getItemMeta().getLore();

                        for(int i=0;i<t_lore.size();i++){
                            if(!t_lore.get(i).equals(t_lore2.get(i)))
                                t_found = false;
                        }
                    }
                    if(t_found){
                        if(t_i2.getAmount() <= t_itemsNeeded){
                            t_itemsNeeded -= t_i2.getAmount();
                            t_i2.setAmount(0);
                        }else if(t_i2.getAmount() > t_itemsNeeded){
                            t_i2.setAmount(t_i2.getAmount() - t_itemsNeeded);
                            t_itemsNeeded = 0;
                        }
                    }
                }
            }
            if(!t_found || t_itemsNeeded > 0)
                return false;
        }
        return true;
    }
}
