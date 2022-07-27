package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Map;

public class SequenceColor extends Actor {

    private int id;
    private Sprite faceDark;
    private Sprite faceBright;
    private boolean isBright;

    private final float SCALE_ON_CLICK = 0.95f;
    private final float NORMAL_SCALE = 1f;


    private static final Map<Integer, Texture> colorMap = Map.of(
        0, new Texture("sequence\\images\\dark-red.png"),
        1, new Texture("sequence\\images\\dark-green.png"),
        2, new Texture("sequence\\images\\dark-blue.png"),
        3, new Texture("sequence\\images\\dark-yellow.png"),
        4, new Texture("sequence\\images\\dark-purple.png"),
        5, new Texture("sequence\\images\\bright-red.png"),
        6, new Texture("sequence\\images\\bright-green.png"),
        7, new Texture("sequence\\images\\bright-blue.png"),
        8, new Texture("sequence\\images\\bright-yellow.png"),
        9, new Texture("sequence\\images\\bright-purple.png")
    );

    public SequenceColor(int id, float x, float y, float width, float height) {
        this.id = id;
        this.faceDark = new Sprite(colorMap.get(this.id));
        this.faceBright = new Sprite(colorMap.get(this.id+5));
        this.faceDark.setBounds(x, y, width, height);
        this.faceBright.setBounds(x, y, width, height);

        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setBright();
                faceBright.setScale(SCALE_ON_CLICK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setDark();
                faceDark.setScale(NORMAL_SCALE);
            }
        });
    }

    /* public SequenceColor(float x, float y, float width, float height) {
        this(0,x,y,width,height);
    } */

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
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCenterX() {
        return getX() + (getWidth()/2f);
    }

    public float getCenterY() {
        return getY() + (getHeight()/2f);
    }

}
