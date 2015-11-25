package com.example.andrew.pongdroid;

public class Player {

    private String name;
    private int score;
    private float speed; // pixels per movement
    private float position; // 0 is center of Screen Height

    public Player( String name ){
        this.name = name;
    }

    public void moveUp(){

        position += 1 * speed;
    }

    public void moveDown(){

        position -= 1 * speed;
    }

}
