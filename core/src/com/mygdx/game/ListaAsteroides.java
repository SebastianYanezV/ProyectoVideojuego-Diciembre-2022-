package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;

public class ListaAsteroides {
    private final ArrayList<Asteroide> ASTEROIDES_1;
    private final ArrayList<Asteroide> ASTEROIDES_2;

    public ListaAsteroides() {
        ASTEROIDES_1 = new ArrayList<>();
        ASTEROIDES_2 = new ArrayList<>();
    }

    public void crearAsteroides(int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            if (MathUtils.random(0, 10) < 2) {
                AsteroideFuerte nuevoAsteroide = new AsteroideFuerte(r.nextInt(Gdx.graphics.getWidth()),
                        50+r.nextInt(Gdx.graphics.getHeight() -50),
                        20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4),
                        new Texture(Gdx.files.internal("asteroideFuerte.png")));
                ASTEROIDES_1.add(nuevoAsteroide);
                ASTEROIDES_2.add(nuevoAsteroide);
            }
            else {
                AsteroideDebil nuevoAsteroide = new AsteroideDebil(r.nextInt(Gdx.graphics.getWidth()),
                        50+r.nextInt(Gdx.graphics.getHeight() -50),
                        20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4),
                        new Texture(Gdx.files.internal("aGreyMedium4.png")));
                ASTEROIDES_1.add(nuevoAsteroide);
                ASTEROIDES_2.add(nuevoAsteroide);
            }
        }
    }

    public void colisionesEntreAsteroides() {
        for (int i=0; i < ASTEROIDES_1.size(); i++) {
            Asteroide a = ASTEROIDES_1.get(i);
            for (int j=0; j < ASTEROIDES_2.size(); j++) {
                Asteroide a2 = ASTEROIDES_2.get(j);
                if (i<j) {
                    a.checkCollision(a2);
                }
            }
        }
    }

    public void actualizarMovimiento() {
        for (Asteroide a : ASTEROIDES_1) {
            if (a.getClass().getSimpleName().equals("AsteroideFuerte")) {
                AsteroideFuerte aux = (AsteroideFuerte) a;
                aux.update();
            }
            else {
                AsteroideDebil aux = (AsteroideDebil) a;
                aux.update();
            }
        }
    }

    public ArrayList<Asteroide> getAsteroides1() {
        ArrayList<Asteroide> a = ASTEROIDES_1;
        return a;
    }
    public ArrayList<Asteroide> getAsteroides2() {
        ArrayList<Asteroide> a = ASTEROIDES_2;
        return a;
    }
}
