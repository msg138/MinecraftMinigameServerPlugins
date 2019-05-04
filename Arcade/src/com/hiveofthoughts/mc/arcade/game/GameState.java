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

    public static GameState getFromName(String a_name){
        a_name = a_name.toUpperCase();
        switch(a_name){
            case "LOBBY":
                return GameState.LOBBY;
            case "STARTING":
                return GameState.STARTING;
            case "IN_GAME":
                return GameState.IN_GAME;
            case "ENDING":
                return GameState.ENDING;
            case "FINISHED":
                return GameState.FINISHED;
            default:
                return GameState.LOBBY;
        }
    }

}
