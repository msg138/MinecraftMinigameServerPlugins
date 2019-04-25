package com.hiveofthoughts.mc.rpg.skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael George on 3/21/2018.
 */
public class BuildingOperation {

    private List<BuildingExchange> m_exchanges;

    private String m_guiName = "Sample Operation";

    public BuildingOperation(){
        m_exchanges = new ArrayList<>();
    }

    public void addExchange(BuildingExchange a_be){
        m_exchanges.add(a_be);
    }

    public void openGui(Player a_p){

    }

    public Inventory generateGui(String a_menuName){
        return null;
    }
}
