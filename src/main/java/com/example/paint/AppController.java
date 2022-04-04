package com.example.paint;

import com.example.paint.utils.Maths;
import com.example.paint.yagl.Drawer;
import com.example.paint.yagl.JavaFXDrawable;
import com.example.paint.yagl.model.Samples;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;


public class AppController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final ArrayList<Model> cubes = new ArrayList<>();

    private final int[] fpss = new int[10];
    private int fpsIndex = 0;

    public void initialize(){
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        initCubes();
        mainLoop();
    }

    private void initCubes() {
        int n = 10;
        for(int i=0; i<n; i++){
            float x = Maths.randomInRange(-20,20);
            float y = Maths.randomInRange(-20f, 20f);
            float z = Maths.randomInRange(30f, 40f);
            cubes.add(Samples.getCubeModel(new Vector3f(x,y,z),Maths.randomColor()));
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
        float moveSpeed = 0.3f;
        long s = System.currentTimeMillis();

        if (Input.isPressed(KeyCode.R)) {
            rotateCubes(new Vector3f(-0.03f,0,0));
        }
        if (Input.isPressed(KeyCode.T)){
            rotateCubes(new Vector3f(0,0.03f,0));
        }
        if(Input.isPressed(KeyCode.W)){
            moveCubes(new Vector3f(0,0,moveSpeed));
        }
        if (Input.isPressed(KeyCode.S)){
            moveCubes(new Vector3f(0,0,-moveSpeed));
        }
        if(Input.isPressed(KeyCode.A)){
            moveCubes(new Vector3f(-moveSpeed,0,0));
        }
        if (Input.isPressed(KeyCode.D)){
            moveCubes(new Vector3f(moveSpeed,0,0));
        }
        if(Input.isPressed(KeyCode.UP)){
            moveCubes(new Vector3f(0,moveSpeed,0));
        }
        if (Input.isPressed(KeyCode.DOWN)){
            moveCubes(new Vector3f(0,-moveSpeed,0));
        }

        Platform.runLater(() ->{
            draw();
            updateFPSCounter(System.currentTimeMillis() - s);
        });
    }

    private void moveCubes(Vector3f vector3f) {
        for(var cube: cubes){
            cube.move(vector3f);
        }
    }

    private void rotateCubes(Vector3f vector3f) {
        for(var cube: cubes){
            cube.rotate(vector3f);
        }
    }

    private void draw() {
        drawer.clearView();
        for (var cube: cubes){
            drawer.drawModel(cube);
//            for(var polygon: cube.polygons){
//                drawer.drawPolygonEdges(polygon);
//            }
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