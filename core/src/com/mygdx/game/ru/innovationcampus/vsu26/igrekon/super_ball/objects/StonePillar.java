package com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.objects;


import static com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.ru.innovationcampus.vsu26.igrekon.super_ball.GameSettings.SCREEN_WIDTH;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;



public class StonePillar {

    Texture textureUpperTube;
    Texture textureDownTube;
    int gapY;
    int gapHeight=400;
    SpriteBatch batch;
    int padding = 100;
    int width = 200;
    int height = 700;
    int distanceBetweenTubes;
    int speed = 10;
    boolean isPointReceived;

    int x;

    public StonePillar(int tubeCount, int tubeIdx){
        Random random = new Random();
        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");
        gapY = gapHeight / 2 + padding + random.nextInt(SCREEN_HEIGHT - 2 * (padding+gapHeight / 2));
        distanceBetweenTubes =  (SCREEN_HEIGHT+ width) / (tubeCount-1);
        x  = distanceBetweenTubes * tubeIdx + SCREEN_WIDTH;
    }

    public void draw(Batch batch){
        batch.draw(textureUpperTube, x, gapY + gapHeight /2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);

    }
    public void dispose(){
        textureDownTube.dispose();
        textureUpperTube.dispose();
    }
    public void  move(){
        Random random = new Random();
        x-=speed;
        if (x < -width){
            isPointReceived = false;
            x = SCREEN_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding + random.nextInt(SCREEN_HEIGHT - 2 * (padding+gapHeight / 2));


        }
    }
    public boolean isHit(StonePillar stone){
        if(ball.y <= gapY - gapHeight / 2 && ball.x + ball.width >= x && ball.x <= x + width){
            return true;
        }

        if (ball.y + ball.height >= gapY + gapHeight / 2 && ball.x + ball.height >= x && ball.x <= x + width ){
            return true;
        }

        return false;

    }
    public boolean needAddPoint(Ball ball){
        return ball.x > x + width && !isPointReceived;
    }
    public void  setPointReceived(){
        isPointReceived = true;
    }

}

