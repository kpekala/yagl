package kpekala.yagl.yagl.api;

import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.model.basic.Vector3f;

public interface Drawable {
    void drawPixel(Vector2f v, Vector3f color);
    void clearCanvas();
}
