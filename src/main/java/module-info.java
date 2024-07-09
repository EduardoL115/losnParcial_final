module org.example.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens org.example.parcial3 to javafx.fxml;
    exports org.example.parcial3;
}