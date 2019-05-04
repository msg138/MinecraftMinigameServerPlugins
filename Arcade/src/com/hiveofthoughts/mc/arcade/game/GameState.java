package com.hiveofthoughts.mc.arcade.game;

public enum GameState {

    LOBBY(0),
    STARTING(1),
    IN_GAME(2),
    ENDING(3),
    FINISHED(4);

    private int m_state;

    GameState(int a_state) {
        m_state = a_state;
    }

    public int getState(){
        return m_state;
    }

}
