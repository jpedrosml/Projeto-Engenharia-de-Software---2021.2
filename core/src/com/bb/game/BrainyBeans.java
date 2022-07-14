package com.bb.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.bb.game.menu.Menu;

public class BrainyBeans extends Game {

	private Screen screen;

	@Override
	public void create () {
		setScreen(new Menu(this));
		this.screen = getScreen();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		if(this.screen != null) {
			this.screen.dispose();
		}
		this.screen = screen;
	}
}
