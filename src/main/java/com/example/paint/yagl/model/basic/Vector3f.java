package com.example.paint.yagl.model.basic;

public class Vector3f {
    public final float x,y,z;

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

    public Vector3f(Vector3f that){
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    public Vector3f add(Vector3f other){
        return new Vector3f(x + other.x, y + other.y,z+ other.z);
    }
}
