package com.example.andrew.pongdroid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Pair;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

public class GameViewRunnable implements Runnable {

    private boolean running;
    private Handler handler;
    private Game game;
    private Message message;
    private long lastRun;
    private long interval;
    private Random random;
    private boolean miss;
    private boolean AIhit;
    private boolean playerHit;

    GameViewRunnable( Handler handler, Game game ){

        this.handler = handler;
        this.running = true;
        this.game = game;
        this.interval = 1000L / (long) Game.FPS;
        this.lastRun = 0;
        this.random = new Random();
        AIhit = true;
        playerHit = true;
        random.setSeed(1000);
    }

    @Override
    public void run() {

        // Become background thread for better system performance
        Process.setThreadPriority( android.os.Process.THREAD_PRIORITY_BACKGROUND );

        while( running ){

            try{

                long cur = System.currentTimeMillis();

                // Run at specified frequency ( FPS )
                if( lastRun != 0 )
                    if( cur - lastRun < interval ) continue;

                if( update() ) { // update game physics

                    // Game over
                    if( (int) game.getMaxTime() - (int) game.getTime() / 1000 == 0 ){
                        this.toggle();


                        message = handler.obtainMessage( -1 );
                        message.sendToTarget(); // update UI thread handler

                        game.end();

                        return;
                    }

                    game.setTime( ( game.getTime() + interval ) );

                    message = handler.obtainMessage();
                    message.sendToTarget(); // update UI thread handler

                    lastRun = System.currentTimeMillis(); // save execution time
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
        game.getBall().setX( (int) Math.ceil( game.getBall().getX() + game.getBall().getXVelocity() ) );
        game.getBall().setY( (int) Math.ceil( game.getBall().getY() + game.getBall().getYVelocity() ) );

        // Update AI position
        if( game.player2 instanceof AI ){

            // When ball hits AI
            if( game.getBall().getX() - game.getPlayer2().getX()
                    <= game.getBall().getSize()/2 + game.getPlayer2().width/2
                    && game.getBall().getYVelocity() != 0 ) // ball isnt horizontal only
            {
                if ( miss ) {
                    game.player2.setY( game.getBall().getY() + ( (AI) game.getPlayer2() ).getMiss() );
                    miss = false;
                }
            }
            else {
                miss = true;

                if( game.getBall().getY() + game.player2.height/2 >= game.getScreenHeight() - 85 ){

                    game.player2.setY( game.getScreenHeight() - 85 - game.player2.height/2 );
                }else if( game.getBall().getY() - game.player2.height/2 <= 0 ){

                    game.player2.setY( 0 + game.player2.height/2 );
                }else {
                    game.player2.setY( game.getBall().getY() );
                }
            }

            /* Randomly spike ball */
            int direction = (int) ( random.nextInt(2) + 0 );

            if( direction == 0 ){ // go up

                game.player2.setY( game.player2.getY() + 20 );
            }
            else if( direction == 1 ){ // go down

                game.player2.setY( game.player2.getY() + 20 );
            }
        }

        if( game.getBall().getX() + game.getBall().getSize()/2 >= game.getScreenWidth() - 150 ){

            /* AI SCORES */

            game.getPlayer1().addPoint(); // Add point

            game.initialize(); // Reset
            AIhit = true;
            playerHit = true;

            game.start(); // Serve
        }
        else if( game.getBall().getX() - game.getBall().getSize()/2 <= 150 ){

            /* PLAYER 1 SCORES */

            game.getPlayer2().addPoint(); // Add point

            game.initialize(); // Reset
            AIhit = true;
            playerHit = true;

            game.start(); // Serve
        }
        else if( game.getBall().getY() + game.getBall().getSize()/2 >= game.getScreenHeight() - 85 ){

            /* Bottom border */

            game.getBall().setYVelocity( -1 * game.getBall().getYVelocity() );
        }
        else if( game.getBall().getY() - game.getBall().getSize()/2 <= 0 ){

            /* Top border */

            game.getBall().setYVelocity( -1 * game.getBall().getYVelocity() );
        }
        else if( ( game.getBall().getX() + game.getBall().getSize()/2 ) >= ( game.getPlayer1().getX() - game.getPlayer1().getWidth()/2 )
                && ( game.getPlayer1().getY() - game.getPlayer1().getHeight()/2 <= ( game.getBall().getY() + game.getBall().getSize()/2 )
                    && game.getPlayer1().getY() + game.getPlayer1().getHeight()/2 >= ( game.getBall().getY() - game.getBall().getSize()/2 ) )
                ){

            if( AIhit ) {

                /* Ball hits Player 1 */
                playerHit = true;
                AIhit = false;

                /* Line for top bound */
                float topLine = 0;

                /* Line for bottom bound */
                float bottomLine = game.getScreenHeight();

                /* Get XY coordinates of the hit */
                float xHit = game.getBall().getX();
                float yHit = game.getBall().getY();

                /* Get ball vector */
                float xv = game.getBall().getXVelocity();
                float yv = game.getBall().getYVelocity();

                /* Vector for player1 */
                int xp = game.player1.getX();
                float yp = game.player1.getYVelocity();

                Pair<Float, Float> reflectedVector = reflect(
                        game.getBall().getXVelocity()
                        , game.getBall().getYVelocity()
                        , game.getBall().getX()
                        , game.getBall().getY()
                );

                game.getBall().setXVelocity(-1 * game.getBall().getXVelocity()); // Reverse
                game.getBall().setYVelocity(
                    (game.player1.getYVelocity() > 0)
                            ? -1 * reflectedVector.second
                            : (game.player1.getYVelocity() < 0)
                            ? reflectedVector.second : 0);
            }
        }
        else if (( game.getBall().getX() - game.getBall().getSize()/2 ) <= ( game.getPlayer2().getX() + game.getPlayer2().getWidth()/2 )
                && ( game.getPlayer2().getY() - game.getPlayer2().getHeight()/2 <= ( game.getBall().getY() + game.getBall().getSize()/2 )
                && game.getPlayer2().getY() + game.getPlayer2().getHeight()/2 >= ( game.getBall().getY() - game.getBall().getSize()/2 ) )
                ) {

            if ( playerHit ) {

                /* Ball hits AI */
                AIhit = true;
                playerHit = false;

                /* Line for top bound */
                float topLine = 0;

                /* Line for bottom bound */
                float bottomLine = game.getScreenHeight();

                /* Get XY coordinates of the hit */
                float xHit = game.getBall().getX();
                float yHit = game.getBall().getY();

                /* Get ball vector */
                float xv = game.getBall().getXVelocity();
                float yv = game.getBall().getYVelocity();

                /* Vector for player1 */
                int xp = game.player2.getX();
                float yp = game.player2.getYVelocity();

                Pair<Float, Float> reflectedVector = reflect(
                        game.getBall().getXVelocity()
                        , game.getBall().getYVelocity()
                        , game.getBall().getX()
                        , game.getBall().getY()
                );

                /* Reflect ball */
                game.getBall().setXVelocity( -1 * game.getBall().getXVelocity() ); // Reverse
                game.getBall().setYVelocity(
                        (game.player2.getYVelocity() > 0)
                                ? -1 * reflectedVector.second
                                : (game.player2.getYVelocity() < 0)
                                ? reflectedVector.second : 0);
            }
        }

        return true;
    }

    /*
        Returns the reflection vector from an input vector
        and the surface normal.
    */
    private Pair<Float, Float> reflect(float inputVectorX,
                                      float inputVectorY,
                                      float surfaceVectorX,
                                      float surfaceVectorY
    ){

        // normalize normal
        double surfaceVectorXNormal = surfaceVectorX
                / Math.sqrt( Math.pow( surfaceVectorX, 2 )
                + Math.pow( surfaceVectorY, 2 ) );

        double surfaceVectorYNormal = surfaceVectorY
                / Math.sqrt( Math.pow( surfaceVectorX, 2 )
                + Math.pow( surfaceVectorY, 2 ) );

        double dot = inputVectorX * surfaceVectorXNormal + inputVectorY * surfaceVectorYNormal;

        surfaceVectorXNormal *= 2 * dot;
        surfaceVectorYNormal *= 2 * dot;

        inputVectorX -= surfaceVectorXNormal;
        inputVectorY -= surfaceVectorYNormal;

        return new Pair< Float, Float >( inputVectorX, inputVectorY );
    }

}
