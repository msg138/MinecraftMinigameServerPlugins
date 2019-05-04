package com.hiveofthoughts.mc.arcade.game;

public enum PlayerStatus {

    SPECTATOR(0),
    PLAYING(1);

    private int m_id;

    PlayerStatus(int a_id){
        m_id = a_id;
    }

    public int getId(){
        return m_id;
    }
}
