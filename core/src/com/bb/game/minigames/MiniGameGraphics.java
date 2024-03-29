package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.GameManager;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;

public abstract class MiniGameGraphics extends BrainyBeansGraphics {
    private Integer score;
    private GameManager manager;
    private float timer;
    private Label scoreIndicator;
    private Label timerIndicator;

    public final float TIME_LIMIT = 60;
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    MiniGameGraphics(){
        this.score = 0;
        this.timer = 0;
    }

    @Override
    public void render(float delta) {
        this.timer += delta;
        this.timerIndicator.setText(String.format("%.0f", TIME_LIMIT - this.timer));
        if(this.timer >= TIME_LIMIT){
            conclude();
        } else if(this.timer > TIME_LIMIT - 10f){
            this.timerIndicator.setStyle(new Label.LabelStyle(Fonts.COMIC_NEUE, Color.RED));
        }else if(this.timer > TIME_LIMIT/2f){
            this.timerIndicator.setStyle(new Label.LabelStyle(Fonts.COMIC_NEUE, Color.YELLOW));
        }
        this.scoreIndicator.setText(score.toString());
        super.render(delta);
    }

    public void setUpPanel() {
        final Color PALE_YELLOW = new Color(243f/255f, 248f/255f, 146f/255f, 1);
        final float FONT_SCALE = 1.5f;
        final Label.LabelStyle STYLE_1 = new Label.LabelStyle(Fonts.COMIC_NEUE, PALE_YELLOW);

        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);
        Label scoreText = new Label("Score: ", STYLE_1);
        scoreText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.85f);
        scoreText.setFontScale(FONT_SCALE);
        getStage().addActor(scoreText);
        Label timeLeftText = new Label("Time left: ", STYLE_1);
        timeLeftText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.75f);
        timeLeftText.setFontScale(FONT_SCALE);
        getStage().addActor(timeLeftText);
        this.scoreIndicator = new Label(this.score.toString(), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        this.scoreIndicator.setPosition(scoreText.getX() + (scoreText.getWidth() * FONT_SCALE), scoreText.getY());
        this.scoreIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.scoreIndicator);
        this.timerIndicator = new Label(String.format("%.0f", this.timer), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.GREEN));
        this.timerIndicator.setPosition(timeLeftText.getX() + (timeLeftText.getWidth() * FONT_SCALE), timeLeftText.getY());
        this.timerIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.timerIndicator);
    }

    public void updateScore(int increment){
        this.score += increment;
    }

    public void setManager(GameManager gameManager){
        this.manager = gameManager;
    }

    public void conclude(){
        this.manager.notifyConclusion(this.score);
    }
}