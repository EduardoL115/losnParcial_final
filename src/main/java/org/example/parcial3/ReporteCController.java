package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReporteCController {

    @FXML
    public TextField txtIDUsuario;//00377223 id asignado al id del cliente
    @FXML
    public TextArea txtVistaPrevia;//00377223 id asignado a la vista previa
    @FXML
    public AnchorPane ancPane;

    ResultSet rs; //00377223 sera el conjunto de datos encontrados, teniendo en cuenta la query
    PreparedStatement pst; //00377223 es la query que se manda a la base de datos
    Connection cn = DB.getInstance();  //00377223 se crea la conexion a la base de datos

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); //00377223 se crea un formato de fecha para guardar la fecha en el formato que se dio

    @FXML
    public void onMostrarReporte() throws SQLException {//00377223 se ejecuta cuando se presiona el boton Mostrar Reporte
        if (txtIDUsuario.getText().isEmpty()) {//00377223 condicion por si los parametros estan vacios
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        }
        else {
            txtVistaPrevia.setText(reporte());//00377223 coloca el reporte dentro del txtArea de vista previa
        }
    }

    @FXML
    public void onGuardarReporte() throws SQLException {//00377223 se encarga de guardar el reporte con la ruta que ya se instancio
        if (txtIDUsuario.getText().isEmpty()) {//00377223 condicion por si los parametros estan vacios
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        }
        else {
            Date date = new Date(); //00377223 se crea un objeto de la fecha actual
            String ruta = "src/main/resources/Reportes/Reporte C - " + formatter.format(date) + ".txt"; //00377223 el reporte se guardara en la carpeta de Reportes y tendra en su nombre el tipo de reporte y la fecha y hora actual

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))){//00377223 se crea un buffer que tiene de parametro al reporte que queremos crear y guardar
                writer.write(reporte());//00377223 se escribe lo que hay en el reporte
            } catch (IOException e) {//00377223 por si no puede encontrar la ruta
                throw new RuntimeException(e);//00377223 causa del error
            }
            //00377223 el buffer se cierra solo

            txtVistaPrevia.clear();//00377223 se limpia la vista previa una vez ya se ha guardado el reporte
            txtIDUsuario.clear();//00377223 se limpia el txtField de id una vez ya se guardo el reporte
        }
    }

    public String reporte() throws SQLException {//00377223 regresa el reporte en cadena de texto, para las demas funciones
        pst = cn.prepareStatement("SELECT t.numero_tarjeta, tt.nombreTipoTarjeta FROM TARJETA as t INNER JOIN TIPO_TARJETA as tt ON t.FK_id_tipo_tarjeta = tt.id inner join CLIENTE as c on t.FK_id_cliente = c.id WHERE c.id = ?");//se hace una consulta sql para obtener el numero de tarjeta y elm nombre

        pst.setString(1, txtIDUsuario.getText());//00377223 se pasa el dato del id a la query que se creo

        rs = pst.executeQuery(); //00377223 se ejecuta la query y los datos se guardan en esta variable

        List<String> credito = new ArrayList<>(); //00377223 en esta lista se guardaran las tarjetas de credito
        List<String> debito = new ArrayList<>(); //00377223 en esta lista se guardaran las tarjetas de debito

        while (rs.next()) {//00377223 se recorreran todas la filas del resultado de la query
            String numTarjeta = rs.getString("numero_tarjeta");//00377223 se guarda el numero de tarjeta de la iteracion
            String tipoTarjeta = rs.getString("nombretipoTarjeta");//00377223 se guarda el tipo de tarjeta de la iteracion

            String tarjetaCensurada = "";//00377223 aqui se guardara la tarjeta ya censurada
            for (int i=0; i<(numTarjeta.length() - 4); i++) {//00377223 se hace un recorrido hasta antes de llegar a los ultimos 4 digitos
                tarjetaCensurada += "X";//00377223se censura la longitud de la tarjeta menos los ultimos 4 numeros
                i++;//00377223 se suma el iterador
            }
            tarjetaCensurada += numTarjeta.substring(numTarjeta.length() - 4);//00377223 a la tarjeta censurada se le agregan los ultimos 4 valores de la tarjeta original
            if (tipoTarjeta.equals("Credito")){//00377223 condicion para tarjetas tipo Credito
                credito.add(tarjetaCensurada);//00377223 se agrega a la lista que se creo antes
            }
            else if (tipoTarjeta.equals("Debito")){//00377223 condicion para tarjetas tipo Debito
                debito.add(tarjetaCensurada);//00377223 se agrega a la lista que se creo antes
            }
        }
        StringBuilder sb = new StringBuilder();//00377223 en este StringBuilder se guardaran los datos a retornar

        String datosEnReporte = "ID Usuario: " + txtIDUsuario.getText() + ".\n";//00377223 se crea una string con los datos que se ingresaron para el reporte
        sb.append(datosEnReporte);//00377223 se agrega el ID correspondiente a lo que se solicito en la cadena que se retornara

        sb.append("\nTarjetas de credito:\n");//00377223 se declaran las tarjetas de credito en la cadena
        if (credito.isEmpty())//00377223 si el array esta vacio:
        {
            sb.append("N/A\n");//00377223 se retorna N/A cuando no hay nada
        }else {//00377223 sino, hay datos
            for (String tarjeta : credito){//00377223 se recorre la lista de credito
                sb.append(tarjeta).append("\n");//00377223 se agregan las tarjetas encontradas
            }
        }

        sb.append("\nTarjetas de debito:\n");//00377223 se declaran las tarjetas de debito en la cadena
        if (debito.isEmpty())//00377223 si el array esta vacio:
        {
            sb.append("N/A\n");//00377223 se retorna N/A cuando no hay nada
        } else {//00377223 sino, hay datos
            for (String tarjeta : debito){//00377223 se recorre la lista de debito
                sb.append(tarjeta).append("\n");//00377223 se agregan las tarjetas encontradas
            }
        }
        return sb.toString();//se retorna toda la cadena
    }
    @FXML
    private void volver(ActionEvent event) throws IOException { // accion de el botton  volver
        try { // craga nuevo fxml de donde piensa cambiar
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Tab_Reportes.fxml")));// asigna a loader el url del fxml
            AnchorPane view = loader.load();// asigna a view el fxml que se cargo

            ancPane.getChildren().setAll(view);// cambia el anchor pane por los children de view
        } catch (IOException e) {// no se encontro el fxml
            throw new RuntimeException(e); // muestra el error al no conectar
        }
    }
}
