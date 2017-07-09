package com.knightasterial.summer2017.common.projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.knightasterial.summer2017.common.util.GameConstants;

public class BasicProjectile implements Disposable{
	public World b2World;
	public Body body;
	public Fixture fixture;
	public TextureRegion texture;
	public float x;
	public float y;
	private Vector2 initialImpulse;
	
	public BasicProjectile(World world, float xCoord, float yCoord, Vector2 force){
		b2World = world;
		x = xCoord;
		y = yCoord;
		initialImpulse = force;
		
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(new Vector2(x,y));
		body = b2World.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4 * GameConstants.PIXEL_TO_METER_RATIO, 4*GameConstants.PIXEL_TO_METER_RATIO);
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 2.0f;
		fixDef.friction = 0.4f;
		fixDef.restitution = 0.0f;
		fixture = body.createFixture(fixDef);
		shape.dispose();
		
		body.applyLinearImpulse(force.x, force.y, x, y, true);
	}

	@Override
	public void dispose() {
		b2World.destroyBody(body);
	}
}
