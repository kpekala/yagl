package kpekala.yagl.app;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import kpekala.yagl.di.DI;
import kpekala.yagl.engine.Drawer;
import kpekala.yagl.engine.api.JavaFXDrawable;
import kpekala.yagl.scene.components.BaseScene;
import kpekala.yagl.scene.model.basic.Vector2f;
import kpekala.yagl.utils.FPSCounter;


public class ViewController {

    public Canvas canvas;
    public Text fpsText;
    private Drawer drawer;
    private final DI di = DI.getInstance();
    private BaseScene scene;

    public void initialize() {
        scene = di.demoScene();
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(), (float) canvas.getHeight()), scene);
        scene.initScene();
        mainLoop();
    }

    private void mainLoop() {
        new Thread(() -> {
            while (true) {
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
        Platform.runLater(() -> {
            drawer.drawScene();
            var fps = FPSCounter.updateAndGetFPS(System.currentTimeMillis() - s);
            fpsText.setText(fps + " fps");
        });
    }
}
