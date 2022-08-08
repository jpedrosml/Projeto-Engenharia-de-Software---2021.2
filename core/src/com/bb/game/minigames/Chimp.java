package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Chimp extends Actor {

    private Sprite idleChimp;

    private Sprite laughingChimp;

    private boolean laughing;

    private static final Texture idleFace = new Texture("chimp\\monkey_idle.png");
    private static final Texture laughingFace = new Texture("chimp\\monkey_laugh.png");

    public Chimp(float x, float y, float width, float height) {
        this.setBounds(x,y,width,height);
        this.setTouchable(Touchable.enabled);
        this.laughing = false;

        this.idleChimp = new Sprite(idleFace);
        this.idleChimp.setBounds(x,y,width,height);
        this.laughingChimp = new Sprite(laughingFace);
        this.laughingChimp.setBounds(x,y,width,height);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                idleChimp.setScale(1.1f);
                laughingChimp.setScale(1.1f);
                laughing = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                idleChimp.setScale(1f);
                laughingChimp.setScale(1f);
                laughing = false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(laughing)
            laughingChimp.draw(batch);
        else
            idleChimp.draw(batch);
    }

    public void laugh() {
        this.laughing = true;
    }

    public void idle() {
        this.laughing = false;
    }
}
