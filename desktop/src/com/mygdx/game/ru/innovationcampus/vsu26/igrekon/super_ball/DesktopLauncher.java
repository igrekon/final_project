package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.SharedLibraryLoader;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		new SharedLibraryLoader().load("gdx-box2d");
		System.setProperty("natives.dir", "natives/");
		Box2D.init();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Super Ball");
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
