package kpekala.yagl.scene.components;

import kpekala.yagl.scene.model.basic.Vector3f;
import kpekala.yagl.scene.model.complex.Model;
import kpekala.yagl.scene.utils.ColorConfig;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseScene {
    private final ArrayList<Model> models = new ArrayList<>();
    private Vector3f lastCameraPosition = Vector3f.zero();
    private Vector3f lastCameraRotation = Vector3f.zero();
    protected final Camera camera;
    protected final ColorConfig color;

    public void addAllToScene(List<Model> models) {
        this.models.addAll(models);
    }

    public void addToScene(Model model) {
        this.models.add(model);
    }

    public List<Model> getDrawableModels() {
        var cameraDeltaPosition = camera.getPosition().subtract(lastCameraPosition);
        var cameraDeltaRotation = camera.getRotation().subtract(lastCameraRotation);

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

    public void rotateAll(Vector3f rotation) {
        for (var model : models) {
            model.rotate(rotation);
        }
    }

    public void rotateAll(Vector3f rotation, Vector3f rotationCenter) {
        for (var model : models) {
            model.rotate(rotation, rotationCenter);
        }
    }

    public abstract void update();

    public abstract void initScene();

    public abstract void drawExtra();
}
