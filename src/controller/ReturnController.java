package controller;

import dao.ReturnDAO;
import model.ReturnTransaction;
import view.ReturnForm;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author user
 */
public class ReturnController {
    private static final double DENDA_PER_HARI = 50000;
    private final ReturnDAO returnDAO;
    private final ReturnForm form; 
    
    // Variabel state untuk Pagination Lokal
    private int halamanSekarang = 1;
    private final int LIMIT_PER_HALAMAN = 5;
    private List<Object[]> semuaDaftarBelumKembali; 

    public ReturnController(ReturnForm form) {
        this.returnDAO = new ReturnDAO();
        this.form = form;
        muatTabel(); // Pertama kali dibuka, langsung load data ber-pagination
    }

    // ==================== LOGIKA PAGINATION LOKAL ====================
    public void muatTabel() {
        // Ambil data segar dari database
        semuaDaftarBelumKembali = returnDAO.getRentalBelumKembali();
        tampilkanKeTabel();
    }

    public void tampilkanKeTabel() {
        DefaultTableModel model = (DefaultTableModel) form.getjTableRental().getModel();
        model.setRowCount(0); // Kosongkan tabel visual

        if (semuaDaftarBelumKembali == null || semuaDaftarBelumKembali.isEmpty()) return;

        // Hitung batas indeks data per halaman (maksimal 5 data)
        int indeksAwal = (halamanSekarang - 1) * LIMIT_PER_HALAMAN;
        int indeksAkhir = Math.min(indeksAwal + LIMIT_PER_HALAMAN, semuaDaftarBelumKembali.size());

        for (int i = indeksAwal; i < indeksAkhir; i++) {
            Object[] baris = semuaDaftarBelumKembali.get(i);
            model.addRow(baris);
        }
    }

    public void returnNext() {
        if (semuaDaftarBelumKembali == null) return;
        int totalHalaman = (int) Math.ceil((double) semuaDaftarBelumKembali.size() / LIMIT_PER_HALAMAN);
        if (halamanSekarang < totalHalaman) {
            halamanSekarang++;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Sudah mencapai halaman terakhir");
        }
    }

    public void returnPrev() {
        if (halamanSekarang > 1) {
            halamanSekarang--;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Ini sudah halaman pertama!");
        }
    }
    // ====================================================================

    public void cariData() {
        try {
            int idRental = Integer.parseInt(form.getTxtIdRental().getText().trim());
            Object[] detail = returnDAO.getDetailRentalUntukPengembalian(idRental);

            if (detail == null) {
                JOptionPane.showMessageDialog(form, "ID rental tidak ditemukan atau sudah dikembalikan.");
                form.kosongkanForm();
                return;
            }

            form.getTxtNamaPelanggan().setText((String) detail[0]);
            form.getTxtNamaKendaraan().setText((String) detail[1]);
            form.getTxtRencanaKembali().setText(((Date) detail[2]).toString());
            form.setTotalHargaSewa((Integer) detail[3]); 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(form, "ID Rental harus berupa angka.");
        }
    }

    public void hitungDenda() {
        try {
            if (form.getTxtRencanaKembali().getText().isEmpty() || form.getTxtDikembali().getText().isEmpty()) {
                JOptionPane.showMessageDialog(form, "Cari data rental dan isi tanggal dikembalikan terlebih dahulu.");
                return;
            }

            Date tglRencana = Date.valueOf(form.getTxtRencanaKembali().getText());
            Date tglKembali = Date.valueOf(form.getTxtDikembali().getText());

            long selisihMillis = tglKembali.getTime() - tglRencana.getTime();
            int terlambatHari = (int) Math.max(selisihMillis / (1000L * 60 * 60 * 24), 0);
            double denda = terlambatHari * DENDA_PER_HARI;
            double totalBayar = form.getTotalHargaSewa() + denda;

            form.getTxtTerlambat().setText(String.valueOf(terlambatHari));
            form.getTxtDenda().setText(String.valueOf(denda));
            form.getTxtTotalBayar().setText(String.valueOf(totalBayar));

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(form, "Format tanggal harus yyyy-MM-dd.");
        }
    }

    public void simpanPengembalian() {
        try {
            if (form.getTxtIdRental().getText().isEmpty() || form.getTxtDikembali().getText().isEmpty()) {
                JOptionPane.showMessageDialog(form, "Lengkapi data terlebih dahulu.");
                return;
            }

            int idRental = Integer.parseInt(form.getTxtIdRental().getText().trim());
            if (returnDAO.sudahDikembalikan(idRental)) {
                JOptionPane.showMessageDialog(form, "Rental ini sudah pernah dikembalikan.");
                return;
            }

            Date tglKembali = Date.valueOf(form.getTxtDikembali().getText());
            double denda = Double.parseDouble(form.getTxtDenda().getText());

            ReturnTransaction dataReturn = new ReturnTransaction();
            dataReturn.setIdRental(idRental);
            dataReturn.setTanggalKembali(tglKembali);
            dataReturn.setDenda(denda);

            if (returnDAO.simpanPengembalian(dataReturn)) {
                JOptionPane.showMessageDialog(form, "Data pengembalian berhasil disimpan.");
                form.kosongkanForm();
                
                // Segarkan data pagination lokal agar baris yang baru kembali langsung hilang
                muatTabel(); 
            } else {
                JOptionPane.showMessageDialog(form, "Gagal menyimpan data pengembalian.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, "Error: " + e.getMessage());
        }
    }
}