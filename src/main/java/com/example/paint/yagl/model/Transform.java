package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;
import com.example.paint.yagl.model.complex.Triangle;

public class Transform {
//    public static Triangle perspective(Triangle t){
//        Triangle triangle = new Triangle(t.vs[0].copy(),t.vs[1].copy(),t.vs[2].copy());
//        for(int i=0; i<3; i++){
//            point = new Vector3f(point.x / point.z, point.y/ point.z,1);
//        }
//        return triangle;
//    }

    public static Polygon perspective(Polygon polygon){
        Polygon pol = new Polygon(polygon);
        for(int i=0; i<pol.vertices.length; i++){
            Vector3f v = pol.vertices[i];
            pol.vertices[i] = new Vector3f(v.x/v.z,v.y/v.z,1);
        }
        return pol;
    }
}
