package com.bb.game;

import com.badlogic.gdx.Game;
import com.bb.game.menu.Menu;
import com.bb.game.minigames.Difficulty;
import com.bb.game.minigames.MiniGameFactory;
import com.bb.game.minigames.MiniGameGraphics;

import java.security.SecureRandom;


public class GameManager {

    private Game game;

    private static final SecureRandom random = new SecureRandom();

    public GameManager(Game game) {

    }

    public void playGame() {

    }

    private MiniGameGraphics createRandGame(Difficulty difficulty){
        int pick = random.nextInt(MiniGameFactory.Type.values().length);
        MiniGameFactory.Type miniGame = MiniGameFactory.Type.values()[pick];
        return MiniGameFactory.createMiniGame(miniGame, difficulty);
    }
}
