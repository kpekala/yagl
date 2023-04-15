package kpekala.yagl.yagl.model.complex;

import kpekala.yagl.yagl.model.basic.Vector3f;

public class Triangle {
    public Vector3f[] vs = new Vector3f[3];

    public Triangle(Vector3f p1, Vector3f p2, Vector3f p3){
        vs[0] = p1;
        vs[1] = p2;
        vs[2] = p3;
    }
}
