package kpekala.yagl.utils;

import kpekala.yagl.app.App;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {

    public enum AxisType{
        HORIZONTAL, VERTICAL
    }

    private static App app;

    private static HashMap<KeyCode,Boolean> keys = new HashMap<>();

    public static void init(App app){
        Input.app = app;
    }

    public static boolean isPressed(KeyCode keyCode){
        return keys.getOrDefault(keyCode,false);
    }

    public static void onKeyPressed(KeyCode keyCode){
        keys.put(keyCode,true);
    }

    public static void onKeyReleased(KeyCode keyCode){
        keys.put(keyCode,false);
    }

    public static int getAxis(AxisType type){
        return switch (type) {
            case HORIZONTAL -> judge(KeyCode.D, KeyCode.A);
            case VERTICAL -> judge(KeyCode.W, KeyCode.S);
        };
    }

    public static int judge(KeyCode frontKey, KeyCode  backKey) {
        var key1 = isPressed(frontKey);
        var key2 = isPressed(backKey);
        if (key1 && !key2)
            return 1;
        if(key2 && !key1)
            return -1;
        return 0;
    }
}
