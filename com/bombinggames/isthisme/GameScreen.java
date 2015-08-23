/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
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
	private Sprite background = new Sprite(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/background.jpg")));
	private final SpriteBatch batch = new SpriteBatch();
	private final Animation walkinAnimation;
	private float time;
	private final Vector2 p1Pos = new Vector2(50f, 50f);
	private float walkingspeed = 1/10f;
	private Music music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/music1.mp3"));
	private final Sound attackSound = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/hit.wav"));
	private final Sound huhSound = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/huh.wav"));
	private final Sound fiep = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/fiep.wav"));
	private final ArrayList<Dude> dudeList = new ArrayList<>(10);
	private float hitTimer = 0f;
	private boolean hitting = false;
	private final Animation hitAnimation;
	private boolean impact;
	private Sprite overlay = new Sprite(new Texture("com/bombinggames/isthisme/graphics/overlay.png"));
	private long fiepInstance;
	private final IsThisMe game;
	
	private boolean deathmode=false;
	private float flashTimer = 200;
	private Sprite stream;
	private Sprite walls;
	private Sprite street;
	private Sprite flash;
	private float shakeWall =-2;
	private boolean shakeWallRight = true;
	private int copsKill;
	private boolean escape;

	public GameScreen(IsThisMe game) {
		this.game = game;
		
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
	}
	
	public void setupDeath(){
		background = new Sprite(new Texture("com/bombinggames/isthisme/graphics/backgroundDeath.jpg"));
		stream = new Sprite(new Texture("com/bombinggames/isthisme/graphics/stream.png"));
		walls = new Sprite(new Texture("com/bombinggames/isthisme/graphics/walls.png"));
		street = new Sprite(new Texture("com/bombinggames/isthisme/graphics/street.png"));
		flash = new Sprite(new Texture("com/bombinggames/isthisme/graphics/light.png"));
		
		stream.setX(-200);
		stream.setY(Gdx.graphics.getHeight() - stream.getHeight());
		flash.setPosition(630, 400);
		street.setX(450);
		music = Gdx.audio.newMusic(Gdx.files.internal("com/bombinggames/isthisme/music/blowjob.mp3"));
		p1Pos.x = 680;
		deathmode = true;
	}

	@Override
	public void show() {
		if (deathmode){
			dudeList.add(new Dude(deathmode, flash.getX(), flash.getY()+50));
			dudeList.add(new Dude(deathmode, flash.getX()+70, flash.getY()-10));
		} else {
			dudeList.add(new Dude(deathmode, 1200, (float) (Math.random()*50+50)));
			dudeList.add(new Dude(deathmode, 1500, (float) (Math.random()*50+50)));
		}
		music.setLooping(true);
		music.play();
		fiepInstance = fiep.loop(0.0f);
	}

	@Override
	public void render(float delta) {
		delta*=1000f;//to ms
		if (!hitting) {
			boolean walking = false;
			if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
				p1Pos.x += delta*walkingspeed;
				walking = true;
			}

			if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)){
				p1Pos.x -= delta*walkingspeed;
				walking = true;
			}

			if (escape || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
				p1Pos.y += delta*walkingspeed;
				walking = true;
			}

			if (!escape && (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))){
				p1Pos.y -= delta*walkingspeed;
				walking = true;
			}

			if (walking) {
				time += delta;
			}
		}
		
		if (hitting)
			hitTimer += delta;
		
		if (hitTimer > hitAnimation.getAnimationDuration())
			hitting = false;
		
		if (hitTimer > hitAnimation.getFrameDuration())
			impact();
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isButtonPressed(Buttons.LEFT)){
			startAttack();
		}
		
		if (!deathmode && p1Pos.y > 200)
			p1Pos.y = 200;
		if (p1Pos.y < 10)
			p1Pos.y = 10;
		
		if (deathmode && p1Pos.x > 850)
			p1Pos.x = 850;
		
		if (p1Pos.x > 1280) {
			fiep.stop();
			music.stop();
			music.dispose();
			game.setScreen(new Blowjob(game));
			return;
		}
		
		if (walls!=null) {
			if (shakeWallRight)
				shakeWall += delta/30f;
			else {
				shakeWall -= delta/30f;
			}
			if (shakeWall>2) shakeWallRight=false;
			if (shakeWall< -2) shakeWallRight=true;
			walls.setRotation(shakeWall);
		}
		
		if (stream!=null){
			stream.setX(stream.getX() + delta/40f);
		}
		
		if (flash!=null) {
			flashTimer -= delta;
			if (flashTimer<0) {
				flashTimer=200;
				flash.setRotation((float) (Math.random()*20f-5f));
			}
			flash.setAlpha(flashTimer/200f);
		}
		
		Dude nearestAliveDude = dudeList.get(0);
		if (!nearestAliveDude.isAlive()) nearestAliveDude = null;
		for (Dude dude : dudeList) {
			dude.update(delta);
			if (
				dude.isAlive()
				&& (nearestAliveDude == null || dude.getPosition().dst2(p1Pos) < nearestAliveDude.getPosition().dst2(p1Pos))
			)
				nearestAliveDude = dude;
		}
		float fiepVolume = 0.01f;
		if (nearestAliveDude ==null)
			fiep.setVolume(fiepInstance, 0);
		else {
			float divisor = (nearestAliveDude.getPosition().dst(p1Pos)-40);
			if (divisor>0)
				fiepVolume = 1f/divisor;
			else
				fiepVolume = 1;
			if (fiepVolume>1) fiepVolume = 1;
			if (fiepVolume<0.01f) fiepVolume = 0.01f;
			fiep.setVolume(fiepInstance, fiepVolume);
		}
		music.setVolume(1f/(fiepVolume/2+0.5f)-0.8f);
		
		//render
		batch.begin();
		background.draw(batch);
		
		if (stream!=null)
			stream.draw(batch);
		
		if (street!=null)
			street.draw(batch);
		
		if (hitting){
			batch.draw(hitAnimation.getKeyFrame(hitTimer), p1Pos.x, p1Pos.y);
		} else {
			batch.draw(walkinAnimation.getKeyFrame(time), p1Pos.x, p1Pos.y);
		}
		
		for (Dude dude : dudeList) {
			dude.draw(batch);
		}
		
		if (walls!=null)
			walls.draw(batch);
		
		if (flash!=null)
			flash.draw(batch);
		
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
		fiep.stop();
		fiep.dispose();
		music.stop();
		music.dispose();
		batch.dispose();
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
			attackSound.play(0.6f);
			for (Dude dude : dudeList) {
				if (dude.getPosition().epsilonEquals(p1Pos, 50)) {
					dude.hit();
					copsKill++;
					if (deathmode && copsKill==2){
						escape = true;
					}
				}
			}
		}
	}
	
}
