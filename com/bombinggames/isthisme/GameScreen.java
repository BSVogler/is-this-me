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
	private final Animation walkinAnimation;
	private float time;
	private final Vector2 p1Pos = new Vector2(50f, 50f);
	private float walkingspeed = 1/10f;
	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/music1.mp3"));
	private final Sound attackSound = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/hit.wav"));
	private final Sound huhSound = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/huh.wav"));
	private final ArrayList<Dude> dudeList = new ArrayList<>(10);
	private float hitTimer = 0f;
	private boolean hitting = false;
	private final Animation hitAnimation;
	private boolean impact;
	private Sprite overlay = new Sprite(new Texture("com/bombinggames/isthisme/graphics/overlay.png"));

	public GameScreen(IsThisMe aThis) {
		TextureRegion[] anim = new TextureRegion[]{
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w1.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w2.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/w3.png")))
		};
		walkinAnimation = new Animation(300f, anim);
		walkinAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		anim = new TextureRegion[]{
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/h1.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/h2.png")))
		};
		hitAnimation = new Animation(300f, anim);
		hitAnimation.setPlayMode(Animation.PlayMode.NORMAL);
		
		music.setLooping(true);
		
		
		dudeList.add(new Dude(1200, (float) (Math.random()*50+50)));
		dudeList.add(new Dude(1500, (float) (Math.random()*50+50)));
	}

	@Override
	public void show() {
		music.play();
	}

	@Override
	public void render(float delta) {
		delta*=1000f;//to ms
		if (!hitting) {
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
		}
		
		if (hitting)
			hitTimer += delta;
		
		if (hitTimer > hitAnimation.getAnimationDuration())
			hitting = false;
		
		if (hitTimer > hitAnimation.getFrameDuration())
			impact();
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			startAttack();
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
		if (hitting){
			batch.draw(hitAnimation.getKeyFrame(hitTimer), p1Pos.x, p1Pos.y);
		} else {
			batch.draw(walkinAnimation.getKeyFrame(time), p1Pos.x, p1Pos.y);
		}
		
		for (Dude dude : dudeList) {
			dude.draw(batch);
		}
		
		overlay.setColor(0, (float) (1-Math.random()*0.5f), (float) (1-Math.random()*0.5f), (float) (1-Math.random()*0.3f));
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
	
	protected void startAttack(){
		if (!hitting) {
			impact = false;
			hitTimer = 0;//reset
			hitting = true;
			huhSound.play();
		}
	}
	
	protected void impact(){
		if (!impact) {
			impact = true;
			attackSound.play();
			for (Dude dude : dudeList) {
				if (dude.getPosition().epsilonEquals(p1Pos, 50))
					dude.hit();
			}
		}
	}
	
}
