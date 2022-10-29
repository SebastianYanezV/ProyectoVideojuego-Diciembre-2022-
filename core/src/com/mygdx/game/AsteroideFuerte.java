package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class AsteroideFuerte extends Asteroide {
    private Sprite spr;
    private int vidas;

    public AsteroideFuerte(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed);
        spr = new Sprite(tx);
        vidas = 2;
    }

    public void update() {
        setX(getXSpeed() + 1);
        setY(getySpeed() + 1);

        if (getX() + getXSpeed() < 0 || getX() + getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (getY()+getySpeed() < 0 || getY()+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
            setySpeed(getySpeed() * -1);
        spr.setPosition(getX(), getY());
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public void checkCollision(Asteroide a) {
        if (a.getClass().getSimpleName().equals("AsteroideFuerte")) {
            AsteroideFuerte aux = (AsteroideFuerte) a;

            if (spr.getBoundingRectangle().overlaps(aux.spr.getBoundingRectangle())){
                // rebote
                if (getXSpeed() ==0) setXSpeed(getXSpeed() + aux.getXSpeed()/2);
                if (aux.getXSpeed() ==0) aux.setXSpeed(aux.getXSpeed() + getXSpeed()/2);
                setXSpeed(- getXSpeed());
                aux.setXSpeed(-aux.getXSpeed());

                if (getySpeed() ==0) setySpeed(getySpeed() + aux.getySpeed()/2);
                if (aux.getySpeed() ==0) aux.setySpeed(aux.getySpeed() + getySpeed()/2);
                setySpeed(- getySpeed());
                aux.setySpeed(- aux.getySpeed());
            }
        }
        else if (a.getClass().getSimpleName().equals("AsteroideDebil")) {
            AsteroideDebil aux = (AsteroideDebil) a;

            if (spr.getBoundingRectangle().overlaps(aux.getSpr().getBoundingRectangle())){
                // rebote
                if (getXSpeed() ==0) setXSpeed(getXSpeed() + aux.getXSpeed()/2);
                if (aux.getXSpeed() ==0) aux.setXSpeed(aux.getXSpeed() + getXSpeed()/2);
                setXSpeed(- getXSpeed());
                aux.setXSpeed(-aux.getXSpeed());

                if (getySpeed() ==0) setySpeed(getySpeed() + aux.getySpeed()/2);
                if (aux.getySpeed() ==0) aux.setySpeed(aux.getySpeed() + getySpeed()/2);
                setySpeed(- getySpeed());
                aux.setySpeed(- aux.getySpeed());
            }
        }
    }
    public Sprite getSpr() {
        return spr;
    }
    public void setSpr(Sprite spr) {
        this.spr = spr;
    }
    public int getVidas() {
        return vidas;
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
