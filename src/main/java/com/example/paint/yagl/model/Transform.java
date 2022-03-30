package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;

public class Transform {

    public static Polygon perspective(Polygon polygon){
        //float scale = (float) (1 / Math.tan(120 * 0.5 * Math.PI / 180));
        //System.out.println(scale);
        float aspect = 4f/3f;
        Polygon p = new Polygon(polygon);
        for(int i=0; i<p.vertices.length; i++){
            Vector3f v = p.vertices[i];
            p.vertices[i] = new Vector3f(v.x/v.z,v.y/v.z,v.z);
        }
        p.update();
        return p;
    }

    public static Vector3f rotateVertex(Vector3f v, Vector3f rot, Vector3f center){
        Vector3f centered = v.subtract(center);
        Vector3f newVertex = centered;
        if (rot.x != 0.0f){
            float a = rot.x;
            newVertex = new Vector3f(newVertex.x,
                    newVertex.y*cos(a)+ newVertex.z*sin(a),
                    -newVertex.y*sin(a) + newVertex.z * cos(a));
        }if(rot.y != 0.0f){
            float a = rot.y;
            newVertex = new Vector3f(newVertex.x*cos(a)- newVertex.z*sin(a),
                    newVertex.y,
                    newVertex.x*sin(a)+ newVertex.z*cos(a));
        }
        if(rot.z != 0.0f){
            float a = rot.y;
            newVertex = new Vector3f(newVertex.x*cos(a)+ newVertex.y*sin(a),
                    -newVertex.x*sin(a)+ newVertex.y*cos(a),
                    newVertex.z);
        }

        Vector3f diff = v.subtract(centered);
        return newVertex.add(diff);
    }

    public static void rotateMesh(Polygon[] polygons, Vector3f rot, Vector3f center){
        for(Polygon p: polygons){
            for(int i=0; i<p.vertices.length; i++){
                Vector3f v = p.vertices[i];
                p.vertices[i] = Transform.rotateVertex(v,rot,center);
            }
            p.update();
        }
    }

    public static void move(Polygon[] cube, Vector3f direction) {
        for(Polygon p: cube){
            for(int i=0; i<p.vertices.length; i++){
                Vector3f v = p.vertices[i];
                p.vertices[i] = v.add(direction);
            }
            p.update();
        }
    }

    public static Vector3f between(Vector3f v1, Vector3f v2, float x){
        float xDiff = Math.abs(x - v1.x);
        float yDiff = Math.abs(v1.y - v2.y);
        float zDiff = Math.abs(v1.z - v2.z);
        float prop =  (x - Math.min(v1.x,v2.x))/xDiff;
        return new Vector3f(x,Math.min(v1.y,v2.y) + prop * yDiff,Math.min(v1.z,v2.z) + prop * zDiff);
    }

    public static float sin(float numb){
        return (float)Math.sin(numb);
    }
    public static float cos(float numb){
        return (float)Math.cos(numb);
    }
}
