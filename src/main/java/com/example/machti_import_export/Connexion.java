package com.example.machti_import_export;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private static final String URL = "jdbc:sqlserver://LAPTOP-2KV6E5EH\\SQLEXPRESS;databaseName=machti_ImportExport;integratedSecurity=true;trustServerCertificate=true";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
