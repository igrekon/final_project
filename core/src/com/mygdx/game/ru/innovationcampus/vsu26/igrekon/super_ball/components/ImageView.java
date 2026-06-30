package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View {
    Texture texture;
    ImageView fullBlackoutView;

    public ImageView(float x, float y, String imagePath) {
        super(x, y);
        texture = new Texture(imagePath);
        this.width = texture.getWidth() ;
        this.height = texture.getHeight() ;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
