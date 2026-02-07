package kpekala.yagl.engine.api;

import kpekala.yagl.scene.model.basic.Vector2f;
import kpekala.yagl.scene.model.basic.Vector3f;

public interface Drawable {
    void drawPixel(Vector2f v, Vector3f color);
    void clearCanvas();
}
