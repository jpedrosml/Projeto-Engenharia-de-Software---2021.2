package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

public class AimLogic {

    private float radius;
    private int pointsLostForDistance;

    private final int maxPoints = 100;
    private final int minPoints = 20;

    public AimLogic(Difficulty difficulty){

        switch (difficulty){
            case EASY:
                this.radius = Constants.WORLD_WIDTH * 0.06f;
                this.pointsLostForDistance = 1;
                break;
            case MEDIUM:
                this.radius = Constants.WORLD_WIDTH * 0.045f;
                this.pointsLostForDistance = 2;
                break;
            case HARD:
                this.radius = Constants.WORLD_WIDTH * 0.035f;
                this.pointsLostForDistance = 3;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public int scorePerHit(float distance){
        return Math.max((this.maxPoints - (int)(distance * this.pointsLostForDistance)), this.minPoints);
    }

    public float getRadius() {
        return radius;
    }

}
