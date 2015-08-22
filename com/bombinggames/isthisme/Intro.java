package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Benedikt Vogler
 */
public class Intro implements Screen {
	private final SpriteBatch batch = new SpriteBatch();
	private Sprite overlay = new Sprite(new Texture("com/bombinggames/isthisme/graphics/introBg.png"));
	private final IsThisMe game;
	private final Sound intro = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/intro.ogg"));
	private float timer;

	public Intro(IsThisMe game) {
		this.game = game;
	}
	
	
	@Override
	public void show() {
		intro.play();
	}

	@Override
	public void render(float delta) {
		timer+=delta;
		Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (timer > 20)overlay.setColor(1, 1, 1, 20-timer);
		if (timer > 21 || Gdx.input.isKeyPressed(Input.Keys.X) ) {
			dispose();
			game.setScreen(new GameScreen(game));
			return;
		}
		
		batch.begin();
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
		intro.stop();
		intro.dispose();
		batch.dispose();
	}
	
}
