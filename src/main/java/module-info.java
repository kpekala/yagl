module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.data.front;

    exports kpekala.yagl.app;
    exports kpekala.yagl.yagl.model.basic;
    exports kpekala.yagl.yagl.model.complex;
    exports kpekala.yagl.yagl.scene.components;
    opens kpekala.yagl.app to javafx.fxml;
    exports kpekala.yagl.yagl.scene;
    opens kpekala.yagl.yagl.scene to javafx.fxml;
}