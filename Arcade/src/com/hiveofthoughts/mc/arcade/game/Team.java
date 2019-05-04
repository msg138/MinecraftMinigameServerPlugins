package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    protected String m_teamName;
    protected String m_teamDescription;

    protected List<ItemStack> m_givenItems;

    public Team(){
        m_teamName = ArcadeConfig.DefaultString;
        m_teamDescription = ArcadeConfig.DefaultString;

        m_givenItems = new ArrayList<>();
    }

    public boolean giveItems(Player a_player){
        for(ItemStack t_item : m_givenItems){
            a_player.getInventory().addItem(t_item);
        }
        return true;
    }

    public String getTeamName(){
        return m_teamName;
    }
    public String getTeamDescription(){
        return m_teamDescription;
    }

}
