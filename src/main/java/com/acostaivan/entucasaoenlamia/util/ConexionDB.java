package com.acostaivan.entucasaoenlamia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL      = "jdbc:mariadb://localhost:3306/en_tu_casa_db";
    private static final String USUARIO  = "ibanTareas";
    private static final String PASSWORD = "1234";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MariaDB no encontrado", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}