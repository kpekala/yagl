package com.example.paint.app;

import com.example.paint.utils.Input;
import com.example.paint.utils.OBJLoader;
import com.example.paint.yagl.model.Samples;
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
import java.util.Arrays;

import static com.example.paint.yagl.utils.ColorUtils.defaultColor;


public class AppController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final Scene scene = new Scene();

    private final int[] fpss = new int[10];
    private int fpsIndex = 0;

    public void initialize() {
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        try {
            Model objModel = loadModelFromFile("/panda.obj", "/rock/material.lib");
            objModel.move(new Vector3f(0,0.3f,5));
            scene.addToScene(objModel);
            setUpScene();
        } catch (IOException e) {
            System.out.println("Error when loading .obj file");
            System.out.println(e.getMessage());
        }
        mainLoop();
    }

    private void setUpScene() {
        Model groundPlane = Samples.plane(ColorUtils.GREEN,1,1);
        groundPlane.move(Vector3f.forward(5));
        scene.addToScene(groundPlane);

        Model wallPlane1 = Samples.plane(ColorUtils.BLUE, 1, 1);
        wallPlane1.move(new Vector3f(0, 1, 6));
        wallPlane1.rotate(new Vector3f((float) (Math.PI/2),0,0));
        scene.addToScene(wallPlane1);

        Model wallPlane2 = Samples.plane(ColorUtils.BLUE, 1, 1);
        wallPlane2.move(new Vector3f(1, 1, 5));
        wallPlane2.rotate(new Vector3f(0,0,(float) (Math.PI/2)));
        scene.addToScene(wallPlane2);

        Model wallPlane3 = Samples.plane(ColorUtils.BLUE, 1, 1);
        wallPlane3.move(new Vector3f(-1, 1, 5));
        wallPlane3.rotate(new Vector3f(0,0,(float) (Math.PI/2)));
        scene.addToScene(wallPlane3);
    }

    private Model loadModelFromFile(String objFileName, String materialFileName) throws IOException {
        Polygon[] objPolygons = OBJLoader.load(objFileName, materialFileName);
        return new Model(objPolygons,defaultColor);
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
//        for (var cube: scene.getDrawableModels()){
//            drawer.drawModelEdges(cube, ColorUtils.BLACK);
//        }
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