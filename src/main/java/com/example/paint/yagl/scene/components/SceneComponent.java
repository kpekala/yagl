package com.example.paint.yagl.scene.components;

import com.example.paint.yagl.model.basic.Vector3f;

public abstract class SceneComponent {
    private Vector3f position;
    private Vector3f rotation;

    public SceneComponent(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }
    public SceneComponent(){
        this.position = Vector3f.zero();
        this.rotation = Vector3f.zero();
    }

    public void move(Vector3f direction){
        position = position.add(direction);
    }

    public void rotate(Vector3f rotation){
        this.rotation = this.rotation.add(rotation);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
