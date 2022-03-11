package com.example.paint.yagl.model.basic;

public class Vector3f {
    public float x,y,z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f copy(){
        return new Vector3f(x,y,z);
    }

    public Vector2f to2(){
        return new Vector2f(x,y);
    }
}
