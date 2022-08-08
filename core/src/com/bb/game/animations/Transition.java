package com.bb.game.animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.GameManager;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import com.bb.game.utils.Fonts;

public class Transition extends BrainyBeansGraphics {
    private Image bean;
    private Group group;
    private Sprite pre;
    private Sprite pos;
    private String newDifficulty;

    private GameManager gameManager;
    private static final Texture easy_pre = new Texture("animations\\transition_1_pre.png");
    private static final Texture easy_pos = new Texture("animations\\transition_1_pos.png");
    private static final Texture medium_pre = new Texture("animations\\transition_2_pre.png");
    private static final Texture medium_pos = new Texture("animations\\transition_2_pos.png");
    private static final Texture backgroundTexture = new Texture("animations\\background.png");
    private static final Texture effectsTexture = new Texture("animations\\effects.png");

    public Transition(Difficulty difficulty, GameManager gameManager){
        this.gameManager = gameManager;
        selectTextures(difficulty);
        bean = new Image(pre);
        group = new Group();
        setUpStage();
        animate();
    }

    private void selectTextures(Difficulty difficulty){
        if(difficulty == Difficulty.EASY){
            pre = new Sprite(easy_pre);
            pos = new Sprite(easy_pos);
            newDifficulty = "Medium";
        }else{
            pre = new Sprite(medium_pre);
            pos = new Sprite(medium_pos);
            newDifficulty = "Hard";
        }
    }

    private void setUpStage() {
        Image background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        group.addActor(background);
        bean.setBounds(Constants.WORLD_WIDTH * 0.32f, 0, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT);
        group.addActor(bean);
        group.setOrigin(Constants.WORLD_WIDTH/2f, Constants.WORLD_HEIGHT * 0.8f);
        getStage().addActor(group);
    }

    private void animate() {
        final int REPETITIONS = 3;
        final float TIME = 0.5f;
        float totalTime = 0;
        for(int i = 0; i < REPETITIONS; i++){
            if(i>0) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        group.addAction(Actions.scaleTo(1f, 1f, TIME));
                    }
                }, totalTime);
                totalTime += TIME;
            }

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    group.addAction(Actions.scaleTo(2.5f, 2.5f, TIME));
                }
            }, totalTime);
            totalTime+=TIME;
        }

        Image effects = new Image(effectsTexture);
        effects.setPosition(Constants.WORLD_WIDTH * -0.1f, Constants.WORLD_WIDTH * -0.25f);
        effects.setOrigin(effects.getWidth()/2f, effects.getHeight()/2f);

        Label text = new Label("Difficulty: " + newDifficulty, new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        text.setFontScale(3f);
        text.setPosition(Constants.WORLD_WIDTH/2f - text.getFontScaleX()*text.getWidth()/2f, Constants.WORLD_HEIGHT * 0.8f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                bean.setDrawable(new SpriteDrawable(pos));
                group.addActor(effects);
                effects.addAction(Actions.rotateBy(180, TIME*4));
                group.addActor(text);
            }
        }, totalTime);
        totalTime+=TIME*4;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                gameManager.playGame();
            }
        }, totalTime);
    }
}
