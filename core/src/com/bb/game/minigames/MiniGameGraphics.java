package com.bb.game.minigames;

import com.bb.game.BrainyBeansGraphics;
import com.bb.game.GameManager;

public abstract class MiniGameGraphics extends BrainyBeansGraphics {
    private int score;
    private GameManager manager;
    private float timer;

    public final float TIME_LIMIT = 60;

    MiniGameGraphics(){
        this.score = 0;
        this.timer = 0;
    }

    @Override
    public void render(float delta) {
        this.timer += delta;
        if(this.timer >= TIME_LIMIT){
            conclude();
        }
        super.render(delta);
    }

    public void updateScore(int increment){
        this.score += increment;
    }

    public void setManager(GameManager gameManager){
        this.manager = gameManager;
    }

    public Float getTimer() {
        return this.timer;
    }

    public Integer getScore() {
        return this.score;
    }

    public void conclude(){
        this.manager.notifyConclusion(this.score);
    }
}
