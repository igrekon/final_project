package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.GameResources;

import jdk.jfr.internal.tool.View;

public class LiveView extends View {

    private final static int livePadding = 6;

    Texture texture;

    int leftLives;

    public LiveView(float x, float y) {
        super(x, y);
        texture = new Texture(GameResources.LIVE_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }


    public void draw(SpriteBatch batch) {
        if (leftLives > 0)
            batch.draw(texture, x + (texture.getWidth() + livePadding), y, width, height);
        if (leftLives > 1) batch.draw(texture, x, y, width, height);
        if (leftLives > 2)
            batch.draw(texture, x + 2 * (texture.getWidth() + livePadding), y, width, height);
    }

}
