package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class PantallaJuego implements Screen {
	private SpaceNavigation game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private Texture wallpaper;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	private Nave4 nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private ArrayList<Asteroide> asteroides1 = new ArrayList<>();
	private ArrayList<Asteroide> asteroides2 = new ArrayList<>();

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;

		wallpaper = new Texture(Gdx.files.internal("espacio.png"));
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.1f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("videoplayback.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);
        //crear asteroides
        Random r = new Random();
	    /*for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}*/

		for (int i = 0; i < cantAsteroides; i++) {
			if (MathUtils.random(0, 10) < 2) {
				AsteroideFuerte nuevoAsteroide = new AsteroideFuerte(r.nextInt((int)Gdx.graphics.getWidth()),
						50+r.nextInt((int)Gdx.graphics.getHeight()-50),
						20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4),
						new Texture(Gdx.files.internal("asteroideFuerte.png")));
				asteroides1.add(nuevoAsteroide);
				asteroides2.add(nuevoAsteroide);
			}
			else {
				AsteroideDebil nuevoAsteroide = new AsteroideDebil(r.nextInt((int)Gdx.graphics.getWidth()),
						50+r.nextInt((int)Gdx.graphics.getHeight()-50),
						20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4),
						new Texture(Gdx.files.internal("aGreyMedium4.png")));
				asteroides1.add(nuevoAsteroide);
				asteroides2.add(nuevoAsteroide);
			}
		}
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}

	public void colisionesBalasYAsteroides(ArrayList<Bullet> balas) {
		for (int i = 0; i < balas.size(); i++) {
			Bullet b = balas.get(i);
			b.update();
			/*for (int j = 0; j < balls1.size(); j++) {
				if (b.checkCollision(balls1.get(j))) {
					explosionSound.play();
					balls1.remove(j);
					balls2.remove(j);
					j--;
					score +=10;
				}
			}*/
			for (int j = 0; j < asteroides1.size(); j++) {
				if (b.checkCollision(asteroides1.get(j))) {
					Asteroide a = asteroides1.get(j);
					if (a.getClass().getSimpleName().equals("AsteroideDebil")) {
						explosionSound.play();
						asteroides1.remove(j);
						asteroides2.remove(j);
						j--;
						score +=10;
					}
					else {
						AsteroideFuerte aux = (AsteroideFuerte) a;
						if (aux.getVidas() == 2) {
							aux.setVidas(1);
						}
						else {
							explosionSound.play();
							asteroides1.remove(j);
							asteroides2.remove(j);
							j--;
							score +=20;
						}
					}
				}
			}
			//   b.draw(batch);
			if (b.isDestroyed()) {
				balas.remove(b);
				i--; //para no saltarse 1 tras eliminar del arraylist
			}
		}
	}

	public void colisionesEntreAsteroides(ArrayList<Asteroide> asteroides1, ArrayList<Asteroide> asteroides2) {
		for (int i=0;i<asteroides1.size();i++) {
			Asteroide a = asteroides1.get(i);
			Asteroide aux1;
			if (a.getClass().getSimpleName().equals("AsteroideFuerte")) {
				aux1 = (AsteroideFuerte) a;
			}
			else {
				aux1 = (AsteroideDebil) a;
			}
			for (int j=0;j<asteroides2.size();j++) {
				Asteroide a2 = asteroides1.get(i);
				if (a2.getClass().getSimpleName().equals("AsteroideFuerte")) {
					AsteroideFuerte aux2 = (AsteroideFuerte) a2;
					if (i<j) {
						aux1.checkCollision(aux2);
					}
				}
				else {
					AsteroideDebil aux2 = (AsteroideDebil) a2;
					if (i<j) {
						aux1.checkCollision(aux2);
					}
				}
			}
		}
	}

	public void colisionesAsteroidesConNave(Nave4 nave, ArrayList<Asteroide> asteroides1, ArrayList<Asteroide> asteroides2) {
		for (int i = 0; i < asteroides1.size(); i++) {
			Asteroide b = asteroides1.get(i);
			b.draw(batch);
			//perdió vida o game over
			if (nave.checkCollision(b)) {
				//asteroide se destruye con el choque
				asteroides1.remove(i);
				asteroides2.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(wallpaper, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		dibujaEncabezado();
		if (!nave.estaHerido()) {
			//colisiones entre balas y asteroides y su destrucción
			colisionesBalasYAsteroides(balas);
			//actualizar movimiento de asteroides dentro del área
			for (Asteroide a : asteroides1) {
				if (a.getClass().getSimpleName().equals("AsteroideFuerte")) {
					AsteroideFuerte aux = (AsteroideFuerte) a;
					aux.update();
				}
				else {
					AsteroideDebil aux = (AsteroideDebil) a;
					aux.update();
				}
			}
			//colisiones entre asteroides y sus rebotes
			colisionesEntreAsteroides(asteroides1, asteroides2);
		}
		//dibujar balas
		for (Bullet b : balas) {b.draw(batch);}
		nave.draw(batch, this);
		//dibujar asteroides y manejar colisión con nave
		colisionesAsteroidesConNave(nave, asteroides1, asteroides2);

		if (nave.estaDestruido()) {
			if (score > game.getHighScore())
				game.setHighScore(score);
			Screen ss = new PantallaGameOver(game, score);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
		batch.end();
		//nivel completado
		if (asteroides1.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score,
					velXAsteroides+1, velYAsteroides+1, cantAsteroides+3);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		gameMusic.stop();
		game.setScreen(new PantallaPausa(game, this));
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
		this.wallpaper.dispose();
	}
}
