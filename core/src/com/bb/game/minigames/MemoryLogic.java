package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryLogic {

    private List<Integer> cards;
    private float timer;
    private int cardsLeft;
    private int pointsLostPerSec;

    MemoryLogic(Difficulty difficulty){
        difficultyConfig(difficulty);
        this.cardsLeft = this.cards.size();
    }

    public void reset() {
        this.timer = 0;
        this.cardsLeft = this.cards.size();
        Collections.shuffle(this.cards);
    }

    private void difficultyConfig(Difficulty difficulty){
        switch (difficulty){
            case EASY:
                this.cards = Arrays.asList(0,0,1,1,2,2);
                this.pointsLostPerSec = 200;
                break;
            case MEDIUM:
                this.cards = Arrays.asList(0,0,1,1,2,2,3,3,4,4,5,5);
                this.pointsLostPerSec = 100;
                break;
            case HARD:
                this.cards = Arrays.asList(0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7);
                this.pointsLostPerSec = 50;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public List<Integer> getCards() {
        return this.cards;
    }

    public int compareCards(int id1, int id2) {
        int points = 0;
        if(id1 == id2){
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - ((int)this.timer) * this.pointsLostPerSec), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.cardsLeft -= 2;
        }
        return points;
    }

    public void incrementTimer(float delta) {
        this.timer += delta;
    }

    public boolean noCardsLeft() {
        return this.cardsLeft == 0;
    }
}
