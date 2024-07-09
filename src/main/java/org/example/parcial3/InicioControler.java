package org.example.parciaal3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class InicioControler implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnSalir;
    Stage stage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void Registrar(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Tab_Registros.fxml")));
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void Reportes(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Tab_Reportes.fxml")));
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit(ActionEvent event){
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("salida");
        alert.setHeaderText("Estas seguro que quieres salir ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();
        }
    }

    
}
