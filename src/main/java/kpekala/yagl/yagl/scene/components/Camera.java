package kpekala.yagl.yagl.scene.components;

import kpekala.yagl.yagl.model.basic.Vector3f;

public class Camera extends SceneComponent{

    public Camera(Vector3f position, Vector3f rotation) {
        super(position, rotation);
    }
    public Camera(){
        super();
    }
}
