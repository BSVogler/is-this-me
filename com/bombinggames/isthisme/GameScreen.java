/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
class GameScreen implements Screen {
	private final Sprite background;
	private final SpriteBatch batch = new SpriteBatch();
	private final Animation mainChar;
	private float time;
	private Vector2 p1Pos = new Vector2(50f, 50f);
	private float walkingspeed = 1/10f;
	private final Music music;

	public GameScreen(IsThisMe aThis) {
		background = new Sprite(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/background.png")));
		TextureRegion[] anim = new TextureRegion[]{
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w1.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w2.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w3.png")))
		};
		mainChar = new Animation(300f, anim);
		mainChar.setPlayMode(Animation.PlayMode.LOOP);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/music1.mp3"));
		music.setLooping(true);
	}

	@Override
	public void show() {
		music.play();
	}

	@Override
	public void render(float delta) {
		delta*=1000f;//to ms
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			p1Pos.x += delta*walkingspeed;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			p1Pos.x -= delta*walkingspeed;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)){
			p1Pos.y += delta*walkingspeed;

		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)){
			p1Pos.y -= delta*walkingspeed;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.LEFT)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			time += delta;
		}
		
		if (p1Pos.y > 200)
			p1Pos.y = 200;
		if (p1Pos.y < 10)
			p1Pos.y = 10;
		
		batch.begin();
		background.draw(batch);
		batch.draw(mainChar.getKeyFrame(time), p1Pos.x, p1Pos.y);
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
