package com.example.paint.yagl.scene;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.scene.components.Camera;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final ArrayList<Model> models;
    private Vector3f lastCameraPosition = null;
    private final Camera camera;

    public Scene(){
        this.camera = new Camera();
        lastCameraPosition = this.camera.getPosition();
        models = new ArrayList<>();
    }

    public void addAllToScene(List<Model> models){
        this.models.addAll(models);
    }

    public void addToScene(Model model){
        this.models.add(model);
    }

    public List<Model> getDrawableModels() {
        Vector3f cameraDeltaPosition = camera.getPosition().subtract(lastCameraPosition);
        lastCameraPosition = camera.getPosition();
        for (var model: models){
            model.move(cameraDeltaPosition.reverse());
        }
        return models;
    }

    public void moveCubes(Vector3f vector3f) {
        for(var model: models){
            model.move(vector3f);
        }
    }

    public void rotateCubes(Vector3f vector3f) {
        for(var model: models){
            model.rotate(vector3f);
        }
    }

    public Camera getCamera() {
        return camera;
    }
}
