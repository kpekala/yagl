package kpekala.yagl.yagl.model.basic;

public class Vector2f {
    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i to2i(){
        return new Vector2i((int) Math.rint(x), (int) Math.rint(y));
    }
}
