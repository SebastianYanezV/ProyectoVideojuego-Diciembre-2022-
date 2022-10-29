package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaPausa implements Screen {
    private final SpaceNavigation GAME;
    private final PantallaJuego JUEGO;
    private final SpriteBatch BATCH;
    private final BitmapFont FONT;
    private final OrthographicCamera CAMERA;

    public PantallaPausa (final SpaceNavigation game, PantallaJuego juego) {
        this.GAME = game;
        this.JUEGO = juego;
        this.BATCH = game.getBatch();
        this.FONT = game.getFont();
        CAMERA = new OrthographicCamera();
        CAMERA.setToOrtho(false, 1200, 800);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1.0f, 0.5f);

        CAMERA.update();
        BATCH.setProjectionMatrix(CAMERA.combined);

        BATCH.begin();
        FONT.draw(BATCH, "Juego en Pausa ", 100, 150);
        FONT.draw(BATCH, "Toca en cualquier lado para continuar !!!", 100, 100);
        BATCH.end();

        if (Gdx.input.isTouched()) {
            GAME.setScreen(JUEGO);
            dispose();
        }
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
