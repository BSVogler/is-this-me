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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 *
 * @author Benedikt Vogler
 */
class GameScreen implements Screen {
	private final Sprite background = new Sprite(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/background.png")));
	private final SpriteBatch batch = new SpriteBatch();
	private final Animation mainChar;
	private float time;
	private final Vector2 p1Pos = new Vector2(50f, 50f);
	private float walkingspeed = 1/10f;
	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/music1.mp3"));
	private Sound attackSound;
	private final ArrayList<Dude> dudeList = new ArrayList<>(10);

	public GameScreen(IsThisMe aThis) {
		TextureRegion[] anim = new TextureRegion[]{
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w1.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w2.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w3.png")))
		};
		mainChar = new Animation(300f, anim);
		mainChar.setPlayMode(Animation.PlayMode.LOOP);
		
		music.setLooping(true);
		
		attackSound = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/hit.wav"));
		
		dudeList.add(new Dude());
	}

	@Override
	public void show() {
		music.play();
	}

	@Override
	public void render(float delta) {
		delta*=1000f;//to ms
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
			p1Pos.x += delta*walkingspeed;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)){
			p1Pos.x -= delta*walkingspeed;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
			p1Pos.y += delta*walkingspeed;

		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
			p1Pos.y -= delta*walkingspeed;
		}
		
		if (
			Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.LEFT)||Gdx.input.isKeyPressed(Keys.RIGHT) ||
			Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.D)
			) {
			time += delta;
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			attack();
		}
		
		if (p1Pos.y > 200)
			p1Pos.y = 200;
		if (p1Pos.y < 10)
			p1Pos.y = 10;
		
		for (Dude dude : dudeList) {
			dude.update(delta);
		}
		
		//render
		batch.begin();
		background.draw(batch);
		batch.draw(mainChar.getKeyFrame(time), p1Pos.x, p1Pos.y);
		
		for (Dude dude : dudeList) {
			dude.draw(batch);
		}
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
	
	protected void attack(){
		attackSound.play();
	}
	
}
