package com.example.paint.app;

import com.example.paint.utils.Input;
import com.example.paint.utils.ObjLoader;
import com.example.paint.yagl.model.complex.Polygon;
import com.example.paint.yagl.scene.Scene;
import com.example.paint.yagl.Drawer;
import com.example.paint.yagl.api.JavaFXDrawable;
import com.example.paint.yagl.model.Samples;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Arrays;


public class AppController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final Scene scene = new Scene();

    private final int[] fpss = new int[10];
    private int fpsIndex = 0;

    public void initialize(){
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));

        //Polygon[] pols = ObjLoader.load("")

        scene.addToScene(Samples.generateCubeModels(10));
        mainLoop();
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
        float moveSpeed = 0.3f;
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
            drawer.drawModelEdges(cube);
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