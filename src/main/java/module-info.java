module org.example.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.parcial3 to javafx.fxml;
    exports org.example.parcial3;
}