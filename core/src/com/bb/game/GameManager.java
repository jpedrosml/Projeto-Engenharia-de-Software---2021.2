package com.bb.game;

import com.badlogic.gdx.Game;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Difficulty;
import com.bb.game.minigames.MiniGameFactory;
import com.bb.game.minigames.MiniGameGraphics;
import java.security.SecureRandom;


public class GameManager {

    private Game game;

    private static final SecureRandom random = new SecureRandom();
    private static final int ROUNDS_PER_GAME = 4;

    public GameManager(Game game) {
        this.game = game;
    }

    public void playGame() {
        MiniGameGraphics miniGame;
        int score = 0;
        Difficulty difficulty = Difficulty.EASY;
        for(int i = 1; i<=ROUNDS_PER_GAME; i++){
            miniGame = createRandGame(difficulty);
            this.game.setScreen(miniGame);
            score += miniGame.getScore();
            difficulty = Difficulty.difficultyIncrement(difficulty);
            if(i==ROUNDS_PER_GAME){
                //exibir a pontuação total
            }else{
                //fazer animação de transição
            }
        }
        game.setScreen(new Menu(this.game));
    }

    private MiniGameGraphics createRandGame(Difficulty difficulty){
        int pick = random.nextInt(MiniGameFactory.Type.values().length);
        MiniGameFactory.Type miniGame = MiniGameFactory.Type.values()[pick];
        return MiniGameFactory.createMiniGame(miniGame, difficulty);
    }
}
