/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ASUS
 */

import config.DBConnection;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    private Connection connection;

    public VehicleDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== CREATE ====================
    public boolean simpanKendaraan(Vehicle vehicle) {
        System.out.println(vehicle);
        String sql = "INSERT INTO vehicles(no_plat, merek, tipe, harga_sewa, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, vehicle.getNoPlat());
            ps.setString(2, vehicle.getMerek());
            ps.setString(3, vehicle.getTipe());
            ps.setInt(4, vehicle.getHargaSewa());
            ps.setString(5, vehicle.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menyimpan kendaraan : " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================
    public List<Vehicle> getAllVehicles() {

        List<Vehicle> list = new ArrayList<>();

        String sql = "SELECT * FROM vehicles ORDER BY id_vehicle ASC";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Vehicle vehicle = new Vehicle();

                vehicle.setIdVehicle(rs.getInt("id_vehicle"));
                vehicle.setNoPlat(rs.getString("no_plat"));
                vehicle.setMerek(rs.getString("merek"));
                vehicle.setTipe(rs.getString("tipe"));
                vehicle.setHargaSewa(rs.getInt("harga_sewa"));
                vehicle.setStatus(rs.getString("status"));

                list.add(vehicle);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data kendaraan : " + e.getMessage());
        }

        return list;
    }

    // ==================== UPDATE ====================
    public boolean updateKendaraan(Vehicle vehicle, int id_vehicle) {

        String sql = "UPDATE vehicles SET no_plat=?, merek=?, tipe=?, harga_sewa=?, status=? WHERE id_vehicle=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, vehicle.getNoPlat());
            ps.setString(2, vehicle.getMerek());
            ps.setString(3, vehicle.getTipe());
            ps.setInt(4, vehicle.getHargaSewa());
            ps.setString(5, vehicle.getStatus());
            ps.setInt(6, id_vehicle);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengupdate kendaraan : " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================
    public boolean hapusKendaraan(int idVehicle) {

        String sql = "DELETE FROM vehicles WHERE id_vehicle=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idVehicle);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus kendaraan : " + e.getMessage());
            return false;
        }
    }
        // ==================== SEARCH ====================
    public List<Vehicle> cariKendaraan(String keyword) {

    List<Vehicle> list = new ArrayList<>();

    String sql = "SELECT * FROM vehicles WHERE no_plat LIKE ? OR merek LIKE ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Vehicle vehicle = new Vehicle();

            vehicle.setIdVehicle(rs.getInt("id_vehicle"));
            vehicle.setNoPlat(rs.getString("no_plat"));
            vehicle.setMerek(rs.getString("merek"));
            vehicle.setTipe(rs.getString("tipe"));
            vehicle.setHargaSewa(rs.getInt("harga_sewa"));
            vehicle.setStatus(rs.getString("status"));

            list.add(vehicle);
        }

    } catch (SQLException e) {
        System.out.println("Gagal mencari kendaraan : " + e.getMessage());
    }

    return list;
}
}
