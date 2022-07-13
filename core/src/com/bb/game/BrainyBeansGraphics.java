package com.bb.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bb.game.utils.Constants;

public abstract class BrainyBeansGraphics extends ScreenAdapter {
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    public BrainyBeansGraphics(){
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        this.stage = new Stage(this.viewport);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
