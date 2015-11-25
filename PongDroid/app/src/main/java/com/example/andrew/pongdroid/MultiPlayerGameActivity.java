package com.example.andrew.pongdroid;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MultiPlayerGameActivity extends Game {


    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);

        player1 = new Player("P1");
        player2 = new Player("P2");
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
