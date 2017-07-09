package com.knightasterial.summer2017.common.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.knightasterial.summer2017.common.util.GameConstants;

public class BasicGroundUnit implements Disposable{
	public World b2World;
	public Body groundBody;
	public Fixture groundFixture;
	public TextureRegion texture;
	public float x;
	public float y;
	
	public BasicGroundUnit(World world, float xCoord, float yCoord){
		b2World = world;
		x = xCoord;
		y = yCoord;
		
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyType.StaticBody;
		floorDef.position.set(new Vector2(x,y));
		groundBody = b2World.createBody(floorDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(25 * GameConstants.PIXEL_TO_METER_RATIO, 20*GameConstants.PIXEL_TO_METER_RATIO);
		groundFixture = groundBody.createFixture(shape, 0.0f);
		shape.dispose();
	}

	@Override
	public void dispose() {
		b2World.destroyBody(groundBody);
	}
	
 }
