package com.bb.game.minigames;

import com.bb.game.BrainyBeansGraphics;

public abstract class MiniGameGraphics extends BrainyBeansGraphics {
    private int score;
    MiniGameGraphics(){
        this.score = 0;
    }
    public int getScore(){
        return this.score;
    }
    public void updateScore(int increment){
        this.score += increment;
    }
}
