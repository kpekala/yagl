package com.example.paint;

import com.example.paint.yagl.Drawable;
import com.example.paint.yagl.Engine;
import com.example.paint.yagl.Pixel;
import com.example.paint.yagl.model.basic.Color4i;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector2i;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Triangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HelloController implements Drawable {

    public Canvas canvas;
    private GraphicsContext graphicsContext;
    private Engine engine;

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();
        engine = new Engine(this,new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        mainLoop();
    }

    private void mainLoop() {
        System.out.println("Siema");
        Triangle t = new Triangle(new Vector3f(2,2,3),new Vector3f(-2,2,3),new Vector3f(0,3,3));
        new Thread(() -> {
            engine.drawTriangle(t);
        }).start();
    }

    @Override
    public void drawPixel(Pixel pixel) {
        Color4i color = pixel.color;
        graphicsContext.getPixelWriter().setColor(pixel.x, pixel.y, Color.rgb(color.r,color.g,color.b));
    }

    @Override
    public void drawLine(Vector2f point1, Vector2f point2, Vector3f color) {
        //graphicsContext.setLineWidth(5);
        System.out.println(point1.x + " " + point1.y + " " + point2.x +  " " + point2.y);
        graphicsContext.strokeLine(point1.x, point1.y, point2.x, point2.y);
    }

    @Override
    public Vector2f screenSize() {
        return new Vector2f((float)canvas.getWidth(),(float)canvas.getHeight());
    }
}