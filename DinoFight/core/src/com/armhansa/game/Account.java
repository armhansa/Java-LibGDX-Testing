package com.armhansa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Account {
	public String name;
	Plantation plant;
	Preferences myPref;
	
	public static int money = 10000;
	public static int[] time = new int[5];
	public int[] seeds = {99, 99, 99};
	public static final int[][] SEED_PRICE = {{50, 60, 50}, {250, 280, 220}, {750, 800, 700}};
	
	ProductStorage storage = new ProductStorage();
	Delivery delivery = new Delivery(storage);
	Well well = new Well();
	Home home = new Home(this);
	
	public Account(Plantation plant, int[] time, String name) {
		this.name = name;
		int[] total = {6, 0, time[0], time[1], time[2]};
		Account.time = total;
		this.plant = plant;
		myPref = Gdx.app.getPreferences("PlantPlants");
	}
	public Account(Plantation plant) {
		this.plant = plant;
		myPref = Gdx.app.getPreferences("PlantPlants");
	}
	
	public void sell(int money) {
		Account.money += money;
		save();
	}
	public boolean buy(byte selectSeed, byte count) {
		if(Account.money >= SEED_PRICE[selectSeed][time[3]-1]*count) {
			Account.money -= SEED_PRICE[selectSeed][time[3]-1]*count;
			seeds[selectSeed] += count;
			return true;
		}
		else return false;
	}
	public void nextTime() {
		time[1]++;
		if(time[1] >= 60) {
			time[0]++;
			time[1] = 0;
		}
		if(time[0] >= 24) {
			time[2]++;
			time[0] = 0;
		}
		if(time[2] > (time[3]==2? 15: 10)) {
			time[3]++;
			time[2] = 1;
		}
		if(time[3] > 3) {
			time[4]++;
			time[3] = 1;
		}
	}
	public boolean sowSeed(int i, int j, byte selectSeed) {
		if(seeds[selectSeed] > 0) {
			seeds[selectSeed]--;
			plant.sowSeed(i, j, selectSeed);
			return true;
		}
		else return false;
	}
	public int getMoney() {
		return money;
	}
	public void save() {
		myPref.putString("Name", name);
		myPref.putInteger("Money", money);
		for(int i=0; i<3; i++) myPref.putInteger("Seed"+i, seeds[i]);
		for(int i=0; i<5; i++) myPref.putInteger("Time"+i, time[i]);
		storage.save(myPref);
		delivery.save(myPref);
		well.save(myPref);
		myPref.putBoolean("SleepStatus", Home.nowSleep);
		plant.save(myPref);
		myPref.flush();
	}
	public void load() {
		name = myPref.getString("Name");
		money = myPref.getInteger("Money");
		for(int i=0; i<3; i++) seeds[i] = myPref.getInteger("Seed"+i);
		for(int i=0; i<5; i++) time[i] = myPref.getInteger("Time"+i);
		storage.load(myPref);
		delivery.load(myPref);
		well.load(myPref);
		Home.nowSleep = myPref.getBoolean("SleepStatus");
		plant.load(myPref);
		myPref.flush();
	}

}
