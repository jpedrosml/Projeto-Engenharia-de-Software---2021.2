package com.bb.game.utils;

public enum Difficulty {

    EASY, MEDIUM, HARD;

    public static Difficulty difficultyIncrement(Difficulty difficulty){
        Difficulty newDifficulty;
        switch (difficulty){
            case EASY:
                newDifficulty = Difficulty.MEDIUM;
                break;
            default:
                newDifficulty = Difficulty.HARD;
        }
        return  newDifficulty;
    }

}
