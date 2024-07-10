package org.example.parcial3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteAController {
    @FXML
    public DatePicker dateFechaInicio; //00377223 id asignado al primer parametro del rango de fechas
    @FXML
    public DatePicker dateFechaFin; //00377223 id asignado al segundo parametro del rango de fechas
    @FXML
    public TextField txtIDUsuario; //00377223 id asignado al id del cliente
    @FXML
    public TextArea txtVistaPrevia; //00377223 id asignado a una vista previa del reporte

    Connection cn = DB.getInstance(); //00377223 se crea la conexion a la base de datos
    ResultSet rs; //00377223 sera el conjunto de datos encontrados, teniendo en cuenta la query
    PreparedStatement pst; //00377223 es la query que se manda a la base de datos
    String datosReporte; //00377223 cadena donde estaran los datos del reporte

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); //00377223 se crea un formato de fecha para guardar la fecha en el formato que se dio

    @FXML
    public void onMostrarReporte() throws SQLException {//00377223 se ejecuta cuando se presiona el boton Mostrar Reporte
        if (txtIDUsuario.getText().isEmpty() || dateFechaInicio == null || dateFechaFin == null) {//00377223 condicion por si los parametros estan vacios
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        }
        else {
            txtVistaPrevia.setText(reporte());//00377223 si todos los textos estan llenos, se puede mostrar el reporte
        }
    }

    @FXML
    public void onGuardarReporte() throws SQLException {//00377223 se encarga de guardar el reporte con la ruta que ya se instancio
        if (txtIDUsuario.getText().isEmpty() || dateFechaInicio == null || dateFechaFin == null) {//00377223 condicion por si los parametros estan vacios
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        } else {
            Date date = new Date(); //00377223 se crea un objeto de la fecha actual
            String ruta = "src/main/resources/Reportes/Reporte A - " + formatter.format(date) + ".txt"; //00377223 el reporte se guardara en la carpeta de Reportes y tendra en su nombre el tipo de reporte y la fecha y hora actual

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))){//00377223 se crea un buffer que tiene de parametro al reporte que queremos crear y guardar
                writer.write(reporte());//00377223 se escribe lo que hay en el reporte
            } catch (IOException e) {//00377223 por si no puede encontrar la ruta
                throw new RuntimeException(e);//00377223 causa del error
            }
            //00377223 el buffer se cierra solo

        txtVistaPrevia.clear();//00377223 se limpia la vista previa una vez ya se ha guardado el reporte
            txtIDUsuario.clear();//00377223 se limpia el txtField de id una vez ya se guardo el reporte
            dateFechaInicio.setValue(null);//00377223 resetea el dateFehaInicio una vez ya se guardo el reporte
            dateFechaFin.setValue(null);//00377223 resetea el dateFechaFin una vez ya se guardo el archivo
        }
    }

    public String reporte() throws SQLException {//00377223 regresa el reporte en cadena de texto, para las demas funciones
        pst = cn.prepareStatement("SELECT c.fecha_compra,c.dinero_gastado,c.descripcion\n" + //00377223 se selecciona los datos que seran parte del reporte
                "FROM " + //00377223 se seleccionara de:
                "CLIENTE cl\n" + //00377223 se declara un alias para la tabla CLIENTE
                "INNER JOIN TARJETA t ON cl.id = t.FK_id_cliente\n" + //00377223 se hace un inner join para tener el conjunto donde el id de CLIENTE y la fk del id cliente en TARJETA coincidan
                "INNER JOIN COMPRA c ON T.id = c.FK_id_tarjeta\n" + //00377223 se hace un inner join para tener el conjunto donde el id de TARJETA y la fk del id tarjeta en COMPRA coincidan y poder agarrar los datos del SELECT
                "WHERE cl.id = ?\n" +//00377223 con la condicion que el id de CLIENTE sea el que el reporte solicite
                "  AND c.fecha_compra BETWEEN ? AND ?\n;");//00377223 y tambien la condicion del rango de fechas que esta en la tabla COMPRA

        pst.setString(1, txtIDUsuario.getText());//00377223 se pasa el dato del id a la query que se creo
        pst.setString(2, dateFechaInicio.getValue().toString());//00377223 se pasa el dato de la primera fecha a la query que se creo
        pst.setString(3, dateFechaFin.getValue().toString());//00377223 se pasa el dato de la fecha limite a la query que se creo

        rs = pst.executeQuery(); //00377223 se ejecuta la query y los datos se guardan en esta variable

        String datosEnReporte = "ID Usuario: " + txtIDUsuario.getText() + " - Fecha de inicio: " + dateFechaInicio.getValue().toString() + " - Fecha de fin: " + dateFechaFin.getValue().toString() + ".\n\n";//00377223 se crea una string con los datos que se ingresaron para el reporte

        StringBuilder sb = new StringBuilder();//00377223 se crea un objeto StringBuilder que sera donde se guardaran todos los datos en cadena
        sb.append(datosEnReporte);//00377223 se agrega datosEnReporte para saber con que parametros se llego al reporte

        while (rs.next()) {//00377223 se recorreran todas la filas del resultado de la query
            datosReporte = "Fecha: " + rs.getString("fecha_compra") + ". Monto: $" + String.format("%.2f",rs.getDouble("dinero_gastado")) + ". Descripcion: " + rs.getString("descripcion") + ".\n"; //00377223 se guardan los datos en una cadena de texto
            sb.append(datosReporte);//00377223 se agregan al StringBuilder previamente creado
        }
        return sb.toString();//00377223 retornamos StringBuilder en un String
    }
}
