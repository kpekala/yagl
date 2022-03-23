package com.example.paint.yagl.model.basic;

import java.util.Objects;

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
    public Vector3f subtract(Vector3f other){
        return new Vector3f(x - other.x, y - other.y,z- other.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(vector3f.x, x) == 0 && Float.compare(vector3f.y, y) == 0 && Float.compare(vector3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
