package com.example.andrew.pongdroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnTouchListener;

public abstract class Game extends Activity {

    protected static final int FPS = 24; // Max FPS for gameplay

    protected Difficulty difficulty;
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public final static String PLAYERS    = "players",
                               DIFFICULTY = "difficulty";

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

        }catch(Exception e){

            System.out.println(e);
        }
    }

}
