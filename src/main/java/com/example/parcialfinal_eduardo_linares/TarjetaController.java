package com.example.parcialfinal_eduardo_linares;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TarjetaController {

    ResultSet rs;

    PreparedStatement pst;

    Connection cn = BDconexion.getInstance();

    Statement st = cn.createStatement();

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    public TextField numeroTarjetaTxt;
    @FXML
    public TextField facilitadorTxt;
    @FXML
    public TextField fechaExpiracionTxt;
    @FXML
    public TextField tipoTarjeta;
    @FXML
    public ListView listaTarjeta;

    public TarjetaController() throws SQLException {}

    @FXML
    public void initialize() throws SQLException {
        mostrarTarjetas();
        setFacilitador(generarNumeroTarjeta());
        setFechaExpiracion();
    }
    @FXML
    public void mostrarTarjetas() throws SQLException {
        // lista.getItems().clear();
        rs = st.executeQuery("SELECT t.id,t.numero_tarjeta,t.fecha_expiracion, f.nombre FROM TARJETA as t inner join FACILITADOR as f on t.FK_id_facilitador = f.id");

        while(rs.next()){
            String record = rs.getInt("id") + "/" + rs.getString("numero_tarjeta") + "/" + rs.getDate("fecha_expiracion") + "/" + rs.getString("nombre") + "/";
            listaTarjeta.getItems().add(record);
        }
        //idCliente.setText();

        listaTarjeta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String str = listaTarjeta.getSelectionModel().getSelectedItem().toString();
                String[] arrOfStr = str.split("/");
                int i = 1;
                for (String a : arrOfStr) {

                    if(i == 2 ){
                        numeroTarjetaTxt.setText(a);
                    }
                    i++;
                }
            }
        });
    }


    @FXML
    public void volver(ActionEvent event)throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));//
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // 00382523 dois y yo en cafeina y nicotina sabe
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public String generarNumeroTarjeta() {
        String nuevoNumeroTarjeta = GeneradorTarjeta.generadorTarjeta();
        numeroTarjetaTxt.setText(nuevoNumeroTarjeta);
        System.out.println(nuevoNumeroTarjeta);
        return nuevoNumeroTarjeta;
    }
    @FXML
    private void setFacilitador(String numeroTarjeta) {
        if (numeroTarjeta.startsWith("4")) {
            facilitadorTxt.setText("Visa");
        } else if (numeroTarjeta.startsWith("5")) {
            facilitadorTxt.setText("MasterCard");
        } else if (numeroTarjeta.startsWith("37")) {
            facilitadorTxt.setText("American Express");
        } else if (numeroTarjeta.startsWith("6")) {
            facilitadorTxt.setText("Discover");
        }
    }
    @FXML
    private void setFechaExpiracion(){
        Random random = new Random();
        int yearsToAdd = random.nextInt(5) + 1; // Genera entre 1 y 5 años en el futuro
        int month = random.nextInt(12) + 1; // Genera un mes entre 1 y 12
        int year = LocalDate.now().getYear() + yearsToAdd;

        // Formatear la fecha en el formato MM/AAAA
        String formattedDate = String.format("%02d/%d", month, year);
        fechaExpiracionTxt.setText(formattedDate);
    }
}

