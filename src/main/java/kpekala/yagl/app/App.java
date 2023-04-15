package kpekala.yagl.app;

import kpekala.yagl.utils.Input;
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
        System.out.println(getClass().getPackage().getName());
        Scene scene = loadScene();

        stage.setOnCloseRequest(event -> exit());
        scene.setOnKeyPressed(event -> Input.onKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> Input.onKeyReleased(event.getCode()));
        stage.setTitle("YAGL");
        stage.setScene(scene);
        stage.show();
    }

    private void exit() {
        Platform.exit();
        System.exit(0);
    }

    private Scene loadScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        return new Scene(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}