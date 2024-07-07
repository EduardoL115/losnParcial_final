package org.example.parcial3;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.*;

public class HelloController {

    ResultSet rs;

    PreparedStatement pst;

    Connection cn = DB.getInstance();

    Statement st = cn.createStatement();

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
                        System.out.println(getId());
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
    @FXML
    public void tarjetaPanel(){
        tarjetaAplication tp = new tarjetaAplication();
        Stage stg = new Stage();

        ((Stage) nombreCompletoTxt.getScene().getWindow()).close();

        try{
            tp.start(stg);
        }catch(Exception e){
            e.printStackTrace();
        }
        stg.show();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}