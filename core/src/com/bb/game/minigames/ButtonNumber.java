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

    private Sprite emptyButton;

    /*
        Inteiro que guarda o identificador
        do botão.
     */
    private int id;
    /*
        Booleano que diz se o botão está revelado.
     */
    private boolean hidden;

    private boolean empty;

    private final float SCALE_ON_CLICK = 0.95f;
    private final float NORMAL_SCALE = 1f;
    private static final Texture hiddenFace = new Texture("chimp\\button.png");
    private static final Texture emptyFace = new Texture("chimp\\emptyFace.png");

    /*
        Mapa que guarda o número de cada botão.
     */
    private static final Map<Integer, Texture> buttonMap = new HashMap<Integer,Texture>(){
        {
            put(0, new Texture("chimp\\number_0.png"));
            put(1, new Texture("chimp\\number_1.png"));
            put(2, new Texture("chimp\\number_2.png"));
            put(3, new Texture("chimp\\number_3.png"));
            put(4, new Texture("chimp\\number_4.png"));
            put(5, new Texture("chimp\\number_5.png"));
            put(6, new Texture("chimp\\number_6.png"));
            put(7, new Texture("chimp\\number_7.png"));
            put(8, new Texture("chimp\\number_8.png"));
            put(9, new Texture("chimp\\number_9.png"));
            put(10, new Texture("chimp\\number_10.png"));
            put(11, new Texture("chimp\\number_11.png"));
            put(12, new Texture("chimp\\number_12.png"));
            put(13, new Texture("chimp\\number_13.png"));
            put(14, new Texture("chimp\\number_14.png"));
            put(15, new Texture("chimp\\number_15.png"));
            put(16, new Texture("chimp\\number_16.png"));
            put(17, new Texture("chimp\\number_17.png"));
            put(18, new Texture("chimp\\number_18.png"));
        }
    };

    /*
        Responsável por criar um botão de acordo com os valores passados.
     */
    public ButtonNumber(int id, float x, float y, float width, float height) {
        this.id = id;
        this.hidden = false;
        this.empty = false;
        this.setBounds(x, y, width, height);
        this.setTouchable(Touchable.enabled);

        this.emptyButton = new Sprite(emptyFace);
        this.emptyButton.setBounds(x, y, width, height);
        this.hiddenButton = new Sprite(hiddenFace);
        this.hiddenButton.setBounds(x, y, width, height);
        this.visibleButton = new Sprite(buttonMap.get(this.id));
        this.visibleButton.setBounds(x, y, width, height);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                visibleButton.setScale(SCALE_ON_CLICK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                visibleButton.setScale(NORMAL_SCALE);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(hidden) {
            hiddenButton.draw(batch);
        } else if(empty)
            emptyButton.draw(batch);
        else {
            visibleButton.draw(batch);
        }
    }

    public void show() {
        this.hidden = false;
        this.empty = false;
        this.setTouchable(Touchable.enabled);
    }

    public void hide() {
        this.hidden = true;
        this.empty = false;
    }

    public void empty() {
        this.empty = true;
        this.hidden = false;
        this.setTouchable(Touchable.disabled);
    }

    public void changePosition(float x, float y, float width, float height) {
        this.setBounds(x,y,width,height);
        this.emptyButton.setBounds(x, y, width, height);
        this.hiddenButton.setBounds(x, y, width, height);
        this.visibleButton.setBounds(x, y, width, height);
    }

    public int getId() {
        return id;
    }
}

