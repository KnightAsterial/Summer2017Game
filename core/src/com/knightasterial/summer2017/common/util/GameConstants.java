package com.knightasterial.summer2017.common.util;

public class GameConstants {
	/**
	 * IN PIXELS
	 */
	public static final int DEFAULT_WINDOW_WIDTH = 1280;
	
	/**
	 * IN PIXELS
	 */
	public static final int DEFAULT_WINDOW_HEIGHT = 720;
	
	public static final float PIXEL_TO_METER_RATIO = 0.04f;
	
	public static final float METER_TO_PIXEL_RATIO = 25;
	
	public static final float PHYSICS_TIME_STEP = 1/45f;
	
	
	//-------------------------------------------------PLAYER STATS
	
	public static final float PLAYER_MAX_WALK_VELOCITY = 5.0f;
	
	public static final float PLAYER_BASE_PROJECTILE_FORCE = 100 * 0.04f;  // px * pxtometer ratio
	
	/**
	 * IN SECONDS
	 */
	public static final float PLAYER_BASE_FIRE_RATE = 1;
}
