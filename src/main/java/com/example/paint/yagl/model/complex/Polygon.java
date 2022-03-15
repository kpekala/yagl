package com.example.paint.yagl.model.complex;

import com.example.paint.utils.Maths;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {
    public final Vector3f[] vertices;
    public Vector2f[] coefs;
    private int n;

    public float yMin, yMax;

    public Polygon(Polygon that){
        this.vertices = that.vertices.clone();
        this.yMax = that.yMax;
        this.yMin = that.yMin;
        this.coefs = that.coefs.clone();
        this.n = that.n;
    }

    public Polygon(float[][] data){
        this.vertices = new Vector3f[data.length];
        for(int i=0; i<data.length; i++){
            vertices[i] = new Vector3f(data[i][0],data[i][1],data[i][2]);
        }
        init(vertices.length);
    }

    private void init(int n) {
        coefs = new Vector2f[n];
        this.n = n;

        computeCoefficients();
        findMinAndMax();
    }

    private void findMinAndMax() {
        yMin = 1000;
        yMax = -1000;
        for(var vertex: vertices){
            yMax = Math.max(yMax,vertex.y);
            yMin = Math.min(yMin, vertex.y);
        }
    }

    private void computeCoefficients() {
        for(int i=0; i< vertices.length; i++){
            Vector3f v1 = vertices[i];
            Vector3f v2 = vertices[(i+1)% vertices.length];
            float a = (v2.y-v1.y)/(v2.x-v1.x);
            float b = v1.y - a*v1.x;
            coefs[i] = new Vector2f(a,b);
        }
    }

    public void update(){
        computeCoefficients();
        findMinAndMax();
    }

    public List<Float> findIntersections(int yHeight){
        ArrayList<Float> xs = new ArrayList<>();
        for(int i=0; i< vertices.length; i++){
            if(isIntersectingWithEdge(i,yHeight)){
                if (coefs[i].x == 0){
                    xs.add(vertices[i].x);
                }else{
                    xs.add(Maths.fParameter(coefs[i].x,coefs[i].y,yHeight));
                }
            }
        }
        return xs;
    }

    private boolean isIntersectingWithEdge(int edgeIndex, int y){
        Vector3f v1 = vertices[edgeIndex];
        Vector3f v2 = vertices[(edgeIndex+1)%n];
        return (y >= v1.y && y <= v2.y) || (y >= v2.y && y <= v1.y);
    }

}
