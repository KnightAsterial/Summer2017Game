package com.knightasterial.summer2017.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.knightasterial.summer2017.SummerGame;
import com.knightasterial.summer2017.common.util.GameConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.height = GameConstants.DEFAULT_WINDOW_HEIGHT;
		config.width = GameConstants.DEFAULT_WINDOW_WIDTH;
		
		new LwjglApplication(new SummerGame(), config);
	}
}
