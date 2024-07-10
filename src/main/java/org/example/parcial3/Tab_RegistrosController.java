package org.example.parcial3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Tab_RegistrosController implements Initializable {//controlador de fxml para tab registros



    @FXML
    private AnchorPane ancPane; // id de anchor pane del fxml al cual este controlador esta relecionado



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}//parte de la interfaz Initializable (no se ocupa)

    @FXML
    private void RegistroCliente(ActionEvent event) throws IOException { // cambia el children relacionado al anchor pane por los de otro fxml
        try {// try encontrar el fxml intentando ser cargado
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));// carga el fxml al del cual se quieren encontrar sus "children"
            AnchorPane view = loader.load();// crea instancia de tipo Anchorpane donde guardar el fxml cargado
            HelloController controller = loader.getController();// crea instancia de otro controlador pasando el controlador del fxml ya cargado
            try {// try usar una funcion del otro controlador
                controller.initialize();//funcion del otro controlador que pone los datos en pantalla
            }catch (SQLException e) { // catch error al cargar los datos de la bas de datos
                throw new RuntimeException(e);// muestra el error
            }
            ancPane.getChildren().setAll(view);//cambia el anchor pane de el fxml en uso por el fxml cargado
        } catch (IOException e) {//catch en ecaso no se encuentre el fxml siendo cargado
            throw new RuntimeException(e);//creaciuon de error
        }
    }


}
