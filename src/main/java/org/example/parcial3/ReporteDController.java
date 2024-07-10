package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ReporteDController {

    @FXML
    private AnchorPane ancPane;
    @FXML
    public TextArea txtVistaPrevia;//00377223 id asignado a vista previa
    @FXML
    public ChoiceBox cbFacilitador;//00377223 id asignado al facilitador

    Connection cn = DB.getInstance(); //00377223 se crea la conexion a la base de datos
    ResultSet rs; //00377223 sera el conjunto de datos encontrados, teniendo en cuenta la query
    PreparedStatement pst; //00377223 es la query que se manda a la base de datos
    String datosReporte; //00377223 cadena donde estaran los datos del reporte
    Statement st = cn.createStatement(); //00377223 es una query que se manda a la base de datos al iniciar el programa

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); //00377223 se crea un formato de fecha para guardar la fecha en el formato que se dio

    public ReporteDController() throws SQLException {} //00377223 sirve para que al inicializar el codigo ya cuente con una SQLException por default

    public void initialize() throws SQLException { //00377223 inicializacion del programa
        setFacilitador(); //00377223 sirve para setear el ChoiceBix
    }

    public void setFacilitador() throws SQLException { //00377223 sirve para setear el choiceBox
        rs = st.executeQuery("SELECT nombreFacilitador FROM FACILITADOR "); //00377223 selecciona los nombres de los facilitadores
        while (rs.next()) {//00377223 itera los nombres de los facilitadores
            cbFacilitador.getItems().add(rs.getString("nombreFacilitador"));//00377223 los guarda en el choice box
        }
    }

    @FXML
    public void onMostrarReporte() throws SQLException {//00377223 se ejecuta cuando se presiona el boton Mostrar Reporte
        if (cbFacilitador == null) {//00377223 condicion por si los parametros no estan
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        }else {
            txtVistaPrevia.setText(reporte());//00377223 si todos los textos estan llenos, se puede mostrar el reporte
        }
    }

    @FXML
    public void onGuardarReporte() throws SQLException {//00377223 se encarga de guardar el reporte con la ruta que ya se instancio
        if (cbFacilitador == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);//00377223 el tipo de alerta es de un error
            alert.setTitle("Error");//00377223 se setea un titulo para la alerta
            alert.setHeaderText("Rellene todos los datos.");//00377223 texto que se encuentra dentro de la caja de error
            alert.showAndWait();//00377223 muestra la caja de error en pantalla
        }else {
            Date date = new Date(); //00377223 se crea un objeto de la fecha actual
            String ruta = "src/main/resources/Reportes/Reporte D - " + formatter.format(date) + ".txt"; //00377223 el reporte se guardara en la carpeta de Reportes y tendra en su nombre el tipo de reporte y la fecha y hora actual

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {//00377223 se crea un buffer que tiene de parametro al reporte que queremos crear y guardar
                writer.write(reporte());//00377223 se escribe lo que hay en el reporte
            } catch (IOException e) {//00377223 por si no puede encontrar la ruta
                throw new RuntimeException(e);//00377223 causa del error
            }
            //00377223 el buffer se cierra solo

            txtVistaPrevia.clear();//00377223 se limpia la vista previa una vez ya se ha guardado el reporte
            cbFacilitador.setValue(null);//00377223 se limpia la choicebox una vez ya se ha guardado el reporte
        }

    }

    public String reporte() throws SQLException {//00377223 regresa el reporte en cadena de texto, para las demas funciones
        pst = cn.prepareStatement("SELECT cl.id AS ID,\n" + //00377223 se selecciona el id del cliente
                "    COUNT(co.id) AS CantidadCompras,\n" + //00377223 se cuenta el numero de compras que ha realizado un cliente
                "    SUM(co.dinero_gastado) AS TotalGastado\n" + //00377223 se suma todo el dinero que ha gastado el cliente
                "FROM \n" + //00377223 se selecciona de:
                "    FACILITADOR as f\n" + //00377223 se declara un alias para FACILITADOR
                "INNER JOIN TARJETA as t ON f.id = t.FK_id_facilitador\n" + //00377223 se hace un inner join para tener el conjunto donde el id de FACILITADOR y la fk del id facilitador en TARJETA coincidan
                "INNER JOIN CLIENTE as cl ON t.FK_id_cliente = cl.id\n" + //00377223 se hace un inner join para tener el conjunto donde el id de CLIENTE y la fk del id cliente en TARJETA coincidan
                "INNER JOIN COMPRA as co ON t.id = co.FK_id_tarjeta\n" + //00377223 se hace un inner join para tener el conjunto donde el id de TARJETA y la fk del id tarjeta en TARJETA coincidan
                "INNER JOIN TIPO_TARJETA as tt ON t.FK_id_tipo_tarjeta = tt.id\n" + //00377223 se hace un inner join para tener el conjunto donde el id de FACILITADOR y la fk del id facilitador en COMPRA coincidan
                "WHERE \n" + //00377223 con la condicion:
                "    f.nombreFacilitador = ?\n" + //00377223 de un nombre de facilitador
                "GROUP BY \n" + //00377223 agrupados por
                "    cl.id;"); //00377223 la identificacion del usuario

        pst.setString(1, cbFacilitador.getValue().toString());//00377223 se pasa el dato del id a la query que se creo

        rs = pst.executeQuery(); //00377223 se ejecuta la query y los datos se guardan en esta variable

        String datosEnReporte = "Facilitador: " + cbFacilitador.getValue().toString() + ".\n\n";//00377223 se crea una string con los datos que se ingresaron para el reporte

        StringBuilder sb = new StringBuilder();//00377223 se crea un objeto StringBuilder que sera donde se guardaran todos los datos en cadena
        sb.append(datosEnReporte);//00377223 se agrega datosEnReporte para saber con que parametros se llego al reporte

        while (rs.next()) {//00377223 se recorreran todas la filas del resultado de la query
            datosReporte = "ID Usuario: " + rs.getString("ID") + ". Compras realizadas: " + rs.getString("CantidadCompras") + ". Total Gastado: $" + String.format("%.2f",rs.getDouble("TotalGastado")) + ".\n"; //00377223 se guardan los datos en una cadena de texto
            sb.append(datosReporte);//00377223 se agregan al StringBuilder previamente creado
        }
        return sb.toString();//00377223 retornamos StringBuilder en un String
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
