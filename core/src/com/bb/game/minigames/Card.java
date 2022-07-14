package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Map;

public class Card extends Actor {
    private Sprite faceDown;
    private Sprite faceUp;
    private int id;
    private boolean revealed;

    private static final Texture cardBack = new Texture("memory\\cardBack.png");
    private static final Map<Integer, Texture> cardMap = Map.of(
            0, new Texture("memory\\card0.png"),
            1, new Texture("memory\\card1.png"),
            2, new Texture("memory\\card2.png"),
            3, new Texture("memory\\card3.png"),
            4, new Texture("memory\\card4.png"),
            5, new Texture("memory\\card5.png"),
            6, new Texture("memory\\card6.png")
    );

    public Card(int id, float x, float y, float width, float height) {
        this.id = id;
        this.revealed = false;
        this.faceDown = new Sprite(cardBack);
        this.faceDown.setBounds(x, y, width, height);
        this.faceUp = new Sprite(cardMap.get(this.id));
        this.faceUp.setBounds(x, y, width, height);
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reveal();
                System.out.println(id);
            }
        });
    }

    public Card(float x, float y, float width, float height){
        this(0, x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(revealed)
            faceUp.draw(batch);
        else
            faceDown.draw(batch);
    }

    public void reveal(){
        this.revealed = true;
    }

    public void hide() {
        this.revealed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.faceUp.setTexture(cardMap.get(this.id));
    }

    public boolean isRevealed() {
        return revealed;
    }
}
