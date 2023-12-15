package kpekala.yagl.yagl;

import kpekala.yagl.yagl.api.Drawable;
import kpekala.yagl.yagl.model.Transform;
import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.model.basic.Vector2i;
import kpekala.yagl.yagl.model.basic.Vector3f;
import kpekala.yagl.yagl.model.complex.Model;
import kpekala.yagl.yagl.model.complex.Polygon;
import kpekala.yagl.yagl.scene.BaseScene;
import kpekala.yagl.yagl.utils.Maths;

import java.util.Arrays;
import java.util.List;

public class Drawer {
    private final Drawable drawable;
    private final Vector3f defaultColor = new Vector3f(0.2f, 0.5f, 0.8f);

    private final Vector2f size;
    private final Vector2f canvasCenter;

    private final DepthTester depthTester;

    private final BaseScene scene;

    public Drawer(Drawable drawable, Vector2f size, BaseScene scene) {
        this.drawable = drawable;
        this.size = size;
        this.scene = scene;
        depthTester = new DepthTester((int) size.x, (int) size.y);
        canvasCenter = new Vector2f(size.x / 2, size.y / 2);
    }

    public void drawScene() {
        clearView();
        List<Model> models = scene.getDrawableModels();
        for (Model cube : models) {
            drawModel(cube);
        }
        for (Model cube : models) {
            drawModelEdges(cube, defaultColor);
        }
        scene.drawExtra();
    }

    /**
     * Drawing model on user-defined screen.
     * This method fills polygons on screen with the color of the model.
     **/

    public void drawModel(Model model) {
        for (var polygon : model.polygons) {
            draw3DPolygon(polygon);
        }
    }

    /**
     * Drawing model on user-defined screen.
     * This method draws edges of the model with a color.
     **/

    public void drawModelEdges(Model model, Vector3f color) {
        for (var polygon : model.polygons) {
            draw3DPolygonEdges(polygon, color);
        }
    }

    /**
     * Drawing 3D Polygon on user-defined screen.
     * This method fills polygon on screen with the given color.
     **/
    public void draw3DPolygon(Polygon polygon) {
        Polygon p = transform3DPolygonToScreenPolygon(polygon);
        if (inScreen(p) && inFrontOfScreen(p)) {
            System.out.println(p.yMin + " " + p.yMax);
            for (int y = (int) Math.rint(Math.max(p.yMin, 0)); y < Math.rint(Math.min(p.yMax, size.y)); y++) {
                drawLineInsidePolygon(p, p.getColor(), y);
            }
        }
    }

    /**
     * Drawing 3D edges of the 3D Polygon.
     * This method draws edges of the polygon with a color.
     **/
    public void draw3DPolygonEdges(Polygon p, Vector3f color) {
        Polygon polygon = transform3DPolygonToScreenPolygon(p);
        if (inScreen(polygon) && inFrontOfScreen(p)) {
            Vector3f[] vs = polygon.vertices;
            for (int i = 0; i < vs.length; i++) {
                Vector3f v1 = vs[i];
                Vector3f v2 = vs[(i + 1) % vs.length];
                if (v1.z >= 1 && v2.z >= 1)
                    draw2DLine(v1.to2i(), v2.to2i(), color);
            }
        }
    }


    public void draw2DLine(Vector2i v1, Vector2i v2, Vector3f color) {
        var coefs = Maths.get2DLineCoefficients(v1.tof(), v2.tof());
        Vector2i topLeft = new Vector2i(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
        Vector2i bottomRight = new Vector2i(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));

        if (topLeft.x == bottomRight.x) {
            drawVertical2DLine(topLeft.x, topLeft.y, bottomRight.y, color);
            return;
        }

        int lastPixelY = (int) Math.rint(coefs[0] * topLeft.x + coefs[1]);
        for (int pixelX = topLeft.x; pixelX <= bottomRight.x; pixelX++) {
            int pixelY = (int) Math.rint(coefs[0] * pixelX + coefs[1]);
            drawVertical2DLine(pixelX, Math.min(lastPixelY, pixelY), Math.max(lastPixelY, pixelY), color);
            lastPixelY = pixelY;
        }
    }

