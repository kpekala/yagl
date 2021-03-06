package com.example.paint.yagl.api;

import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;

public interface Drawable {
    void drawPixel(Vector2f v, Vector3f color);
    void clearCanvas();
}
