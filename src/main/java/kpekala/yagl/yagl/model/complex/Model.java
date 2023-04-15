package kpekala.yagl.yagl.model.complex;

import kpekala.yagl.yagl.model.Transform;
import kpekala.yagl.yagl.model.basic.Vector3f;

public class Model {
    public final Polygon[] polygons;
    private Vector3f center;
    private Vector3f color;

    public Model(Polygon[] polygons, Vector3f color){
        this(polygons,new Vector3f(0,0,0),color);
    }

    public Model(Polygon[] polygons, Vector3f center, Vector3f color) {
        this.polygons = polygons;
        this.center = center;
        this.color = color;

        Vector3f baseCenter = new Vector3f(0, 0, 0);
        if (!baseCenter.equals(center)){
            Vector3f dirToMove = center.subtract(baseCenter);
            Transform.move(polygons, dirToMove);
        }
    }

    public Model(Model that){
        this(that.polygons,that.center, that.color);

    }


    public void rotateAroundPosition(Vector3f rotation, Vector3f position){
        Transform.rotateMesh(polygons, rotation, position);
        center = Transform.rotateVertex(center,rotation, position);
    }

    public void move(Vector3f direction){
        Transform.move(polygons,direction);
        center = center.add(direction);
    }
    public void rotate(Vector3f rotation){
        Transform.rotateMesh(polygons,rotation,center);
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
