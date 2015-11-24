package com.example.andrew.pongdroid;

public class Player {

    private float speed; // pixels per movement
    private float position; // 0 is center of Screen Height

    public Player(){
        this.speed = 5f;
    }

    public Player( float f ){

        this.speed = Math.abs( f );
    }

    public void moveUp(){

        position += 1 * speed;
    }

    public void moveDown(){

        position -= 1 * speed;
    }

}
