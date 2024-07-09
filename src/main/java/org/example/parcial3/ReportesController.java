package org.example.parciaal3;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class ReportesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private AnchorPane ancPane;


    @FXML
    public void reporteA(ActionEvent actionEvent) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reporteA.fxml")));
            ancPane.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void reporteB(ActionEvent actionEvent) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reporteB.fxml")));
            ancPane.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
