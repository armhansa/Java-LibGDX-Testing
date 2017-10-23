package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Delivery {
	Texture[] car = new Texture[4];
	Texture toCity;
	Rectangle sentRect;
	static boolean nowDelivery = false;
	static boolean nowOverlap = false;
	int saveMoney = 0;
	int way = 1; // If way=1 is ToCity and way = -1 is ComeBack
	float[] lastStart = new float[5];
	int percentSuccess = 0;
	ProductStorage storage;
	
	public Delivery(ProductStorage storage) {
		this.storage = storage;
		for(int i=0; i<4; i++) car[i] = new Texture(Gdx.files.internal("img/delivery/car-"+i+".png"));
		toCity = new Texture(Gdx.files.internal("img/delivery/toCity.png"));
		sentRect = new Rectangle(1000, 600, 100, 50);
	}
	
	public void save(Preferences myPref) {
		myPref.putBoolean("NowDelivery", nowDelivery);
		myPref.putInteger("SaveMoney", saveMoney);
		for(int i=0; i<5; i++) myPref.putFloat("LastStart"+i, lastStart[i]);
		myPref.putInteger("PercentSuccess", percentSuccess);
		myPref.putInteger("Way", way);
	}
	public void load(Preferences myPref) {
		nowDelivery = myPref.getBoolean("NowDelivery");
		saveMoney = myPref.getInteger("SaveMoney");
		for(int i=0; i<5; i++) lastStart[i] = myPref.getFloat("LastStart"+i);
		percentSuccess = myPref.getInteger("PercentSuccess");
		way = myPref.getInteger("Way");
	}
	
	public void updateCheck() {
		if(sentRect.overlaps(new Rectangle(Gdx.input.getX(), MyMain.height-Gdx.input.getY(), 0.5f, 0.5f))) {
			nowOverlap = true;
			if(Gdx.input.justTouched() && storage.storageRemain > 0) {
				saveMoney = storage.storage[0]*Account.SEED_PRICE[0][Account.time[3]-1];
			}
		}
		else nowOverlap = false;
	}
	public boolean start(int money) {
		if(!nowDelivery) {
			lastStart[0] = Account.time[0]/1;
			lastStart[1] = Account.time[1]/1;
			lastStart[2] = Account.time[2]/1;
			lastStart[3] = Account.time[3]/1;
			lastStart[4] = Account.time[4]/1;
			saveMoney = money;
			nowDelivery = true;
			return true;
		}
		else return false;
	}
	public int update() {
		int lastHourStart = ((int) lastStart[4]-2010)*91*24*60+(((int) lastStart[3]==1?0:((int) lastStart[3]==2?30:61))+(int) lastStart[2])*24*60+(int) lastStart[0]*60+(int) lastStart[1];
 		int nowHour = (Account.time[4]-2010)*91*24*60+((Account.time[3]==1?0:(Account.time[3]==2?30:61))+Account.time[2])*24*60+Account.time[0]*60+Account.time[1];
 		if(nowHour-lastHourStart >= 4*60) {
 			if(nowDelivery) {
 				nowDelivery = false;
 				return saveMoney;
 			}
 		}
 		else {
 			percentSuccess = (nowHour-lastHourStart)/(4*60)*100;
 		}
		return nowHour;
	}

}
