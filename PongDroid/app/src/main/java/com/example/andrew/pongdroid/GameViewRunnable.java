package com.example.andrew.pongdroid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

public class GameViewRunnable implements Runnable {

    private boolean running;
    private Handler handler;
    private Game game;
    private Message message;
    private long lastRun;
    long interval;

    GameViewRunnable( Handler handler, Game game ){

        this.handler = handler;
        this.running = true;
        this.game = game;
        this.interval = 1000L / (long) Game.FPS;
        this.lastRun = 0;
    }

    @Override
    public void run() {

        // Become background thread for better system performance
        Process.setThreadPriority( android.os.Process.THREAD_PRIORITY_BACKGROUND );

        while( running ){

            try{

                long cur = System.currentTimeMillis();

                if( lastRun != 0 )
                    if( cur - lastRun < interval ) continue;

                if( update() ) { // update game physics

                    message = handler.obtainMessage();
                    message.sendToTarget();

                    lastRun = System.currentTimeMillis();
                }

            }catch( Exception e ) {
                Toast.makeText(game.getApplicationContext(), "ANDOASNDSAOD", Toast.LENGTH_LONG).show();
                Thread.currentThread().interrupt();
            }

        }
    }

    public void toggle() { running = !running; }

    // Run game physics
    private boolean update(){

        // Update ball position
        game.getBall().setX( game.getBall().getX() + game.getBall().getXVelocity() );
        game.getBall().setY(game.getBall().getY() + game.getBall().getYVelocity());

        if( game.getBall().getX() + game.getBall().getSize()/2 >= game.getScreenWidth() ){

            /* AI SCORES */

            game.getPlayer1().addPoint(); // Add point
            game.initialize(); // Reset

            // Chill for 5 seconds
            try {
                Thread.sleep( 5000 );
            }catch(Exception e){ }

            game.start(); // Serve
        }
        else if( game.getBall().getX() - game.getBall().getSize()/2 <= 0 ){

            /* PLAYER 1 SCORES */

            game.getPlayer2().addPoint(); // Add point
            game.initialize(); // Reset

            // Chill for 5 seconds
            try {
                Thread.sleep( 5000 );
            }catch(Exception e){ }

            game.start(); // Serve
        }
        else if( game.getBall().getY() + game.getBall().getSize()/2 >= game.getScreenWidth() ){

            /* Top border line */

            //game.getBall().getX()

        }
        else if( game.getBall().getY() - game.getBall().getSize()/2 <= 0 ){

            /* Bottom border line */

        }
        else if( ( game.getBall().getX() + game.getBall().getSize()/2 ) >= ( game.getPlayer1().getX() - game.getPlayer1().getWidth()/2 )
                && ( game.getPlayer1().getY() - game.getPlayer1().getHeight()/2 <= ( game.getBall().getY() + game.getBall().getSize()/2 )
                    && game.getPlayer1().getY() + game.getPlayer1().getHeight()/2 >= ( game.getBall().getY() - game.getBall().getSize()/2 ) )
                ){

            /* Ball hits Player 1 */

            game.getBall().setXVelocity( -1 * game.getBall().getXVelocity() ); // Reverse
            game.getBall().setYVelocity( (int)reflectY( game.getBall().getYVelocity(), game.getPlayer1().getYVelocity() ) );
        }
        else if (( game.getBall().getX() - game.getBall().getSize()/2 ) <= ( game.getPlayer2().getX() + game.getPlayer2().getWidth()/2 )
                && ( game.getPlayer2().getY() - game.getPlayer2().getHeight()/2 <= ( game.getBall().getY() + game.getBall().getSize()/2 )
                && game.getPlayer2().getY() + game.getPlayer2().getHeight()/2 >= ( game.getBall().getY() - game.getBall().getSize()/2 ) )
                ){

            /* Ball hits AI */

            game.getBall().setXVelocity( -1 * game.getBall().getXVelocity() ); // Reverse
            game.getBall().setYVelocity( (int)reflectY( game.getBall().getYVelocity(), game.getPlayer2().getYVelocity() ) );
        }

        return true;
    }

    public double reflectY( int ballVelocity, int playerVelocity ){

        // normalize ball's y velocity
        double d = game.getBall().getYVelocity() / Math.sqrt( Math.pow( game.getBall().getXVelocity(), 2 ) + Math.pow( game.getBall().getYVelocity(), 2 ) );

        double dd = game.getPlayer1().getYVelocity() / Math.sqrt( Math.pow( game.getPlayer1().getYVelocity(), 2 ) );

        return ( d + dd ) / 2;
    }

}
