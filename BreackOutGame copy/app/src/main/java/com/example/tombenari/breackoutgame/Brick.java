package com.example.tombenari.breackoutgame;

import android.graphics.RectF;

/**
 * Created by tombenari on 24.9.2016.
 */

public class Brick {

    private RectF rect;
    private Boolean isVisible;

    public Brick(int row, int column, int width, int height) {
        isVisible = true;
        int padding = 1;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }
    public RectF getRect() {
        return this.rect;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

}

