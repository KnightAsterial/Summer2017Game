package com.knightasterial.summer2017.common.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class IOUtil {
	public static int getMouseX(OrthographicCamera cam){
		return (int)getMouseVector3InWorld(cam).x;
	}
	/**
	 * Function for when origin is in the bottom left corner
	 * @return mouse Y-coordinate
	 */
	public static int getMouseY(OrthographicCamera cam){
		return ( (int) getMouseVector3InWorld(cam).y );
	}
	
	public static Vector2 getMouseVector(OrthographicCamera cam){
		
		return new Vector2( getMouseVector3InWorld(cam).x, getMouseVector3InWorld(cam).y);
	}
	
	public static Vector3 getMouseVector3InWorld(OrthographicCamera cam){
		return cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
}
