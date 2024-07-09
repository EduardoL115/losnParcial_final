package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

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
    private AnchorPane ancPane;//id asignado al AnchorPane fxml
    private String id = "0"; //variable de tipo String que almacena localmente el valor de id de la base de datos
    @FXML
    public ListView lista;//id asignado a la lista fxml
    @FXML
    public TextField nombreCompletoTxt;//id asignado a la textfeild
    @FXML
    public TextField direccionTxt;//id asignado a  textfeild
    @FXML
    public TextField telefonoTxt;// id asignado a textfeild

    @FXML
    public Label error;




    public HelloController() throws SQLException {}//constructor

    @FXML
    public void initialize() throws SQLException { //preparacion para mostrar en la pantalla
        mostrarClientes();// Funcion para mostrar clientes en la lista
    }

    /**
     * Funcion sirve para almacenar en un array los clientes de la base de datos, para luego dividir los datos con la funcion "lista.setOnMouseClicked"
     * y asi guardar y mostrarlos.
     * @throws SQLException
     */
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

    /**
     * Funcion para poder ingresar nuevos clientes a la base de datos
     * @throws SQLException
     */
    @FXML
    public void insercionClientes() throws SQLException {
        if(!nombreCompletoTxt.getText().isBlank()|| !direccionTxt.getText().isBlank() || !telefonoTxt.getText().isBlank()) {
            pst = cn.prepareStatement("INSERT INTO CLIENTE (nombre_completo,direccion,telefono) VALUES (?,?,?)");
            pst.setString(1, nombreCompletoTxt.getText());
            pst.setString(2, direccionTxt.getText());
            pst.setString(3, telefonoTxt.getText());
            pst.executeUpdate();
            mostrarClientes();
            error.setText(" ");
        }else{
            error.setText("*Debe ingresar datos para poder guardar al nuevo cliente!");
        }
    }

    /**
     * Esta funcion sirve para poder eliminar al cliente que el empleado desee, esta funcion crea la consulta de sql, luego pregunta por el id del cliente a eliminar
     * para asi posteriormente eliminarlo de la base de datos y quitar sus datos alamacenados en el programa
     * @throws SQLException
     */
    @FXML
    public void eliminarCliente() throws SQLException {
        pst = cn.prepareStatement("DELETE FROM CLIENTE WHERE id = ?");
        pst.setInt(1, Integer.parseInt(getId()));
        pst.executeUpdate();
        nombreCompletoTxt.setText(" ");
        direccionTxt.setText(" ");
        telefonoTxt.setText(" ");
        id = "0";
        mostrarClientes();
    }

    @FXML
    public void actualziarCliente() throws SQLException {
        pst = cn.prepareStatement("UPDATE CLIENTE SET nombre_completo = ?, direccion = ?, telefono = ? WHERE id = ?");
        pst.setString(1, nombreCompletoTxt.getText());
        pst.setString(2,direccionTxt.getText());
        pst.setString(3, telefonoTxt.getText());
        pst.setInt(4, Integer.parseInt(getId()));
        pst.executeUpdate();
        mostrarClientes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}// funcion de la implementacion de inizalitable en la clase (no es necesaria para el programa)

    @FXML
    private void RegistrarTarjeta(ActionEvent event) throws IOException { //funcion para el boton de registrar tarjeta, sirve para poder cambiar de escena a la siguiente la cual su funcionalidad es registrar tarjetas

        if(getId() != "0") {
            System.out.println("ENTRO");
            try {//try asignar una fxml
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("tarjeta.fxml"))); // crea un objeto de FXMLLoader para guardar la ubicacion de el fxml
                AnchorPane view = loader.load();//guarda loader y la direccion en el una nuevo Objeto de anchor pane
                TarjetaController tc = loader.getController();//crea un objeto tarjetaController agarando el controller asignado a el fxml en loader

                try {//try coneccion a la base de datos
                    tc.obtenerId(id);// llama una funcion para llenar la lista de tarjeta con la informacion del id seleccionado en la lista de esta clase
                } catch (SQLException e) {// catch error al no connectar a la base de datos
                    throw new RuntimeException(e);// revisa si no se logro conectar a la base d e datos;
                }
                ancPane.getChildren().setAll(view);// cambia lo que se muestra en el anchor pane que se esta mostrando en la parte central
            } catch (IOException e) {//catch error al no poder encontra fxml
                throw new RuntimeException(e);//mensaje error en caso que no se pueda cargar el fxml
            }
            error.setText(" ");
        }else{
            error.setText("* Debe seleccionar un Cliente!");
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {//asigna fucion al boton volver
        try { // try para intentar asignar una fxml
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Tab_Registros.fxml")));// gurada la ruta del fxml en un objeto FXMLLoader
            AnchorPane view = loader.load();// guarda loader y la direccion en el una nuevo Objeto de anchor pane
            ancPane.getChildren().setAll(view);//cambia lo que se muestra en el anchor pane que se esta mostrando en la parte central
        } catch (IOException e) {//catch error al no poder encontra fxml
            throw new RuntimeException(e);//mensaje error en caso que no se pueda cargar el fxml
        }

    }

    public String getId() {
        return id;
    }//getter id

    public void setId(String id) {
        this.id = id;
    }//setter id
}
