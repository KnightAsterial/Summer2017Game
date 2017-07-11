package com.knightasterial.summer2017.common.entities;

import java.util.ArrayList;

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
import com.knightasterial.summer2017.common.projectiles.BasicProjectile;
import com.knightasterial.summer2017.common.util.GameConstants;
import com.knightasterial.summer2017.common.util.IOUtil;

public class PlayerEntity implements Disposable{
	public World b2World;
	public Body body;
	public Fixture fixture;
	public TextureRegion texture;
	
	private float fireRate;
	private long nanoTimeLastFired;
	
	private Vector2 tempProjectileVector;
	private Vector2 tempUpdatedOriginOfProjectile;
	
	public PlayerEntity(World world){
		b2World = world;
		
		//initialize box2D body
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyType.DynamicBody;
		playerDef.position.set(new Vector2( pxToMeters(50), pxToMeters(200) ));
		body = b2World.createBody(playerDef);
		body.setFixedRotation(true);
		
		//Creates box for player
		PolygonShape square = new PolygonShape();
		square.setAsBox(1, 1);
		FixtureDef playerFixtureDef = new FixtureDef();
		playerFixtureDef.shape = square;
		playerFixtureDef.density = 0.985f;
		playerFixtureDef.friction = 0.4f;
		playerFixtureDef.restitution = 0.0f;
		fixture = body.createFixture(playerFixtureDef);
		square.dispose();
		
		fireRate = GameConstants.PLAYER_BASE_FIRE_RATE;
		nanoTimeLastFired = -1;
		
	}
	private float pxToMeters(int pixels){
		return pixels*GameConstants.PIXEL_TO_METER_RATIO;
	}
	@Override
	public void dispose() {
		b2World.destroyBody(body);
	}
	
	public void setFireRate(float rate){
		fireRate = rate;
	}
	
	public float getFireRate(){
		return fireRate;
	}
	
	public boolean canShoot(){
		if ((System.nanoTime() - nanoTimeLastFired) > 1000000000*fireRate){
			return true;
		}
		else{
			return false;
		}
	}
	public void shoot(Vector2 mousepos, ArrayList<BasicProjectile> projectiles){
		tempProjectileVector = mousepos;
		//makes a vector in direction of mouse position from player body
		tempProjectileVector.sub(body.getPosition()).nor().scl(GameConstants.PLAYER_BASE_PROJECTILE_FORCE);
		//makes it originate not entirely from player origin, but slightly in a direction towards mouse so it pops out of player correctly
		tempUpdatedOriginOfProjectile = new Vector2().set(body.getPosition()).add(tempProjectileVector.cpy().nor().scl(pxToMeters(20)));
		projectiles.add(new BasicProjectile(b2World, tempUpdatedOriginOfProjectile.x, tempUpdatedOriginOfProjectile.y, tempProjectileVector));
		
		nanoTimeLastFired = System.nanoTime();
	}
}
