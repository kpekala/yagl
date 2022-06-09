package com.example.paint.app;

import com.example.paint.utils.FPSCounter;
import com.example.paint.utils.OBJLoader;
import com.example.paint.yagl.model.ModelGenerator;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.model.complex.Polygon;
import com.example.paint.yagl.scene.BaseScene;
import com.example.paint.yagl.Drawer;
import com.example.paint.yagl.api.JavaFXDrawable;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.scene.DemoScene;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.example.paint.yagl.model.ModelGenerator.loadModelFromFile;
import static com.example.paint.yagl.utils.ColorUtils.defaultColor;


public class SceneController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final BaseScene scene = new DemoScene();

    public void initialize() {
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(),(float)canvas.getHeight()));
        try {
            Model objModel = ModelGenerator.loadModelFromName("panda");
            objModel.move(new Vector3f(0,0,5));
            scene.addToScene(objModel);
        } catch (IOException e) {
            System.out.println("Error when loading .obj file");
            System.out.println(e.getMessage());
        }
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
        long s = System.currentTimeMillis();
        scene.update();
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
        scene.drawExtra();
    }

}