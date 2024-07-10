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

    ResultSet rs; // resultados de query

    PreparedStatement pst; // statemenet a mandar a la query

    Connection cn = DB.getInstance();// singolton de la base de datos

    Statement st = cn.createStatement();// statement de la query




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
    public Label error; // id asignado a label que muestra error




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
    public void mostrarClientes() throws SQLException { // funcio muestra  la  lista de clientes
        lista.getItems().clear();// limpia la lista
        rs = st.executeQuery("SELECT * FROM CLIENTE"); // devuelve resultados de query

        while(rs.next()){
            String record = rs.getInt("id") + "/" + rs.getString("nombre_completo") + "/" + rs.getString("direccion") + "/" + rs.getString("telefono") + "/";lista.getItems().add(record); // busca las feilds y las asigna a sus correspondientes lugares
        }

        lista.setOnMouseClicked(new EventHandler<MouseEvent>() { // revisa los clicks en la lista
            @Override
            public void handle(MouseEvent mouseEvent) { // busca los events de el mous click y selecciona el item de la lista

                String str = lista.getSelectionModel().getSelectedItem().toString(); // pasa el item seleccionado de la lista a string
                String[] arrOfStr = str.split("/");//separa la string en otras cada "/" y  los guarda en un array de string
                int i = 1; // inicio de contador
                for (String a : arrOfStr) { // traversa el array de string
                    if(i == 1){ //si contador = 1
                        setId(a); // muestra el id  en a
                    }else if(i == 2 && a != null){ // si el la string existe y contador 2
                        nombreCompletoTxt.setText(a);// pone el nombre de el id en el textfeild
                    }else if(i == 3 && a != null){ // si la string no es nula y contador =3
                        direccionTxt.setText(a); // set label a la string a
                    }else if(i == 4 && a != null){//if contador es 4 y la string a no es nulo
                        telefonoTxt.setText(a);// set text feild a la string a
                    }
                    i++;//aumentar counter
                }
            }
        });
    }

    /**
     * Funcion para poder ingresar nuevos clientes a la base de datos
     * @throws SQLException
     */
    @FXML
    public void insercionClientes() throws SQLException { //
        if(!nombreCompletoTxt.getText().isBlank()|| !direccionTxt.getText().isBlank() || !telefonoTxt.getText().isBlank()) {// mientras las casillas de datos no esten vacias
            pst = cn.prepareStatement("INSERT INTO CLIENTE (nombre_completo,direccion,telefono) VALUES (?,?,?)");// crear query
            pst.setString(1, nombreCompletoTxt.getText());// asignar valores de casilla a querry
            pst.setString(2, direccionTxt.getText());// asignar valores de casilla a querry
            pst.setString(3, telefonoTxt.getText());// asignar valores de casilla a querry
            pst.executeUpdate(); // ejecutar query
            mostrarClientes(); // volver a cargar los clientes de la lista
            error.setText(" ");//reiniciar el error
        }else{// si las casillas estan vacias
            error.setText("*Debe ingresar datos para poder guardar al nuevo cliente!");//mostrar que las casillas estan vacias
        }
    }

    /**
     * Esta funcion sirve para poder eliminar al cliente que el empleado desee, esta funcion crea la consulta de sql, luego pregunta por el id del cliente a eliminar
     * para asi posteriormente eliminarlo de la base de datos y quitar sus datos alamacenados en el programa
     * @throws SQLException
     */
    @FXML
    public void eliminarCliente() throws SQLException { // funcion para eliminar cliente de la base de datos
        pst = cn.prepareStatement("DELETE FROM CLIENTE WHERE id = ?"); // query para borrar  el record
        pst.setInt(1, Integer.parseInt(getId())); // mada el id a borrar
        pst.executeUpdate(); // ejecuta querry
        nombreCompletoTxt.setText(" "); // limpia el text feild
        direccionTxt.setText(" ");// limpia el text feild
        telefonoTxt.setText(" ");// limpia el text feild
        id = "0"; // reset id
        mostrarClientes(); // actualizar lista de clientes
    }

    @FXML
    public void actualziarCliente() throws SQLException { // funcion para modificar los clientes
        pst = cn.prepareStatement("UPDATE CLIENTE SET nombre_completo = ?, direccion = ?, telefono = ? WHERE id = ?"); // crear query a evaluar
        pst.setString(1, nombreCompletoTxt.getText()); // ocupa el parametro de la textfeild
        pst.setString(2,direccionTxt.getText());// ocupa el parametro de la textfeild
        pst.setString(3, telefonoTxt.getText());// ocupa el parametro de la textfeild
        pst.setInt(4, Integer.parseInt(getId()));// ocupa el parametro de la textfeild
        pst.executeUpdate(); // ejecuta la query
        mostrarClientes(); // actualiza la lista
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}// funcion de la implementacion de inizalitable en la clase (no es necesaria para el programa)

    @FXML
    private void RegistrarTarjeta(ActionEvent event) throws IOException { //funcion para el boton de registrar tarjeta, sirve para poder cambiar de escena a la siguiente la cual su funcionalidad es registrar tarjetas

        if(getId() != "0") {

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
