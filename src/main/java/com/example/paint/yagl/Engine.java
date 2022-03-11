package com.example.paint.yagl;

import com.example.paint.yagl.model.Transform;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Triangle;

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

    public void drawTriangle(Triangle triangle){
        Triangle t = Transform.perspective(triangle);
        Vector3f[] vs = t.vs;
        if (inScreen(t)){
            changeCoordinates(t);
            drawable.drawLine(vs[0].to2(),vs[1].to2(),defaultColor);
            drawable.drawLine(vs[1].to2(),vs[2].to2(),defaultColor);
            drawable.drawLine(vs[0].to2(),vs[2].to2(),defaultColor);
        }
    }

    private void changeCoordinates(Triangle t) {
        Vector2f canvasCenter = new Vector2f(size.x/2, size.y/2);
        for(Vector3f v: t.vs){
            v.x = canvasCenter.x - v.x * scaleFactor;
            v.y = canvasCenter.y - v.y * scaleFactor;
        }
    }

    private boolean inScreen(Triangle t) {
        return Arrays.stream(t.vs).allMatch(p -> Math.abs(p.x) <= Math.abs(p.z) && Math.abs(p.y) <= Math.abs(p.z));
    }
}
