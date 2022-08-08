package com.bb.game.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.bb.game.utils.Constants;

public class BrainyBeansSlider extends Slider {
    public BrainyBeansSlider(float min, float max, float stepSize, boolean vertical, SliderStyle style){
        super(min, max, stepSize, vertical, style);
    }

    @Override
    public float getPrefWidth() {
        return Constants.WORLD_WIDTH * 0.6f;
    }
}
