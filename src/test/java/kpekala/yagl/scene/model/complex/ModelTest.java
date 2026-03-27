package kpekala.yagl.scene.model.complex;

import kpekala.yagl.scene.model.basic.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ModelTest {
    private Polygon[] polygons;
    private Vector3f color;
    private Model model;

    @BeforeEach
    void setUp() {
        polygons = new Polygon[]{new Polygon(new Vector3f[]{new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), new Vector3f(0, 1, 0)})};
        color = new Vector3f(1, 0, 0);
        model = new Model(polygons, color);
    }

    @Test
    void testConstructorWithPolygonsAndColor() {
        assertNotNull(model);
        assertEquals(color, model.getColor());
        assertEquals("Default", model.getName());
    }

    @Test
    void testConstructorWithPolygonsColorAndName() {
        Model namedModel = new Model(polygons, color, "TestModel");
        assertEquals("TestModel", namedModel.getName());
    }

    @Test
    void testMove() {
        Vector3f direction = new Vector3f(1, 1, 1);
        model.move(direction);
        assertEquals(new Vector3f(1, 1, 1), model.getRotationCenter());
    }

    @Test
    void testRotateAroundPosition() {
        Vector3f rotation = new Vector3f(0, 90, 0); // Rotate 90 degrees around Y-axis
        Vector3f position = new Vector3f(0, 0, 0);
        model.rotateAroundPosition(rotation, position);
        // Add assertions based on expected rotated positions
    }

    @Test
    void testSetColor() {
        Vector3f newColor = new Vector3f(0, 1, 0); // Green color
        model.setColor(newColor);
        assertEquals(newColor, model.getColor());
    }

    @Test
    void testSetName() {
        model.setName("NewName");
        assertEquals("NewName", model.getName());
    }

    @Test
    void testCopyConstructor() {
        Model copiedModel = new Model(model);
        assertEquals(model.getColor(), copiedModel.getColor());
        assertEquals(model.getName(), copiedModel.getName());
        assertNotSame(model, copiedModel);
    }
}
