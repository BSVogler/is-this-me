package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Benedikt Vogler
 */
public class Intro implements Screen {
	private final SpriteBatch batch = new SpriteBatch();
	private final Sprite bg = new Sprite(new Texture("com/bombinggames/isthisme/graphics/introBg.png"));
	private final IsThisMe game;
	private final Sound intro = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/intro.ogg"));
	private float timer;
	private final Animation flyAnimation;
	private final Vector2 flyPos = new Vector2(0, 150);

	public Intro(IsThisMe game) {
		this.game = game;
		
		TextureRegion[] anim = new TextureRegion[]{
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/fly1.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/fly2.png")))
		};
		flyAnimation = new Animation(0.25f, anim);
		flyAnimation.setPlayMode(Animation.PlayMode.LOOP);
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
		if (timer > 20)bg.setColor(1, 1, 1, 20-timer);
		if (timer > 21 || Gdx.input.isKeyPressed(Input.Keys.X) ) {
			dispose();
			game.setScreen(new GameScreen(game));
			return;
		}
		
		flyPos.x = timer*80f;
		flyPos.y = timer*10f+300;
		
		batch.begin();
		bg.draw(batch);
		batch.draw(flyAnimation.getKeyFrame(timer), flyPos.x, flyPos.y);
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
