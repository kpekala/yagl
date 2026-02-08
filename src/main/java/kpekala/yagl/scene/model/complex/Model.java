package kpekala.yagl.scene.model.complex;

import kpekala.yagl.scene.model.Transform;
import kpekala.yagl.scene.model.basic.Vector3f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Model {
    public final Polygon[] polygons;
    private Vector3f center;

    @Getter @Setter
    private Vector3f color;
    @Getter @Setter
    private String name = "Default";

    public Model(Polygon[] polygons, Vector3f color){
        this(polygons,new Vector3f(0,0,0),color);
    }

    public Model(Polygon[] polygons, Vector3f color, String name) {
        this(polygons, color);
        this.name = name;
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
        this(that.polygons,that.center, that.color, that.getName());
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

    public void rotate(Vector3f rotation, Vector3f center) {
        Transform.rotateMesh(polygons, rotation, center);
    }

}
