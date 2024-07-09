package com.example.parcialfinal_eduardo_linares;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Chamba2!");
        stage.setScene(scene);
        stage.show();*/
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Inicio.fxml"));
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        }catch (Exception e){e.printStackTrace();}

    }

    public static void main(String[] args) {
        launch();
    }
}