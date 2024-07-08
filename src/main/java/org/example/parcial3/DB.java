package org.example.parcial3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class DB {

    private static volatile Connection cn = null;

    public static Connection getInstance() {
        if (cn == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                String url = "jdbc:sqlserver://LAPTOP-Q8NBL7TT\\MSSQLSERVER03\\MSSQLSERVER03:1433;databaseName=POOPARCIAL3;integratedSecurity=true;encrypt=false;";

                cn = DriverManager.getConnection(url);

                Statement st = cn.createStatement();

                System.out.println("Conexion exitosa!");
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        return cn;
    }

    private DB(){}
}

