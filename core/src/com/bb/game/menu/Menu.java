package com.bb.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.utils.Constants;

public class Menu extends BrainyBeansGraphics {

    private Game game;

    private Actor playButton;
    private Actor rankingButton;
    private Actor settingsButton;
    private Actor aboutButton;
    private Actor logo;
    private Actor background;

    public Menu(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(getStage());
        this.playButton = new Button("menu\\play_button.png", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.7f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.rankingButton = new Button("menu\\ranking_button.png", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.5f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.settingsButton = new Button("menu\\settings_button.png", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.3f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.aboutButton = new Button("menu\\about_button.png", Constants.WORLD_WIDTH * 0.05f, Constants.WORLD_HEIGHT * 0.1f, Constants.WORLD_WIDTH * 0.36f, Constants.WORLD_HEIGHT * 0.18f);
        this.logo = new Image(new Texture("menu\\bb_logo.png"));
        this.background = new Image(new Texture("menu\\bb_bg.png"));
        setUpActors();
    }

    private void setUpActors() {
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);
        logo.setBounds(Constants.WORLD_WIDTH * 0.45f, Constants.WORLD_HEIGHT * 0.2f, Constants.WORLD_WIDTH * 0.5f, Constants.WORLD_HEIGHT * 0.7f);
        getStage().addActor(logo);
        getStage().addActor(this.playButton);
        getStage().addActor(this.rankingButton);
        getStage().addActor(this.settingsButton);
        getStage().addActor(this.aboutButton);
    }
}
