package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

public class ProductStorage {
	Texture[] storageImg = new Texture[2];
	Polygon barnPolygon;
	final int[] storageSize = {100, 200, 300, 400};
	final int[][] SELL_PRICE = {{75, 70, 80}, {320, 300, 300}, {300, 300, 320}};
	final int[] UPGRADE_PRICE = {500, 1000, 2000};
	final int[] productSize = {1, 2, 3};
	int[] storage = {0, 0, 0};
	int storageRemain = 10000;
	int level = 0;
	Polygon mousePolygon;
	static boolean nowOverlap = false;
	
	public ProductStorage() {
		storageImg[0] = new Texture(Gdx.files.internal("img/building/barn.png"));
		storageImg[1] = new Texture(Gdx.files.internal("img/building/select-barn.png"));
		barnPolygon = new Polygon();
		mousePolygon = new Polygon();
	}
	
	public void updatePosition() {
		float[] tmp = {61, 101, 28, 251, 258, 358, 342, 274, 332, 127, 184, 48};
		float[] vertices = new float[12];
		for(int i=0; i<6; i++) {
			vertices[i*2] = MyGame.zoom*(270+tmp[i*2])+MyGame.moveX;
			vertices[i*2+1] = MyGame.zoom*(650+tmp[i*2+1])+MyGame.moveY;
		}
		barnPolygon.setVertices(vertices);
		mouseToPolygon();
		
		if(!BuySeed.nowBuySeed && Intersector.overlapConvexPolygons(mousePolygon, barnPolygon)) {
			if(Gdx.input.justTouched() && storage[0]+storage[1]+storage[2]>0) {
				for(int i=0; i<3; i++) {
					MyGame.allSound[4].play();
					Account.money += storage[i]*SELL_PRICE[i][Account.time[3]-1];
					storage[i] = 0;
					storageRemain = 10000;
				}
			}
			nowOverlap = true;
		}
		else nowOverlap = false;
	}
	public void save(Preferences myPref) {
		myPref.putInteger("StorageLevel", level);
		myPref.putInteger("StorageRemain", storageRemain);
		for(int i=0; i<3; i++) myPref.putInteger("Storage"+i, storage[i]);
	}
	public void load(Preferences myPref) {
		level = myPref.getInteger("StorageLevel");
		storageRemain = myPref.getInteger("StorageRemain");
		for(int i=0; i<3; i++) storage[i] = myPref.getInteger("Storage"+i);
	}
	public boolean canAdd(int type) {
		if(productSize[type] <= storageRemain) {
			storageRemain -= productSize[type];
			return true;
		}
		else return false;
	}
	public void add(int type) {
		storage[type]++;
	}
	private void mouseToPolygon() {
		float x = MyMain.mouseX[0];
		float y = MyMain.mouseY[0];
		float[] vertices = {x-1, y-1, x-1, y+1, x+1, y+1, x+1, y-1};
		mousePolygon.setVertices(vertices);
	}
	public boolean upgrade(int nowMoney) {
		if(nowMoney >= UPGRADE_PRICE[level]) {
			level++;
			return true;
		}
		else return false;
	}

}
