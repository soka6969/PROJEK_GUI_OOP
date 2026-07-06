package controller;

import dao.CustomerDAO;
import model.Customer;
import view.CustomerForm;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @author evr
 */
public class CustomerController {
    
    private final CustomerForm form;
    private final CustomerDAO customerDAO;
    
    // Variabel Pendukung Pagination Lokal
    private int halamanSekarang = 1;
    private final int LIMIT_PER_HALAMAN = 5;
    private List<Customer> semuaDaftarCustomer; // Tempat menampung seluruh data dari DB

    public CustomerController(CustomerForm form) {
        this.form = form;
        this.customerDAO = new CustomerDAO();
        initListeners();
        muatTabel(); // Pertama kali dibuka, langsung isi tabel ber-pagination
    }
 
    private void initListeners() {
        form.getBtnTambah().addActionListener(e -> tambahCustomer());
        form.getBtnUbah().addActionListener(e -> ubahCustomer());
        form.getBtnHapus().addActionListener(e -> hapusCustomer());
        form.getBtnBersih().addActionListener(e -> bersihkanForm());
        form.getBtnRefresh().addActionListener(e -> muatTabel());
        form.getBtnCari().addActionListener(e -> cariCustomer());

        form.getBtnPrev().addActionListener(e -> customerPrev());
        form.getBtnNext().addActionListener(e -> customerNext());
        
        form.getTableCustomer().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabelTerpilih();
            }
        });
        
        
    }

    private void tambahCustomer() {
        if (!validasiInput()) return;
 
        try {
            Customer c = ambilDataDariForm();
            boolean sukses = customerDAO.tambah(c);
            if (sukses) {
                tampilkanInfo("Data customer berhasil ditambahkan.");
                bersihkanForm();
                muatTabel();
            } else {
                tampilkanError("Gagal menambahkan data customer.");
            }
        } catch (Exception ex) {
            tampilkanError("Terjadi kesalahan: " + ex.getMessage());
        }
    }

    private void ubahCustomer() {
        String idText = form.getTxtId().getText().trim();
        if (idText.isEmpty()) {
            tampilkanError("Pilih data pada tabel terlebih dahulu sebelum mengubah.");
            return;
        }
        if (!validasiInput()) return;
 
        try {
            Customer c = ambilDataDariForm();
            c.setId(idText);
            boolean sukses = customerDAO.ubah(c);
            if (sukses) {
                tampilkanInfo("Data customer berhasil diubah.");
                bersihkanForm();
                muatTabel();
            } else {
                tampilkanError("Gagal mengubah data customer.");
            }
        } catch (Exception ex) {
            tampilkanError("Terjadi kesalahan: " + ex.getMessage());
        }
    }

    private void hapusCustomer() {
        String idText = form.getTxtId().getText().trim();
        if (idText.isEmpty()) {
            tampilkanError("Pilih data pada tabel terlebih dahulu sebelum menghapus.");
            return;
        }
 
        int konfirmasi = JOptionPane.showConfirmDialog(form,
                "Yakin ingin menghapus data customer ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
 
        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean sukses = customerDAO.hapus(idText);
            if (sukses) {
                tampilkanInfo("Data customer berhasil dihapus.");
                bersihkanForm();
                muatTabel();
            } else {
                tampilkanError("Gagal menghapus data customer.");
            }
        }
    }

    public void cariCustomer() {
        String keyword = form.getTxtCari().getText().trim();
        if (keyword.isEmpty()) {
            semuaDaftarCustomer = customerDAO.getAll();
        } else {
            semuaDaftarCustomer = customerDAO.cariBerdasarkanNama(keyword);
        }
        halamanSekarang = 1; // Reset ke halaman pertama saat melakukan pencarian baru
        tampilkanKeTabel();
    }

    public void muatTabel() {
        // Tarik data mentah dari database
        semuaDaftarCustomer = customerDAO.getAll();
        tampilkanKeTabel();
    }
 
    private void tampilkanKeTabel() {
        DefaultTableModel model = form.getTableModel();
        model.setRowCount(0); // Kosongkan tabel lokal
        
        if (semuaDaftarCustomer == null || semuaDaftarCustomer.isEmpty()) return;

        // Hitung batas indeks data yang mau ditampilkan (Maksimal 5 data per halaman)
        int indeksAwal = (halamanSekarang - 1) * LIMIT_PER_HALAMAN;
        int indeksAkhir = Math.min(indeksAwal + LIMIT_PER_HALAMAN, semuaDaftarCustomer.size());

        for (int i = indeksAwal; i < indeksAkhir; i++) {
            Customer c = semuaDaftarCustomer.get(i);
            model.addRow(new Object[]{
                c.getId(),            
                c.getNama(),          
                c.getAlamat(),        
                c.getNoTelepon(),    
                c.getNoKtp() 
            });
        }
    }

    public void customerNext() {
        if (semuaDaftarCustomer == null) return;
        int totalHalaman = (int) Math.ceil((double) semuaDaftarCustomer.size() / LIMIT_PER_HALAMAN);
        if (halamanSekarang < totalHalaman) {
            halamanSekarang++;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Sudah mencapai halaman terakhir");
        }
    }

    public void customerPrev() {
        if (halamanSekarang > 1) {
            halamanSekarang--;
            tampilkanKeTabel();
        } else {
            JOptionPane.showMessageDialog(form, "Ini sudah halaman pertama!");
        }
    }
    

    private void isiFormDariTabelTerpilih() {
        int row = form.getTableCustomer().getSelectedRow();
        if (row < 0) return;
 
        DefaultTableModel model = form.getTableModel();
        form.getTxtId().setText(model.getValueAt(row, 0).toString());
        form.getTxtNama().setText(model.getValueAt(row, 1).toString());
        form.getTxtAlamat().setText(model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "");
        form.getTxtNoTelepon().setText(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
        form.getTxtNoKtp().setText(model.getValueAt(row, 4).toString());
    }

    private void bersihkanForm() {
        form.getTxtId().setText("");
        form.getTxtNama().setText("");
        form.getTxtAlamat().setText("");
        form.getTxtNoTelepon().setText("");
        form.getTxtNoKtp().setText("");
        form.getTableCustomer().clearSelection();
    }

    private boolean validasiInput() {
        String nama = form.getTxtNama().getText().trim();
        String alamat = form.getTxtAlamat().getText().trim();
        String noTelepon = form.getTxtNoTelepon().getText().trim();
        String noKtp = form.getTxtNoKtp().getText().trim();
 
        if (nama.isEmpty() || alamat.isEmpty() || noTelepon.isEmpty() || noKtp.isEmpty()) {
            tampilkanError("Semua field wajib diisi.");
            return false;
        }
 
        if (!noTelepon.matches("\\d{8,15}")) {
            tampilkanError("No. Telepon harus berupa angka (8-15 digit).");
            return false;
        }
 
        if (!noKtp.matches("\\d{1,16}")) { 
            tampilkanError("No. SIM/KTP tidak valid.");
            return false;
        }
 
        return true;
    }

    private Customer ambilDataDariForm() {
        String nama = form.getTxtNama().getText().trim();
        String alamat = form.getTxtAlamat().getText().trim();
        String noTelepon = form.getTxtNoTelepon().getText().trim();
        String noKtp = form.getTxtNoKtp().getText().trim();
 
        return new Customer(nama, alamat, noTelepon, noKtp);
    } 
 
    private void tampilkanInfo(String pesan) {
        JOptionPane.showMessageDialog(form, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(form, pesan, "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}