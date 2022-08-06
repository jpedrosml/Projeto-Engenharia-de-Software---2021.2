package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.HashMap;
import java.util.Map;

/*
    Classe responsável pela criação do botão numérico como objeto.
*/
public class ButtonNumber extends Actor{
    /*
        Imagem do botão oculto.
     */
    private Sprite hiddenButton;
    /*
        Imagem do botão visível.
    */
    private Sprite visibleButton;
    /*
        Inteiro que guarda o identificador
        do botão.
     */
    private int id;
    /*
        Booleano que diz se o botão está revelado.
     */
    private boolean revealed;

    private final float SCALE_ON_CLICK = 0.95f;
    private static final Texture cardBack = new Texture("memory\\cardBack.png");
    /*
        Mapa que guarda o número de cada botão.
     */
    private static final Map<Integer, Texture> buttonMap = new HashMap<Integer,Texture>(){
        {
            put(0, new Texture("chimp\\number1.png"));
            put(1, new Texture("chimp\\number1.png"));
            put(2, new Texture("chimp\\number2.png"));
            put(3, new Texture("chimp\\number3.png"));
            put(4, new Texture("chimp\\number4.png"));
            put(5, new Texture("chimp\\number5.png"));
            put(6, new Texture("chimp\\number6.png"));
            put(7, new Texture("chimp\\number7.png"));
            put(8, new Texture("chimp\\number8.png"));
            put(9, new Texture("chimp\\number9.png"));
            put(10, new Texture("chimp\\number10.png"));
            put(11, new Texture("chimp\\number11.png"));
            put(12, new Texture("chimp\\number12.png"));
            put(13, new Texture("chimp\\number13.png"));
            put(14, new Texture("chimp\\number14.png"));
            put(15, new Texture("chimp\\number15.png"));
            put(16, new Texture("chimp\\number16.png"));
            put(17, new Texture("chimp\\number17.png"));
            put(18, new Texture("chimp\\number18.png"));
        }
    };

    /*
        Responsável por criar um botão de acordo com os valores passados.
     */
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

