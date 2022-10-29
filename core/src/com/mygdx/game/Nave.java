package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Nave {
    private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private final Sprite SPR;
    private final Sound SONIDO_HERIDO;
    private final Sound SONIDO_BALA;
    private final Texture TX_BALA;
    private boolean herido = false;
    private int tiempoHerido;
    private final ArrayList<Bullet> BALAS;

    public Nave(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        SONIDO_HERIDO = soundChoque;
        this.SONIDO_BALA = soundBala;
        this.TX_BALA = txBala;
        SPR = new Sprite(tx);
        SPR.setPosition(x, y);
        //spr.setOriginCenter();
        SPR.setBounds(x, y, 45, 45);
        BALAS = new ArrayList<>();
    }
    public void draw(SpriteBatch batch){
        float x =  SPR.getX();
        float y =  SPR.getY();
        if (!herido) {
            // que se mueva con teclado
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;

            // que se mantenga dentro de los bordes de la ventana
            if (x+xVel < 0 || x+xVel+SPR.getWidth() > Gdx.graphics.getWidth())
                xVel*=-1;
            if (y+yVel < 0 || y+yVel+SPR.getHeight() > Gdx.graphics.getHeight())
                yVel*=-1;

            SPR.setPosition(x+xVel, y+yVel);

            SPR.draw(batch);
        } else {
            SPR.setX(SPR.getX()+MathUtils.random(-2,2));
            SPR.draw(batch);
            SPR.setX(x);
            tiempoHerido--;
            if (tiempoHerido<=0) herido = false;
        }
        // disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet  bala = new Bullet(SPR.getX()+SPR.getWidth()/2-5,SPR.getY()+ SPR.getHeight()-5,0,10,TX_BALA);
            //juego.agregarBala(bala);
            BALAS.add(bala);
            SONIDO_BALA.play();
        }

    }

    public boolean checkCollision(Asteroide b) {
        if(!herido && b.getArea().overlaps(SPR.getBoundingRectangle())){
            // rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());

            if (yVel ==0) yVel += b.getYSpeed()/2;
            if (b.getYSpeed() ==0) b.setYSpeed(b.getYSpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setYSpeed(- b.getYSpeed());

            //actualizar vidas y herir
            vidas--;
            herido = true;
            tiempoHerido = 50;
            SONIDO_HERIDO.play();
            if (vidas<=0)
                destruida = true;
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }
    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) SPR.getX();}
    public int getY() {return (int) SPR.getY();}
    public void setVidas(int vidas2) {vidas = vidas2;}
    public ArrayList<Bullet> getBalas() {
        ArrayList<Bullet> a = BALAS;
        return a;
    }
}
