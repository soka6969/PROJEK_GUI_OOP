package controller;

import dao.RentalDAO;
import java.text.SimpleDateFormat;
import model.RentalTransaction;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import view.RentalForm;

/**
 * @author Abin Tara
 */
public class RentalController {
    private final RentalDAO rentalDAO;
    private final RentalForm form; // Ditambahkan agar objek form bisa diakses di controller
    private int halamanSekarang = 1;
    private final int LIMIT_PER_HALAMAN = 5;
    private List<String[]> semuaDaftarTransaksi; 

    // Constructor diperbarui untuk menerima RentalForm dari View
    public RentalController(RentalForm form) {
        this.form = form;
        this.rentalDAO = new RentalDAO(); 
        muatTabel(); // Pertama kali dibuka, langsung isi tabel ber-pagination
    }

    // Fungsi asli kelompok untuk menarik data asli dari SQL
    public List<String[]> ambilCustomer() { 
        return rentalDAO.getCustomerList(); 
    }
    
    public List<String[]> ambilKendaraan() { 
        return rentalDAO.getAvailableVehicles(); 
    }
    
    public List<String[]> ambilTabelTransaksi() { 
        return rentalDAO.getRentalTransactionList(); 
    }
    
    public String hitungTotalBayar(String lamaSewaStr, String hargaSewaStr) {
        try {
            int lamaSewa = Integer.parseInt(lamaSewaStr.trim());
            int hargaSewa = Integer.parseInt(hargaSewaStr.trim());
            int total = lamaSewa * hargaSewa;
            return String.valueOf(total);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    // PROSES SIMPAN TRANSAKSI
    public boolean prosesSimpanTransaksi(String customerRaw, String vehicleRaw, int idUser, String lamaSewaStr, String totalHargaStr, String tglPinjamStr) {
        try {
            int idCust = Integer.parseInt(customerRaw.split(" - ")[0]);
            int idMobil = Integer.parseInt(vehicleRaw.split(" - ")[0]);
            int lamaSewa = Integer.parseInt(lamaSewaStr);
            int totalHarga = Integer.parseInt(totalHargaStr);
            
            RentalTransaction rentalBaru = new RentalTransaction();
            rentalBaru.setIdCustomer(idCust);
            rentalBaru.setIdVehicle(idMobil);
            rentalBaru.setIdUser(idUser); 
            rentalBaru.setLamaSewa(lamaSewa);
            rentalBaru.setTotalHarga(totalHarga);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            rentalBaru.setTglPinjam(sdf.parse(tglPinjamStr));
            
            boolean sukses = rentalDAO.simpanTransaksi(rentalBaru);
            if (sukses) {
                muatTabel(); // Refresh pagination otomatis jika ada data baru masuk
            }
            return sukses;
            
        } catch (Exception e) {
            System.out.println("Eror Validasi/Parsing di Controller: " + e.getMessage());
            return false;
        }
    }
    
    public void cariTransaksiBagiPaginasi(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
        // Kalau kotak pencarian kosong, load kembali semua data transaksi secara normal
        semuaDaftarTransaksi = rentalDAO.getRentalTransactionList();
    } else {
        // Tembak fungsi ambilTabelTransaksi() kelompokmu, lalu saring berdasarkan kata kunci secara lokal
        List<String[]> semuaData = rentalDAO.getRentalTransactionList();
        semuaDaftarTransaksi = new java.util.ArrayList<>();
        
        String keywordLower = keyword.toLowerCase().trim();
        for (String[] data : semuaData) {
            // data[1] = Nama Pelanggan, data[2] = Kendaraan / Plat
            if (data[1].toLowerCase().contains(keywordLower) || data[2].toLowerCase().contains(keywordLower)) {
                semuaDaftarTransaksi.add(data);
            }
        }
    }
    halamanSekarang = 1; // Reset ke halaman pertama setiap kali melakukan pencarian baru
    tampilkanKeTabel();   // Gelar datanya ke JTable (maksimal 5 data saja)
}
    
    public void muatTabel() {
        // Ambil data fresh dari database lewat fungsi asli kelompokmu (Urutan ASC)
        semuaDaftarTransaksi = rentalDAO.getRentalTransactionList(); 
        tampilkanKeTabel();
    }

    public void tampilkanKeTabel() {
        DefaultTableModel model = (DefaultTableModel) form.getjTable1().getModel();
        model.setRowCount(0); // Reset tabel visual

        if (semuaDaftarTransaksi == null || semuaDaftarTransaksi.isEmpty()) return;

        // Atur range indeks data yang diambil per halaman (maksimal 5 data)
        int indeksAwal = (halamanSekarang - 1) * LIMIT_PER_HALAMAN;
        int indeksAkhir = Math.min(indeksAwal + LIMIT_PER_HALAMAN, semuaDaftarTransaksi.size());

        for (int i = indeksAwal; i < indeksAkhir; i++) {
            String[] data = semuaDaftarTransaksi.get(i);
            model.addRow(new Object[]{
                data[0], // No. Nota
                data[1], // Nama Pelanggan
                data[2], // Kendaraan
                data[3], // Tgl Pinjam
                data[4], // Lama Sewa
                data[5]  // Total Bayar
            });
        }
    }

    public void transaksiNext() {
        if (semuaDaftarTransaksi == null) return;
        int totalHalaman = (int) Math.ceil((double) semuaDaftarTransaksi.size() / LIMIT_PER_HALAMAN);
        if (halamanSekarang < totalHalaman) {
            halamanSekarang++;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Sudah mencapai halaman terakhir!");
        }
    }

    public void transaksiPrev() {
        if (halamanSekarang > 1) {
            halamanSekarang--;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Ini sudah halaman pertama!");
        }
    }
    
    public void prosesHapusTransaksi() {
        JTable tabel = form.getjTable1(); 
        int barisTerpilih = tabel.getSelectedRow();
        
        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(form, "Silakan pilih baris data pada tabel terlebih dahulu yang ingin dihapus!");
            return;
        }
        
        int idRental = Integer.parseInt(tabel.getValueAt(barisTerpilih, 0).toString());
        String namaPelanggan = tabel.getValueAt(barisTerpilih, 1).toString();
        
        int konfirmasi = JOptionPane.showConfirmDialog(form, 
                "Apakah Anda yakin ingin menghapus transaksi Nota No. " + idRental + " atas nama " + namaPelanggan + "?", 
                "Konfirmasi Hapus Data", 
                JOptionPane.YES_NO_OPTION);
                
        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Memakai fungsi hapusTransaksi kelompokmu yang menembak SQL
            if (rentalDAO.hapusTransaksi(idRental)) { 
                JOptionPane.showMessageDialog(form, "Transaksi Berhasil Dihapus dari Database!");
                
                // Segarkan form transaksi visual kamu
                form.resetForm();
                form.loadDataKendaraanDariSQL(); 
                
                // Refresh data pagination lokal biar barisnya langsung hilang
                muatTabel(); 
            } else {
                JOptionPane.showMessageDialog(form, "Gagal menghapus transaksi! Silakan cek koneksi database.");
            }
        }
    }
}