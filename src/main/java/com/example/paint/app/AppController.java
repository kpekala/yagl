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
import com.example.paint.yagl.utils.ColorUtils;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class AppController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final Scene scene = new Scene();

    private final int[] fpss = new int[10];
    private int fpsIndex = 0;

    private Vector3f defaultColor = new Vector3f(0.2f,0.5f,0.8f);

    public void initialize(){
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));

        //scene.addToScene(Samples.generateCubeModels(10));
        loadObj();
        mainLoop();
    }

    private void loadObj() {
        try {
            Polygon[] objPolygons = OBJLoader.load("/panda.obj");
            Model objModel = new Model(objPolygons, new Vector3f(0,0,10),defaultColor);
            scene.addToScene(objModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mainLoop() {
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
        float moveSpeed = 0.1f;
        long s = System.currentTimeMillis();

        if (Input.isPressed(KeyCode.R)) {
            scene.rotateCubes(new Vector3f(-0.03f,0,0));
        }
        if (Input.isPressed(KeyCode.T)){
            scene.rotateCubes(new Vector3f(0,0.03f,0));
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

        Platform.runLater(() ->{
            draw();
            updateFPSCounter(System.currentTimeMillis() - s);
        });
    }
    private void draw() {
        drawer.clearView();
        for (var cube: scene.getDrawableModels()){
            drawer.drawModel(cube);
        }
        for (var cube: scene.getDrawableModels()){
            drawer.drawModelEdges(cube, ColorUtils.BLACK);
        }
    }

    private void updateFPSCounter(long frameDuration) {
        double diffSec =  frameDuration / 1000f;
        int fps = (int) (1/diffSec);
        fpss[fpsIndex++] = fps;
        if (fpsIndex == fpss.length){
            int avg = Arrays.stream(fpss).sum()/fpss.length;
            fpsCounter.setText(avg + " fps");
            fpsIndex = 0;
        }
    }
}