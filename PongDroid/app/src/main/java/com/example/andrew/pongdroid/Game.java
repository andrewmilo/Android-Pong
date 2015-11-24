package com.example.andrew.pongdroid;

import android.app.Activity;

public abstract class Game extends Activity {

    protected int[] score;

    protected Ball ball;
    protected Player player1;
    protected Player player2;

    protected Game(){

        score = new int[2];
    }


}
