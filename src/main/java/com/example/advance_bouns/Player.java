package com.example.advance_bouns;

public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addScore(int value) {
        score += value;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
