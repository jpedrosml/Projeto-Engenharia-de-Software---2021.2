package com.bb.game.minigames;

import com.bb.game.utils.Difficulty;

import java.util.Arrays;
import java.util.List;

public class MemoryLogic {

    private int score;
    private List<Integer> cards;

    MemoryLogic(Difficulty difficulty){
        score = 0;
        cards = Arrays.asList(1,1,2,2,3,3);
    }

    public void reset() {

    }

    public List<Integer> getCards() {
        return this.cards;
    }

    public int compareCards(int id, int id1) {
        return 0;
    }
}
