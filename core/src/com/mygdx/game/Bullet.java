package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
	private final int XSPEED;
	private final int YSPEED;
	private boolean destroyed = false;
	private final Sprite SPR;

	public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
		SPR = new Sprite(tx);
		SPR.setPosition(x, y);
		this.XSPEED= xSpeed;
		this.YSPEED = ySpeed;
	}
	public void update() {
		SPR.setPosition(SPR.getX()+XSPEED, SPR.getY()+YSPEED);
		if (SPR.getX() < 0 || SPR.getX()+SPR.getWidth() > Gdx.graphics.getWidth()) {
			destroyed = true;
		}
		if (SPR.getY() < 0 || SPR.getY()+SPR.getHeight() > Gdx.graphics.getHeight()) {
			destroyed = true;
		}

	}

	public void draw(SpriteBatch batch) {
		SPR.draw(batch);
	}

	public boolean checkCollision(Asteroide b2) {
		if(SPR.getBoundingRectangle().overlaps(b2.getArea())){
			// Se destruyen ambos
			this.destroyed = true;
			return true;

		}
		return false;
	}
	public boolean isDestroyed() {return destroyed;}
}
