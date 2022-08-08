package com.bb.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.GameManager;
import com.bb.game.utils.Constants;

public class Menu extends BrainyBeansGraphics {

    private Game game;

    private Actor playButton;
    private Actor rankingButton;
    private Actor settingsButton;
    private Actor aboutButton;
    private Actor background_1;
    private Actor background_2;

    private static final Texture backgroundTexture = new Texture("menu\\bb_bg.png");
    private static final Texture logoTexture = new Texture("menu\\bb_logo.png");

    private static final float MENU_BG_SPEED = 0.001f;

    public Menu(Game game) {
        this.game = game;
        this.playButton = new Button("play", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.7f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.rankingButton = new Button("ranking", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.5f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.settingsButton = new Button("settings", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.3f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.aboutButton = new Button("about", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.1f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.background_1 = new Image(backgroundTexture);
        this.background_2 = new Image(backgroundTexture);
        setUpActors();
    }

    private void setUpActors() {
        background_1.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background_1);
        background_2.setBounds(-Constants.WORLD_WIDTH, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background_2);
        Actor logo = new Image(logoTexture);
        logo.setBounds(Constants.WORLD_WIDTH * 0.45f, Constants.WORLD_HEIGHT * 0.2f, Constants.WORLD_WIDTH * 0.5f, Constants.WORLD_HEIGHT * 0.7f);
        getStage().addActor(logo);
        getStage().addActor(this.playButton);
        getStage().addActor(this.rankingButton);
        getStage().addActor(this.settingsButton);
        getStage().addActor(this.aboutButton);
        getStage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if(event.getTarget().getName() != null)
                    selectOption(event.getTarget().getName());
            }
        });
    }

    @Override
    public void render(float delta) {
        backgroundAnimation();
        super.render(delta);
    }

    private void selectOption(String buttonName) {
        switch (buttonName){
            case "play":
                GameManager gm = new GameManager(game);
                gm.playGame();
                break;
            case "ranking":
                break;
            case "settings":
                game.setScreen(new Settings(game));
                break;
            case "about":
                break;
            default:
                throw new IllegalStateException();
        }
    }

    private void backgroundAnimation(){
        background_1.moveBy(Constants.WORLD_WIDTH * MENU_BG_SPEED, 0);
        background_2.moveBy(Constants.WORLD_WIDTH * MENU_BG_SPEED, 0);
        if (background_1.getX() > Constants.WORLD_WIDTH) {
            background_1.setX(-Constants.WORLD_WIDTH);
        } else if (background_2.getX() > Constants.WORLD_WIDTH) {
            background_2.setX(-Constants.WORLD_WIDTH);
        }
    }
}
