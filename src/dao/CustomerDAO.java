package dao;

import model.Customer;
import config.DBConnection; 
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author evr & Soka
 */
public class CustomerDAO {
    
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id_customer, nama, telepon, alamat, no_sim FROM customers";

        try (Connection conn = DBConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getString("id_customer"));
                c.setNama(rs.getString("nama"));
                c.setNomorTelepon(rs.getString("telepon"));
                c.setAlamat(rs.getString("alamat"));
                c.setNomorKTP(rs.getString("no_sim")); 
                c.setTanggalDaftar(java.time.LocalDate.now().toString()); 
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Eror getAll: " + e.getMessage());
        }
        return list;
    }

    public boolean tambah(Customer c) {
        String sql = "INSERT INTO customers (nama, telepon, alamat, no_sim) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNama());
            pst.setString(2, c.getNomorTelepon());
            pst.setString(3, c.getAlamat());
            pst.setString(4, c.getNomorKTP());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Eror tambah: " + e.getMessage());
            return false;
        }
    }

    public boolean ubah(Customer c) {
        String sql = "UPDATE customers SET nama=?, telepon=?, alamat=?, no_sim=? WHERE id_customer=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNama());
            pst.setString(2, c.getNomorTelepon());
            pst.setString(3, c.getAlamat());
            pst.setString(4, c.getNomorKTP());
            pst.setString(5, c.getId());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Eror ubah: " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(String id) {
        // Sesuaikan nama tabel ke customers dan primary key-nya ke id_customer
        String sql = "DELETE FROM customers WHERE id_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Eror hapus: " + e.getMessage());
            return false;
        }
    }

    public List<Customer> cariBerdasarkanNama(String nama) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id_customer, nama, telepon, alamat, no_sim FROM customers WHERE nama LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + nama + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getString("id_customer"));
                    c.setNama(rs.getString("nama"));
                    c.setNomorTelepon(rs.getString("telepon"));
                    c.setAlamat(rs.getString("alamat"));
                    c.setNomorKTP(rs.getString("no_sim"));
                    c.setTanggalDaftar("YYYY-MM-DD");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Eror cari: " + e.getMessage());
        }
        return list;
    }
}