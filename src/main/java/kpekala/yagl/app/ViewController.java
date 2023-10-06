package kpekala.yagl.app;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import kpekala.yagl.utils.FPSCounter;
import kpekala.yagl.yagl.Drawer;
import kpekala.yagl.yagl.api.JavaFXDrawable;
import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.scene.BaseScene;
import kpekala.yagl.yagl.scene.DemoScene;


public class ViewController {

    public Canvas canvas;
    public Text fpsCounter;
    private Drawer drawer;
    private final BaseScene scene = new DemoScene();

    public void initialize() {
        drawer = new Drawer(new JavaFXDrawable(canvas), new Vector2f((float) canvas.getWidth(), (float) canvas.getHeight()), scene);
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
            fpsCounter.setText(fps + " fps");
        });
    }

}
