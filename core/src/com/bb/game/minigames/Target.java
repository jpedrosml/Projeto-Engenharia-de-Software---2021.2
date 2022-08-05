package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class Target extends Actor {

    private Sprite sprite;
    private float radius;
    private float centerX;
    private float centerY;

    private static final float ratio = 3.4f;
    private static final Texture texture = new Texture("aim\\target.png");
    public static final float FALL_DURATION = 0.2f;

    public Target(float radius){
        this.sprite = new Sprite(texture);
        this.radius = radius;
        setPosition(0, 0);
        sprite.setCenterY(0f);
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(inTarget(x, y)){
                    addAction(Actions.scaleBy(1f, 0.1f, FALL_DURATION));
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            setScale(1f);
                        }
                    }, FALL_DURATION);
                }
                return true;
            }
        });
    }

    public void setPosition(float x, float y){
        float diameter = radius * 2;
        float height = diameter * this.ratio;
        setBounds(x, y, diameter, diameter);
        sprite.setBounds(x, y + diameter - height, diameter, height);
        this.centerX = x + radius;
        this.centerY = y + radius;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public float distanceToCenter(float x, float y) {
        return (float) Math.hypot(Math.abs(centerX - x), Math.abs(centerY - y));
    }

    public boolean inTarget(float x, float y){
        return distanceToCenter(x, y) <= this.radius;
    }

    @Override
    protected void scaleChanged() {
        sprite.setScale(getScaleX(), getScaleY());
        super.scaleChanged();
    }
}
