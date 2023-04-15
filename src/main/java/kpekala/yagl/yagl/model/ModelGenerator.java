package kpekala.yagl.yagl.model;

import kpekala.yagl.utils.OBJLoader;
import kpekala.yagl.yagl.model.basic.Vector3f;
import kpekala.yagl.yagl.model.complex.Model;
import kpekala.yagl.yagl.model.complex.Polygon;
import kpekala.yagl.yagl.utils.ColorUtils;
import kpekala.yagl.yagl.utils.Maths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelGenerator {
    public static Polygon[] getCube() {
        return new Polygon[]{
                new Polygon(new float[][]{
                        {2, -2, 4},
                        {2, -2, 8},
                        {-2, -2, 8},
                        {-2, -2, 4},
                }), new Polygon(new float[][]{
                {2, 2, 4},
                {2, 2, 8},
                {-2, 2, 8},
                {-2, 2, 4},
        }), new Polygon(new float[][]{
                {-2, 2, 4},
                {-2, 2, 8},
                {-2, -2, 8},
                {-2, -2, 4},
        }), new Polygon(new float[][]{
                {2, 2, 4},
                {2, 2, 8},
                {2, -2, 8},
                {2, -2, 4},
        }), new Polygon(new float[][]{
                {-2, -2, 4},
                {-2, 2, 4},
                {2, 2, 4},
                {2, -2, 4}
        }), new Polygon(new float[][]{
                {-2, -2, 8},
                {-2, 2, 8},
                {2, 2, 8},
                {2, -2, 8}})
        };
    }

    public static Model getCubeModel(Vector3f pos, Vector3f color) {
        return new Model(getCube(), pos, color);
    }

    public static List<Model> generateCubeModels(int number) {
        var cubes = new ArrayList<Model>();
        for (int i = 0; i < number; i++) {
            float x = Maths.randomInRange(-20, 20);
            float y = Maths.randomInRange(-20f, 20f);
            float z = Maths.randomInRange(30f, 40f);
            cubes.add(getCubeModel(new Vector3f(x, y, z), ColorUtils.randomColor()));
        }
        return cubes;
    }

    public static Model plane(Vector3f color, Vector3f scale) {
        float[][] data = {
                {1, 0, 1},
                {1, 0, -1},
                {-1, 0, -1},
                {-1, 0, 1}
        };
        data = scale(data, scale);
        Polygon planePolygon = new Polygon(data, color);
        return new Model(new Polygon[]{planePolygon}, color);
    }

    private static float[][] scale(float[][] data, Vector3f scale) {
        return Arrays.stream(data).map(p -> new float[]{p[0] * scale.x, p[1] * scale.y, p[2] * scale.z})
                .toArray(float[][]::new);
    }


    public static Model loadModelFromFile(String objFileName, String materialFileName) throws IOException {
        Polygon[] objPolygons = OBJLoader.load(objFileName, materialFileName);
        return new Model(objPolygons, ColorUtils.defaultColor);
    }

    public static Model loadModelFromName(String name) throws IOException {
        if (name.equals("panda")) {
            return loadModelFromFile("/panda.obj", "/rock/material.lib");
        }
        throw new IllegalArgumentException();
    }

}
