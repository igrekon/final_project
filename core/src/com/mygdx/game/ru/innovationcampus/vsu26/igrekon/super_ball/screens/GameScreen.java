package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.MyGdxGame;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.objects.ClonePlayer;
import com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.objects.Obstacle;

public class GameScreen extends ScreenAdapter {
    private final MyGdxGame game;
    private World world;
    private OrthographicCamera camera;
    private Texture background;
    private boolean nextIsTop = false; // Первая колонна будет снизу


    private Array<ClonePlayer> players = new Array<>();
    private Array<Obstacle> obstacles = new Array<>();

    private float cameraSpeed = 1.5f;
    private boolean isGameOver = false;

    public GameScreen(MyGdxGame game) {
        this.game = game;

        this.background = game.assets.get("textures/background.png", Texture.class);
        this.world = new World(new Vector2(0, -9.8f), true);

        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0.5f);

        Body groundBody = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(1000f, 0.1f);
        groundBody.createFixture(groundShape, 0.0f);
        groundShape.dispose();

        BodyDef ceilingDef = new BodyDef();
        ceilingDef.type = BodyDef.BodyType.StaticBody;
        ceilingDef.position.set(0, 7.2f); // Высота 7.2 метра (720 пикселей / 100)

        Body ceilingBody = world.createBody(ceilingDef);
        PolygonShape ceilingShape = new PolygonShape();
        ceilingShape.setAsBox(1000f, 0.1f);
        ceilingBody.createFixture(ceilingShape, 0.0f);
        ceilingShape.dispose();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        players.add(new ClonePlayer(world, 2.0f, 4.0f, game));


        createCollisionListener();
    }

    @Override
    public void render(float delta) {
        float cameraXBox2D = camera.position.x / 100f;

        if (obstacles.size == 0 || obstacles.peek().getX() - cameraXBox2D < 12f) {
            float nextX = 8.0f;
            if (obstacles.size > 0) {
                nextX = obstacles.peek().getX() + 6.0f;
            }

            obstacles.add(new Obstacle(world, nextX, nextIsTop, game));
            nextIsTop= !nextIsTop;
        }
        if (isGameOver) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        for (int i = obstacles.size - 1; i >= 0; i--) {
            Obstacle obs = obstacles.get(i);
            if (cameraXBox2D - obs.getX() > 8f) {
                obstacles.removeIndex(i);
            }
        }


        if (Gdx.input.justTouched()) {
            for (ClonePlayer p: players) {
                if (p != null && p.getBody() != null && p.isAlive()) {
                    p.flyUp();
                }
            }
        }

        world.step(1 / 60f, 6, 2);

        camera.position.x += cameraSpeed * delta * 100;
        camera.update();

        for (ClonePlayer p : players) {
            if (p.getBody() != null) {
                p.getBody().setLinearVelocity(cameraSpeed, p.getBody().getLinearVelocity().y);
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Obstacle obs : obstacles) {
            obs.update(delta);
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();


        game.batch.draw(background, camera.position.x - camera.viewportWidth / 2, 0, camera.viewportWidth, camera.viewportHeight);


        for (Obstacle obs : obstacles) {
            obs.draw(game.batch);
        }


        for (ClonePlayer p : players) {
            p.draw(game.batch);
        }

        game.batch.end();
    }

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getBody() == null || fixtureB.getBody() == null) return;

                if ("SAW".equals(fixtureA.getBody().getUserData()) || "SAW".equals(fixtureB.getBody().getUserData())) {
                    for (ClonePlayer p : players) {
                        if (p != null) p.setAlive(false);
                    }
                    isGameOver = true;
                }
            }
            @Override public void endContact(Contact contact) {}
            @Override public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (world != null) {
            world.dispose();
            world = null;
            players.clear();
            obstacles.clear();
        }
    }
}
