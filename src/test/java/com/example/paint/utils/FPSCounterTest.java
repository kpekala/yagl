package com.example.paint.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FPSCounterTest {

    @Test
    void testUpdateAndGetFPS_returnsCorrectAverageFPS() {
        //Assume
        long frameDurationMillis = 1000;

        //Act
        for (int i = 0; i < 3; i++) {
            FPSCounter.updateAndGetFPS(frameDurationMillis);
            FPSCounter.updateAndGetFPS(frameDurationMillis);
            FPSCounter.updateAndGetFPS(frameDurationMillis);
        }
        int fps = FPSCounter.updateAndGetFPS(frameDurationMillis);

        //Assert
        Assertions.assertEquals(1, fps);
    }
}
