package com.bb.game.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Map;

public class Button extends Actor {
    Sprite sprite;

    private static final float BUTTON_SCALE_ON_ANIMATION = 0.9f;
    private static final Map<String, Texture> buttonMap = Map.of(
            "play", new Texture("menu\\play_button.png"),
            "ranking", new Texture("menu\\ranking_button.png"),
            "settings", new Texture("menu\\settings_button.png"),
            "about",new Texture("menu\\about_button.png"),
            "return",new Texture("menu\\return_button.png")
    );

    public Button(String name, float x, float y, float width, float height) {
        setName(name);
        this.sprite = new Sprite(buttonMap.get(name));
        sprite.setBounds(x, y, width, height);
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setScale(BUTTON_SCALE_ON_ANIMATION);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               sprite.setScale(1, 1);
            }

        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
