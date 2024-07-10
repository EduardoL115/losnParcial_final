package org.example.parcial3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {//try encontrar el fxml
            Parent root = FXMLLoader.load(getClass().getResource("Inicio.fxml"));// primera ecena mostrada
            Scene scene1 = new Scene(root);//guradando ecena
            stage.setScene(scene1);//implementando ecena en el stage
            stage.show();//mostrando en pantalla
        }catch (Exception e){e.printStackTrace();} // catch en el caso que la scene no se encuentre fxml
    }
    public static void main(String[] args) {
        launch();// comienza la aplicacion
    }
}