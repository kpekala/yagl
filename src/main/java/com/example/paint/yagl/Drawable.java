package com.example.paint.yagl;

import com.example.paint.yagl.model.Vector2f;
import com.example.paint.yagl.model.Vector3f;

public interface Drawable {
    void drawPixel(Pixel pixel);
    void drawLine(Vector2f point1, Vector2f point2, Vector3f color);
}
