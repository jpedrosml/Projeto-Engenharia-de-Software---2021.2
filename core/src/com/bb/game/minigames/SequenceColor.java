package com.bb.game.minigames;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SequenceColor extends Actor {

    private int id;
    private Sprite face;

    private final float SCALE_ON_CLICK = 0.95f;
    private final float NORMAL_SCALE = 1f;

    /*
    private static final Map<Integer, Texture> colorMap = Map.of(
        0, new Texture("sequence\\color0.png"),
        1, new Texture("sequence\\color1.png"),
        2, new Texture("sequence\\color2.png"),
        3, new Texture("sequence\\color3.png"),
    );
     */

    public SequenceColor(int id, float x, float y, float width, float height) {
        this.id = id;
        // this.face = new Sprite(colorMap.get(this.id));
        this.face.setBounds(x, y, width, height);

        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                face.setScale(SCALE_ON_CLICK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                face.setScale(NORMAL_SCALE);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        face.draw(batch);
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
