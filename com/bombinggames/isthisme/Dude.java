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
	private static final Texture corpseTexture = new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/splatter.png"));
	private final Sprite sprite = new Sprite(dudeTexture);
	private boolean alive = true;
		
	public Dude() {
		sprite.setPosition(1200, (float) (Math.random()*50+50));
	}
	
	/**
	 *  
	 * @param dt time in ms
	 */
	public void update(float dt){
		//walk
		if (alive)
			sprite.setX(sprite.getX() - dt/20f);
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
}
