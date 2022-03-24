package com.example.paint.yagl;

import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
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
    public void drawLine(Vector2f point1, Vector2f point2, Vector3f color) {
        graphicsContext.strokeLine(point1.x, point1.y, point2.x, point2.y);
    }

    @Override
    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
