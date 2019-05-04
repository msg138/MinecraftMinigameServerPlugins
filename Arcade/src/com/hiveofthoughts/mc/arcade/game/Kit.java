package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    protected String m_kitName;
    protected String m_kitDescription;

    protected List<ItemStack> m_givenItems;

    public Kit(){
        m_kitName = ArcadeConfig.DefaultString;
        m_kitDescription = ArcadeConfig.DefaultString;

        m_givenItems = new ArrayList<>();
    }

    public boolean giveItems(Player a_player){
        for(ItemStack t_item : m_givenItems){
            a_player.getInventory().addItem(t_item);
        }
        return true;
    }

    public String getKitName(){
        return m_kitName;
    }
    public String getKitDescription(){
        return m_kitDescription;
    }
}
