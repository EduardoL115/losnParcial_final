module com.example.parcialfinal_eduardo_linares {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.parcialfinal_eduardo_linares to javafx.fxml;
    exports com.example.parcialfinal_eduardo_linares;
}