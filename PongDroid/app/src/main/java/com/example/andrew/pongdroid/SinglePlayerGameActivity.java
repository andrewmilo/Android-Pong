package com.example.andrew.pongdroid;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class SinglePlayerGameActivity extends Game {

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);

        player1 = new Player("You");
        player2 = new Player("AI");
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId( index );

        switch( action ){

            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }
}
