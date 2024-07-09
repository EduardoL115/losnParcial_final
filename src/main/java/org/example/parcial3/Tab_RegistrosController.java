package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Tab_RegistrosController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    public void RegistroCliente(ActionEvent event)throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));//
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // 00382523 dois y yo en cafeina y nicotina sabe
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
