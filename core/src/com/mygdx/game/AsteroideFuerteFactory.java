package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class AsteroideFuerteFactory implements AsteroideFactory {
    @Override
    public Asteroide createAsteroide(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        return new AsteroideFuerte(x, y, size, xSpeed, ySpeed, tx);
    }
}
