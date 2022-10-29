package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AsteroideDebil extends Asteroide {
    private int vidas;

    public AsteroideDebil(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        setSpr(new Sprite(tx));
        vidas = 1;
    }
    public void update() {
        setX(getX() + getXSpeed());
        setY(getY() + getySpeed());

        if (getX() + getXSpeed() < 0 || getX() + getXSpeed()+getSpr().getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (getY()+getySpeed() < 0 || getY()+getySpeed()+getSpr().getHeight() > Gdx.graphics.getHeight())
            setySpeed(getySpeed() * -1);
        getSpr().setPosition(getX(), getY());
    }
    public int getVidas() {
        return vidas;
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
