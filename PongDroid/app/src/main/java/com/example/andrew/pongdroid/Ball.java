package com.example.andrew.pongdroid;

public class Ball {

    public Ball( int size ){ this.size = size; }

    private int x;
    private int y;
    private int size;
    private float xVelocity;
    private float yVelocity;

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getSize(){ return size; }
    public float getXVelocity(){ return xVelocity; }
    public float getYVelocity(){ return yVelocity; }

    public void setX( int x ){ this.x = x; }
    public void setY( int y ){ this.y = y; }
    public void setPosition( int x, int y){ this.x = x; this.y = y; }
    public void setSize( int size ){ this.size = size; }
    public void setXVelocity( float xVelocity ){ this.xVelocity = xVelocity; }
    public void setYVelocity( float yVelocity ){ this.yVelocity = yVelocity; }
}
