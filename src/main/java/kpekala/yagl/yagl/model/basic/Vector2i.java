package kpekala.yagl.yagl.model.basic;

public class Vector2i {
    public int x,y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f tof(){
        return new Vector2f(x,y);
    }
}
