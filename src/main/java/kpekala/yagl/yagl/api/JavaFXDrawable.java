package kpekala.yagl.yagl.api;

import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.model.basic.Vector3f;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JavaFXDrawable implements Drawable{

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;

    public JavaFXDrawable(Canvas canvas){
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
    }

    @Override
    public void drawPixel(Vector2f v, Vector3f color) {
        graphicsContext.getPixelWriter().setColor((int) v.x, (int) v.y, Color.color(color.x,color.y,color.z));
    }

    @Override
    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
