package com.bb.game;

import com.badlogic.gdx.Game;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Difficulty;
import com.bb.game.minigames.MiniGameFactory;
import com.bb.game.minigames.MiniGameGraphics;
import com.bb.game.utils.Volume;

import java.security.SecureRandom;


public class GameManager {

    private Game game;
    private int currentRound;
    private Difficulty difficulty;
    private int score;

    private static final SecureRandom random = new SecureRandom();
    private static final int ROUNDS_PER_GAME = 4;

    public GameManager(Game game) {
        this.game = game;
        this.currentRound = 0;
        this.difficulty = Difficulty.EASY;
    }

    public void playGame() {
        if(this.currentRound < ROUNDS_PER_GAME){
            MiniGameGraphics miniGame = createRandGame(this.difficulty);
            miniGame.setManager(this);
            this.game.setScreen(miniGame);
        }else{
            //exibir a pontuação total
            this.game.setScreen(new Menu(this.game));
        }
        this.currentRound++;
    }

    private MiniGameGraphics createRandGame(Difficulty difficulty){
        int pick = random.nextInt(MiniGameFactory.Type.values().length);
        MiniGameFactory.Type miniGame = MiniGameFactory.Type.values()[pick];
        return MiniGameFactory.createMiniGame(miniGame, difficulty);
    }

    public void notifyConclusion(int miniGameScore){
        this.score += miniGameScore;
        //animação de transição;
        difficulty = Difficulty.difficultyIncrement(this.difficulty);
        playGame();
    }
}
