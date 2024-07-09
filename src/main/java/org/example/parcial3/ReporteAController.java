package org.example.parciaal3;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.*;

public class ReporteAController {
    @FXML
    public DatePicker dateFechaInicio;
    @FXML
    public DatePicker dateFechaFin;
    @FXML
    public TextField txtIDUsuario;
    @FXML
    public TextArea txtVistaPrevia;

    ResultSet rs;
    PreparedStatement pst;
    Connection cn = DB.getInstance();
    Statement st = cn.createStatement();
    String userData;
    String url = "resources/Reportes";


    public ReporteAController() throws SQLException {}

    @FXML
    public void mostrarReporte() throws SQLException {
        txtVistaPrevia.setText(reporte());
    }



    public String reporte() throws SQLException {
        pst = cn.prepareStatement("SELECT c.fecha_compra,c.dinero_gastado,c.descripcion\n" +
                "FROM CLIENTE cl\n" +
                "INNER JOIN TARJETA t ON cl.id = t.FK_id_cliente\n" +
                "INNER JOIN COMPRA c ON T.id = c.FK_id_tarjeta\n" +
                "WHERE cl.id = ?\n" +
                "  AND c.fecha_compra BETWEEN ? AND ?\n;");

        pst.setString(1, txtIDUsuario.getText());
        pst.setString(2, dateFechaInicio.getValue().toString());
        pst.setString(3, dateFechaFin.getValue().toString());

        rs = pst.executeQuery();

        StringBuilder sb = new StringBuilder();

        while (rs.next()) {
            userData = rs.getString("fecha_compra") + " $" + rs.getString("dinero_gastado") + " - " + rs.getString("descripcion") + ".\n";
            sb.append(userData);
        }

        return sb.toString();
    }

}
