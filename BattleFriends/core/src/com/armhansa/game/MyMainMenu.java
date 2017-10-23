package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MyMainMenu implements Screen {
	final MyMain MAIN;
    
    public MyMainMenu(MyMain main) {
    	this.MAIN = main;
    	
    }
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// if Clicked Start Button
		MAIN.setScreen(new MyLoading(MAIN));
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
