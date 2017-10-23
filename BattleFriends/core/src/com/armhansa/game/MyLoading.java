package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MyLoading implements Screen {
	final MyMain MAIN;
	
	public MyLoading(MyMain main) {
		this.MAIN = main;
		// if Clicked Start Button
		MAIN.setScreen(new MyGame(MAIN));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	}

	@Override
	public void dispose() {
		
	}
	@Override
	public void resize(int width, int height) {
		MyMain.height = height;
		MyMain.width = width;
	}
	@Override
	public void pause() { }
	@Override
	public void resume() { }
	@Override
	public void show() { }
	@Override
	public void hide() { }

}
