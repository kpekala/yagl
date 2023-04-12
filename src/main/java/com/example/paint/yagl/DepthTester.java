package com.example.paint.yagl;

import com.example.paint.yagl.model.basic.Vector3f;

import java.util.Arrays;

public class DepthTester {
    private final float MAX = 10000;
    private final float[][] depthBuffer;

    public DepthTester(int x, int y) {
        depthBuffer = new float[y][x];
        clearBuffer();
    }

    public void clearBuffer() {
        for (float[] floats : depthBuffer) {
            Arrays.fill(floats, MAX);
        }
    }

    public boolean isCloser(Vector3f v) {
        return v.z < depthBuffer[(int) v.y][(int) v.x];
    }

    public void update(Vector3f v) {
        depthBuffer[(int) v.y][(int) v.x] = v.z;
    }
}
