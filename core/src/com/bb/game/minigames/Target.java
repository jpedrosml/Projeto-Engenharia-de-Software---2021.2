package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Target extends Image {

    private float radius;
    private float centerX;
    private float centerY;

    private static final float ratio = 8f;
    private static final Texture texture = new Texture("aim\\target.png");
    public static final float FALL_DURATION = 0.5f;

    public Target(float radius){
        super(texture);
        this.radius = radius;
        setPosition(0, 0);
        setTouchable(Touchable.enabled);
    }

    public void setPosition(float x, float y){
        float diameter = radius * 2;
        float height = diameter * ratio;
        setBounds(x, y + diameter - height, diameter, height);
        this.centerX = x + radius;
        this.centerY = y + radius;
    }

    public float distanceToCenter(float x, float y) {
        return (float) Math.hypot(Math.abs(centerX - x), Math.abs(centerY - y));
    }

    public boolean inTarget(float x, float y){
        return distanceToCenter(x, y) <= this.radius;
    }
}
