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
   private AnchorPane ancPane;
    ResultSet rs;

    PreparedStatement pst;

    Connection cn = DB.getInstance();

    Statement st = cn.createStatement();

    private Parent root;
    private Scene scene;
    private Stage stage;



    @FXML
    public TextField numeroTarjetaTxt;

    @FXML
    public ChoiceBox facilitador;

    @FXML
    public DatePicker fechaExpiracion;

    @FXML
    public TextField idCliente;

    @FXML
    public ListView listaTarjeta;

    @FXML
    public Label error;

    private String id = "0";

    GeneradorTarjeta generadorTarjeta = new GeneradorTarjeta();


    public TarjetaController() throws SQLException {}

    @FXML
    public void initialize() throws SQLException {
        mostrarTarjetas();
        //setFacilitador();
        //generarNumeroTarjeta();
    }
    @FXML
    public void mostrarTarjetas() throws SQLException {
        listaTarjeta.getItems().clear();
        String query = "SELECT t.id,t.numero_tarjeta,t.fecha_expiracion, f.nombre " +
                "FROM TARJETA as t inner join FACILITADOR as f on t.FK_id_facilitador = f.id " +
                "WHERE t.FK_id_cliente = ?";

        pst = cn.prepareStatement(query);
        pst.setInt(1, Integer.parseInt(id));

        rs = pst.executeQuery();

        while(rs.next()){
            String record = rs.getInt("id") + "/" + rs.getString("numero_tarjeta") + "/" + rs.getDate("fecha_expiracion") + "/" + rs.getString("nombre") + "/";
            listaTarjeta.getItems().add(record);
        }

        listaTarjeta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String str = listaTarjeta.getSelectionModel().getSelectedItem().toString();
                String[] arrOfStr = str.split("/");
                int i = 1;
                for (String a : arrOfStr) {

                    if(i == 2 ){
                        numeroTarjetaTxt.setText(a);
                    }else if(i == 3 ){
                        fechaExpiracion.setValue( LocalDate.parse(a));
                    }
                    i++;
                }
            }
        });
    }

    public void agregarTarjeta() throws SQLException {
        if(!numeroTarjetaTxt.getText().isBlank() || fechaExpiracion.getValue() != null) {
            if (generadorTarjeta.isValid(Long.parseLong(numeroTarjetaTxt.getText()))) {
                pst = cn.prepareStatement("INSERT INTO TARJETA (numero_tarjeta,fecha_expiracion,FK_id_facilitador,FK_id_cliente) VALUES (?,?,?,?)");
                pst.setString(1, numeroTarjetaTxt.getText());
                pst.setDate(2, Date.valueOf(fechaExpiracion.getValue()));
                pst.setInt(3, generadorTarjeta.getFacilitador(Long.parseLong(numeroTarjetaTxt.getText())));
                pst.setInt(4, Integer.parseInt(idCliente.getText()));
                pst.executeUpdate();
                mostrarTarjetas();
                error.setText(" ");
            } else {
                error.setText("*Ingrese una tarjeta valida!");
            }
        }else{
            error.setText("*Debe ingresar los datos que se le piden en las casillas!");
        }
    }
    @FXML
    public void eliminarTarjeta() throws SQLException {
        pst = cn.prepareStatement("DELETE FROM TARJETA WHERE id = ?");
        pst.setInt(1, Integer.parseInt(idCliente.getText()));
        pst.executeUpdate();
        numeroTarjetaTxt.setText(" ");
        fechaExpiracion.setValue(null);
        mostrarTarjetas();
    }

    @FXML
    public void actualizarTarjeta() throws SQLException {
        if(generadorTarjeta.isValid(Long.parseLong(numeroTarjetaTxt.getText()))) {
            pst = cn.prepareStatement("UPDATE TARJETA SET numero_tarjeta = ?, fecha_expiracion = ?, FK_id_facilitador = ? WHERE id = ?");
            pst.setString(1, numeroTarjetaTxt.getText());
            pst.setDate(2, Date.valueOf(fechaExpiracion.getValue()));
            pst.setInt(3,generadorTarjeta.getFacilitador(Long.parseLong(numeroTarjetaTxt.getText())) );
            pst.setInt(4, Integer.parseInt(id));
            pst.executeUpdate();
            mostrarTarjetas();
            error.setText(" ");
        }else{
            error.setText("*Ingrese una tarjeta valida!");
        }
    }

    public void obtenerId(String id) throws SQLException {
        this.id = id;
        idCliente.setText(id);
        mostrarTarjetas();
    }

    /*public void setFacilitador() throws SQLException {
        rs = st.executeQuery("SELECT nombre FROM FACILITADOR ");
        while (rs.next()) {
            facilitador.getItems().add(rs.getString("nombre"));
        }
    }*/
    @FXML
    private void volver(ActionEvent event) throws IOException {
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
   /* @FXML
    public void generarNumeroTarjeta() {
        String nuevoNumeroTarjeta = GeneradorTarjeta.generadorTarjeta();
        numeroTarjetaTxt.setText(nuevoNumeroTarjeta);
        System.out.println(nuevoNumeroTarjeta);
    }*/
}

