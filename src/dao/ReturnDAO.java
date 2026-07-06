/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.ReturnTransaction;

/**
 *
 * @author user
 */
public class ReturnDAO {
    public boolean sudahDikembalikan(int idRental) {
        boolean sudahAda = false;

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT id_return FROM returns WHERE id_rental = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRental);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                sudahAda = true;
            }

        } catch (Exception e) {
            System.out.println("Error cek pengembalian: " + e.getMessage());
        }

        return sudahAda;
    }
    
    public boolean simpan(ReturnTransaction dataReturn) {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "INSERT INTO returns "
                    + "(id_rental, tanggal_kembali, terlambat_hari, denda, "
                    + "kondisi_kendaraan, total_bayar) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, dataReturn.getIdRental());
            ps.setDate(2, dataReturn.getTanggalKembali());
            ps.setInt(3, dataReturn.getTerlambatHari());
            ps.setDouble(4, dataReturn.getDenda());
            ps.setString(5, dataReturn.getKondisiKendaraan());
            ps.setDouble(6, dataReturn.getTotalBayar());

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error simpan pengembalian: " + e.getMessage());
            return false;
        }
    }

    public void updateStatusRental(int idRental) {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "UPDATE rentals "
                    + "SET status_rental = 'Selesai' "
                    + "WHERE id_rental = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRental);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error update status rental: " + e.getMessage());
        }
    }
    
    public void updateStatusKendaraan(int idRental) {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "UPDATE vehicles v "
                    + "INNER JOIN rentals r ON v.id_vehicle = r.id_vehicle "
                    + "SET v.status = 'Tersedia' "
                    + "WHERE r.id_rental = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRental);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error update status kendaraan: " + e.getMessage());
        }
    }    
}
