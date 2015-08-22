package com.bombinggames.isthisme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Benedikt Vogler
 */
public class Dude extends Sprite {

	public Dude() {
		super(new Texture(Gdx.files.internal("com/bombinggames/isthisme/graphics/dude.png")));
		setPosition(1200, (float) (Math.random()*50+50));
	}
	
	/**
	 * 
	 * @param dt time in ms
	 */
	public void update(float dt){
		setX(getX() - dt/20f);
	}
}
