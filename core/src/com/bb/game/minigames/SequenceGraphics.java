package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;

import java.util.List;

public class SequenceGraphics extends MiniGameGraphics {
    private List<SequenceColor> colors;
    private SequenceColor lastClickedColor;
    private SequenceLogic logic;
    private Image hand;
    private Label scoreIndicator;
    private Label timerIndicator;

    private final float HAND_ANIMATION_DURATION = 0.25f;
    private final float HAND_ORIGINAL_POSX = Constants.WORLD_WIDTH * 0.95f;
    private final float HAND_ORIGINAL_POSY = Constants.WORLD_HEIGHT * (-0.72f);
    private static final Texture handTexture = new Texture("memory\\arm.png");
    private static final Texture backgroundTexture = new Texture("memory\\table.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    private void reset() {
        this.logic.reset();
        for(int i = 0; i < this.logic.getSequenceSize(); i++) {
            this.colors.get(i).setId(this.logic.getFromSequence(i));
        }
    }

    private void setUpStage() {
        setUpActors();
        setUpText();
        getStage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof SequenceColor) {
                    SequenceColor clickedColor = (SequenceColor) event.getTarget();
                    hand.clearActions();
                    hand.addAction(Actions.moveTo(HAND_ORIGINAL_POSX, HAND_ORIGINAL_POSY, HAND_ANIMATION_DURATION));
                    if(lastClickedColor == null) {
                        lastClickedColor = clickedColor;
                    } else {

                    }
                }
            }
        });
    }

    private void setUpActors() {
        Actor background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);
        for(Actor color: this.colors) {
            getStage().addActor(color);
        }
        getStage().addActor(this.hand);
        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);
    }

    private void setUpText() {
        final Color PALE_YELLOW = new Color(243f/255f, 248f/255f, 146f/255f, 1);
        final float FONT_SCALE = 1.5f;
        final Label.LabelStyle STYLE_1 = new Label.LabelStyle(Fonts.COMIC_NEUE, PALE_YELLOW);

        Label scoreText = new Label("Score: ", STYLE_1);
        scoreText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.85f);
        scoreText.setFontScale(FONT_SCALE);
        getStage().addActor(scoreText);
        Label timeLeftText = new Label("Time left: ", STYLE_1);
        timeLeftText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.75f);
        timeLeftText.setFontScale(FONT_SCALE);
        getStage().addActor(timeLeftText);
        this.scoreIndicator = new Label(getScore().toString(), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        this.scoreIndicator.setPosition(scoreText.getX() + (scoreText.getWidth() * FONT_SCALE), scoreText.getY());
        this.scoreIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.scoreIndicator);
        this.timerIndicator = new Label(String.format("%.0f", getTimer()), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.GREEN));
        this.timerIndicator.setPosition(timeLeftText.getX() + (timeLeftText.getWidth() * FONT_SCALE), timeLeftText.getY());
        this.timerIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.timerIndicator);
    }

}
