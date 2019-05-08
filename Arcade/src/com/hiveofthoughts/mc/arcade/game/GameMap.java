package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.arcade.ArcadeConfig;

import org.bukkit.Location;

import java.util.HashMap;

public class GameMap {

    private String m_worldName;

    private HashMap<String, Object> m_settings;

    public GameMap(){
        m_settings = new HashMap<>();
        m_worldName = ArcadeConfig.DefaultWorldName;
    }

    public String getWorldName(){
        return m_worldName;
    }

    public void setWorldName(String a_worldName){
        m_worldName = a_worldName;
    }

    public boolean setSetting(String a_setting, Object a_value){
        if(a_value != null)
            m_settings.put(a_setting, a_value);
        else
            return false;
        return true;
    }

    public Object getSetting(String a_setting){
        if(m_settings.containsKey(a_setting))
            return m_settings.get(a_setting);
        return null;
    }

    public boolean hasSetting(String a_setting){
        return m_settings.containsKey(a_setting);
    }

    public Location getSpawnLocation(PlayerInfo a_playerinfo){
        Location r_spawn = null;

        if(a_playerinfo.getStatus().equals(PlayerStatus.SPECTATOR) && hasSetting("spawn-spectator"))
        {
            r_spawn = (Location) getSetting("spawn-spectator");
        }else if(hasSetting("spawn-" + a_playerinfo.getTeam().getTeamName() + "-" + a_playerinfo.getKit().getKitName())){
            r_spawn = (Location) getSetting("spawn-" + a_playerinfo.getTeam().getTeamName() + "-" + a_playerinfo.getKit().getKitName());
        } else if(hasSetting("spawn-" + a_playerinfo.getTeam().getTeamName())) {
            r_spawn = (Location) getSetting("spawn-" + a_playerinfo.getTeam().getTeamName());
        } else {
            r_spawn = (Location) getSetting("spawn");
        }

        return r_spawn;
    }
}
