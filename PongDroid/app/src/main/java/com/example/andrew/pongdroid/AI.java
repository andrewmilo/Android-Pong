package com.example.andrew.pongdroid;

public class AI extends Player {
	
	public AI( Game.Difficulty difficulty, int width, int height ){

		this.width = width;
		this.height = height;

		if( difficulty == Game.Difficulty.EASY )
			easyMode();
		else if( difficulty == Game.Difficulty.MEDIUM )
			mediumMode();
		else if( difficulty == Game.Difficulty.HARD )
			hardMode();
	}
	
	public void easyMode(){
		
		
	}
	
	public void mediumMode(){
		
		
	}
	
	public void hardMode(){
		
		
	}
	
}