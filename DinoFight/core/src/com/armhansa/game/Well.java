package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Well {
	static int waterRemain = 15;
	static boolean nowOverlap = false;
	Texture[] wellImg = new Texture[2];
	Rectangle wellRect;
	final int[] capacityWater = {40, 50, 60, 70};
	final int[] UPGRADE_PRICE = {500, 1000, 2000};
	private int level = 0;
	
	public Well() {
		wellImg[0] = new Texture(Gdx.files.internal("img/building/well.png"));
		wellImg[1] = new Texture(Gdx.files.internal("img/building/select-well.png"));
	}
	
	public void save(Preferences myPref) {
		myPref.putInteger("WellLevel", level);
		myPref.putInteger("WaterRemain", waterRemain);
	}
	public void load(Preferences myPref) {
		level = myPref.getInteger("WellLevel");
		waterRemain = myPref.getInteger("WaterRemain");
	}
	
	public boolean fillWater() {
		if(waterRemain > 0) {
			waterRemain--;
			return true;
		}
		else return false;
	}
	public void updatePositionAndCheck() {
		wellRect = new Rectangle();
		wellRect.x = MyGame.zoom*1200+MyGame.moveX;
		wellRect.y = MyGame.zoom*625+MyGame.moveY;
		wellRect.height = MyGame.zoom*100;
		wellRect.width = MyGame.zoom*100;
		if(!BuySeed.nowBuySeed && wellRect.overlaps(new Rectangle(MyMain.mouseX[0], MyMain.mouseY[0], 0.5f, 0.5f))) {
			nowOverlap = true;
			if(Gdx.input.justTouched() && Account.money >= 20 && waterRemain == 0) {
				Account.money-= 10;
				waterRemain = capacityWater[level];
			}
		}
		else nowOverlap = false;
	}
	public boolean upgrade(int nowMoney) {
		if(nowMoney >= UPGRADE_PRICE[level]) {
			level++;
			return true;
		}
		else return false;
	}

}
