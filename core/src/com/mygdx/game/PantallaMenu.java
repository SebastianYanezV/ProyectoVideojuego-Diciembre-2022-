package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
public class PantallaMenu implements Screen {
	private final SpaceNavigation GAME;
	private final OrthographicCamera CAMERA;
	public PantallaMenu(SpaceNavigation game) {
		this.GAME = game;
		CAMERA = new OrthographicCamera();
		CAMERA.setToOrtho(false, 1200, 800);
	}
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		CAMERA.update();
		GAME.getBatch().setProjectionMatrix(CAMERA.combined);

		GAME.getBatch().begin();
		GAME.getFont().draw(GAME.getBatch(), "Bienvenido a Space Navigation !", 140, 400);
		GAME.getFont().draw(GAME.getBatch(), "Pincha en cualquier lado o presiona cualquier tecla para comenzar ...", 100, 300);

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