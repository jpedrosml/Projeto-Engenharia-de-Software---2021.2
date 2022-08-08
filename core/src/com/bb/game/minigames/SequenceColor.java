package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.List;

// Classe que especifica a cor da sequência como objeto.
public class SequenceColor extends Actor {

    // Imagem normal da cor.
    private Sprite faceDark;

    //Imagem destacada da cor.
    private Sprite faceBright;

    // Identificador da cor.
    private int id;

    // Variável que diz se a cor atual é a normal ou a destacada.
    private boolean isBright;

    // Tamanho normal da cor.
    private final float NORMAL_SCALE = 1f;

    // Tamanho da cor quando clicada.
    private final float SCALE_ON_CLICK = 1.1f;

    // Lista que guarda as texturas de cada cor.
    private static final List<Texture> colorList = List.of(
        new Texture("sequence\\blue_idle.png"),
        new Texture("sequence\\purple_idle.png"),
        new Texture("sequence\\red_idle.png"),
        new Texture("sequence\\green_idle.png"),
        new Texture("sequence\\yellow_idle.png"),
        new Texture("sequence\\blue_highlight.png"),
        new Texture("sequence\\purple_highlight.png"),
        new Texture("sequence\\red_highlight.png"),
        new Texture("sequence\\green_highlight.png"),
        new Texture("sequence\\yellow_highlight.png")
    );

    // Cria uma cor baseada nas informações recebidas.
    public SequenceColor(int id, float x, float y, float width, float height) {
        this.id = id;
        this.setBounds(x, y, width, height);
        this.setTouchable(Touchable.enabled);
        this.faceDark = new Sprite(colorList.get(this.id));
        this.faceDark.setBounds(x, y, width, height);
        this.faceBright = new Sprite(colorList.get(this.id+5));
        this.faceBright.setBounds(x, y, width, height);

        // Muda o tamanho quando o usuário toca na cor e quando deixa de tocar nela.
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setBright();
                faceBright.setScale(SCALE_ON_CLICK);
                faceDark.setScale(SCALE_ON_CLICK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setDark();
                faceDark.setScale(NORMAL_SCALE);
                faceBright.setScale(NORMAL_SCALE);
            }
        });
    }

    // Metódo que desenha a cor baseada se ela está definida como normal ou destacada no momento.
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isBright)
            faceBright.draw(batch);
        else
            faceDark.draw(batch);
    }

    // Define que a cor está destacada.
    public void setBright() {
        this.isBright = true;
    }

    // Define que a cor está normal.
    public void setDark() {
        this.isBright = false;
    }

    public int getId() {
        if(this.id < 5) {
            return this.id;
        } else {
            return this.id-5;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}
