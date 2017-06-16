package com.knightasterial.summer2017.common.worlds;

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
import com.knightasterial.summer2017.common.util.GameConstants;

public class WorldOneController {
	
	World box2DWorld;
	Body player;
	Fixture playerFixture;
	Body groundBody;
	
	
	Body wall;
	
	
	OrthographicCamera inGameCamera = new OrthographicCamera();
	
	public WorldOneController(){
		init();
	}
	

	public void init(){
		box2DWorld = new World(new Vector2(0,-10), true);
		initializeFloor();
		initializeWall();
		initializePlayer();
		
		
		
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
		groundBox.setAsBox(pxToMeters(1281), pxToMeters(20));
		//adds the fixture to the body
		groundBody.createFixture(groundBox, 0.0f);
		groundBox.dispose();
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
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
			player.applyLinearImpulse(0, 20f, pos.x, pos.y, true);
		}

		if (player.getPosition().x > (inGameCamera.position.x + (inGameCamera.viewportWidth/4))){
			inGameCamera.translate(player.getPosition().x - (inGameCamera.position.x + (inGameCamera.viewportWidth/4)), 0);
		}

		
		doPhysicsStep(delta);
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
}
