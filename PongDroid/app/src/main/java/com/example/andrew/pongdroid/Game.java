package com.example.andrew.pongdroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


public abstract class Game extends Activity {

    public static final int FPS = 30; // Max FPS for gameplay

    public final static String PLAYERS    = "players",
                               DIFFICULTY = "difficulty";

    protected State state;
    public enum State{
        PLAYING,
        PAUSED
    }

    protected Difficulty difficulty;
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    protected Ball ball;
    protected Player player1;
    protected Player player2;

    private int screenWidth;
    private int screenHeight;

    private double time;
    private double maxTime = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try{

            // Set Difficulty
            difficulty = (Difficulty) getIntent().getExtras().get( DIFFICULTY );

            // Cache screen size
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            screenWidth = point.x;
            screenHeight = point.y;

            // Instantiate ball
            int size = 40;
            ball = new Ball( size );

            initialize(); // start fresh

        }catch(Exception e){

            System.out.println(e);
        }
    }

    public Ball getBall(){ return ball; }
    public Player getPlayer1(){ return player1; }
    public Player getPlayer2(){ return player2; }
    public int getScreenHeight(){ return screenHeight; }
    public int getScreenWidth(){ return screenWidth; }

    /* Initializes the game view */
    public void initialize(){

        // Ball in CENTER XY
        getBall().setPosition(
                screenWidth/2 - getBall().getSize()/2,
                screenHeight/2 - getBall().getSize()/2 );

        // Player 1 on RIGHT side
        getPlayer1().setPosition(
                screenWidth - getPlayer1().getWidth() / 2 - 200,
                screenHeight / 2 - getPlayer1().getWidth());
        // Player 2 on LEFT side
        getPlayer2().setPosition(
                getPlayer2().getWidth() / 2 + 200,
                screenHeight / 2 - getPlayer2().getWidth());
    }

    /* Serves the ball */
    public void start(){

        // Serve ball towards the player
        ball.setXVelocity( FPS ); // FPS pixels per second
        ball.setYVelocity(0);
    }

    public void end(){

        initialize();

        try{
            Thread.sleep(4000);
        }catch(Exception e){
            Thread.currentThread().interrupt();
        }

        this.finish();
    }

    public double getTime(){ return this.time; }
    public void setTime( double t ){ this.time = t; }
    public double getMaxTime(){ return this.maxTime; }
    public void setMaxTime( double maxTime ){ this.maxTime = maxTime; }
}
