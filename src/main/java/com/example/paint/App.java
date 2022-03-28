package com.example.paint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static App instance;
    public static App getInstance(){
        return instance;
    }
    public App(){
        instance = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        scene.setOnKeyPressed(event -> Input.onKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> Input.onKeyReleased(event.getCode()));
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}