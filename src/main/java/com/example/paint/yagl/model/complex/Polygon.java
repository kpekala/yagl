package com.example.paint.yagl.model.complex;

import com.example.paint.yagl.model.basic.Vector3f;

import java.util.Arrays;

public class Polygon {
    public final Vector3f[] vertices;

    public Polygon(Vector3f[] vertices) {
        this.vertices = vertices;
    }

    public Polygon(Polygon that){
        this.vertices = that.vertices.clone();
    }

    public Polygon(float[][] data){
        this.vertices = new Vector3f[data.length];
        for(int i=0; i<data.length; i++){
            vertices[i] = new Vector3f(data[i][0],data[i][1],data[i][2]);
        }
    }
}
