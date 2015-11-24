package com.example.andrew.pongdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SinglePlayerOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleplayer);
    }

    public void easyMode( View v ){

        startActivity(new Intent(this, HomeActivity.class));
    }
}
