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
    private AnchorPane ancPane;//id asignado al AnchorPane fxml
    private String id; //variable de tipo String que almacena localmente el valor de id de la base de datos
    @FXML
    public ListView lista;//id asignado a la lista fxml
    @FXML
    public TextField nombreCompletoTxt;//id asignado a la textfeild
    @FXML
    public TextField direccionTxt;//id asignado a  textfeild
    @FXML
    public TextField telefonoTxt;// id asignado a textfeild

    public HelloController() throws SQLException {}//constructor

    @FXML
    public void initialize() throws SQLException { //preparacion para mostrar en la pantalla
        mostrarClientes();// Funcion para mostrar clientes en la lista
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
    public void initialize(URL url, ResourceBundle resourceBundle) {}// funcion de la implementacion de inizalitable en la clase (no es necesaria para el programa)

    @FXML
    private void RegistrarTarjeta(ActionEvent event) throws IOException { //funcion para el boton de registrar tarjeta
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
