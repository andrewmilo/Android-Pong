package com.example.andrew.pongdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void singlePlayer( View v ){

        // Intent to SinglePlayer
        startActivity(new Intent(this, SinglePlayerOptionsActivity.class));
    }

    public void multiPlayer( View v ){

        // Intent to MultiPlayer
        startActivity(new Intent(this, MultiPlayerOptionsActivity.class));
    }

    public void info( View v ){

        // Intent to InfoView
        startActivity(new Intent(this, InfoActivity.class));
    }

}
