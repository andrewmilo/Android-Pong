package com.example.andrew.pongdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SinglePlayerOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleplayeroptions);
    }

    public void easyMode( View v ){

        Intent i = new Intent( this, SinglePlayerGameActivity.class );

        Bundle mBundle = new Bundle();
        mBundle.putSerializable( Game.DIFFICULTY, Game.Difficulty.EASY );
        i.putExtras(mBundle);

        startActivity(i);

    }

    public void mediumMode( View v ){

        Intent i = new Intent( this, SinglePlayerGameActivity.class );

        Bundle mBundle = new Bundle();
        mBundle.putSerializable( Game.DIFFICULTY, Game.Difficulty.MEDIUM );
        i.putExtras( mBundle );

        startActivity( i );
    }

    public void hardMode( View v ){

        Intent i = new Intent( this, SinglePlayerGameActivity.class );

        Bundle mBundle = new Bundle();
        mBundle.putSerializable( Game.DIFFICULTY, Game.Difficulty.HARD );
        i.putExtras( mBundle);

        startActivity( i );
    }
}
