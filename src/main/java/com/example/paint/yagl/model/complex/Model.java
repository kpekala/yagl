package com.example.paint.yagl.model.complex;

import com.example.paint.yagl.model.Transform;
import com.example.paint.yagl.model.basic.Vector3f;

public class Model {
    public final Polygon[] polygons;
    private Vector3f center;
    private Vector3f color;

    private final Vector3f baseCenter = new Vector3f(0,0,0);

    public Model(Polygon[] polygons, Vector3f center, Vector3f color) {
        this.polygons = polygons;
        this.center = center;
        this.color = color;

        if (!baseCenter.equals(center)){
            Vector3f dirToMove = center.subtract(baseCenter);
            Transform.move(polygons, dirToMove);
        }
    }

    public void rotate(Vector3f rotation){
        Transform.rotateMesh(polygons,rotation,center);
    }

    public void move(Vector3f direction){
        Transform.move(polygons,direction);
        center = center.add(direction);
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
