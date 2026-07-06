/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ReportDAO {
    private static final String BASE_QUERY =
            "SELECT r.id_rental, c.nama AS nama_customer, "
            + "CONCAT(v.merek, ' - ', v.no_plat) AS kendaraan, "
            + "r.tgl_pinjam, rt.tgl_kembali, "
            + "GREATEST(DATEDIFF(rt.tgl_kembali, DATE_ADD(r.tgl_pinjam, INTERVAL r.lama_sewa DAY)), 0) AS terlambat_hari, "
            + "rt.denda, "
            + "(r.total_harga + rt.denda) AS total_bayar "
            + "FROM returns rt "
            + "INNER JOIN rentals r ON rt.id_rental = r.id_rental "
            + "INNER JOIN customers c ON r.id_customer = c.id_customer "
            + "INNER JOIN vehicles v ON r.id_vehicle = v.id_vehicle ";
    
    /**
     * Mengambil laporan transaksi yang sudah selesai (sudah ada di tabel returns),
     * opsional difilter berdasarkan rentang tanggal kembali.
     */
    public DefaultTableModel getLaporan(String tanggalDari, String tanggalSampai) {
 
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Rental");
        model.addColumn("Pelanggan");
        model.addColumn("Kendaraan");
        model.addColumn("Tgl Sewa");
        model.addColumn("Tgl Kembali");
        model.addColumn("Terlambat");
        model.addColumn("Denda");
        model.addColumn("Total Bayar");
 
        boolean pakaiFilter = isFilterDipakai(tanggalDari, tanggalSampai);
        String sql = BASE_QUERY
                + (pakaiFilter ? "WHERE rt.tgl_kembali BETWEEN ? AND ? " : "")
                + "ORDER BY r.id_rental DESC";
 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            if (pakaiFilter) {
                ps.setString(1, tanggalDari);
                ps.setString(2, tanggalSampai);
            }
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_rental"),
                            rs.getString("nama_customer"),
                            rs.getString("kendaraan"),
                            rs.getDate("tgl_pinjam"),
                            rs.getDate("tgl_kembali"),
                            rs.getInt("terlambat_hari") + " hari",
                            "Rp " + rs.getInt("denda"),
                            "Rp " + rs.getInt("total_bayar")
                    });
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error mengambil laporan: " + e.getMessage());
        }
 
        return model;
    }
 
    /** Total pendapatan (total_harga + denda) pada rentang tanggal tertentu. */
    public double getTotalPendapatan(String tanggalDari, String tanggalSampai) {
        double totalPendapatan = 0;
 
        boolean pakaiFilter = isFilterDipakai(tanggalDari, tanggalSampai);
        String sql = "SELECT SUM(r.total_harga + rt.denda) AS total "
                + "FROM returns rt "
                + "INNER JOIN rentals r ON rt.id_rental = r.id_rental "
                + (pakaiFilter ? "WHERE rt.tgl_kembali BETWEEN ? AND ?" : "");
 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            if (pakaiFilter) {
                ps.setString(1, tanggalDari);
                ps.setString(2, tanggalSampai);
            }
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalPendapatan = rs.getDouble("total");
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error total pendapatan: " + e.getMessage());
        }
 
        return totalPendapatan;
    }
 
    private boolean isFilterDipakai(String tanggalDari, String tanggalSampai) {
        return tanggalDari != null && !tanggalDari.isEmpty()
                && tanggalSampai != null && !tanggalSampai.isEmpty();
    }
}
