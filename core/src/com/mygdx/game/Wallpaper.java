package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Wallpaper {
    private WallpaperStrategy wp;

    public Wallpaper() {}

    public void setWallpaperStrategy(WallpaperStrategy ws) {
        wp = ws;
    }

    public Texture ponerFondo() {
        return wp.actualizarFondo();
    }
}
