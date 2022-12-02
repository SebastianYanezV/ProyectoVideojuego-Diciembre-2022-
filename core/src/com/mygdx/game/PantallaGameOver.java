package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


public class PantallaGameOver implements Screen {
	private final SpaceNavigation GAME;
	private final OrthographicCamera CAMERA;
	private final int SCORE;

	public PantallaGameOver(SpaceNavigation game, int score) {
		this.GAME = game;
		this.SCORE = score;
		CAMERA = new OrthographicCamera();
		CAMERA.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		CAMERA.update();
		GAME.getBatch().setProjectionMatrix(CAMERA.combined);

		GAME.getBatch().begin();
		GAME.getFont().draw(GAME.getBatch(), "Game Over !!! ", 120, 400,400,1,true);
		GAME.getFont().draw(GAME.getBatch(), "Puntaje alcanzado: " + SCORE + " puntos ", 120, 350);
		GAME.getFont().draw(GAME.getBatch(), "Pincha en cualquier lado para reiniciar ...", 100, 300);

		GAME.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(GAME,1,3,0,1,1,5);
			ss.resize(1200, 800);
			GAME.setScreen(ss);
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
