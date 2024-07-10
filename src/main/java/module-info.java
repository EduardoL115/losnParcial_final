
module org.example.parcial3 {


    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.parcialfinal_eduardo_linares to javafx.fxml;
    exports com.example.parcialfinal_eduardo_linares;
}