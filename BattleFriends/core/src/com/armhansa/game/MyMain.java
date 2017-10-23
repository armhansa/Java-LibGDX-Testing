package com.armhansa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MyMain extends Game {
	public SpriteBatch batch;
	public static int width;
	public static int height;
	public OrthographicCamera cam;
	
	public static float touchX[] = new float[5];
	public static float touchY[] = new float[5];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(1600, 900);
		this.setScreen(new MyGame(this));
		cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);
		cam.update();
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
	}

	@Override
	public void render () {
		super.render(); //important!
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public BitmapFont setStyleFont(int size, Color color) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arista.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.color = color;
		return generator.generateFont(parameter);
	}

	public void updateMouse() {
		for(int i=0; i<5; i++) {
			if(Gdx.input.isTouched(i)) {
				MyMain.touchX[i] = Gdx.input.getX(i)*1200/MyMain.width;
				MyMain.touchY[i] = (MyMain.height-Gdx.input.getY(i))*700/MyMain.height;
			}
		}
	}
}
