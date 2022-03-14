package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;

public class Transform {

    public static Polygon perspective(Polygon polygon){
        Polygon pol = new Polygon(polygon);
        for(int i=0; i<pol.vertices.length; i++){
            Vector3f v = pol.vertices[i];
            pol.vertices[i] = new Vector3f(v.x/v.z,v.y/v.z,1);
        }
        return pol;
    }

    public static Vector3f rotateVertex(Vector3f v, Vector3f rot, Vector3f center){
        float angle = rot.x;
        Vector3f centered = v.subtract(center);
        Vector3f rotatedVertex = new Vector3f(centered.x,
                centered.y*cos(angle)+ centered.z*sin(angle),
                -centered.y*sin(angle) + centered.z * cos(angle));
        Vector3f diff = v.subtract(centered);
        return rotatedVertex.add(diff);
    }

    public static void rotateMesh(Polygon[] polygons, Vector3f rot, Vector3f center){
        for(Polygon p: polygons){
            for(int i=0; i<p.vertices.length; i++){
                Vector3f v = p.vertices[i];
                p.vertices[i] = Transform.rotateVertex(v,rot,center);
            }
        }
    }

    public static void move(Polygon[] cube, Vector3f direction) {
        for(Polygon p: cube){
            for(int i=0; i<p.vertices.length; i++){
                Vector3f v = p.vertices[i];
                p.vertices[i] = v.add(direction);
            }
        }
    }



    public static float sin(float numb){
        return (float)Math.sin(numb);
    }
    public static float cos(float numb){
        return (float)Math.cos(numb);
    }
}
