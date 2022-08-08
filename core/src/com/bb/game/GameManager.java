package com.bb.game;

import com.badlogic.gdx.Game;
import com.bb.game.animations.Transition;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Difficulty;
import com.bb.game.minigames.MiniGameFactory;
import com.bb.game.minigames.MiniGameGraphics;
import com.bb.game.utils.Volume;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GameManager {

    private Game game;
    private int currentRound;
    private Difficulty difficulty;
    private int score;
    private List<Integer> ranking;

    private static final SecureRandom random = new SecureRandom();
    private static final int ROUNDS_PER_GAME = 4;

    public GameManager(Game game) {
        this.game = game;
        this.currentRound = 0;
        this.difficulty = Difficulty.EASY;
        this.ranking = new ArrayList<>();
    }

    public List<Integer> getRanking() {
        Collections.sort(ranking);
        return this.ranking;
    }

    public void playGame() {
        if(this.currentRound !=0 ){
            difficulty = Difficulty.difficultyIncrement(this.difficulty);
        }

        MiniGameGraphics miniGame = createRandGame(this.difficulty);
        miniGame.setManager(this);
        this.game.setScreen(miniGame);
        this.currentRound++;
    }

    private MiniGameGraphics createRandGame(Difficulty difficulty){
        int pick = random.nextInt(MiniGameFactory.Type.values().length);
        MiniGameFactory.Type miniGame = MiniGameFactory.Type.values()[pick];
        return MiniGameFactory.createMiniGame(miniGame, difficulty);
    }

    public void notifyConclusion(int miniGameScore){
        if(this.currentRound < ROUNDS_PER_GAME) {
            this.score += miniGameScore;
            game.setScreen(new Transition(difficulty, this));
        }else{
            if(ranking.size() < 10)
                ranking.add(score);
            else {
                Collections.sort(ranking);
                if(score > ranking.get(0))
                    ranking.set(0, score);
            }
            this.game.setScreen(new ScoreScreen(score, game));
        }
    }
}
