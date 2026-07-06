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
 
    public CustomerController(CustomerForm form) {
        this.form = form;
        this.customerDAO = new CustomerDAO();
        initListeners();
        muatTabel();
    }
 
    private void initListeners() {
        form.getBtnTambah().addActionListener(e -> tambahCustomer());
        form.getBtnUbah().addActionListener(e -> ubahCustomer());
        form.getBtnHapus().addActionListener(e -> hapusCustomer());
        form.getBtnBersih().addActionListener(e -> bersihkanForm());
        form.getBtnRefresh().addActionListener(e -> muatTabel());
        form.getBtnCari().addActionListener(e -> cariCustomer());

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

    private void cariCustomer() {
        String keyword = form.getTxtCari().getText().trim();
        List<Customer> hasil;
        if (keyword.isEmpty()) {
            hasil = customerDAO.getAll();
        } else {
            hasil = customerDAO.cariBerdasarkanNama(keyword);
        }
        tampilkanKeTabel(hasil);
    }

    private void muatTabel() {
        List<Customer> daftar = customerDAO.getAll();
        tampilkanKeTabel(daftar);
    }
 
    private void tampilkanKeTabel(List<Customer> daftar) {
        DefaultTableModel model = form.getTableModel();
        model.setRowCount(0); // Kosongkan tabel
        
        for (Customer c : daftar) {
            model.addRow(new Object[]{
                c.getId(),            
                c.getNama(),          
                c.getAlamat(),        
                c.getNoTelepon(),    
                c.getNoKtp() 
            });
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
 
        if (!noKtp.matches("\\d{1,16}")) { // Dibuat fleksibel jika panjang plat/SIM bervariasi
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
 
        // Mengirim string kosong "" di parameter ke-5 agar constructor Customer lama tidak pecah
        return new Customer(nama, alamat, noTelepon, noKtp);
    } 
 
    private void tampilkanInfo(String pesan) {
        JOptionPane.showMessageDialog(form, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(form, pesan, "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}