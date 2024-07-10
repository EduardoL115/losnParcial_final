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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TarjetaController {

    @FXML
    private AnchorPane ancPane;// id de anchor pane utilizado en el fxml
    ResultSet rs; // devuelve valores de base de datos tras una query

    PreparedStatement pst; // donde se manda el stement

    Connection cn = DB.getInstance(); // revisa coneccion al singletonv

    Statement st = cn.createStatement(); //es donde se guarda la query


    @FXML
    public TextField numeroTarjetaTxt; // id de una texfeild ocupada

    @FXML
    public DatePicker fechaExpiracion; //id de un date picker

    @FXML
    public TextField idCliente; // id de una text feil

    @FXML
    public ListView listaTarjeta; // id de la list view

    @FXML
    public Label error; // label mostrados con errores

    @FXML
    private ChoiceBox tipoTarjeta; // id choice box ocupado

    private String id = "0",idTarjeta;  // guradan id de cliente y id de tarjeta

    GeneradorTarjeta generadorTarjeta = new GeneradorTarjeta(); // objeto de la verificacion de la tarjeta

    public TarjetaController() throws SQLException {} //constructor

    @FXML
    public void initialize() throws SQLException { // funcion para comenzar datos en le pantalla
        mostrarTarjetas();// llama funcion mostrar tarjeta
        setTipoTarjeta(); // llama funcion el tipo de tarjeta

    }
    @FXML
    public void mostrarTarjetas() throws SQLException { // muestra la tarjeta de el cliente selecionado
        listaTarjeta.getItems().clear(); // la lista se limpia para poner nuevos datos
        String query = "SELECT t.id,t.numero_tarjeta,t.fecha_expiracion, f.nombreFacilitador, tt.nombreTipoTarjeta " +
                "FROM TARJETA as t inner join FACILITADOR as f on t.FK_id_facilitador = f.id inner join TIPO_TARJETA as tt on t.FK_id_tipo_tarjeta = tt.id " +
                "WHERE t.FK_id_cliente = ?"; // query a pasar al la base de datos

        pst = cn.prepareStatement(query); // prepara el sql stament y lo manda
        pst.setInt(1, Integer.parseInt(id)); // muestra especificamente los valores con el id

        rs = pst.executeQuery(); // manda la query

        while(rs.next()){ // while hayan valores en los resultado

            String record = rs.getInt("id") + "/" + rs.getString("numero_tarjeta") + "/"// consigue id  y numero de tarjeta
                    + rs.getDate("fecha_expiracion") + "/"// consigue la fecha de expiracion de la tarjeta
                    + rs.getString("nombreFacilitador") + "/"//consigue el nombre de facilitador
                    + rs.getString("nombreTipoTarjeta") + "/"; // consigue el nombre de la tarjeta

            listaTarjeta.getItems().add(record); // muestra los resultados en la lista
        }
        listaTarjeta.setOnMouseClicked(new EventHandler<MouseEvent>() { // activa un on mouse click event para los miembros de la lista
            @Override
            public void handle(MouseEvent mouseEvent) { // funcion acargo de ejecutar los clicks en un mouse

                String str = listaTarjeta.getSelectionModel().getSelectedItem().toString(); // revisa donde se dio click lo selecciona y lo cambia a string
                String[] arrOfStr = str.split("/"); // crea un array de string y separa el item de la lista separandolo en diferentes string cuando encuentra un /
                int i = 1;// inicia un contador
                for (String a : arrOfStr) {  // recore el array de strings
                    if(i == 1) { // si el contador es 1
                        idTarjeta = a;//muestra en el text feild idtarjeta el id
                    }else if(i == 2 ){ // si el contador es 2
                        numeroTarjetaTxt.setText(a); // muestra el text field numero de tarjeta el numero de tarjeta
                    }else if(i == 3 ){// si el contador es 3
                        fechaExpiracion.setValue( LocalDate.parse(a));//muestra la fecha en el date picker
                    }else if(i == 5){ // sie el contador es 5
                        tipoTarjeta.setValue(a);// muestra el tipo de tarjeta en el choice box
                    }
                    i++;// aumenta el contador
                }
            }
        });
    }

    @FXML
    public void agregarTarjeta() throws SQLException { //funcion para incertar nueva tarjeta
        if ((!numeroTarjetaTxt.getText().isBlank() || fechaExpiracion.getValue() != null) && !existe(numeroTarjetaTxt.getText())) { //si las casillas de datos estan nulas y la tarjeta no existe
            if (generadorTarjeta.isValid(Long.parseLong(numeroTarjetaTxt.getText()))) { // revisa si la tarjeta es valida
                pst = cn.prepareStatement("INSERT INTO TARJETA (numero_tarjeta,fecha_expiracion,FK_id_facilitador,FK_id_cliente,FK_id_tipo_tarjeta) VALUES (?,?,?,?,?)"); // prepara una query para agregar tarjeta
                pst.setString(1, numeroTarjetaTxt.getText());// ingresa el valor de el teextfeild numero de tarjeta
                pst.setDate(2, Date.valueOf(fechaExpiracion.getValue())); // ingrasa valor de fecha de expiracion
                pst.setInt(3, generadorTarjeta.getFacilitador(Long.parseLong(numeroTarjetaTxt.getText()))); // ingresa el facilitador de tarjeta
                pst.setInt(4, Integer.parseInt(idCliente.getText())); // asigna cliente a la tarjeta
                pst.setInt(5, getTipoTarjetaId()); // asigna el tipo de tarjeta
                pst.executeUpdate();// updates el statement
                mostrarTarjetas(); // llama la funcion mostrar tabla actualizada
                error.setText(" ");// reinicia el error
            } else { // si todas casillas estan vacias y no existe
                error.setText("*Ingrese una tarjeta valida!");// imprimir erorr de tarjeta no valida
            }
        } else {
            error.setText("*Debe ingresar los datos que se le piden en las casillas!"); // imprime error de llenar
        }
    }
    @FXML
    public void eliminarTarjeta() throws SQLException { //funcion para eliminar una tarjeta de la base de datos
        try {
            pst = cn.prepareStatement("DELETE FROM TARJETA WHERE id = ?"); // preparacion para el query y borrar datos
            pst.setInt(1, Integer.parseInt(idTarjeta)); // manda el id donde borrar datos
            System.out.println(idTarjeta);
            pst.executeUpdate(); // ejecuta la query
            numeroTarjetaTxt.setText("");// limpia el texfeild
            fechaExpiracion.setValue(null);// limpia la fecha de expiracion
            tipoTarjeta.setValue("");// limpia el textfeild
            mostrarTarjetas();// muestra la lista con el update de tarjetas
        }catch(SQLException e){
            error.setText("*No puede eliminar una tarjeta asociada a una compra");
        }
    }

    @FXML
    public void actualizarTarjeta() throws SQLException { // funcion para actualizar los datos de la tarjeta o cambiarlos
        if(generadorTarjeta.isValid(Long.parseLong(numeroTarjetaTxt.getText()))) { // revisa que el valor de la tarjeta sea valida
            pst = cn.prepareStatement("UPDATE TARJETA SET numero_tarjeta = ?, fecha_expiracion = ?, FK_id_facilitador = ?, FK_id_tipo_tarjeta = ? WHERE id = ?"); // preparacion del query para cambiar el rocord en la base de datos
            pst.setString(1, numeroTarjetaTxt.getText()); // agara valor de el textfeild numero tarjeta
            pst.setDate(2, Date.valueOf(fechaExpiracion.getValue()));// utiliza valor de Facha expiracion
            pst.setInt(3,generadorTarjeta.getFacilitador(Long.parseLong(numeroTarjetaTxt.getText())) );// verifica y utiliza el valor de la tarjeta
            pst.setInt(4, getTipoTarjetaId());// cambia el id de la tarjeta por lo de la text field
            pst.setInt(5, Integer.parseInt(idTarjeta)); // utiliza la combo box para recibir el valor del tipo de tarjeta
            pst.executeUpdate(); // hace la query con los nuevos datos
            mostrarTarjetas(); // muestra la update de la lista de tarjeta
            error.setText(" ");//reinicia la lista y muestra su update
        }else{ // si la tarjeta no es valida
            error.setText("*Ingrese una tarjeta valida!"); //  muestra el error en un label
        }
    }

    public void obtenerId(String id) throws SQLException { // obtiene id de otro controller
        this.id = id;// asigna un valor a id
        idCliente.setText(id);// cambia el valor del text feild  a el id
        mostrarTarjetas(); // actualiza las tajetas
    }

    @FXML
    public void setTipoTarjeta() throws SQLException { // funcion para set el choice box
        rs = st.executeQuery("SELECT nombreTipoTarjeta FROM TIPO_TARJETA ");// busca llos tipos de tarjeta
        while (rs.next()) { // mientras hayan datos de la query
            tipoTarjeta.getItems().add(rs.getString("nombreTipoTarjeta")); // pone los valores de la combo box
        }
    }

    @FXML
    public int getTipoTarjetaId() { // regresa el tipo de tarjeta que es en el combo box
        if (Objects.equals(tipoTarjeta.getValue().toString(), "Credito")) { // comparacion entre num tarjeta de base de datos y el valor ingresado
            System.out.println("credito");
            return 1;// return id tipo tarjeta
        } else {
            System.out.println("debito");
            return 2;// return id tipo tarjeta
        }
    }

    public boolean existe(String numTarjeta) throws SQLException { //revisa si la tarjeta existe en la base de datos
        pst = cn.prepareStatement("SELECT numero_tarjeta FROM TARJETA WHERE FK_id_cliente = ?"); // prepara un querry
        pst.setInt(1, Integer.parseInt(id));// parametors de el query
        rs = pst.executeQuery();// ejecuta el query y regresa tabla
        while (rs.next()){ // mientra lo tabla tenga valores
            if(Objects.equals(rs.getString("numero_tarjeta"), numTarjeta)){ // si el numero de la tarjeta es gual a la base de datos return true
                return true;// regresa que la tarjeta existe
            }

        }
        return false;
    }

    @FXML
    private void volver(ActionEvent event) throws IOException { // accion de el botton  volver
        try { // craga nuevo fxml de donde piensa cambiar
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));// asigna a loader el url del fxml
            AnchorPane view = loader.load();// asigna a view el fxml que se cargo
            HelloController controller = loader.getController(); // inicia un objeto de el controlador del fxml que se cargo
            try {// intenta conectar a la base de datos
                controller.initialize();// inicia listas y otros valores en el fxml antes del cambio
            }catch (SQLException e) {// error al conectarse a la base de datos
                throw new RuntimeException(e);// muestra error
            }
            ancPane.getChildren().setAll(view);// cambia el anchor pane por los children de view
        } catch (IOException e) {// no se encontro el fxml
            throw new RuntimeException(e); // muestra el error al no conectar
        }
    }
}

