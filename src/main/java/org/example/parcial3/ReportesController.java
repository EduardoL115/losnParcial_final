package org.example.parcial3;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;


public class ReportesController {//00377223 controlador de Tab_Reportes

    @FXML
    private AnchorPane ancPane; //00377223 id asignado al AnchorPane que es la ventana de Tab_Reportes en Inicio


    @FXML
    public void reporteA(ActionEvent actionEvent) throws IOException { //00377223 cambio de Tab_Reportes a Reporte A en el mismo lugar
        try { //00377223 try para encontrar el fxml
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reporteA.fxml")));//00377223 le asigna view a la ruta de reporteA.fxml
            ancPane.getChildren().setAll(view);//00377223 muestra view donde esta Tab_Reportes
        } catch (IOException e) {//00377223 por si no se encuentra el fxml
            throw new RuntimeException(e);//00377223 si hay error lo muestra
        }
    }

    @FXML
    public void reporteB(ActionEvent actionEvent) throws IOException { //00377223 cambio de Tab_Reportes a Reporte B en el mismo lugar
        try { //00377223 try para encontrar el fxml
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reporteB.fxml")));//00377223 le asigna view a la ruta de reporteB.fxml
            ancPane.getChildren().setAll(view);//00377223 muestra view donde esta Tab_Reportes
        } catch (IOException e) {//00377223 por si no se encuentra el fxml
            throw new RuntimeException(e);//00377223 causa del error
        }
    }


}
