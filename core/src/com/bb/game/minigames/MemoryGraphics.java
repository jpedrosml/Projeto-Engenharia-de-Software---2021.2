package com.bb.game.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import com.bb.game.utils.Fonts;
import static com.bb.game.utils.Volume.*;

import java.util.ArrayList;
import java.util.List;

public class MemoryGraphics extends MiniGameGraphics{

    private List<Card> cards;
    private MemoryLogic logic;
    private Image hand;
    private Card lastClickedCard;
    private Label scoreIndicator;
    private Label timerIndicator;

    private final float HAND_ANIMATION_DURATION = 0.25f;
    private final float HAND_ORIGINAL_POSX = Constants.WORLD_WIDTH * 0.95f;
    private final float HAND_ORIGINAL_POSY = Constants.WORLD_HEIGHT * (-0.72f);
    private static final Texture handTexture = new Texture("memory\\arm.png");
    private static final Texture backgroundTexture = new Texture("memory\\table.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");
    private static final Sound sfx1 = Gdx.audio.newSound(Gdx.files.internal("memory\\sfx1.wav"));
    private static final Sound sfx2 = Gdx.audio.newSound(Gdx.files.internal("memory\\sfx2.wav"));
    private static final Music bgmusic = Gdx.audio.newMusic(Gdx.files.internal("memory\\bgmusic.mp3"));


    MemoryGraphics(Difficulty difficulty){
        this.logic = new MemoryLogic(difficulty);
        this.hand = new Image(handTexture);
        this.hand.setBounds(HAND_ORIGINAL_POSX, HAND_ORIGINAL_POSY, Constants.WORLD_WIDTH * 0.26f, Constants.WORLD_HEIGHT * 1.3f);
        this.hand.setRotation(15f);
        this.hand.setTouchable(Touchable.disabled);
        bgmusic.play();
        bgmusic.setVolume(MUSIC_VOLUME);
        bgmusic.setLooping(true);
        initializeCards();
        setUpStage();
        reset();
    }

    private void reset() {
        this.logic.reset();
        for(int i = 0; i < this.logic.getCards().size(); i++){
            this.cards.get(i).setId(this.logic.getCards().get(i));
            this.cards.get(i).hide();
        }
    }

    private void setUpStage() {
        setUpActors();
        setUpText();
        getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof Card){
                    Card clickedCard = (Card) event.getTarget();
                    if(!clickedCard.isRevealed()){
                        hand.clearActions();
                        hand.addAction(Actions.moveTo(HAND_ORIGINAL_POSX, HAND_ORIGINAL_POSY, HAND_ANIMATION_DURATION));
                        hand.setPosition(clickedCard.getCenterX() - hand.getImageWidth()*0.25f, clickedCard.getCenterY() - hand.getImageHeight()*0.8f);
                        clickedCard.reveal();
                        if(lastClickedCard == null) {
                            lastClickedCard = clickedCard;
                        } else {
                            makePlay(clickedCard);
                        }
                    }
                }
            }
        });
    }

    private void setUpActors() {
        Actor background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);
        for(Actor card: this.cards){
            getStage().addActor(card);
        }
        getStage().addActor(this.hand);
        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);
    }

    private void initializeCards() {
        this.cards = new ArrayList<>();
        float x = 0f;
        float y = Constants.WORLD_HEIGHT * 0.37f;
        float width = Constants.WORLD_WIDTH * 0.1f;
        float height = Constants.WORLD_HEIGHT * 0.27f;

        for(int i = 0; i < this.logic.getCards().size(); i++){
            if(i % 6 == 0)
                x = Constants.WORLD_WIDTH * 0.03f;
            this.cards.add(new Card(x, y, width, height));
            x = x + Constants.WORLD_WIDTH * 0.14f;
            if(i == 5){
                y = Constants.WORLD_HEIGHT * 0.68f;
            }else if(i == 11){
                y = Constants.WORLD_HEIGHT * 0.05f;
            }
        }
    }

    @Override
    public void render(float delta) {
        this.logic.incrementTimer(delta);
        super.render(delta);
    }

    private void makePlay(final Card clickedCard){
        final Card lcc = this.lastClickedCard;
        this.lastClickedCard = null;
        int points = this.logic.compareCards(lcc.getId(), clickedCard.getId());
        float HIDE_DELAY = 0.25f;
        if(points == 0){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    lcc.hide();
                    clickedCard.hide();
                }
            }, HIDE_DELAY);
        } else {
            updateScore(points);
            if(this.logic.noCardsLeft()) {
                sfx2.play(SFX_VOLUME);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, HIDE_DELAY);
            } else {
                sfx1.play(SFX_VOLUME);
            }
        }
    }
}
