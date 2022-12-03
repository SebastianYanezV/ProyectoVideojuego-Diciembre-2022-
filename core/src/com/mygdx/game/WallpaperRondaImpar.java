package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WallpaperRondaImpar implements WallpaperStrategy {
    @Override
    public Texture actualizarFondo() {
        Texture aux = new Texture(Gdx.files.internal("space2.png"));
        return aux;
    }
}
