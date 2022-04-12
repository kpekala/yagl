package com.example.paint.yagl;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final ArrayList<Model> models;

    public Scene(){
        models = new ArrayList<>();
    }

    public void addToScene(List<Model> models){
        this.models.addAll(models);
    }

    public ArrayList<Model> getDrawableModels() {
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
