/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.ReturnTransaction;

/**
 *
 * @author user
 */
public class ReturnController {
    
     private static final double DENDA_PER_HARI = 50000;

    public int hitungTerlambatHari(Date tanggalRencana, Date tanggalKembali) {
        long selisihWaktu = tanggalKembali.getTime() - tanggalRencana.getTime();

        int terlambatHari = (int) (selisihWaktu / (1000 * 60 * 60 * 24));

        return Math.max(terlambatHari, 0);
    }
    
     public double hitungDenda(int terlambatHari) {
        return terlambatHari * DENDA_PER_HARI;
    }
     
     public boolean sudahDikembalikan(int idRental) {
        String sql = "SELECT id_return FROM returns WHERE id_rental = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idRental);

            ResultSet rs = ps.executeQuery();

            return rs.next();
         } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal mengecek data pengembalian: " + e.getMessage()
            );

            return false;
        }
    }
     
     public boolean simpanPengembalian(ReturnTransaction dataReturn) {
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);

            simpanDataReturn(conn, dataReturn);
            updateStatusRental(conn, dataReturn.getIdRental());
            updateStatusKendaraan(conn, dataReturn.getIdRental());

            conn.commit();

            JOptionPane.showMessageDialog(
                    null,
                    "Data pengembalian berhasil disimpan."
            );
            
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackError) {
                JOptionPane.showMessageDialog(
                        null,
                        "Gagal membatalkan transaksi: "
                        + rollbackError.getMessage()
                );
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Gagal menyimpan pengembalian: " + e.getMessage()
            );

            return false;
            
            } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
     
     private void simpanDataReturn(Connection conn, ReturnTransaction dataReturn)
            throws Exception {

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
    }
     
      private void updateStatusRental(Connection conn, int idRental)
            throws Exception {

        String sql = "UPDATE rentals "
                + "SET status_rental = 'Selesai' "
                + "WHERE id_rental = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, idRental);

        ps.executeUpdate();
    }
      
      private void updateStatusKendaraan(Connection conn, int idRental)
            throws Exception {

        String sql = "UPDATE vehicles v "
                + "INNER JOIN rentals r ON v.id_vehicle = r.id_vehicle "
                + "SET v.status = 'Tersedia' "
                + "WHERE r.id_rental = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, idRental);

        ps.executeUpdate();
    }   
}
