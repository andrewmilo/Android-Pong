package com.example.andrew.pongdroid;

public class Ball {

    public Ball( int size ){ this.size = size; }

    private int x;
    private int y;
    private int size;
    private int xvelocity;
    private int yvelocity;

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getSize(){ return size; }
    public int getXVelocity(){ return xvelocity; }
    public int getYVelocity(){ return yvelocity; }

    public void setX( int x ){ this.x = x; }
    public void setY( int y ){ this.y = y; }
    public void setPosition( int x, int y){ this.x = x; this.y = y; }
    public void setSize( int size ){ this.size = size; }
    public void setXVelocity( int xvelocity ){ this.xvelocity = xvelocity; }
    public void setYVelocity( int yvelocity ){ this.yvelocity = yvelocity; }
}
