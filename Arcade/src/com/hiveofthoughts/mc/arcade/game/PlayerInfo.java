package com.hiveofthoughts.mc.arcade.game;

import org.bukkit.entity.Player;

public class PlayerInfo {

    private Player m_player;

    private PlayerStatus m_status;

    private String m_score;

    private Team m_team;

    private Kit m_kit;

    public PlayerInfo(Player a_player){
        m_player = a_player;

        m_status = PlayerStatus.SPECTATOR;

        m_score = "";
    }

    public Kit getKit(){
        return m_kit;
    }

    public String getScore(){
        return m_score;
    }
    public void setScore(String a_score) {
        m_score = a_score;
    }

    public Team getTeam(){
        return m_team;
    }

    public void setKit(Kit a_kit){
        m_kit = a_kit;
    }
    public void setTeam(Team a_team){
        m_team = a_team;
    }

    public Player getPlayer(){
        return m_player;
    }

    public PlayerStatus getStatus(){
        return m_status;
    }

    public void setStatus(PlayerStatus a_newStatus){
        m_status = a_newStatus;
    }

}
