package com.bb.game.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;
import com.bb.game.utils.Volume;

public class Settings extends BrainyBeansGraphics {
    private Slider sfxSlider;
    private Slider musicSlider;
    private Game game;

    private static final Texture sliderTexture = new Texture("menu\\slider.png");
    private static final Texture knobTexture = new Texture("menu\\slider_indicator.png");
    private static final Texture backgroundTexture = new Texture("menu\\bb_bg.png");

    Settings(Game game){
        this.game = game;
        Sprite slider = new Sprite(sliderTexture);
        Sprite knob = new Sprite(knobTexture);
        Slider.SliderStyle style = new Slider.SliderStyle(new SpriteDrawable(slider),new SpriteDrawable(knob));
        sfxSlider = new BrainyBeansSlider(0f, 1f, 0.01f, false, style);
        musicSlider = new BrainyBeansSlider(0f, 1f, 0.01f, false, style);
        sfxSlider.setValue(Volume.SFX_VOLUME);
        musicSlider.setValue(Volume.MUSIC_VOLUME);
        setUpStage();
    }

    private void setUpStage() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Fonts.COMIC_NEUE, Color.BLACK);
        Image background = new Image(backgroundTexture);
        background.setBounds(0,0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);

        Label settingsLabel = new Label("Settings", labelStyle);
        settingsLabel.setFontScale(3f);
        settingsLabel.setPosition(Constants.WORLD_WIDTH/2f - settingsLabel.getFontScaleX() * settingsLabel.getWidth()/2f, Constants.WORLD_HEIGHT*0.9f);
        getStage().addActor(settingsLabel);

        Label sfxLabel = new Label("SFX Volume", labelStyle);
        sfxLabel.setFontScale(2f);
        sfxLabel.setPosition(Constants.WORLD_WIDTH * 0.2f, Constants.WORLD_HEIGHT*0.7f);
        getStage().addActor(sfxLabel);

        Label musicLabel = new Label("Music Volume", labelStyle);
        musicLabel.setFontScale(2f);
        musicLabel.setPosition(Constants.WORLD_WIDTH * 0.2f, Constants.WORLD_HEIGHT*0.4f);
        getStage().addActor(musicLabel);

        sfxSlider.setPosition(Constants.WORLD_WIDTH * 0.2f, Constants.WORLD_HEIGHT*0.5f);
        getStage().addActor(sfxSlider);

        musicSlider.setPosition(Constants.WORLD_WIDTH * 0.2f, Constants.WORLD_HEIGHT*0.2f);
        getStage().addActor(musicSlider);

        Button returnButton = new Button("return", Constants.WORLD_WIDTH * 0.8f, Constants.WORLD_HEIGHT * 0.05f, Constants.WORLD_WIDTH * 0.18f, Constants.WORLD_HEIGHT * 0.09f);
        getStage().addActor(returnButton);

        getStage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if(event.getTarget() instanceof Button)
                    game.setScreen(new Menu(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Volume.SFX_VOLUME = sfxSlider.getValue();
        Volume.MUSIC_VOLUME = musicSlider.getValue();
        super.render(delta);
    }
}
