/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author user
 */
public class Koneksi {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {

                String url = "jdbc:mysql://localhost:3306/db_rental";
                String user = "root";
                String password = "";

                conn = DriverManager.getConnection(url, user, password);
            }

        } catch (Exception e) {
            System.out.println("Koneksi database gagal: " + e.getMessage());
        }

        return conn;
    }
    
}
