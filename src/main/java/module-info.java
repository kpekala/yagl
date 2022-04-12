module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.paint.app;
    opens com.example.paint.app to javafx.fxml;
    exports com.example.paint.utils;
    opens com.example.paint.utils to javafx.fxml;
}