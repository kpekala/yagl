package com.example.paint.app;

import com.example.paint.utils.FPSCounter;
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
import com.example.paint.yagl.scene.components.Camera;
import com.example.paint.yagl.utils.ColorUtils;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.example.paint.yagl.utils.ColorUtils.defaultColor;


public class SceneController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final Scene scene = new Scene();
    private final Camera camera = scene.getCamera();


    public void initialize() {
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        try {
            Model objModel = loadModelFromFile("/panda.obj", "/rock/material.lib");
            objModel.move(new Vector3f(0,0,5));
            scene.addToScene(objModel);
            //setUpScene();
        } catch (IOException e) {
            System.out.println("Error when loading .obj file");
            System.out.println(e.getMessage());
        }
        mainLoop();
    }

    private void setUpScene() {
        Model groundPlane = Samples.plane(ColorUtils.GREEN, new Vector3f(4,1,4));
        groundPlane.move(Vector3f.forward(5));
        scene.addToScene(groundPlane);

        Model wallPlane1 = Samples.plane(ColorUtils.BLUE, new Vector3f(1,1,1));
        wallPlane1.move(new Vector3f(0, 1, 6));
        wallPlane1.rotate(new Vector3f((float) (Math.PI/2),0,0));
        scene.addToScene(wallPlane1);

        Model wallPlane2 = Samples.plane(ColorUtils.BLUE, new Vector3f(1,1,1));
        wallPlane2.move(new Vector3f(1, 1, 5));
        wallPlane2.rotate(new Vector3f(0,0,(float) (Math.PI/2)));
        scene.addToScene(wallPlane2);

        Model wallPlane3 = Samples.plane(ColorUtils.BLUE, new Vector3f(1,1,1));
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
        float rotateSpeed = 0.05f;
        long s = System.currentTimeMillis();

        camera.move(Vector3f.forward(moveSpeed * Input.getAxis(Input.AxisType.VERTICAL)));
        camera.move(Vector3f.right(moveSpeed * Input.getAxis(Input.AxisType.HORIZONTAL)));
        camera.move(Vector3f.up(moveSpeed * Input.judge(KeyCode.DIGIT1, KeyCode.DIGIT2)));
        scene.rotateAll(Vector3f.up(rotateSpeed * Input.judge(KeyCode.Q,KeyCode.E)));
        scene.rotateAll(Vector3f.right(rotateSpeed * Input.judge(KeyCode.R,KeyCode.T)));
        Platform.runLater(() ->{
            draw();
            var fps = FPSCounter.updateAndGetFPS(System.currentTimeMillis() - s);
            fpsCounter.setText(fps + " fps");
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

}