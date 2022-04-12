package com.example.paint.yagl.model;

import com.example.paint.yagl.model.basic.Vector3f;
import com.example.paint.yagl.model.complex.Model;
import com.example.paint.yagl.model.complex.Polygon;

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
}
