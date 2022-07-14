package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class MemoryGraphics extends MiniGameGraphics{

    private List<Card> cards;
    private MemoryLogic logic;
    private Actor hand;
    private Card lastClickedCard;

    private static final Texture handTexture = new Texture("memory\\arm.png");
    private static final Texture backgroundTexture = new Texture("memory\\table.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    MemoryGraphics(Difficulty difficulty){
        this.logic = new MemoryLogic(difficulty);
        this.hand = new Image(handTexture);
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
        Actor background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);
        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);
        for(Actor card: this.cards){
            getStage().addActor(card);
        }
        this.hand = new Image(handTexture);
        this.hand.setBounds(Constants.WORLD_WIDTH * 0.9f, Constants.WORLD_HEIGHT * (-0.35f), Constants.WORLD_WIDTH * 0.26f, Constants.WORLD_HEIGHT * 0.87f);
        this.hand.setRotation(15f);
        getStage().addActor(this.hand);
        getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof Card){
                    Card clickedCard = (Card) event.getTarget();
                    System.out.println(clickedCard.getId());
                    if(!clickedCard.isRevealed()){
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

    private void initializeCards() {
        this.cards = new ArrayList<>();
        float x = Constants.WORLD_WIDTH * 0.03f;
        float y = Constants.WORLD_HEIGHT * 0.37f;
        float width = Constants.WORLD_WIDTH * 0.1f;
        float height = Constants.WORLD_HEIGHT * 0.27f;

        for(int i = 0; i < this.logic.getCards().size(); i++){
            this.cards.add(new Card(x, y, width, height));
            x += Constants.WORLD_WIDTH * 0.14f;
            if(i == 5){
                y = Constants.WORLD_HEIGHT * 0.68f;
            }else if(i == 11){
                y = Constants.WORLD_HEIGHT * 0.05f;
            }
        }
    }

    private void makePlay(Card clickedCard){
        int playScore = this.logic.compareCards(lastClickedCard.getId(), clickedCard.getId());
        if(playScore == 0){
            lastClickedCard.hide();
            clickedCard.hide();
            lastClickedCard = null;
        }else{
            updateScore(playScore);
        }
    }
}
