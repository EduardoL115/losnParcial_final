package org.example.parcial3;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

import Tarjeta.GeneradorTarjeta;
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

    Connection cn = DB.getInstance();

    Statement st = cn.createStatement();

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    public TextField numeroTarjetaTxt;

    @FXML
    public ChoiceBox facilitador;

    @FXML
    public DatePicker fechaExpiracion;

    @FXML
    public TextField idCliente;

    @FXML
    public ListView listaTarjeta;

    private String id = "0";

    public TarjetaController() throws SQLException {}

    @FXML
    public void initialize() throws SQLException {
        mostrarTarjetas();
        setFacilitador();
        //generarNumeroTarjeta();
    }
    @FXML
    public void mostrarTarjetas() throws SQLException {
        String query = "SELECT t.id,t.numero_tarjeta,t.fecha_expiracion, f.nombre " +
                "FROM TARJETA as t inner join FACILITADOR as f on t.FK_id_facilitador = f.id " +
                "WHERE t.FK_id_cliente = ?";

        pst = cn.prepareStatement(query);
        pst.setInt(1, Integer.parseInt(id));

        rs = pst.executeQuery();

        while(rs.next()){
            String record = rs.getInt("id") + "/" + rs.getString("numero_tarjeta") + "/" + rs.getDate("fecha_expiracion") + "/" + rs.getString("nombre") + "/";
            listaTarjeta.getItems().add(record);
        }

        listaTarjeta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String str = listaTarjeta.getSelectionModel().getSelectedItem().toString();
                String[] arrOfStr = str.split("/");
                int i = 1;
                for (String a : arrOfStr) {

                    if(i == 2 ){
                        numeroTarjetaTxt.setText(a);
                    }else if(i == 3 ){
                        fechaExpiracion.setValue( LocalDate.parse(a));
                    }else if(i == 4 ){
                        facilitador.setValue(a);
                    }
                    i++;
                }
            }
        });
    }

    public void obtenerId(String id) throws SQLException {
        this.id = id;
        idCliente.setText(id);
        mostrarTarjetas();
    }

    public void setFacilitador() throws SQLException {
        rs = st.executeQuery("SELECT nombre FROM FACILITADOR ");
        while (rs.next()) {
            facilitador.getItems().add(rs.getString("nombre"));
        }
    }
    @FXML
    public void volver(ActionEvent event)throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));//
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // 00382523 dois y yo en cafeina y nicotina sabe
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   /* @FXML
    public void generarNumeroTarjeta() {
        String nuevoNumeroTarjeta = GeneradorTarjeta.generadorTarjeta();
        numeroTarjetaTxt.setText(nuevoNumeroTarjeta);
        System.out.println(nuevoNumeroTarjeta);
    }*/
}

