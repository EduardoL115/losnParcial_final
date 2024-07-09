package org.example.parcial3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class DB {

    private static volatile Connection cn = null;

    public static Connection getInstance() {   // singlton para la coneccion a la base de datos
        if (cn == null) {   // revisa que no hay instancia del singleton
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //driver utilizado para conectar a la base de datos

                String url = "jdbc:sqlserver://localhost\\MSSQLSERVER03\\MSSQLSERVER03:1433;databaseName=POOPARCIAL3;integratedSecurity=true;encrypt=false;"; // url de la base de datos

                cn = DriverManager.getConnection(url);//asignando  el url de connecion a la instancia de singleton

                Statement st = cn.createStatement();// query de la base de datos

                System.out.println("Conexion exitosa!");//mensaje consola para arfirmar la coneccion
            } catch (Exception e) {
                System.out.println("Error: " + e);//imprime en consola el error  en caso de que no funcione la coneccion a la base de datos
            }
        }
        return cn;// regresa la instancia de la base de datos
    }


}

