package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.MyGdxGame;

public class Obstacle {
    private Body body;
    private Texture texture;
    private float width = 0.8f;
    private float height = 3.5f; // Фиксированная высота колонны в метрах Box2D

    // Высота экрана в метрах Box2D (при разрешении 720p и масштабе 100 это 7.2 метра)
    private final float SCREEN_HEIGHT_METERS = 5.0f;

    public Obstacle(World world, float x, boolean isTop, MyGdxGame game) {
        this.texture = game.assets.get("textures/stone.png", Texture.class);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Определяем Y-координату центра колонны
        float y;
        if (isTop) {
            // Если верхняя: свисает с потолка (7.2 метра) вниз
            y = SCREEN_HEIGHT_METERS - (height / 2f);
        } else {
            // Если нижняя: растет от земли (0.5f у тебя земля) вверх
            y = 0.5f + (height / 2f);
        }
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 2.0f);
        shape.setAsBox(width / 2f, height / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData("SAW");
    }

    public void update(float delta) {}

    public void draw(SpriteBatch batch) {
        if (body == null) return;
        float drawWidth = width * 100;
        float drawHeight = height * 100;
        float drawX = (body.getPosition().x * 100) - drawWidth / 2;
        float drawY = (body.getPosition().y * 100) - drawHeight / 2;
        batch.draw(texture, drawX, drawY, drawWidth, drawHeight);
    }

    public float getX() {
        if (body != null) return body.getPosition().x;
        return 0;
    }
}
