package com.example.paint;

import com.example.paint.yagl.Drawable;
import com.example.paint.yagl.Engine;
import com.example.paint.yagl.Pixel;
import com.example.paint.yagl.model.Samples;
import com.example.paint.yagl.model.Transform;
import com.example.paint.yagl.model.basic.Color4i;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class HelloController implements Drawable {

    public Canvas canvas;
    public Text fpsCounter;
    private GraphicsContext graphicsContext;
    private Engine engine;
    private final Polygon[] cube = Samples.getCube();

    private float lastTimeCHeck = 0f;

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();
        engine = new Engine(this,new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        mainLoop();
    }

    private void mainLoop() {
        Transform.move(cube,new Vector3f(0,0,3f));
        new Thread(() -> {
            while (true){
                onUpdate();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void onUpdate() {
        if (Input.isPressed(KeyCode.W)) {
            rotate(cube, new Vector3f(-0.03f,0,0));
        }
        if (Input.isPressed(KeyCode.S)){
            rotate(cube, new Vector3f(0.03f,0,0));
        }
        if (Input.isPressed(KeyCode.A)) {
            rotate(cube, new Vector3f(0,-0.03f,0));
        }
        if (Input.isPressed(KeyCode.D)){
            rotate(cube, new Vector3f(0,0.03f,0));
        }
        Platform.runLater(() ->{
            draw();
        });
    }

    private void draw() {
        updateFPSCounter();
        clearCanvas();
        for(var polygon: cube){
            engine.drawPolygon(polygon);
        }
    }

    private void updateFPSCounter() {
        if(lastTimeCHeck != 0.0f){
            double diffMillis = System.currentTimeMillis() - lastTimeCHeck;
            if (diffMillis == 0.0) diffMillis = 1;
            double diffSec =  diffMillis / 1000f;
           // System.out.println(diffSec);
            int fps = (int) (1/diffSec);
            fpsCounter.setText(fps + " fps");
        }
         lastTimeCHeck = System.currentTimeMillis();
    }

    private void rotate(Polygon[] cube, Vector3f rotation){
        Vector3f rotationCenter = new Vector3f(0,0,9);
        Transform.rotateMesh(cube,rotation,rotationCenter);
    }

    @Override
    public void drawPixel(Pixel pixel) {
        Color4i color = pixel.color;
        graphicsContext.getPixelWriter().setColor(pixel.x, pixel.y, Color.rgb(color.r,color.g,color.b));
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