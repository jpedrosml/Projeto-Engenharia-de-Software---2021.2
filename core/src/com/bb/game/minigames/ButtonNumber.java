package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Map;

public class ButtonNumber extends Actor{
    private Sprite hiddenButton;
    private Sprite visibleButton;
    private int id;
    private boolean revealed;

    private final float SCALE_ON_CLICK = 0.95f;
    private static final Texture cardBack = new Texture("memory\\cardBack.png");
    private static final Map<Integer, Texture> buttonMap = Map.of(
            0, new Texture("chimp\\number1.png"),
            1, new Texture("chimp\\number1.png"),
            2, new Texture("chimp\\number2.png"),
            3, new Texture("chimp\\number3.png"),
            4, new Texture("chimp\\number4.png"),
            5, new Texture("chimp\\number5.png"),
            6, new Texture("chimp\\number6.png"),
            7, new Texture("chimp\\number7.png"),
            8, new Texture("chimp\\number8.png"),
            9, new Texture("chimp\\number9.png")
    );

    public ButtonNumber(int id, float x, float y, float width, float height) {
        this.id = id;
        this.revealed = true;
        this.hiddenButton = new Sprite(cardBack);
        this.hiddenButton.setBounds(x, y, width, height);
        this.visibleButton = new Sprite(buttonMap.get(this.id));
        this.visibleButton.setBounds(x, y, width, height);
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hiddenButton.setScale(SCALE_ON_CLICK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hiddenButton.setScale(1f);
            }
        });
    }

    public ButtonNumber(float x, float y, float width, float height){
        this(0, x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(revealed)
            hiddenButton.draw(batch);

        else
            visibleButton.draw(batch);
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
        this.visibleButton.setTexture(buttonMap.get(this.id));
    }

    public boolean isRevealed() {
        return revealed;
    }

    public float getCenterX() {
        return getX() + (getWidth()/2f);
    }

    public float getCenterY() {
        return getY() + (getHeight()/2f);
    }
}

