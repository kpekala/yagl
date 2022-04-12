package com.example.paint.utils;

import com.example.paint.app.App;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {

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
}
