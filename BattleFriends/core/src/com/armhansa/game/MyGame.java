package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MyGame implements Screen {
	final MyMain MAIN;
	
	Texture bgImg;
	Rectangle player;
	private float vX = 0;
	private float vY = 0;
	private boolean isFirstMmtX = true;
	private boolean isFirstMmtY = true;
	
	// For Android
	private float accelX;
	private float accelY;
	
	ShapeRenderer test;
	private int r;
	private int g;
	private int b;

	public MyGame(MyMain main) {
		this.MAIN = main;
		
		bgImg = new Texture("table.png");
		
		player = new Rectangle(1600/2-50/2, 900/2-50/2, 50, 50);
		
		test = new ShapeRenderer();
		test.setProjectionMatrix(MAIN.cam.combined);
		r = MathUtils.random(0, 255);
		g = MathUtils.random(0, 255);
		b = MathUtils.random(0, 255);
		test.setColor(Color.valueOf(Color.rgb565(r, g, b)+"FF"));
//		clockGame = TimeUtils.nanoTime();
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		MAIN.cam.update();
		MAIN.updateMouse();
        MAIN.batch.setProjectionMatrix(MAIN.cam.combined);

        if(Gdx.app.getVersion()==0){
			// Get Input And Control You Player
	        if(Gdx.input.isKeyPressed(Keys.A)) vX -= 1000*delta;
	        if(Gdx.input.isKeyPressed(Keys.D)) vX += 1000*delta;
	        if(player.y <= 35 && Gdx.input.isKeyPressed(Keys.SPACE)) vY = 5000*delta;

			// Virtual Gravity And Virtual Friction
			player.y += 10*vY*delta;
			player.x += 10*vX*delta;
			if(player.y > 32) vY -= 98*delta;
			if(vX != 0) {
				if(player.y <= 32) {
					vX *= 1-5*delta;
				}
				else vX *= 1-0.5*delta;
			}

			// Stop And Momentum
			if(player.y <= 32 && isFirstMmtY) {
				vY = vY*-0.8f;
				isFirstMmtY = false;
				r = MathUtils.random(0, 255);
				g = MathUtils.random(0, 255);
				b = MathUtils.random(0, 255);
				test.setColor(Color.valueOf(Color.rgb565(r, g, b)+"FF"));
			}
			else if(player.y > 32) isFirstMmtY = true;
			
			if((player.x <= 0 || player.x >= 1600-player.width) && isFirstMmtX) {
				vX *= -1;
				isFirstMmtX = false;
				r = MathUtils.random(0, 255);
				g = MathUtils.random(0, 255);
				b = MathUtils.random(0, 255);
				test.setColor(Color.valueOf(Color.rgb565(r, g, b)+"FF"));
			}
			else if(player.x > 0 && player.x < 1600) isFirstMmtX = true;
			
			player.x = MathUtils.clamp(player.x, 0, 1600-player.getWidth());
        }
		else {
			// Get Input And Control You Player
			accelX = Gdx.input.getAccelerometerX();
			accelY = Gdx.input.getAccelerometerY();
			if(player.x > 0 && player.x < 1600) vX += 100*accelY*delta;
			if(player.y > 0 && player.y < 900) vY -= 100*accelX*delta;

			// Virtual Gravity And Virtual Friction
			player.y += 3*vY*delta;
			player.x += 3*vX*delta;

			// Stop And Momentum
			if((player.y <= 0 || player.y >= 900-player.height) && isFirstMmtY) {
				vY = vY*-0.8f;
				isFirstMmtY = false;
			}
			else if(player.y > 0) isFirstMmtY = true;
			if((player.x <= 0 || player.x >= 1600-player.width) && isFirstMmtX) {
				vX = vX*-0.8f;
				isFirstMmtX = false;
			}
			else if(player.x > 0 && player.x < 1600) isFirstMmtX = true;
			
			
			player.x = MathUtils.clamp(player.x, 0, 1600-50);
			player.y = MathUtils.clamp(player.y, 0, 900-50);
		}
        
        
        // Draw
        MAIN.batch.begin();
        MAIN.batch.draw(bgImg, 0, 0, 1600, 900);
        MAIN.batch.end();
        
        test.begin(ShapeType.Filled);
        test.circle(player.x-1600/2+25, player.y-900/2+25+100, 25);
        test.line(0-1600/2, 32-900/2+100, 1600-1600/2, 32-900/2+100);
        test.end();
        
        
	}
	
	@Override
	public void resize(int width, int height) {
		MyMain.height = height;
		MyMain.width = width;
	}
	@Override
	public void dispose() {	}
	@Override
	public void pause() { }
	@Override
	public void resume() { }
	@Override
	public void show() { }
	@Override
	public void hide() { }

}
