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

public class Tab_RegistrosController implements Initializable {



    @FXML
    private AnchorPane ancPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void RegistroCliente(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            AnchorPane view = loader.load();
            HelloController controller = loader.getController();
            try {
                controller.initialize();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ancPane.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