    public void clearView() {
        drawable.clearCanvas();
        depthTester.clearBuffer();
    }

    private void drawVertical2DLine(int x, int yMin, int yMax, Vector3f color) {
        for (int y = yMin; y <= yMax; y++) {
            drawable.drawPixel(new Vector2f(x, y), color);
        }
    }

    private Polygon transform3DPolygonToScreenPolygon(Polygon p) {
        Polygon polygonWithPerspective = Transform.perspective(p);
        return transformToScreenCoordinates(polygonWithPerspective);
    }

    private void drawLineInsidePolygon(Polygon p, Vector3f color, int screenY) {
        List<Float> xs = p.findIntersections(screenY);
        for (int i = 0; i < xs.size() - 1; i += 2) {
            float z1 = p.zValueAtPoint(xs.get(i), screenY);
            float z2 = p.zValueAtPoint(xs.get(i + 1), screenY);
            drawHorizontalLine(new Vector3f(xs.get(i), screenY, z1),
                    new Vector3f(xs.get(i + 1), screenY, z2), p, color);
        }
    }

    private void drawHorizontalLine(Vector3f v1, Vector3f v2, Polygon p, Vector3f color) {
        float y = v1.y;
        for (int x = (int) Math.rint(Math.max(v1.x, 0)); x <= Math.min(v2.x, size.x - 1); x++) {
            Vector3f vd = new Vector3f(x, y, p.zValueAtPoint(x, y));
            if (depthTester.isCloser(vd)) {
                depthTester.update(vd);
                drawable.drawPixel(vd.to2f(), color);
            }
        }
    }

    private Polygon transformToScreenCoordinates(Polygon polygon) {
        Polygon p = new Polygon(polygon);
        for (int i = 0; i < p.vertices.length; i++) {
            p.vertices[i] = screenPosition(p.vertices[i]);
        }
        p.update();
        return new Polygon(p);
    }

    /**
     * Change coordinates from image Plane(-1, 1) to screen pixels
     **/

    private Vector3f screenPosition(Vector3f v) {
        float scaleFactor = 600;

        Vector3f newPosition = new Vector3f(canvasCenter.x + v.x * scaleFactor,
                canvasCenter.y - v.y * scaleFactor, v.z + 1/*!!!*/);
        return newPosition;
    }

    /**
     * This method checks if polygon's 2d coordinates are in screen
     * For now, it checks if at least one vertex is in screen
     **/

    private boolean inScreen(Polygon pol) {
        float minX = size.x/2, maxX = size.x/2, minY = size.y/2, maxY = size.y/2;
        for (Vector3f v: pol.vertices){
            if((v.x >= 0 && v.x <= size.x
                    && v.y >= 0 && v.y <= size.y))
                return true;
            maxY = Math.max(maxY, v.y);
            minY = Math.min(minY, v.y);
            maxX = Math.max(maxX, v.x);
            minX = Math.min(minX, v.x);
        }
        if ((minX <= 0 && maxX >= size.x ) && Arrays.stream(pol.vertices).anyMatch(vertex -> vertex.y >= 0 && vertex.y < size.y))
            return true;
        if (minY <= 0 && maxY >= size.y && Arrays.stream(pol.vertices).anyMatch(vertex -> vertex.x >= 0 && vertex.x < size.x))
            return true;
        return false;
//        return Arrays.stream(pol.vertices).anyMatch(p -> p.x >= 0 && p.x <= size.x
//                && p.y >= 0 && p.y <= size.y);
    }

    private boolean inFrontOfScreen(Polygon pol) {
        return Arrays.stream(pol.vertices).anyMatch(p -> p.z >= 1);
    }


}
