package com.example.paint.yagl.scene;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.scene.components.Camera;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScene {
    private final ArrayList<Model> models;
    private Vector3f lastCameraPosition;
    private Vector3f lastCameraRotation;
    protected final Camera camera;

    public BaseScene() {
        this.camera = new Camera(new Vector3f(0, 0, 0), Vector3f.zero());
        lastCameraPosition = Vector3f.zero();
        lastCameraRotation = Vector3f.zero();
        models = new ArrayList<>();

        awake();
    }

    public void addAllToScene(List<Model> models) {
        this.models.addAll(models);
    }

    public void addToScene(Model model) {
        this.models.add(model);
    }

    public List<Model> getDrawableModels() {
        Vector3f cameraDeltaPosition = camera.getPosition().subtract(lastCameraPosition);
        Vector3f cameraDeltaRotation = camera.getRotation().subtract(lastCameraRotation);

        lastCameraPosition = camera.getPosition();
        lastCameraRotation = camera.getRotation();
        for (var model : models) {
            if (!cameraDeltaRotation.equals(Vector3f.zero()))
                model.rotateAroundPosition(cameraDeltaRotation, camera.getPosition());
            if (!cameraDeltaPosition.equals(Vector3f.zero()))
                model.move(cameraDeltaPosition.reverse());

        }
        return models;
    }

    public void moveAll(Vector3f vector3f) {
        for (var model : models) {
            model.move(vector3f);
        }
    }

    public void rotateAll(Vector3f vector3f) {
        for (var model : models) {
            model.rotate(vector3f);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public abstract void update();

    public abstract void awake();

    public abstract void drawExtra();
}
