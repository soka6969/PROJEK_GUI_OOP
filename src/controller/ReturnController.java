/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.Koneksi;
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

    // Denda keterlambatan per hari
    private final double DENDA_PER_HARI = 50000;

    // Menghitung jumlah hari keterlambatan
    public int hitungTerlambatHari(Date tanggalRencana, Date tanggalKembali) {
        
        long selisihWaktu = tanggalKembali.getTime() - tanggalRencana.getTime();

        int terlambatHari = (int) (selisihWaktu / (1000 * 60 * 60 * 24));

        // Jika kembali lebih awal, tidak terkena denda
        if (terlambatHari < 0) {
            terlambatHari = 0;
        }
         return terlambatHari;
    }
    
     // Menghitung denda
    public double hitungDenda(int terlambatHari) {
        return terlambatHari * DENDA_PER_HARI;
    }
    
    // Mengecek apakah rental sudah dikembalikan
    public boolean sudahDikembalikan(int idRental) {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT id_return FROM returns WHERE id_rental = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRental);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal mengecek data pengembalian: " + e.getMessage());
        }
        return false;
    }
    
    // Menyimpan data pengembalian ke tabel returns
    public boolean simpanPengembalian(ReturnTransaction dataReturn) {
        

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
            
            updateStatusRental(dataReturn.getIdRental());
            updateStatusKendaraan(dataReturn.getIdRental());

            JOptionPane.showMessageDialog(null,
                    "Data pengembalian berhasil disimpan.");

            return true;
            
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal menyimpan pengembalian: " + e.getMessage());

            return false;
        }
    }

    // Mengubah status rental menjadi Selesai
    private void updateStatusRental(int idRental) {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "UPDATE rentals "
                    + "SET status_rental = 'Selesai' "
                    + "WHERE id_rental = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRental);

            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal mengubah status rental: " + e.getMessage());
        }
    }
    
    // Mengubah status kendaraan menjadi Tersedia
    private void updateStatusKendaraan(int idRental) {
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
            JOptionPane.showMessageDialog(null,
                    "Gagal mengubah status kendaraan: " + e.getMessage());
        }
    }
}
    


            


    
   
    
