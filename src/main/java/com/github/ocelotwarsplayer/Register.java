package com.github.ocelotwarsplayer;

public class Register implements OutMessage {

    private String playerName;

    public Register(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
    
}
