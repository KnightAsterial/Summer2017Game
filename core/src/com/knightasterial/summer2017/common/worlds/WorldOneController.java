package com.knightasterial.summer2017.common.worlds;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.knightasterial.summer2017.common.map.BasicGroundUnit;
import com.knightasterial.summer2017.common.projectiles.BasicProjectile;
import com.knightasterial.summer2017.common.util.GameConstants;
import com.knightasterial.summer2017.common.util.IOUtil;

public class WorldOneController implements Disposable{
	
	World box2DWorld;
	Body player;
	Fixture playerFixture;
	Body groundBody;
	
	
	Body wall;
	
	
	OrthographicCamera inGameCamera = new OrthographicCamera();
	
	/**
	 * in pixels
	 */
	int xOriginOfLastGeneratedFloor;
	ArrayList<BasicGroundUnit> groundTiles;
	private BasicGroundUnit groundToDestroy;
	
	ArrayList<BasicProjectile> projectiles;
	Vector2 tempProjectileVector;
	Vector2 tempUpdatedOriginOfProjectile;
	
	public WorldOneController(){
		init();
	}
	

	public void init(){
		box2DWorld = new World(new Vector2(0,-10), true);
		
		initializeFloor();
		initializeWall();
		initializePlayer();
		
		groundTiles = new ArrayList<BasicGroundUnit>();
		projectiles = new ArrayList<BasicProjectile>();
		
		/*
		RopeJointDef ropePlayerWallDef = new RopeJointDef();
		ropePlayerWallDef.maxLength = pxToMeters(500);
		ropePlayerWallDef.bodyA = player;
		ropePlayerWallDef.bodyB = wall;
		RopeJoint ropePlayerWall = (RopeJoint) box2DWorld.createJoint(ropePlayerWallDef);
		*/
	}
	
	private void initializeFloor() {
		//INITIALIZES FLOOR
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		//origin is center of the body
		groundBodyDef.position.set(new Vector2( pxToMeters(0),pxToMeters(20) ));
		//adds the body to the world
		groundBody = box2DWorld.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(pxToMeters(1380), pxToMeters(20));
		//adds the fixture to the body
		groundBody.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		xOriginOfLastGeneratedFloor = 1355;
	}
	private void initializeWall() {

		BodyDef wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		//origin is center of the body
		wallDef.position.set(new Vector2( pxToMeters(400),pxToMeters(100) ));
		//adds the body to the world
		wall = box2DWorld.createBody(wallDef);
		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(pxToMeters(20), pxToMeters(60));
		//adds the fixture to the body
		wall.createFixture(wallBox, 0.0f);
		wallBox.dispose();
		
	}
	private void initializePlayer() {
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyType.DynamicBody;
		playerDef.position.set(new Vector2( pxToMeters(50), pxToMeters(200) ));
		player = box2DWorld.createBody(playerDef);
		player.setFixedRotation(true);
		
		//Creates box for player
		PolygonShape square = new PolygonShape();
		square.setAsBox(1, 1);
		FixtureDef playerFixtureDef = new FixtureDef();
		playerFixtureDef.shape = square;
		playerFixtureDef.density = 0.985f;
		playerFixtureDef.friction = 0.4f;
		playerFixtureDef.restitution = 0.0f;
		playerFixture = player.createFixture(playerFixtureDef);
		square.dispose();
		BodyDef wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		//origin is center of the body
		wallDef.position.set(new Vector2( pxToMeters(400),pxToMeters(100) ));
		//adds the body to the world
		wall = box2DWorld.createBody(wallDef);
		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(pxToMeters(20), pxToMeters(60));
		//adds the fixture to the body
		wall.createFixture(wallBox, 0.0f);
		wallBox.dispose();
		
	}
	
	public void update(float delta){
		Vector2 vel = player.getLinearVelocity();
		Vector2 pos = player.getPosition();
		
		// apply left impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(Keys.A) && vel.x > -GameConstants.PLAYER_MAX_WALK_VELOCITY) {			
		     player.applyLinearImpulse(-1.4f, 0, pos.x, pos.y, true);
		}
		// apply right impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(Keys.D) && vel.x < GameConstants.PLAYER_MAX_WALK_VELOCITY) {
		     player.applyLinearImpulse(1.4f, 0, pos.x, pos.y, true);
		}
		// apply vertical jump impulse
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
			player.applyLinearImpulse(0, 20f, pos.x, pos.y, true);
		}
		if (Gdx.input.isTouched()){
			tempProjectileVector = IOUtil.getMouseVector(inGameCamera);
			tempProjectileVector.sub(player.getPosition()).nor().scl(GameConstants.BASE_PROJECTILE_FORCE);
			//makes it originate not entirely from player origin, but slightly in a direction towards mouse so it pops out of player correctly
			tempUpdatedOriginOfProjectile = new Vector2().set(player.getPosition()).add(tempProjectileVector.cpy().nor().scl(pxToMeters(20)));
			System.out.println("(" + tempProjectileVector.x + "," + tempProjectileVector.y + ")");
			projectiles.add(new BasicProjectile(box2DWorld, tempUpdatedOriginOfProjectile.x, tempUpdatedOriginOfProjectile.y, tempProjectileVector));
		}
		
		//if camera moves forwards
		if (player.getPosition().x > (inGameCamera.position.x + (inGameCamera.viewportWidth/4))){
			//moves camera forwards
			inGameCamera.translate(player.getPosition().x - (inGameCamera.position.x + (inGameCamera.viewportWidth/4)), 0);
			//generates new BASE floor tiles
			if ((inGameCamera.position.x + inGameCamera.viewportWidth/2) - xOriginOfLastGeneratedFloor < 100){
				groundTiles.add(new BasicGroundUnit(box2DWorld, pxToMeters(xOriginOfLastGeneratedFloor+50), pxToMeters(20)));			
				xOriginOfLastGeneratedFloor += 50;
			}
			//deletes off screen ground units
			deleteOffScreenGroundUnits();
		}
		
		doPhysicsStep(delta);
	}
	
	private void deleteOffScreenGroundUnits(){
		//Deletes off screen tiles
		Iterator<BasicGroundUnit> deleteGroundIter = groundTiles.iterator();
		while (deleteGroundIter.hasNext()){
			groundToDestroy = deleteGroundIter.next();
			if (groundToDestroy.x < (inGameCamera.position.x - (inGameCamera.viewportWidth/2))-26){
				groundToDestroy.dispose();
				deleteGroundIter.remove();
			}
			//heh, efficiency :P don't need to search entire thing because we know tiles are ordered from olders to newest :D
			else{
				break;
			}
		}
	}
	
	public World getWorld(){
		return box2DWorld;
	}
	
	public void setInGameCamera(OrthographicCamera cam){
		inGameCamera = cam;
	}
	
	
	private float accumulator = 0;
	private void doPhysicsStep(float deltaTime) {
	    // fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
	    float frameTime = Math.min(deltaTime, 0.25f);
	    accumulator += frameTime;
	    while (accumulator >= GameConstants.PHYSICS_TIME_STEP) {
	        box2DWorld.step(GameConstants.PHYSICS_TIME_STEP, 6, 2);
	        accumulator -= GameConstants.PHYSICS_TIME_STEP;
	    }
	}
	
	private float pxToMeters(int pixels){
		return pixels*GameConstants.PIXEL_TO_METER_RATIO;
	}
	
	private int metersToPx(float meters) {
		return (int) (meters * GameConstants.METER_TO_PIXEL_RATIO);
	}


	@Override
	public void dispose() {
	}
}
