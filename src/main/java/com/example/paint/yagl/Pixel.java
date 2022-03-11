package com.example.paint.yagl;


import com.example.paint.yagl.model.basic.Color4i;

public class Pixel {

    public final int x;
    public final int y;
    public final Color4i color;

    public Pixel(int x, int y, Color4i color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
