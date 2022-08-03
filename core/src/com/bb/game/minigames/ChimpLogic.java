package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChimpLogic {
    private List<Integer> buttons;
    private int buttonsLeft;
    private float timer;
    private int pointsLostPerSec;

    ChimpLogic(Difficulty difficulty) {
        difficultyConfig(difficulty);
        this.buttonsLeft = this.buttons.size();
    }

    public void reset() {
        this.timer = 0;
        this.buttonsLeft = this.buttons.size();
    }

    private void difficultyConfig(Difficulty difficulty) {
        switch (difficulty){
            case EASY:
                this.buttons = Arrays.asList(1,2,3,4,5,6);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 200;
                break;
            case MEDIUM:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 100;
                break;
            case HARD:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 50;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public int tryButton(int button, int nextButton) {
        int points = 0;

/*        for (int i = 0; i < this.buttons.size() - 3 + 1; ++i) {
            if (this.buttons.indexOf(i) == this.buttons.indexOf(i + 2) - 2) {
                this.buttons.remove(i);
            }
        }*/

        if(nextButton == button) {
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - (int)(this.timer * this.pointsLostPerSec)), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.buttonsLeft -= 1;
        }
        return points;
    }

    public void incrementTimer(float delta) {
        this.timer += delta;
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public void setButtons(List<Integer> buttons) {
        this.buttons = buttons;
    }

    public boolean noButtonsLeft() {
        return this.buttonsLeft == 0;
    }

}
