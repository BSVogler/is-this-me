package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Benedikt Vogler
 */
public class Blowjob implements Screen {
	private final SpriteBatch batch = new SpriteBatch();
	private final IsThisMe game;
	private final Sound blowjob = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/blowjob.wav"));
	private final Sprite background = new Sprite(new Texture("com/bombinggames/isthisme/graphics/backgroundBJ.jpg"));
	private final Sprite head = new Sprite(new Texture("com/bombinggames/isthisme/graphics/blowjobdude.png"));
	private final Sprite guyWithDick = new Sprite(new Texture("com/bombinggames/isthisme/graphics/guyWithDick.png"));
	private final Sprite overlay = new Sprite(new Texture("com/bombinggames/isthisme/graphics/overlaysquared.png"));
	private float shake =-5;
	private boolean shakeRight = true;
	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/blowjob.mp3"));
	private float amountmoved = 0;
	
	public Blowjob(IsThisMe game) {
		this.game = game;
		head.setY(Gdx.graphics.getHeight()-head.getHeight());
		guyWithDick.setX(400);
		overlay.setPosition(-160, -230);
		music.setLooping(true);
		music.setVolume(0.55f);
	}
	
	@Override
	public void show() {
		blowjob.loop();
		music.play();
	}

	@Override
	public void render(float delta) {
		delta *= 1000;
		//update
		boolean moved = false;
		if ((head.getX() < 100) && (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))) {
			head.setX(head.getX()+delta/5f);
			amountmoved+=delta/5f;
			moved = true;
		}
		if ((head.getX() > -50) && (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))) {
			head.setX(head.getX()-delta/5f);
			amountmoved+=delta/5f;
			moved = true;
		}
		
		if (!moved){
			blowjob.pause();
		} else {
			blowjob.resume();
		}
		
		if (shakeRight)
			shake += delta/80f;
		else {
			shake -= delta/80f;
		}
		if (shake>3) shakeRight=false;
		if (shake< -3) shakeRight=true;
		guyWithDick.setRotation(shake);
		overlay.rotate(delta/40f);
		
		if (amountmoved > 1200) {
			dispose();
			GameScreen screen = new GameScreen(game);
			screen.setupDeath();
			game.setScreen(screen);
			return;
		}
		
		//render
		batch.begin();
		background.draw(batch);
		guyWithDick.draw(batch);
		head.draw(batch);
		overlay.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		music.dispose();
		batch.dispose();
		blowjob.dispose();
	}
	
}
