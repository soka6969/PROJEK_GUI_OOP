/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import model.ReturnTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ReturnDAO {
    /**
     * Mengecek apakah sebuah rental sudah pernah dikembalikan
     * (id_rental sudah punya baris di tabel returns).
     */
    public boolean sudahDikembalikan(int idRental) {
        String sql = "SELECT id_return FROM returns WHERE id_rental = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

     
       ps.setInt(1, idRental);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengecek data pengembalian: " + e.getMessage());
            return false;
        }
    }
    /**
     * Mengambil daftar rental yang BELUM dikembalikan, yaitu rental yang belum
     * memiliki baris pasangan di tabel returns (LEFT JOIN ... IS NULL).
     *
     * Setiap baris hasil: { id_rental, nama_customer, kendaraan, tgl_pinjam, rencanaKembali }
     */
    public List<Object[]> getRentalBelumKembali() {
        List<Object[]> list = new ArrayList<>();

        String sql = "SELECT r.id_rental, c.nama, "
                + "CONCAT(v.merek, ' - ', v.no_plat) AS kendaraan, "
                + "r.tgl_pinjam, "
                + "DATE_ADD(r.tgl_pinjam, INTERVAL r.lama_sewa DAY) AS rencana_kembali "
                + "FROM rentals r "
                + "JOIN customers c ON r.id_customer = c.id_customer "
                + "JOIN vehicles v ON r.id_vehicle = v.id_vehicle "
                + "LEFT JOIN returns rt ON rt.id_rental = r.id_rental "
                + "WHERE rt.id_return IS NULL "
                + "ORDER BY r.id_rental ASC";
        
         try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("id_rental"),
                        rs.getString("nama"),
                        rs.getString("kendaraan"),
                        rs.getDate("tgl_pinjam"),
                        rs.getDate("rencana_kembali")
                });
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data rental belum kembali: " + e.getMessage());
        }

        return list;
    }
    
    /**
     * Mengambil detail satu rental untuk ditampilkan saat tombol "Cari Data" ditekan.
     * Mengembalikan null jika id_rental tidak ada atau sudah pernah dikembalikan.
     *
     * Hasil: { namaCustomer(String), kendaraan(String), rencanaKembali(java.sql.Date), totalHarga(Integer) }
     */
    public Object[] getDetailRentalUntukPengembalian(int idRental) {
        if (sudahDikembalikan(idRental)) {
            return null;
        }

        String sql = "SELECT c.nama, "
                + "CONCAT(v.merek, ' - ', v.no_plat) AS kendaraan, "
                + "DATE_ADD(r.tgl_pinjam, INTERVAL r.lama_sewa DAY) AS rencana_kembali, "
                + "r.total_harga "
                + "FROM rentals r "
                + "JOIN customers c ON r.id_customer = c.id_customer "
                + "JOIN vehicles v ON r.id_vehicle = v.id_vehicle "
                + "WHERE r.id_rental = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRental);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Object[]{
                        rs.getString("nama"),
                        rs.getString("kendaraan"),
                        rs.getDate("rencana_kembali"),
                        rs.getInt("total_harga")
                };
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil detail rental: " + e.getMessage());
            return null;
        }
    }

    /**
     * Menyimpan transaksi pengembalian dalam satu transaction:
     *  1) INSERT baris baru ke tabel returns (id_rental, tgl_kembali, denda)
     *  2) UPDATE status kendaraan terkait menjadi 'Tersedia' lagi
     */
    public boolean simpanPengembalian(ReturnTransaction dataReturn) {
        String sqlInsertReturn =
                "INSERT INTO returns (id_rental, tgl_kembali, denda) VALUES (?, ?, ?)";
        String sqlUpdateVehicle =
                "UPDATE vehicles v "
                + "INNER JOIN rentals r ON v.id_vehicle = r.id_vehicle "
                + "SET v.status = 'Tersedia' "
                + "WHERE r.id_rental = ?";

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psReturn = conn.prepareStatement(sqlInsertReturn)) {
                psReturn.setInt(1, dataReturn.getIdRental());
                psReturn.setDate(2, dataReturn.getTanggalKembali());
                psReturn.setInt(3, (int) Math.round(dataReturn.getDenda()));
                psReturn.executeUpdate();
            }

            try (PreparedStatement psVehicle = conn.prepareStatement(sqlUpdateVehicle)) {
                psVehicle.setInt(1, dataReturn.getIdRental());
                psVehicle.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("Gagal menyimpan pengembalian: " + e.getMessage());
            return false;

        } finally {
            closeQuietly(conn);
        }
    }

    private void rollbackQuietly(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Rollback gagal: " + e.getMessage());
        }
    }

    private void closeQuietly(Connection conn) {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}


    
    