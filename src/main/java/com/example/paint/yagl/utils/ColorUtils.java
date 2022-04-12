package com.example.paint.yagl.utils;

import com.example.paint.yagl.model.basic.Vector3f;

import java.util.Random;

public class ColorUtils {
    public static Random random = new Random();

    public static Vector3f RED = new Vector3f(1,0,0);
    public static Vector3f GREEN = new Vector3f(0,1,0);
    public static Vector3f BLUE = new Vector3f(0,0,1);
    public static Vector3f randomColor(){
        return new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }
}
