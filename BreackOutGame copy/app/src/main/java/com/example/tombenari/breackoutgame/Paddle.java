package com.example.tombenari.breackoutgame;

import android.graphics.RectF;

/**
 * Created by tombenari on 19.9.2016.
 */
public class Paddle {

    private RectF rect;
    private float lenght, hight, x, y, paddleSpeed;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int paddleMoving = STOPPED;

    public Paddle (int screenX, int screenY){
        lenght =130;
        hight = 20;
        x = screenX/2;
        y= screenY - 20;
        rect = new RectF(x,y,x+lenght,y+hight);
        paddleSpeed=350;
    }

    public RectF getRect() {
        return rect;
    }

    public void setMovementState (int state){
        paddleMoving = state;
    }

    public void update (long fps){
        if (paddleMoving == LEFT){
            x = x- paddleSpeed/fps;
        }
        if (paddleMoving == RIGHT){
            x = x + paddleSpeed/fps;
        }
        rect.left = x;
        rect.right = x+lenght;
    }
}
