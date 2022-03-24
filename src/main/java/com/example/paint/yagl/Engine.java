package com.example.paint.yagl;

import com.example.paint.yagl.model.Transform;
import com.example.paint.yagl.model.basic.Vector2f;
import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Polygon;

import java.lang.Math;
import java.util.Arrays;
import java.util.List;

public class Engine {
    private final Drawable drawable;
    private final Vector3f defaultColor;

    private final Vector2f size;
    private final Vector2f canvasCenter;
    private float scaleFactor = 130;

    private final DepthTester depthTester;

    public Engine(Drawable drawable, Vector2f size){
        this.drawable = drawable;
        this.size = size;
        defaultColor = new Vector3f(0.2f,0.5f,0.8f);
        depthTester = new DepthTester((int) size.x, (int) size.y);
        canvasCenter = new Vector2f(size.x/2, size.y/2);
    }

    public void drawPolygonEdges(Polygon p){
        Polygon polygon = Transform.perspective(p);
        if (inScreen(polygon)){
            changeCoordinates(polygon);
            Vector3f[] vs = polygon.vertices;
            for(int i=0; i<vs.length; i++){
                drawable.drawLine(vs[i].to2(),vs[(i+1)%vs.length].to2(),defaultColor);
            }
        }
    }

    public void render3DPolygon(Polygon p, Vector3f color){
        p = Transform.perspective(p);
        changeCoordinates(p);
        if (inFrontOfScreen(p) && inScreen(p)) {
            for(int y = (int) Math.max(p.yMin,0); y<Math.min(p.yMax, size.y); y++){
                renderLineInsidePolygon(p, color,y);
            }
        }
    }

    private void renderLineInsidePolygon(Polygon p, Vector3f color, int screenY) {
        List<Float> xs = p.findIntersections(screenY);
        for(int i=0; i<xs.size()-1; i+=2){
            float z1 = p.zValueAtPoint(xs.get(i),screenY);
            float z2 = p.zValueAtPoint(xs.get(i+1),screenY);
            drawLine(new Vector3f(xs.get(i),screenY,z1),
                    new Vector3f(xs.get(i+1),screenY,z2),p, color);
        }
    }

    private void drawLine(Vector3f v1, Vector3f v2, Polygon p,  Vector3f color) {
        float y = v1.y;
        for(int x = (int) v1.x; x<= v2.x; x++){
            if(x < 0 || x >= size.x) continue;
            Vector3f vd = new Vector3f(x,y,p.zValueAtPoint(x,y));
            if(depthTester.isCloser(vd)){
                depthTester.update(vd);
                drawable.drawPixel(vd.to2(),color);
            }
        }
    }

    private void changeCoordinates(Polygon p) {
        for(int i=0; i<p.vertices.length; i++){
            p.vertices[i] = screenPosition(p.vertices[i]);
        }
        p.update();
    }

    private Vector3f screenPosition(Vector3f v) {
        return new Vector3f(canvasCenter.x + v.x * scaleFactor,
                canvasCenter.y - v.y*scaleFactor, v.z);
    }

    private boolean inScreen(Polygon pol) {
        return Arrays.stream(pol.vertices).anyMatch(p -> p.x >= 0 && p.x <= size.x
                && p.y >= 0 && p.y <= size.y);
    }

    private boolean inFrontOfScreen(Polygon pol){
        return Arrays.stream(pol.vertices).allMatch(p -> p.z >= 1);
    }

    public void clearView() {
        drawable.clearCanvas();
        depthTester.clearBuffer();
    }
}
