package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class CompraController {
    @FXML
    private AnchorPane ancPane;// id de anchor pane utilizado en el fxml
    ResultSet rs; // devuelve valores de base de datos tras una query

    PreparedStatement pst; // donde se manda el stement

    Connection cn = DB.getInstance(); // revisa coneccion al singletonv

    Statement st = cn.createStatement(); //es donde se guarda la query

    private String id = "0", idCompra;// variable id que es la del cliente, variable idCompra que obtiene el id de la compra para uso posterior

    @FXML
    public ListView listaCompras, listaTarjetaVer;//dos listviews, uno para enseñar las compras y otro para las tarjetas disponibles

    @FXML
    public TextField idTarjeta, descripcionCompra, precio;//cuadros de textos para enseñar y guardar los datos ingresados

    @FXML
    public DatePicker fechaCompra;//cuadro para poder escoger el dia de la compra

    public CompraController() throws SQLException {}//constructor

    @FXML
    public void initialize() throws SQLException {//funcion para poder incializar el programa
        mostrarCompras();//se llama la funcion para poder llenar por defecto la lista
        mostrarTarjetas();//se llama la funcion para poder llenar por defecto la lista
    }
    @FXML
    public void mostrarTarjetas() throws SQLException { // muestra la tarjeta de el cliente selecionado
        listaTarjetaVer.getItems().clear(); // la lista se limpia para poner nuevos datos
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

            listaTarjetaVer.getItems().add(record); // muestra los resultados en la lista
        }
        listaTarjetaVer.setOnMouseClicked(new EventHandler<MouseEvent>() { // activa un on mouse click event para los miembros de la lista
            @Override
            public void handle(MouseEvent mouseEvent) { // funcion acargo de ejecutar los clicks en un mouse

                String str = listaTarjetaVer.getSelectionModel().getSelectedItem().toString(); // revisa donde se dio click lo selecciona y lo cambia a string
                String[] arrOfStr = str.split("/"); // crea un array de string y separa el item de la lista separandolo en diferentes string cuando encuentra un /
                int i = 1;//inicia el contador en 1
                for (String a : arrOfStr) {//se un for each para ir por todo el array de strings que se divide
                    if(i == 1) {//si es 1, o sea el id que entre
                        idTarjeta.setText(a);//guarda el id obtenido por la accion
                    }
                    i++;//se incrementa el contador
                }
            }
        });
    }

    public void mostrarCompras() throws SQLException {//funcion para poder mostrar todas las compras del cliente en el listview
        listaCompras.getItems().clear();//funcion para poder limpiar la lista anterior y enseñar la nueva
        String query = "SELECT c.id ,c.fecha_compra, c.dinero_gastado, c.descripcion, c.FK_id_tarjeta FROM COMPRA as c inner join TARJETA as t on c.FK_id_tarjeta = t.id  inner join CLIENTE as cl on t.FK_id_cliente = cl.id WHERE cl.id = ?"; //este string guarda la consulta sql para asi poder usarla en el preparesStatement

        pst = cn.prepareStatement(query); // prepara el sql stament y lo manda
        pst.setInt(1, Integer.parseInt(id)); // muestra especificamente los valores con el id

        rs = pst.executeQuery(); // manda la query
        while(rs.next()){ // while hayan valores en los resultado

            String record = rs.getInt("id") + "/" //funcion para obtener el id de la compra de la abse de datos
                    + rs.getDate("fecha_compra") + "/" //funcion para obtener la fecha de la compra de la compra de la abse de datos
                    + rs.getBigDecimal("dinero_gastado") + "/" //funcion para obtener el dinero gastado de la compra de la compra de la abse de datos
                    + rs.getString("descripcion") + "/" //funcion para obtener la descripcion de la compra de la compra de la abse de datos
                    + rs.getString("FK_id_tarjeta") + "/"; //funcion para obtener la llave foranea de la tarjeta usada
            listaCompras.getItems().add(record); //esta funcion sirve para poder guardar el string en el listview

        }

        listaCompras.setOnMouseClicked(new EventHandler<MouseEvent>() {//funcion para cuando el usuario clickee en alguna fila se pueda obtener los datos
            @Override
            public void handle(MouseEvent mouseEvent) {//funcion para cuando se este clickeando
                String str = listaCompras.getSelectionModel().getSelectedItem().toString(); // revisa donde se dio click lo selecciona y lo cambia a string
                String[] arrOfStr = str.split("/"); // crea un array de string y separa el item de la lista separandolo en diferentes string cuando encuentra un /
                int i = 1;// inicia un contador
                for (String a : arrOfStr) {  // recore el array de strings
                    if(i == 1) { //si es 1 el contador, significa que el id esta en la posicion
                        idCompra = a;//se guarda el id en la variable para uso posterior
                    }else if(i == 2 ){//si es 2 entonces en la posicion esta la fecha de compra
                        fechaCompra.setValue(LocalDate.parse(a));//se guarda la fecha en el cuadro de fecha para mejor visualizacion
                    }else if(i == 3 ){//si es 3 entonces en la posicion esta el dinero gastado en la compra
                        precio.setText(a);//se agrega el precio en el cuadro de texto para mejor visualizacion o edicion
                    }else if(i == 4){//si es 4 entonces en la posicion esta la desrcipcion de la compra
                        descripcionCompra.setText(a);//se guarda la descripcion en el cuadro de texto para mejor visualizcion o edicion
                    }else if(i == 5){//si es 5 entonces en la posicion esta el id de la tarjeta
                        idTarjeta.setText(a);//se guarda el id de la tarjeta en mi
                    }
                    i++;//sube el contador
                }
            }
        });
    }

    @FXML
    public void agregarCompra() throws SQLException {//funcion para incertar nueva tarjeta
        pst = cn.prepareStatement("INSERT INTO COMPRA (fecha_compra,dinero_gastado,descripcion,FK_id_tarjeta) VALUES (?,?,?,?)");//se crea la consulta para poder insertar los valores a la base de datos
        pst.setDate(1, Date.valueOf(fechaCompra.getValue()));//se obtiene la fecha de compra para guardarla
        pst.setBigDecimal(2, new BigDecimal(precio.getText()) );//se obtiene el dinero gastado de la compra para guardarla
        pst.setString(3, descripcionCompra.getText());//se obtiene la descripcion de compra para guardarla
        System.out.println(idTarjeta.getText());
        int u = Integer.parseInt(idTarjeta.getText());
        pst.setInt(4, u);//se obtiene el id de la tarjeta de compra para guardarla
        pst.executeUpdate();//se ejecuta la consulta creada
        mostrarCompras();//se vuelve a llamar a la funcion para reiniciar la lista
    }

    @FXML
    public void eliminarCompra() throws SQLException {//esta funcion sirve para poder eliminar compras en la base de datos
        pst = cn.prepareStatement("DELETE FROM COMPRA WHERE id = ?");// se crea la consulta sql para pode eliminar
        pst.setInt(1, Integer.parseInt(idCompra));// se da el id de la compra para indicarle a la consulta cual compra debe eliminarse
        pst.executeUpdate();//se ejecuta la consulta
        descripcionCompra.setText(" ");//se póne en blanco el cuadro de texto para eliminar los rastros
        fechaCompra.setValue(null);//se póne en blanco el cuadro de fecha para eliminar los rastros
        precio.setText(" ");//se póne en blanco el cuadro de texto para eliminar los rastros
        idTarjeta.setText(" ");//se póne en blanco el cuadro de texto para eliminar los rastros
        mostrarCompras();//se llama la funcion para poder reiniciar la lista
    }

    @FXML
    public void actualizarCompra() throws SQLException {//esta funcion sirve para poder actualizar los valores de un cliente seleccionado
        pst = cn.prepareStatement("UPDATE COMPRA SET fecha_compra = ?, dinero_gastado = ?, descripcion = ?, FK_id_tarjeta = ? WHERE id = ?");//se crea la consulta sql para poder actualziar los datos del cliente
        pst.setDate(1, Date.valueOf(fechaCompra.getValue()));//se obtienen los datos de la fecha
        pst.setString(2, precio.getText());//se obtienen los datos del dinero gastado
        pst.setString(3,descripcionCompra.getText());//se obtienen los datos de la descripcion
        pst.setInt(4, Integer.parseInt(idTarjeta.getText()));//se obtienen los datos del id de la tarjeta
        pst.setInt(5, Integer.parseInt(idCompra));//se obtienen los datos del id de la compra
        pst.executeUpdate();//se ejecuta la consulta
        mostrarCompras();//se reinicia la lista
    }

    public void obtenerId(String id) throws SQLException {//esta funcion obtiene el id del cliente y de paso llama a las funciones para inicializar las listas
        this.id = id;//se obtiene el id del cliente obtenido anteriormente para usarse en las consultas
        mostrarCompras();//se crea la lista con los datos
        mostrarTarjetas();//se crea la lista con los datos
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
