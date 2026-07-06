/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ReportDAO {
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
        
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT r.id_rental, c.nama_customer, "
                    + "v.nama_vehicle, r.tanggal_sewa, "
                    + "rt.tanggal_kembali, rt.terlambat_hari, "
                    + "rt.denda, rt.total_bayar "
                    + "FROM returns rt "
                    + "INNER JOIN rentals r ON rt.id_rental = r.id_rental "
                    + "INNER JOIN customers c ON r.id_customer = c.id_customer "
                    + "INNER JOIN vehicles v ON r.id_vehicle = v.id_vehicle ";

            boolean pakaiFilter = !tanggalDari.isEmpty() && !tanggalSampai.isEmpty();

            if (pakaiFilter) {
                sql += "WHERE rt.tanggal_kembali BETWEEN ? AND ?";
            }
            
             PreparedStatement ps = conn.prepareStatement(sql);

            if (pakaiFilter) {
                ps.setString(1, tanggalDari);
                ps.setString(2, tanggalSampai);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_rental"),
                    rs.getString("nama_customer"),
                    rs.getString("nama_vehicle"),
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali"),
                    rs.getInt("terlambat_hari") + " hari",
                    "Rp " + rs.getDouble("denda"),
                    "Rp " + rs.getDouble("total_bayar")
                });
            }
        } catch (Exception e) {
            System.out.println("Error mengambil laporan: " + e.getMessage());
        }

        return model;
    }
    
    public double getTotalPendapatan(String tanggalDari, String tanggalSampai) {
        double totalPendapatan = 0;

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT SUM(total_bayar) AS total "
                    + "FROM returns ";

            boolean pakaiFilter = !tanggalDari.isEmpty() && !tanggalSampai.isEmpty();

            if (pakaiFilter) {
                sql += "WHERE tanggal_kembali BETWEEN ? AND ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            if (pakaiFilter) {
                ps.setString(1, tanggalDari);
                ps.setString(2, tanggalSampai);
            }
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalPendapatan = rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Error total pendapatan: " + e.getMessage());
        }

        return totalPendapatan;
    }
   
}
