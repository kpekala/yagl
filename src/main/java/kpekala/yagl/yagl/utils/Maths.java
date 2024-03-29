package kpekala.yagl.yagl.utils;

import kpekala.yagl.yagl.model.basic.Vector2f;
import kpekala.yagl.yagl.model.basic.Vector3f;

import java.util.Random;

public class Maths {
    public static Random random = new Random();

    public static float fValue(float a, float b, float x) {
        return a * x + b;
    }

    public static float fParameter(float a, float b, float y) {
        return (y - b) / a;
    }

    public static float[] planeCoefficients(Vector3f v1, Vector3f v2, Vector3f v3) {
        float a1 = v2.x - v1.x;
        float b1 = v2.y - v1.y;
        float c1 = v2.z - v1.z;
        float a2 = v3.x - v1.x;
        float b2 = v3.y - v1.y;
        float c2 = v3.z - v1.z;
        float[] planeCoefs = new float[4];
        planeCoefs[0] = b1 * c2 - b2 * c1;
        planeCoefs[1] = a2 * c1 - a1 * c2;
        planeCoefs[2] = a1 * b2 - b1 * a2;
        planeCoefs[3] = (-planeCoefs[0] * v1.x - planeCoefs[1] * v1.y - planeCoefs[2] * v1.z);
        return planeCoefs;
    }

    public static float zPlaneValue(float[] coefs, float x, float y) {
        return (-coefs[3] - coefs[1] * y - coefs[0] * x) / coefs[2];
    }

    public static float randomInRange(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    public static float[] get2DLineCoefficients(Vector2f v1, Vector2f v2) {
        float a = (v2.y - v1.y) / (v2.x - v1.x);
        float b = v1.y - a * v1.x;
        return new float[]{a, b};
    }

}
