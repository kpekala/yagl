module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.data.front;

    exports com.example.paint.app;
    exports com.example.paint.yagl.model.basic;
    exports com.example.paint.yagl.model.complex;
    exports com.example.paint.yagl.scene.components;
    opens com.example.paint.app to javafx.fxml;
    exports com.example.paint.yagl.scene;
    opens com.example.paint.yagl.scene to javafx.fxml;
}