package com.hiveofthoughts.mc.server;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael George on 3/22/2018.
 */
public class ItemBuilder {

    public static ItemStack buildItem(ItemStack a_i, String a_dname, String[] a_lore) {
        return buildItem(a_i, a_dname, a_lore, -1);
    }
    public static ItemStack buildItem(ItemStack a_i, String a_dname, String[] a_lore, int data){
        ItemMeta t_im = a_i.getItemMeta();
        List<String> t_lore = new ArrayList<>();
        if(a_lore != null)
            for(int i=0;i<a_lore.length;i++){
                t_lore.add(a_lore[i]);
            }
        t_im.setLore(t_lore);
        t_im.setDisplayName(a_dname);
        a_i.setItemMeta(t_im);
        //if(data != -1)
        //    a_i.setTypeId(data);
        return a_i;
    }
}
