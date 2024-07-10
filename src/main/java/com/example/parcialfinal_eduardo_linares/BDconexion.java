package com.example.parcialfinal_eduardo_linares;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class BDconexion{

    private static volatile Connection cn = null;

    public static Connection getInstance() {
        if (cn == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                String url = "jdbc:sqlserver://LAPTOP-LUXIE\\MSSQLSERVER:1433;databaseName=ParcialFinalPOO_Eduardo_Linares;integratedSecurity=true;encrypt=false;";

                cn = DriverManager.getConnection(url);

                Statement st = cn.createStatement();

                System.out.println("Conexion exitosa!");
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        return cn;
    }

    private BDconexion(){}
}

