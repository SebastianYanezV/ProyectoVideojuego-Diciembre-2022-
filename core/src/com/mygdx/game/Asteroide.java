package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Asteroide {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;

    public Asteroide(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        this.x = x;

        //validar que borde de esfera no quede fuera
        if (x-size < 0) this.x = x+size;
        if (x+size > Gdx.graphics.getWidth())this.x = x-size;

        this.y = y;
        //validar que borde de esfera no quede fuera
        if (y-size < 0) this.y = y+size;
        if (y+size > Gdx.graphics.getHeight())this.y = y-size;

        spr.setPosition(x, y);
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
    }
    public Rectangle getArea()  {
        return spr.getBoundingRectangle();
    }
    public abstract void update();
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    public void checkCollision(Asteroide a) {
        if(spr.getBoundingRectangle().overlaps(a.spr.getBoundingRectangle())){
            // rebote
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + a.getXSpeed()/2);
            if (a.getXSpeed() ==0) a.setXSpeed(a.getXSpeed() + getXSpeed()/2);
            setXSpeed(- getXSpeed());
            a.setXSpeed(-a.getXSpeed());

            if (getySpeed() ==0) setySpeed(getySpeed() + a.getySpeed()/2);
            if (a.getySpeed() ==0) a.setySpeed(a.getySpeed() + getySpeed()/2);
            setySpeed(- getySpeed());
            a.setySpeed(- a.getySpeed());
        }
    }
    public int getXSpeed() {
        return xSpeed;
    }
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    public int getySpeed() {
        return ySpeed;
    }
    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Sprite getSpr() {
        return spr;
    }
    public void setSpr(Sprite spr) {
        this.spr = spr;
    }
}
