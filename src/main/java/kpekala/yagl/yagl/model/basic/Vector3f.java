package kpekala.yagl.yagl.model.basic;

import java.util.Objects;

public class Vector3f {
    public final float x,y,z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f that){
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    public Vector3f copy(){
        return new Vector3f(x,y,z);
    }

    public Vector2f to2f(){
        return new Vector2f(x,y);
    }

    public Vector3f add(Vector3f other){
        return new Vector3f(x + other.x, y + other.y,z+ other.z);
    }
    public Vector3f subtract(Vector3f other){
        return new Vector3f(x - other.x, y - other.y,z- other.z);
    }

    public Vector3f reverse(){
        return new Vector3f(-this.x,-this.y, -this.z);
    }

    public static Vector3f forward(float distance){
        return new Vector3f(0,0,1 * distance);
    }
    public static Vector3f back(float distance){
        return new Vector3f(0,0,-1 * distance);
    }
    public static Vector3f up(float distance){
        return new Vector3f(0,1 * distance,0);
    }
    public static Vector3f down(float distance){
        return new Vector3f(0,-1 * distance,0);
    }

    public static Vector3f right(float distance){
        return new Vector3f(1 * distance,0,0);
    }
    public static Vector3f left(float distance){
        return new Vector3f(-1 * distance,0,0);
    }

    public static Vector3f zero(){return new Vector3f(0,0,0);}


    @Override
    public String toString() {
        return String.format("(%f, %f, %f)",x,y,z);
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
