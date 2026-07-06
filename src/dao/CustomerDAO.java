/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Customer;
import config.DBConnection;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author evr
 */
public class CustomerDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/db_rental";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getString("id"));
                c.setNama(rs.getString("nama"));
                c.setNomorKTP(rs.getString("nomor_ktp"));
                c.setJenisKelamin(rs.getString("jenis_kelamin"));
                c.setAlamat(rs.getString("alamat"));
                c.setNomorTelepon(rs.getString("nomor_telepon"));
                c.setTanggalDaftar(rs.getString("tanggal_daftar"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean tambah(Customer c) {
        String sql = "INSERT INTO customer (nama, nomor_ktp, jenis_kelamin, alamat, nomor_telepon, tanggal_daftar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNama());
            pst.setString(2, c.getNomorKTP());
            pst.setString(3, c.getJenisKelamin());
            pst.setString(4, c.getAlamat());
            pst.setString(5, c.getNomorTelepon());
            pst.setString(6, c.getTanggalDaftar());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ubah(Customer c) {
        String sql = "UPDATE customer SET nama=?, nomor_ktp=?, jenis_kelamin=?, alamat=?, nomor_telepon=?, tanggal_daftar=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNama());
            pst.setString(2, c.getNomorKTP());
            pst.setString(3, c.getJenisKelamin());
            pst.setString(4, c.getAlamat());
            pst.setString(5, c.getNomorTelepon());
            pst.setString(6, c.getTanggalDaftar());
            pst.setString(7, c.getId());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapus(String id) {
        String sql = "DELETE FROM customers WHERE id_customers = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> cariBerdasarkanNama(String nama) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE nama LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + nama + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getString("id"));
                c.setNama(rs.getString("nama"));
                c.setNomorKTP(rs.getString("nomor_ktp"));
                c.setJenisKelamin(rs.getString("jenis_kelamin"));
                c.setAlamat(rs.getString("alamat"));
                c.setNomorTelepon(rs.getString("nomor_telepon"));
                c.setTanggalDaftar(rs.getString("tanggal_daftar"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
