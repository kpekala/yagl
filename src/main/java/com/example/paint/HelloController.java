package com.example.paint;

import com.example.paint.yagl.Drawable;
import com.example.paint.yagl.Engine;
import com.example.paint.yagl.Pixel;
import com.example.paint.yagl.model.Color4i;
import com.example.paint.yagl.model.Vector2f;
import com.example.paint.yagl.model.Vector3f;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class HelloController implements Drawable {

    public Canvas canvas;
    private GraphicsContext graphicsContext;
    private Engine engine = new Engine(this);

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();
    }

    @Override
    public void drawPixel(Pixel pixel) {
        Color4i color = pixel.color;
        graphicsContext.getPixelWriter().setColor(pixel.x, pixel.y, Color.rgb(color.r,color.g,color.b));
    }

    @Override
    public void drawLine(Vector2f point1, Vector2f point2, Vector3f color) {

    }
}