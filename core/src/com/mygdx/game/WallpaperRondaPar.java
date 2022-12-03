package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WallpaperRondaPar implements WallpaperStrategy {
    @Override
    public Texture actualizarFondo() {
        Texture aux = new Texture(Gdx.files.internal("espacio.png"));
        return aux;
    }
}
