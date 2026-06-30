package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.MyGdxGame;

public class MainMenuScreen extends ScreenAdapter {
    private final MyGdxGame game;
    private Stage stage;

    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        // Передаем управление кнопкам

        // Создание текстур для кнопок
        TextureRegionDrawable playBtnDrawable = new TextureRegionDrawable(new TextureRegion(game.assets.get("textures/start.png", Texture.class)));


        TextureRegionDrawable exitBtnDrawable = new TextureRegionDrawable(new TextureRegion(game.assets.get("textures/exit.png", Texture.class)));

        ImageButton playButton = new ImageButton(playBtnDrawable);
        ImageButton exitButton = new ImageButton(exitBtnDrawable);

        // Получаем размеры экрана для вычислений
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        playButton.setPosition(screenWidth / 2f - 100f, screenHeight / 2f + 60f);
        exitButton.setPosition(screenWidth / 2f - 80f, screenHeight / 2f - 140f);

        // Слушатели нажатий
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Переход в игру
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Выход из игры
            }
        });

        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        // Очищаем экран темно-серым цветом
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
