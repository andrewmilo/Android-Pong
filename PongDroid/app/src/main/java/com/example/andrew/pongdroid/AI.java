package com.example.andrew.pongdroid;

import java.util.Random;

public class AI extends Player {

	private Random random;
	private int missDistance;
	private Game.Difficulty difficulty;

	public AI( Game.Difficulty difficulty, int width, int height ){

		this.width = width;
		this.height = height;
		this.random = new Random();
		this.difficulty = difficulty;
		random.setSeed(1000);

		if( difficulty == Game.Difficulty.EASY )
			easyMode();
		else if( difficulty == Game.Difficulty.MEDIUM )
			mediumMode();
		else if( difficulty == Game.Difficulty.HARD )
			hardMode();
	}

	public void easyMode(){

		missDistance = random.nextInt( this.height );
	}

	public void mediumMode(){

		missDistance = random.nextInt( ( this.height / 2 ) + this.height/4 );
	}

	public void hardMode(){

		missDistance = random.nextInt( ( this.height / 2 ) + this.height/6 ) ;
	}

	public int getMiss(){

		if( this.difficulty == Game.Difficulty.EASY )
			easyMode();
		else if( this.difficulty == Game.Difficulty.MEDIUM )
			mediumMode();
		else
			hardMode();

		return missDistance;
	}

}