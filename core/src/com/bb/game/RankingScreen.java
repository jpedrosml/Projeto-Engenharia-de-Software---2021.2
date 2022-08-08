package com.bb.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;

import java.util.Collections;
import java.util.List;

public class RankingScreen extends BrainyBeansGraphics{
    private Game game;
    private List<Integer> ranking;

    private static final Texture backgroundTexture = new Texture("animations\\background.png");
    private static final Texture effectsTexture = new Texture("animations\\effects.png");

    public RankingScreen(List<Integer> ranking, Game game){
        this.game = game;
        this.ranking = ranking;
        setUpStage();
    }

    private void setUpStage() {
        Image background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);

        Image effects = new Image(effectsTexture);
        effects.setPosition(Constants.WORLD_WIDTH * -0.1f, Constants.WORLD_WIDTH * -0.45f);
        effects.setOrigin(effects.getWidth()/2f, effects.getHeight()/2f);
        effects.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(30f, 1)));
        getStage().addActor(effects);

        if(!ranking.isEmpty()) {
            float y = Constants.WORLD_HEIGHT;
            for (int i = 0; i < ranking.size(); i++) {
                Label text = new Label("Rank " + i + ":" + ranking.get(0), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
                text.setFontScale(5f);
                text.setPosition(Constants.WORLD_WIDTH / 2f - text.getFontScaleX() * text.getWidth() / 2f, y - Constants.WORLD_HEIGHT * 0.1f * i);
                getStage().addActor(text);
            }
        } else {
            Label text = new Label("No score saved.", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
            text.setFontScale(5f);
            text.setPosition(Constants.WORLD_WIDTH / 2f - text.getFontScaleX() * text.getWidth() / 2f, Constants.WORLD_HEIGHT / 2f - text.getFontScaleY() * text.getHeight() / 2f);
            getStage().addActor(text);
        }

        getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Menu(game));
            }
        });
    }
}
