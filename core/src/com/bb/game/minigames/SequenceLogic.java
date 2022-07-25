package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class SequenceLogic {

    private List<Integer> sequence;
    private int sequenceSize;
    private int sequenceIterator;
    private float timer;
    private int colorsLeft;
    private int pointsLostPerSec;

    SequenceLogic(Difficulty difficulty) {
        this.sequence = new ArrayList<>();
        difficultyConfig(difficulty);
        this.colorsLeft = this.sequenceSize;
        this.sequenceIterator = 0;
        this.timer = 0;
        createSequence();
    }

    public void reset() {
        this.timer = 0;
        this.sequenceIterator = 0;
        this.colorsLeft = this.sequenceSize;
        createSequence();
    }

    private void difficultyConfig(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                this.sequenceSize = 2;
                this.pointsLostPerSec = 200;
                break;
            case MEDIUM:
                this.sequenceSize = 3;
                this.pointsLostPerSec = 100;
                break;
            case HARD:
                this.sequenceSize = 4;
                this.pointsLostPerSec = 50;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    private void createSequence() {
        this.sequence.clear();
        for(int i = sequenceSize; i > 0; i--) {
            this.sequence.add((int)(Math.random()*sequenceSize));
        }
    }

    public List<Integer> getSequence() {
        return this.sequence;
    }

    public int tryColor(int number) {
        int points = 0;

        if(number == sequence.get(sequenceIterator)) {
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - (int)(this.timer * this.pointsLostPerSec)), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.colorsLeft -= 1;
            this.sequenceIterator += 1;
        }

        return points;
    }

    public void incrementTimer(float delta) {
        this.timer += delta;
    }

    public boolean noColorsLeft() {
        return this.colorsLeft == 0;
    }

}
