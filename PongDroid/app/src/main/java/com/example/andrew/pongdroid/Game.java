package com.example.andrew.pongdroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public abstract class Game extends Activity {

    public static final int FPS = 24; // Max FPS for gameplay

    public final static String PLAYERS    = "players",
                               DIFFICULTY = "difficulty";

    protected int screenWidth;
    protected int screenHeight;

    protected GameView gameView; // view that manages collisions

    protected Difficulty difficulty;
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    protected Ball ball;
    protected Player player1;
    protected Player player2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try{

            // Set Difficulty
            Difficulty diff = (Difficulty) savedInstanceState.get( DIFFICULTY );
            difficulty = diff;

            // Instantiate ball
            ball = new Ball();

            // Cache screen size
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;

        }catch(Exception e){

            System.out.println(e);
        }
    }

}
