package org.example.parcial3;

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

public class InicioControler implements Initializable {//controlador de el menu principal

    @FXML
    private BorderPane borderPane;//id asignado al  border pane
    @FXML
    private Button btnRegistrar;//id asignado al boton
    @FXML
    private Button btnReportes;//id asignado al boton
    @FXML
    private Button btnSalir;//id asignado al boton
    Stage stage; // objeto de stage



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {} // funcion de la interface (no se utiliza)

    @FXML
    private void Registrar(ActionEvent event) throws IOException {//cambio en la parte central del border pane para mostrar el fxml de tab_registros
        try {// try buscar fxml
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Tab_Registros.fxml")));// le asigna view la ruta de el fxml
            borderPane.setCenter(view);// muestra view en la parte central del border pane
        } catch (IOException e) {//catch en caso no se encuetre el fxml
            throw new RuntimeException(e);//muestra el error
        }
    }

    @FXML
    private void Reportes(ActionEvent event) throws IOException {//cambio en la parte central del border pane para mostrar el fxml de tab_reporte
        try {//try buscar fxml
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Tab_Reportes.fxml")));// guarda el fxml en el objeto view
            borderPane.setCenter(view);// muestra view en la parte central del border pane
        } catch (IOException e) {//catch si no se encuentra el fxml
            throw new RuntimeException(e);//muestra el error
        }
    }

    @FXML
    public void exit(ActionEvent event){//funcion de boton de salida
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION); // objeto de tipo alert  Busca confirmacion de la accion
        alert.setTitle("salida");// nombre de alert window
        alert.setHeaderText("Estas seguro que quieres salir ?");// texto mostrado en la alerta
        if (alert.showAndWait().get() == ButtonType.OK) { //revisa si la opcion escogida es el boton ok
            stage = (Stage) borderPane.getScene().getWindow();// busca la stage en la window abierta
            stage.close();//ciera la stage y el programa
        }
    }

    
}
