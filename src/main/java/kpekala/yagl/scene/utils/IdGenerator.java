package kpekala.yagl.scene.utils;

public class IdGenerator {

    private static Integer availableId = 0;

    public static String getNextId() {
        return (availableId++).toString();
    }
}
