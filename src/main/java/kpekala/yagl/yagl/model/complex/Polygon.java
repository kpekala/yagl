package kpekala.yagl.yagl.model.complex;

import kpekala.yagl.yagl.utils.Maths;
import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.model.basic.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polygon {
    public final Vector3f[] vertices;
    public Vector2f[] coefs;
    public float yMin, yMax;

    private int n;
    private float[] planeCoefs = new float[4];

    private Vector3f color;


    public Polygon(Polygon that){
        this.vertices = that.vertices.clone();
        this.yMax = that.yMax;
        this.yMin = that.yMin;
        this.coefs = that.coefs.clone();
        this.n = that.n;
        this.planeCoefs = that.planeCoefs;
        this.color = that.color;
    }

    public Polygon(float[][] data){
        this.vertices = new Vector3f[data.length];
        for(int i=0; i<data.length; i++){
            vertices[i] = new Vector3f(data[i][0],data[i][1],data[i][2]);
        }
        init();
    }

    public Polygon(float [][] data, Vector3f color){
        this(data);
        this.color = color;
    }

    private void init() {
        this.n = vertices.length;
        coefs = new Vector2f[n];

        findMinAndMax();
        computeCoefficients();
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
        planeCoefs = Maths.planeCoefficients(vertices[0], vertices[1],vertices[2]);
    }

    public float zValueAtPoint(float x, float y){
        return Maths.zPlaneValue(planeCoefs,x,y);
    }

    public void update(){
        findMinAndMax();
        computeCoefficients();
    }

    public List<Float> findIntersections(int yHeight){
        ArrayList<Float> xs = new ArrayList<>();
        for(int i=0; i< vertices.length; i++){
            if(isIntersectingWithEdge(i,yHeight)){
                if (coefs[i].x == 0){
                    xs.add(vertices[i].x);
                }else{
                    if (Float.isFinite(coefs[i].x))
                        xs.add(Maths.fParameter(coefs[i].x,coefs[i].y,yHeight));
                    else
                        xs.add(vertices[i].x);
                }
            }
        }
        Collections.sort(xs);
        return xs;
    }

    private boolean isIntersectingWithEdge(int edgeIndex, int y){
        Vector3f v1 = vertices[edgeIndex];
        Vector3f v2 = vertices[(edgeIndex+1)%n];
        return (y >= v1.y && y <= v2.y) || (y >= v2.y && y <= v1.y);
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
