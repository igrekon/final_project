package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.MyGdxGame;

public class ClonePlayer {
    private Body body;
    private Texture texture;
    private boolean isAlive = true;
    private float radius = 0.4f;
    // В метрах Box2D
    private MyGdxGame game;

    public ClonePlayer(World world, float x, float y, MyGdxGame game) {
        this.game = game;
        this.texture = game.assets.get("textures/clone.png", Texture.class);

        // Определение физического тела Box2D
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = false;

        body = world.createBody(bodyDef);
        body.setLinearDamping(2.5f);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;     // Вес
        fixtureDef.friction = 0.6f;    // Трение о стены
        fixtureDef.restitution = 0.2f;// Прыгучесть

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this); // Чтобы определять его при столкновениях
    }

    public void flyUp() {
        // Применяем импульс вверх (взмах)
        if (body != null && isAlive) {
            float currentXVelocity = body.getLinearVelocity().x;

            float currentY = body.getPosition().y;
            if (currentY > 3.6f) {
                body.setLinearVelocity(currentXVelocity, -6.0f);
            }
            else {  // Сбрасываем вертикальное падение
            body.setLinearVelocity(currentXVelocity,6.0f);}

            // game.assets.get("sounds/sword-swing-sound.mp3", Sound.class).play(0.3f);
        }
    }

    public void draw(SpriteBatch batch) {
        // ИСПРАВЛЕНО: Защита от вылета. Если тело уничтожено или игрок мертв — не рисуем его
        if (body == null || !isAlive) return;

        // Конвертируем метры Box2D обратно в пиксели для отрисовки (масштаб х100)
        float size = radius * 2 * 100;
        float drawX = (body.getPosition().x * 100) - size / 2;
        float drawY = (body.getPosition().y * 100) - size / 2;

        batch.draw(texture, drawX, drawY, size, size);
    }

    public Body getBody() {
        return body;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
