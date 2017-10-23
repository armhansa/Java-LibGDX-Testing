package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Melon extends Product {
 	private final int[] SELL_PRICE = {300, 300, 320};
 	private final float[] MAX_GROW = {100/13, 100/10, 100/15};
 	private final float[] MIN_GROW = {100/17, 100/14, 100/19};
 	private final byte TYPE = 2;
 	private byte countLeft = 3;
 	private Texture plantImage;
 	private Texture productImage;
 	
 	public Melon() {
 		super();
 		plantImage = new Texture(Gdx.files.internal("img/plant/wtml-step.png"));
 		productImage = new Texture(Gdx.files.internal("img/product/melon.png"));
		
 	}
 	
 	public void save(Preferences myPref, int i, int j) {
 		myPref.putInteger("PlantType"+i+","+j, TYPE);
 		myPref.putInteger("NowLevel"+i+","+j, nowLevel);
 		myPref.putInteger("PercentSuccess"+i+","+j, percentSuccess);
 		myPref.putBoolean("Growwing"+i+","+j, growwing);
 		myPref.putInteger("WaitGrow"+i+","+j, waitGrow);
 		myPref.putInteger("CountLeft"+i+","+j, countLeft);
 		myPref.putBoolean("Randomed"+i+","+j, randomed);
 		myPref.putBoolean("NowWorm"+i+","+j, nowWorm);
		for(int k=0; k<5; k++) myPref.putInteger("LastWormTime"+i+","+j+","+k, (int) lastWormTime[k]);
		for(int k=0; k<5; k++) myPref.putInteger("LastDateWater"+i+","+j+","+k, (int) lastDateWater[k]);
 	}
 	public void load(Preferences myPref, int i, int j) {
 		nowLevel = (byte) myPref.getInteger("NowLevel"+i+","+j);
 		percentSuccess = (byte) myPref.getInteger("PercentSuccess"+i+","+j);
 		growwing = myPref.getBoolean("Growwing"+i+","+j);
 		waitGrow = (byte) myPref.getInteger("WaitGrow"+i+","+j);
 		countLeft = (byte) myPref.getInteger("CountLeft"+i+","+j);
 		randomed = myPref.getBoolean("Randomed"+i+","+j);
 		nowWorm = myPref.getBoolean("NowWorm"+i+","+j);
		for(int k=0; k<5; k++) lastWormTime[k] = myPref.getInteger("LastWormTime"+i+","+j+","+k);
		for(int k=0; k<5; k++) lastDateWater[k] = myPref.getInteger("LastDateWater"+i+","+j+","+k);
 	}
	public int sell() {
		countLeft--;
		percentSuccess = 75;
		nowLevel = (byte) (percentSuccess<35?0:(percentSuccess<70?1:(percentSuccess<100?2:3)));
 		return SELL_PRICE[Account.time[3]-1];
 	}
	public byte getCountLeft () { return countLeft; }
	public byte getType() { return TYPE; }
	public byte growUp() { return (byte) MathUtils.random((int) MIN_GROW[Account.time[3]-1], (int) MAX_GROW[Account.time[3]-1]); }
	public Texture getPlantImage() { return plantImage; }
	public Texture getProductImage() { return productImage; }
	public int getSellPrice() { return SELL_PRICE[Account.time[3]-1]; }

}
