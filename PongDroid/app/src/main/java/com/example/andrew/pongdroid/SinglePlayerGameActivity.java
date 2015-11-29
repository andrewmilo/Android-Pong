package com.example.andrew.pongdroid;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Toast;

public class SinglePlayerGameActivity extends Game {

    private VelocityTracker mVelocityTracker;

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        player1 = new Player( "You", 40, 200 );
        player2 = new AI( this.difficulty, 40, 200 );

    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        int x = (int) event.getX();
        int y = (int) event.getY();

        int oldY = 0;

        boolean onPlayer = false;

        switch( action ){

            case MotionEvent.ACTION_DOWN:

               // if( Math.abs( player1.getX() - x ) <= player1.getWidth()
               //         && Math.abs(player1.getY() - y) <= player1.getWidth() ) {
                //    Toast.makeText(this, "ON TPLAYER", Toast.LENGTH_LONG).show();
               //     onPlayer = true;
                //}

                if (mVelocityTracker == null) {

                    mVelocityTracker = VelocityTracker.obtain();
                } else {

                    mVelocityTracker.clear();
                }

                mVelocityTracker.addMovement(event);

                oldY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                mVelocityTracker.addMovement(event);

                mVelocityTracker.computeCurrentVelocity(1000);

                //if( onPlayer ) {

                    player1.setYVelocity( (int) VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                            pointerId) );

                    player1.setPosition(player1.getX(), y);
                //}

                break;
            case MotionEvent.ACTION_UP:
                player1.setYVelocity( 0 ); // clear Y velocity
                break;
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                break;
        }

        return true;
    }
}
