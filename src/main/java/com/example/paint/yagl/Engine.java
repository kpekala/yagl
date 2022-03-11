package com.example.paint.yagl;

import com.example.paint.yagl.model.Transform;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;

import java.lang.Math;
import java.util.Arrays;

public class Engine {
    private final Drawable drawable;
    private final Vector3f defaultColor;

    private Vector3f focalPoint = new Vector3f(0,0,0);
    private Vector3f screenPlane = new Vector3f(0,0,1);

    private Vector2f size;
    private float scaleFactor = 100;

    public Engine(Drawable drawable, Vector2f size){
        this.drawable = drawable;
        this.size = size;
        defaultColor = new Vector3f(1,1,1);
    }

    public void drawPolygon(Polygon p){
        Polygon polygon = Transform.perspective(p);
        if (inScreen(polygon)){
            changeCoordinates(polygon);
            Vector3f[] vs = polygon.vertices;
            for(int i=0; i<vs.length; i++){
                drawable.drawLine(vs[i].to2(),vs[(i+1)%vs.length].to2(),defaultColor);
            }
        }
    }

    private void changeCoordinates(Polygon p) {
        Vector2f canvasCenter = new Vector2f(size.x/2, size.y/2);
        for(int i=0; i<p.vertices.length; i++){
            p.vertices[i] = screenPosition(p.vertices[i],canvasCenter);
        }
    }

    private Vector3f screenPosition(Vector3f v, Vector2f canvasCenter) {
        return new Vector3f(canvasCenter.x + v.x * scaleFactor,
                canvasCenter.y - v.y*scaleFactor, v.z);
    }

    private boolean inScreen(Polygon pol) {
        return Arrays.stream(pol.vertices).allMatch(p -> Math.abs(p.x) <= Math.abs(p.z)
                && Math.abs(p.y) <= Math.abs(p.z));
    }
}
