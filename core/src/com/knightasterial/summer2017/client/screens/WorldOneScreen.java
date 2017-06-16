package com.knightasterial.summer2017.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.knightasterial.summer2017.common.util.GameConstants;
import com.knightasterial.summer2017.common.worlds.WorldOneController;

public class WorldOneScreen implements Screen{

	Game parentGame;
	WorldOneController worldController;
	
	/**
	 * EVERYTHING IS DRAWN IN METERS SCALE!!!! USE CONVERSION RATIO
	 */
	SpriteBatch batch;
	/**
	 * EVERYTHING IS DRAWN IN METERS SCALE!!!! USE CONVERSION RATIO
	 */
	OrthographicCamera inGameCamera;
	
	/**
	 * EVERYTHING IS DRAWN IN METERS SCALE!!!! USE CONVERSION RATIO
	 */
	ShapeRenderer sRender;
	
	Box2DDebugRenderer box2ddebugrenderer;
	
	public WorldOneScreen(Game parentGame, WorldOneController worldController){
		this.parentGame = parentGame;
		this.worldController = worldController;
		
		batch = new SpriteBatch();
		inGameCamera = new OrthographicCamera();
		inGameCamera.setToOrtho(false, GameConstants.DEFAULT_WINDOW_WIDTH * GameConstants.PIXEL_TO_METER_RATIO, GameConstants.DEFAULT_WINDOW_HEIGHT * GameConstants.PIXEL_TO_METER_RATIO);
		
		sRender = new ShapeRenderer();
		
		box2ddebugrenderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		inGameCamera.update();
		
		batch.setProjectionMatrix(inGameCamera.combined);
		batch.begin();
		box2ddebugrenderer.render(worldController.getWorld(), inGameCamera.combined);
		batch.end();
		
		
		worldController.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public OrthographicCamera getInGameCamera(){
		return inGameCamera;
	}

}
