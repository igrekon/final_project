package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.screens.MainMenuScreen;

// ИСПРАВЛЕНО: Наследуемся от Game, чтобы работал метод setScreen
public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public AssetManager assets;
	public int score = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new AssetManager();

		// --- ЗАГРУЗКА РЕСУРСОВ ---

		assets.load("textures/background.png", Texture.class);
		assets.load("textures/clone.png", Texture.class);
		assets.load("textures/stone.png", Texture.class);
		assets.load("textures/start.png", Texture.class);
		assets.load("textures/exit.png", Texture.class);

//		assets.load("audio/flap.mp3", Sound.class);
//		assets.load("audio/death.mp3", Sound.class);
//		assets.load("audio/basic.mp3", Music.class);

		assets.finishLoading(); // Ждем полной загрузки файлов в память

		// ИСПРАВЛЕНО: Музыка инициализируется строго внутри метода create после загрузки
//		Music music = assets.get("music/basic.mp3", Music.class);
//		music.setLooping(true);
//		music.setVolume(0.5f);
//		music.play();

		// ИСПРАВЛЕНО: Открываем главное меню внутри метода create
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		// Метод суперкласса Game автоматически отрисовывает текущий активный экран
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
	}
}
