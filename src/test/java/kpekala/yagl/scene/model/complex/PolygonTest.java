package kpekala.yagl.scene.model.complex;

import kpekala.yagl.scene.model.basic.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolygonTest {
    private Polygon polygon;

    @BeforeEach
    void setUp() {
        Vector3f[] vertices = {
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)
        };
        polygon = new Polygon(vertices);
    }

    @Test
    void testConstructorWithVertices() {
        assertNotNull(polygon);
        assertEquals(3, polygon.vertices.length);
    }

    @Test
    void testFindMinAndMax() {
        assertEquals(0, polygon.yMin);
        assertEquals(1, polygon.yMax);
    }

    @Test
    void testZValueAtPoint() {
        float z = polygon.zValueAtPoint(0.5f, 0.5f);
        assertEquals(0, z, 0.001); // Assuming the plane lies on Z=0
    }

    @Test
    void testFindIntersections() {
        List<Float> intersections = polygon.findIntersections(0.5f);
        assertEquals(2, intersections.size());
        assertTrue(intersections.contains(0f));
        assertTrue(intersections.contains(0.5f));
    }

    @Test
    void testUpdate() {
        polygon.vertices[0] = new Vector3f(0, 0, 1);
        polygon.update();
        assertEquals(0, polygon.yMin);
        assertEquals(1, polygon.yMax);
    }

    @Test
    void testCopyConstructor() {
        Polygon copiedPolygon = new Polygon(polygon);
        assertNotSame(polygon, copiedPolygon);
        assertArrayEquals(polygon.vertices, copiedPolygon.vertices);
        assertEquals(polygon.yMin, copiedPolygon.yMin);
        assertEquals(polygon.yMax, copiedPolygon.yMax);
    }
}
