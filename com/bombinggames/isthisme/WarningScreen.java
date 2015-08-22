package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 *
 * @author Benedikt Vogler
 */
public class WarningScreen implements Screen {
	private final SpriteBatch batch = new SpriteBatch();
	private Sprite overlay = new Sprite(new Texture("com/bombinggames/isthisme/graphics/warning.png"));
	private final IsThisMe game;

	public WarningScreen(IsThisMe game) {
		this.game = game;
	}
	
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER) )
			game.setScreen(new GameScreen());
		
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
	}
	
}
