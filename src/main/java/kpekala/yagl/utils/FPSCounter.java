package kpekala.yagl.utils;

import java.util.Arrays;

public class FPSCounter {
    private static final int[] fpss = new int[10];
    private static int fpsIndex = 0;
    private static int avgFPS = 0;

    public static int updateAndGetFPS(long frameDurationMillis) {
        double diffSec = frameDurationMillis / 1000f;
        int fps = (int) (1 / diffSec);
        fpss[fpsIndex++] = fps;
        if (fpsIndex == fpss.length) {
            avgFPS = Arrays.stream(fpss).sum() / fpss.length;
            fpsIndex = 0;
        }
        return avgFPS;
    }
}
