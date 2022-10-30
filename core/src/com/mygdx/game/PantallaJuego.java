package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaJuego implements Screen {
	private final SpaceNavigation GAME;
	private final SpriteBatch BATCH;
	private final Sound EXPLOSION_SOUND;
	private final Music GAME_MUSIC;
	private final Texture WALLPAPER;
	private int score;
	private final int RONDA;
	private final int VELX_ASTEROIDES;
	private final int VELY_ASTEROIDES;
	private final int CANT_ASTEROIDES;
	private final Nave NAVE;
	private final ListaAsteroides LISTA_ASTEROIDES;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.GAME = game;
		this.RONDA = ronda;
		this.score = score;
		this.VELX_ASTEROIDES = velXAsteroides;
		this.VELY_ASTEROIDES = velYAsteroides;
		this.CANT_ASTEROIDES = cantAsteroides;

		WALLPAPER = new Texture(Gdx.files.internal("espacio.png"));
		BATCH = game.getBatch();
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		EXPLOSION_SOUND = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		EXPLOSION_SOUND.setVolume(1,0.1f);
		GAME_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("videoplayback.wav")); //
		
		GAME_MUSIC.setLooping(true);
		GAME_MUSIC.setVolume(0.5f);
		GAME_MUSIC.play();
		
	    // cargar imagen de la nave, 64x64   
	    NAVE = new Nave(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        NAVE.setVidas(vidas);
        //crear asteroides
		LISTA_ASTEROIDES = new ListaAsteroides();
		LISTA_ASTEROIDES.crearAsteroides(velXAsteroides, velYAsteroides, cantAsteroides);
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: " + NAVE.getVidas()+ " Ronda: " + RONDA;
		GAME.getFont().getData().setScale(2f);
		GAME.getFont().draw(BATCH, str, 10, 30);
		GAME.getFont().draw(BATCH, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		GAME.getFont().draw(BATCH, "HighScore:"+GAME.getHighScore(), Gdx.graphics.getWidth()/2f-100, 30);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BATCH.begin();
		BATCH.draw(WALLPAPER, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		dibujaEncabezado();
		if (!NAVE.estaHerido()) {
			//colisiones entre balas y asteroides y su destrucción
			for (int i = 0; i < NAVE.getBalas().size(); i++) {
				Bullet b = NAVE.getBalas().get(i);
				b.update();
				Colisionable colisionable = b;
				b.colisionesBalasYAsteroides(LISTA_ASTEROIDES, EXPLOSION_SOUND, this, colisionable);
				if (b.isDestroyed()) {
					NAVE.getBalas().remove(b);
					i--; //para no saltarse 1 tras eliminar del arraylist
				}
			}
			//actualizar movimiento de asteroides dentro del área
			LISTA_ASTEROIDES.actualizarMovimiento();
			//colisiones entre asteroides y sus rebotes
			LISTA_ASTEROIDES.colisionesEntreAsteroides();
		}
		//dibujar balas
		for (Bullet b : NAVE.getBalas()) {b.draw(BATCH);}
		NAVE.draw(BATCH);
		//dibujar asteroides y manejar colisión con nave
		//colisionesAsteroidesConNave(NAVE, LISTA_ASTEROIDES);
		NAVE.colisionesAsteroidesConNave(LISTA_ASTEROIDES, BATCH);

		if (NAVE.estaDestruido()) {
			if (score > GAME.getHighScore())
				GAME.setHighScore(score);
			Screen ss = new PantallaGameOver(GAME, score);
			ss.resize(1200, 800);
			GAME.setScreen(ss);
			dispose();
		}
		BATCH.end();
		//nivel completado
		if (LISTA_ASTEROIDES.getAsteroides1().size()==0) {
			Screen ss = new PantallaJuego(GAME, RONDA + 1, NAVE.getVidas(), score,
					VELX_ASTEROIDES+1, VELY_ASTEROIDES+1, CANT_ASTEROIDES+3);
			ss.resize(1200, 800);
			GAME.setScreen(ss);
			dispose();
		}
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		GAME_MUSIC.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		GAME_MUSIC.stop();
		GAME.setScreen(new PantallaPausa(GAME, this));
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
		this.EXPLOSION_SOUND.dispose();
		this.GAME_MUSIC.dispose();
		this.WALLPAPER.dispose();
	}
}
