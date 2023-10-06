package kpekala.yagl.yagl.scene;

import kpekala.yagl.utils.Input;
import kpekala.yagl.yagl.model.ModelGenerator;
import kpekala.yagl.yagl.model.basic.Vector3f;
import kpekala.yagl.yagl.model.complex.Model;
import kpekala.yagl.yagl.utils.ColorUtils;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class DemoScene extends BaseScene {

    float moveSpeed = 0.1f;
    float rotateSpeed = 0.05f;


    @Override
    public void awake() {
        Model groundPlane = ModelGenerator.plane(ColorUtils.GREEN, new Vector3f(4, 1, 4));
        groundPlane.move(Vector3f.forward(5));
        addToScene(groundPlane);

        Model wallPlane1 = ModelGenerator.plane(ColorUtils.BLUE, new Vector3f(1, 1, 1));
        wallPlane1.move(new Vector3f(0, 1, 6));
        wallPlane1.rotate(new Vector3f((float) (Math.PI / 2), 0, 0));
        addToScene(wallPlane1);

        Model wallPlane2 = ModelGenerator.plane(ColorUtils.BLUE, new Vector3f(1, 1, 1));
        wallPlane2.move(new Vector3f(1, 1, 5));
        wallPlane2.rotate(new Vector3f(0, 0, (float) (Math.PI / 2)));
        addToScene(wallPlane2);

        Model wallPlane3 = ModelGenerator.plane(ColorUtils.BLUE, new Vector3f(1, 1, 1));
        wallPlane3.move(new Vector3f(-1, 1, 5));
        wallPlane3.rotate(new Vector3f(0, 0, (float) (Math.PI / 2)));
        addToScene(wallPlane3);

        addPanda();
    }

    @Override
    public void drawExtra() {

    }

    @Override
    public void update() {
        camera.move(Vector3f.forward(moveSpeed * Input.getAxis(Input.AxisType.VERTICAL)));
        camera.move(Vector3f.right(moveSpeed * Input.getAxis(Input.AxisType.HORIZONTAL)));
        camera.move(Vector3f.up(moveSpeed * Input.judge(KeyCode.DIGIT1, KeyCode.DIGIT2)));
        rotateAll(Vector3f.up(rotateSpeed * Input.judge(KeyCode.Q, KeyCode.E)));
        rotateAll(Vector3f.right(rotateSpeed * Input.judge(KeyCode.R, KeyCode.T)));
    }

    private void addPanda() {
        try {
            Model objModel = ModelGenerator.loadModelFromName("panda");
            objModel.move(new Vector3f(0, 0, 5));
            addToScene(objModel);
        } catch (IOException e) {
            System.out.println("Error when loading .obj file");
            System.out.println(e.getMessage());
        }
    }

}
