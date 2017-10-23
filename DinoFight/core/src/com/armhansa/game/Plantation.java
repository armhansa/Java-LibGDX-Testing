package com.armhansa.game;

import com.badlogic.gdx.Preferences;

public class Plantation {
	
	Product[][] farm = new Product[9][9];
	public boolean[][] hasField = new boolean[9][9];
	
	public Plantation() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				hasField[i][j] = false;
			}
		}
	}
	
	public void save(Preferences myPref) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				myPref.putBoolean("HasField"+i+","+j, hasField[i][j]);
				if(hasField[i][j]) {
					farm[i][j].save(myPref, i, j);
				}
			}
		}
 	}
	public void load(Preferences myPref) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				hasField[i][j] = myPref.getBoolean("HasField"+i+","+j, false);
				if(hasField[i][j]) {
					switch(myPref.getInteger("PlantType"+i+","+j)) {
					case 0 : farm[i][j] = new Bean();break;
					case 1 : farm[i][j] = new Carrot();break;
					case 2 : farm[i][j] = new Melon();break;
				}
				farm[i][j].load(myPref, i, j);
				}
			}
		}
 	}
	public void sowSeed(int i, int j, int selectSeed) {
		hasField[i][j] = true;
		switch(selectSeed) {
			case 0 : farm[i][j] = new Bean();break;
			case 1 : farm[i][j] = new Carrot();break;
			case 2 : farm[i][j] = new Melon();break;
		}
	}

}
