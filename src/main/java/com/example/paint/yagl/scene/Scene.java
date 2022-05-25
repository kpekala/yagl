package com.example.paint.yagl.scene;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final ArrayList<Model> models;

    public Scene(){
        models = new ArrayList<>();
    }

    public void addAllToScene(List<Model> models){
        this.models.addAll(models);
    }

    public void addToScene(Model model){
        this.models.add(model);
    }

    public List<Model> getDrawableModels() {
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

}
