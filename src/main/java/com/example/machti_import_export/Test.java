package com.example.machti_import_export;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static Connection conn;

    public static void main(String[] args) {
        Connecter();
    }

    public static void Connecter(){
        try{
            conn = Connexion.connect();
            if(conn != null){
                System.out.println("Connexion reussite");
                testAffichage();
            }else{
                System.out.println("Connexion échouée");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void testAffichage() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from AVION");
        while(rs.next()){
            System.out.println(rs.getInt(1)+" " +rs.getString(2)+" "+ rs.getInt(3)+" "+ rs.getString(4));
            System.out.println();
        }
    }
}
