/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import model.User;
import java.sql.*;

public class UserDao {
    private Connection conn;

    public UserDao() {
        try {
            this.conn = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal membuka koneksi database: " + e.getMessage(), e);
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("[UserDAO.login] " + e.getMessage());
        }
        return null;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean createUser(String namaPetugas, String username,
                               String password, String role) {
        String sql = "INSERT INTO users (nama_petugas, username, password, role) "
                   + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaPetugas);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserDAO.createUser] " + e.getMessage());
            return false;
        }
    }

    public void seedAdminIfNotExists() {
        if (!usernameExists("admin")) {
            createUser("Administrator", "admin", "admin123", "Petugas");
            System.out.println("[UserDAO] Akun default dibuat: admin / admin123 (role: Petugas)");
        }
    }

    // Tutup koneksi kalau DAO ini sudah tidak dipakai
    public void closeConnection() {
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) { System.err.println("Gagal tutup koneksi: " + e.getMessage()); }
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt   ("id_user"),
            rs.getString("nama_petugas"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("role")
        );
    }
}
