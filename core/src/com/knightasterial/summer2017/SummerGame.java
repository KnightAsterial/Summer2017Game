package com.knightasterial.summer2017;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.knightasterial.summer2017.client.screens.WorldOneScreen;
import com.knightasterial.summer2017.common.worlds.WorldOneController;

public class SummerGame extends Game {
	WorldOneController worldController1 = new WorldOneController();
	WorldOneScreen worldScreen1;
	List<Screen> screensToDispose;
	
	@Override
	public void create () {
		screensToDispose = new ArrayList<Screen>();
		
		worldController1 = new WorldOneController();
		worldScreen1 = new WorldOneScreen(this, worldController1);
		worldController1.setInGameCamera(worldScreen1.getInGameCamera());
		setScreen(worldScreen1);
		
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//renders the current set screen
		super.render();		
	}
	
	@Override
	public void dispose () {
		for (Screen screen : screensToDispose){
			screen.dispose();
		}
	}
}
