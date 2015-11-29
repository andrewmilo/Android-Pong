package com.example.andrew.pongdroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Handler;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    protected Thread gameViewThread;
    protected GameViewRunnable gameViewRunnable; // detects collisions
    protected GameViewHandler handler;
    protected Context context;
    protected SurfaceHolder surfaceHolder;
    protected boolean hasActiveHolder;
    protected Paint paint;
    protected int screenWidth;
    protected int screenHeight;
    private Game game;

    protected class GameViewHandler extends Handler {

        @Override
        public void handleMessage( Message inputMessage ){

            draw(); // update UI
            invalidate(); // mark current frame dirty
        }
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.setFocusable(true);
        this.handler = new GameViewHandler();
        this.context = context;
        this.paint = new Paint();
        this.paint.setARGB(150, 0, 150, 0); // transparent green
        this.game = (Game)getContext(); // cache current game
        this.gameViewRunnable = new GameViewRunnable( handler, game );
        this.gameViewThread = new Thread( gameViewRunnable );

        // Cache screen size
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Toast.makeText( getContext(), "CREATED", Toast.LENGTH_SHORT ).show();

        game.initialize();
        game.start();

        synchronized (this) {
            hasActiveHolder = true;
            this.notifyAll();
        }

        if( !gameViewThread.isAlive() )
            gameViewThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        synchronized (this) {
            hasActiveHolder = false;

            this.notifyAll();
        }

        Toast.makeText( getContext(), "STOPPED THREAD", Toast.LENGTH_LONG ).show();
        gameViewRunnable.toggle();
    }

    protected void draw() {

        synchronized ( this ) {
            if ( hasActiveHolder == true ) {
                try {

                    Canvas canvas = surfaceHolder.lockCanvas();

                    // Draw background
                    canvas.drawRGB(255, 255, 255);

                    // Draw score
//                    TextView tv = (TextView) ((Activity) getContext()).findViewById(R.id.score1);
//                    tv.setText( game.getPlayer1().getScore() );
//                    tv = (TextView) game.findViewById( R.id.score2 );
//                    tv.setText(game.getPlayer2().getScore());

                    // Draw players
                    canvas.drawRect( new Rect(
                                    game.getPlayer1().getX() - game.getPlayer1().getWidth()/2,
                                    game.getPlayer1().getY() + game.getPlayer1().getHeight()/2,
                                    game.getPlayer1().getX() + game.getPlayer1().getWidth()/2,
                                    game.getPlayer1().getY() - game.getPlayer1().getHeight()/2 ),
                                    paint );
                    canvas.drawRect( new Rect(
                                    game.getPlayer2().getX() - game.getPlayer2().getWidth()/2,
                                    game.getPlayer2().getY() + game.getPlayer2().getHeight()/2,
                                    game.getPlayer2().getX() + game.getPlayer2().getWidth()/2,
                                    game.getPlayer2().getY() - game.getPlayer2().getHeight()/2 ),
                                    paint );

                    // Draw ball
                    canvas.drawRect( new Rect(
                                    game.getBall().getX() - game.getBall().getSize()/2,
                                    game.getBall().getY() + game.getBall().getSize()/2,
                                    game.getBall().getX() + game.getBall().getSize()/2,
                                    game.getBall().getY() - game.getBall().getSize()/2 ),
                                    paint );

                    surfaceHolder.unlockCanvasAndPost( canvas );

                }catch(Exception e){

                }
            }
        }
    }

}