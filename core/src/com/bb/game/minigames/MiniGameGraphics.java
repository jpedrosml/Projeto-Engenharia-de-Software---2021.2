package com.bb.game.minigames;

import com.badlogic.gdx.Gdx;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.GameManager;

public abstract class MiniGameGraphics extends BrainyBeansGraphics {
    private int score;
    private boolean running;
    private GameManager manager;

    MiniGameGraphics(){
        this.score = 0;
        this.running = true;
    }

    public void updateScore(int increment){
        this.score += increment;
    }

    public boolean isRunning(){
        return this.running;
    }

    public void end(){
        this.running = false;
    }

    public void setManager(GameManager gameManager){
        this.manager = gameManager;
    }

    public void conclude(){
        this.manager.notifyConclusion(this.score);
    }
}
