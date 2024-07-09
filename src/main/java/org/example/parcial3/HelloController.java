package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    ResultSet rs;

    PreparedStatement pst;

    Connection cn = DB.getInstance();

    Statement st = cn.createStatement();




    @FXML
    private AnchorPane ancPane;
    private String id;
    @FXML
    public ListView lista;
    @FXML
    public TextField nombreCompletoTxt;
    @FXML
    public TextField direccionTxt;
    @FXML
    public TextField telefonoTxt;

    public HelloController() throws SQLException {}

    @FXML
    public void initialize() throws SQLException {
        mostrarClientes();
    }

    @FXML
    public void mostrarClientes() throws SQLException {
        lista.getItems().clear();
        rs = st.executeQuery("SELECT * FROM CLIENTE");

        while(rs.next()){
            String record = rs.getInt("id") + "/" + rs.getString("nombre_completo") + "/" + rs.getString("direccion") + "/" + rs.getString("telefono") + "/";
            lista.getItems().add(record);
        }

        lista.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String str = lista.getSelectionModel().getSelectedItem().toString();
                String[] arrOfStr = str.split("/");
                int i = 1;
                for (String a : arrOfStr) {
                    if(i == 1){
                        setId(a);
                        //System.out.println(getId());
                    }else if(i == 2 && a != null){
                        nombreCompletoTxt.setText(a);
                    }else if(i == 3 && a != null){
                        direccionTxt.setText(a);
                    }else if(i == 4 && a != null){
                        telefonoTxt.setText(a);
                    }
                    i++;
                }
            }
        });
    }

    @FXML
    public void insercionClientes() throws SQLException {
        pst = cn.prepareStatement("INSERT INTO CLIENTE (nombre_completo,direccion,telefono) VALUES (?,?,?)");
        pst.setString(1, nombreCompletoTxt.getText());
        pst.setString(2, direccionTxt.getText());
        pst.setString(3, telefonoTxt.getText());
        pst.executeUpdate();
        mostrarClientes();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void RegistrarTarjeta(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("tarjeta.fxml")));
            ancPane.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void volver(ActionEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Tab_Registros.fxml")));
            ancPane.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
