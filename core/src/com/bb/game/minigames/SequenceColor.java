package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.List;

public class SequenceColor extends Actor {

    private Sprite faceDark;
    private Sprite faceBright;
    private int id;
    private boolean isBright;

    private final float SCALE_ON_CLICK = 1.1f;
    private final float NORMAL_SCALE = 1f;

    private static final List<Texture> colorList = List.of(
        new Texture("sequence\\images\\blue_idle.png"),
        new Texture("sequence\\images\\purple_idle.png"),
        new Texture("sequence\\images\\red_idle.png"),
        new Texture("sequence\\images\\green_idle.png"),
        new Texture("sequence\\images\\yellow_idle.png"),
        new Texture("sequence\\images\\blue_highlight.png"),
        new Texture("sequence\\images\\purple_highlight.png"),
        new Texture("sequence\\images\\red_highlight.png"),
        new Texture("sequence\\images\\green_highlight.png"),
        new Texture("sequence\\images\\yellow_highlight.png")
    );

    public SequenceColor(int id, float x, float y, float width, float height) {
        this.id = id;
        this.faceDark = new Sprite(colorList.get(this.id));
        this.faceBright = new Sprite(colorList.get(this.id+5));
        this.faceDark.setBounds(x, y, width, height);
        this.faceBright.setBounds(x, y, width, height);

        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isBright)
            faceBright.draw(batch);
        else
            faceDark.draw(batch);
    }

    public void setBright() {
        this.isBright = true;
    }

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
