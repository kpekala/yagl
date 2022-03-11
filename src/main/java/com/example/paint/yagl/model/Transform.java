package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Triangle;

public class Transform {
    public static Triangle perspective(Triangle t){
        Triangle triangle = new Triangle(t.vs[0].copy(),t.vs[1].copy(),t.vs[2].copy());
        for(Vector3f point: triangle.vs){
            point.x /= point.z;
            point.y /= point.z;
            point.z = 1;
            System.out.println(point.x + " " + point.y);
        }
        return triangle;
    }
}
