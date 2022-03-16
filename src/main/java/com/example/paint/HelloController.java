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
    private final Polygon[] cube1 = Samples.getCube();

    private long lastTimeCheck = 0;

    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();
        engine = new Engine(this,new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        mainLoop();
    }

    private void mainLoop() {
        Transform.move(cube,new Vector3f(0,0,3f));
        Transform.move(cube1,new Vector3f(4,0,2.5f));
        new Thread(() -> {
            while (true){
                onUpdate();
                try {
                    Thread.sleep(20);
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
        //updateFPSCounter();
        engine.clearView();
        for(var polygon: cube){
            //engine.drawPolygonEdges(polygon);
            engine.fillPolygon(polygon, new Vector3f(1,0,0));
        }
        for(var polygon: cube1){
            //engine.drawPolygonEdges(polygon);
            engine.fillPolygon(polygon, new Vector3f(0,1,0));
        }
    }

    private void updateFPSCounter() {
        if(lastTimeCheck != 0.0f){
            long diffMillis = System.currentTimeMillis() - lastTimeCheck;
            double diffSec =  diffMillis / 1000f;
            int fps = (int) (1/diffSec);
            fpsCounter.setText(fps + " fps");
        }
         lastTimeCheck = System.currentTimeMillis();
    }

    private void rotate(Polygon[] cube, Vector3f rotation){
        Vector3f rotationCenter = new Vector3f(0,0,9);
        Transform.rotateMesh(cube,rotation,rotationCenter);
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