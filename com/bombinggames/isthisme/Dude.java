package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Benedikt Vogler
 */
public class Dude {
	private static final Sound impact = Gdx.audio.newSound(Gdx.files.internal("com/bombinggames/isthisme/sound/impact.wav"));
	private static final Texture dudeTexture = new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/dude.png"));
	private static final Texture copTexture = new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/cop.png"));
	private static final Texture corpseTexture = new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/corpse.png"));
	private final Sprite sprite;
	private boolean alive = true;
	private boolean deathmode = false;
		
	public Dude(boolean deathmode, float x, float y) {
		this.deathmode = deathmode;
		if (deathmode)
			sprite = new Sprite(copTexture);
		else
			sprite = new Sprite(dudeTexture);
		sprite.setPosition(x, y);
	}
	
	/**
	 *  
	 * @param dt time in ms
	 */
	public void update(float dt){
		//walk
		if (alive) {
			if (deathmode)
				sprite.setY(sprite.getY() - dt/20f);
			else 
				sprite.setX(sprite.getX() - dt/20f);
		}
		
	}

	public void draw(Batch batch) {
		sprite.draw(batch);
	}
	
	public void hit(){
		sprite.setTexture(corpseTexture);
		impact.play();
		alive = false;
	}
	
	public Vector2 getPosition(){
		return new Vector2(sprite.getX(), sprite.getY());
	}

	boolean isAlive() {
		return alive;
	}
}
