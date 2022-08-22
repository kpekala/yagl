package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.model.complex.Polygon;
import com.example.paint.yagl.utils.ColorUtils;
import com.example.paint.yagl.utils.Maths;
import javafx.scene.PointLight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Samples {
    public static Polygon[] getCube(){
        return new Polygon[]{
                new Polygon(new float[][]{
                {2,-2,4},
                {2,-2,8},
                {-2,-2,8},
                {-2,-2,4},
        }), new Polygon(new float[][]{
                {2, 2,4},
                {2, 2,8},
                {-2,2,8},
                {-2,2,4},
        }), new Polygon(new float[][]{
                {-2, 2,4},
                {-2, 2,8},
                {-2,-2,8},
                {-2,-2,4},
        }), new Polygon(new float[][]{
                {2, 2,4},
                {2, 2,8},
                {2,-2,8},
                {2,-2,4},
        }), new Polygon(new float[][]{
                {-2, -2,4},
                {-2, 2,4},
                {2,2,4},
                {2,-2,4}
        }), new Polygon(new float[][]{
                {-2, -2,8},
                {-2, 2,8},
                {2,2,8},
                {2,-2,8}})
        };
    }

    public static Model getCubeModel(Vector3f pos, Vector3f color){
        return new Model(getCube(),pos, color);
    }

    public static List<Model> generateCubeModels(int number) {
        var cubes = new ArrayList<Model>();
        for(int i=0; i<number; i++){
            float x = Maths.randomInRange(-20,20);
            float y = Maths.randomInRange(-20f, 20f);
            float z = Maths.randomInRange(30f, 40f);
            cubes.add(getCubeModel(new Vector3f(x,y,z), ColorUtils.randomColor()));
        }
        return cubes;
    }

    public static Model plane(Vector3f color, Vector3f scale){
        float[][] data = {
                {1,0,1},
                {1,0,-1},
                {-1,0,-1},
                {-1,0,1}
        };
        data = scale(data, scale);
        Polygon planePolygon = new Polygon(data,color);
        return new Model(new Polygon[]{planePolygon},color);
    }

    public static float[][] scale(float[][] data, Vector3f scale){
        return  Arrays.stream(data).map(p -> new float[]{p[0] * scale.x, p[1] * scale.y, p[2] * scale.z})
                .toArray(float[][]::new);
    }

}