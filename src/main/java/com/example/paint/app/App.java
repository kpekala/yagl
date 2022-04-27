package com.example.paint.app;

import com.example.paint.utils.Input;
import com.example.paint.utils.OBJLoader;
import com.mokiat.data.front.parser.IOBJParser;
import com.mokiat.data.front.parser.OBJModel;
import com.mokiat.data.front.parser.OBJParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

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
        //testObj();
    }

    private void testObj() {
        URL objFileURL = OBJLoader.class.getResource("/sample.obj");
        if (objFileURL != null) {
            try (InputStream in = objFileURL.openStream()) {
                // Create an OBJParser and parse the resource
                final IOBJParser parser = new OBJParser();
                final OBJModel model = parser.parse(in);

                // Use the model representation to get some basic info
                System.out.println(MessageFormat.format(
                        "OBJ model has {0} vertices, {1} faces, {2} texture coordinates, and {3} objects.",
                        model.getVertices().size(),
                        model.getObjects().get(0).getMeshes().get(0).getFaces().size(),
                        model.getTexCoords().size(),
                        model.getObjects().size()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }
}