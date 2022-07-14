package com.bb.game.minigames;

import com.bb.game.utils.Difficulty;

import java.util.Arrays;
import java.util.List;

public class MemoryLogic {

    private int score;
    private List<Integer> cards;

    MemoryLogic(Difficulty difficulty){
        score = 0;
        cards = Arrays.asList(1,1,2,2,3,3,4,4,5,5,6,6,7,7,0,0);
    }

    public void reset() {

    }

    public List<Integer> getCards() {
        return this.cards;
    }

    public boolean compareCards(int id1, int id2) {
        return id1 == id2;
    }

    public int getScore() {
        return  this.score;
    }
}
