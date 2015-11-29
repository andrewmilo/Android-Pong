package com.example.andrew.pongdroid;

public class Player {

    private String name;
    protected int score;
    protected int width;
    protected int height;
    protected int yVelocity; // pixels per movement
    protected int x;
    protected int y;

    public Player(){}

    public Player( String name, int width, int height ){

        this.name = name;
        this.width = width;
        this.height = height;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public int getYVelocity(){ return yVelocity; }

    public void moveUp(){ x += 1; }
    public void moveDown(){ x -= 1; }
    public void addPoint(){ score += 1; }
    public int getScore(){ return score; }

    public void setX( int x ){ this.x = x; }
    public void setY( int y ){ this.y = y; }
    public void setYVelocity( int yVelocity ){ this.yVelocity = yVelocity; }
    public void setPosition( int x, int y ){ this.x = x; this.y = y; }
}
