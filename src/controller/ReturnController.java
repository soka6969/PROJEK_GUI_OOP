/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ReturnDAO;
import model.ReturnTransaction;
 
import java.sql.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class ReturnController {
    private static final double DENDA_PER_HARI = 50000;
 
    private final ReturnDAO returnDAO;
 
    public ReturnController() {
        this.returnDAO = new ReturnDAO();
    }
 
    /** Cek apakah rental tertentu sudah pernah dikembalikan. */
    public boolean sudahDikembalikan(int idRental) {
        return returnDAO.sudahDikembalikan(idRental);
    }
 
    /**
     * Daftar rental yang belum dikembalikan, untuk mengisi tabel di ReturnForm.
     * Setiap baris: { id_rental, nama_customer, kendaraan, tgl_pinjam, rencanaKembali }
     */
    public List<Object[]> ambilRentalBelumKembali() {
        return returnDAO.getRentalBelumKembali();
    }
 
    /**
     * Detail satu rental untuk diisi otomatis di form saat "Cari Data" ditekan.
     * Return null jika id_rental tidak ditemukan / sudah dikembalikan.
     * Hasil: { namaCustomer, kendaraan, rencanaKembali(java.sql.Date), totalHarga(Integer) }
     */
    public Object[] ambilDetailRental(int idRental) {
        return returnDAO.getDetailRentalUntukPengembalian(idRental);
    }
 
    /** Menghitung jumlah hari keterlambatan (tidak pernah negatif). */
    public int hitungTerlambatHari(Date tanggalRencana, Date tanggalKembali) {
        long selisihMillis = tanggalKembali.getTime() - tanggalRencana.getTime();
        long selisihHari = selisihMillis / (1000L * 60 * 60 * 24);
        return (int) Math.max(selisihHari, 0);
    }
 
    /** Menghitung denda berdasarkan jumlah hari terlambat. */
    public double hitungDenda(int terlambatHari) {
        return terlambatHari * DENDA_PER_HARI;
    }
 
    /**
     * Menyimpan transaksi pengembalian. Hanya id_rental, tanggal kembali, dan
     * denda yang disimpan ke tabel `returns`, karena hanya itu yang tersedia
     * di skema database (id_return, id_rental, tgl_kembali, denda).
     */
    public boolean simpanPengembalian(int idRental, Date tanggalKembali, double denda) {
        ReturnTransaction dataReturn = new ReturnTransaction();
        dataReturn.setIdRental(idRental);
        dataReturn.setTanggalKembali(tanggalKembali);
        dataReturn.setDenda(denda);
 
        return returnDAO.simpanPengembalian(dataReturn);
    }
}