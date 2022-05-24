package com.example.paint.app;

import com.example.paint.utils.Input;
import com.example.paint.utils.OBJLoader;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.model.complex.Polygon;
import com.example.paint.yagl.scene.Scene;
import com.example.paint.yagl.Drawer;
import com.example.paint.yagl.api.JavaFXDrawable;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;

import static com.example.paint.yagl.utils.ColorUtils.defaultColor;


public class AppController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final Scene scene = new Scene();

    private final int[] fpss = new int[10];
    private int fpsIndex = 0;
    private int timeSinceLastFrame = 0;
    private final int minimumDeltaTime = 5;

    public void initialize() {
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        try {
            Model objModel = loadObj();
            scene.addToScene(objModel);
        } catch (IOException e) {
            System.out.println("Error when loading .obj file");
            System.out.println(e.getMessage());
        }
        mainLoop();
    }

    private Model loadObj() throws IOException {
        Polygon[] objPolygons = OBJLoader.load("/panda.obj", "/rock/material.lib");
        return new Model(objPolygons, new Vector3f(0,0,10),defaultColor);
    }

    private void mainLoop() {
        new Thread(() -> {
            int deltaTime = 10;
            while (true){
                long s = System.currentTimeMillis();
                onUpdate(deltaTime);
                deltaTime = (int) (System.currentTimeMillis() - s);
            }
        }).start();
    }

    private void onUpdate(float deltaTime) {
        float moveSpeed = 0.001f * deltaTime;

        if (Input.isPressed(KeyCode.R)) {
            scene.rotateCubes(new Vector3f(-moveSpeed,0,0));
        }
        if (Input.isPressed(KeyCode.T)){
            scene.rotateCubes(new Vector3f(0,moveSpeed,0));
        }
        if(Input.isPressed(KeyCode.W)){
            scene.moveCubes(new Vector3f(0,0,moveSpeed));
        }
        if (Input.isPressed(KeyCode.S)){
            scene.moveCubes(new Vector3f(0,0,-moveSpeed));
        }
        if(Input.isPressed(KeyCode.A)){
            scene.moveCubes(new Vector3f(-moveSpeed,0,0));
        }
        if (Input.isPressed(KeyCode.D)){
            scene.moveCubes(new Vector3f(moveSpeed,0,0));
        }
        if(Input.isPressed(KeyCode.UP)){
            scene.moveCubes(new Vector3f(0,moveSpeed,0));
        }
        if (Input.isPressed(KeyCode.DOWN)){
            scene.moveCubes(new Vector3f(0,-moveSpeed,0));
        }
        timeSinceLastFrame += deltaTime;
        if(timeSinceLastFrame < minimumDeltaTime){
            return;
        }
        timeSinceLastFrame = 0;

        Platform.runLater(() ->{
            draw();
            updateFPSCounter((long) deltaTime);
        });
    }
    private void draw() {
        drawer.clearView();
        for (var cube: scene.getDrawableModels()){
            drawer.drawModel(cube);
        }
//        for (var cube: scene.getDrawableModels()){
//            drawer.drawModelEdges(cube, ColorUtils.BLACK);
//        }
    }

    private void updateFPSCounter(long frameDuration) {
        System.out.println(frameDuration);
        int fps = (int) (1000/ frameDuration);
        fpss[fpsIndex++] = fps;
        if (fpsIndex == fpss.length){
            int avg = Arrays.stream(fpss).sum()/fpss.length;
            fpsCounter.setText(avg + " fps");
            fpsIndex = 0;
        }
    }
}