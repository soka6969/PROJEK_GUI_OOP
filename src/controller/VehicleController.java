package controller;

import dao.VehicleDAO;
import model.Vehicle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ASUS
 */
public class VehicleController {

    private VehicleDAO vehicleDAO;
    
    // State Pendukung Pagination Lokal
    private int halamanSekarang = 1;
    private final int LIMIT_PER_HALAMAN = 5;
    private List<Vehicle> semuaDaftarVehicle; 

    public VehicleController() {
        vehicleDAO = new VehicleDAO();
    }

    // ===================== LOGIKA PAGINATION LOKAL =====================
    public void muatDataAwal(view.VehicleForm form) {
        // Ambil data fresh dari database saat form pertama kali dibuka
        semuaDaftarVehicle = vehicleDAO.getAllVehicles();
        halamanSekarang = 1;
        tampilkanKeTabelPaginasi(form);
    }

    public void tampilkanKeTabelPaginasi(view.VehicleForm form) {
        if (semuaDaftarVehicle == null) return;

        // Hitung batasan indeks data per halaman (maksimal 5 data)
        int indeksAwal = (halamanSekarang - 1) * LIMIT_PER_HALAMAN;
        int indeksAkhir = Math.min(indeksAwal + LIMIT_PER_HALAMAN, semuaDaftarVehicle.size());

        int jumlahBarisTampil = Math.max(0, indeksAkhir - indeksAwal);
        String[][] data = new String[jumlahBarisTampil][6];

        int barisTabel = 0;
        for (int i = indeksAwal; i < indeksAkhir; i++) {
            Vehicle v = semuaDaftarVehicle.get(i);
            data[barisTabel][0] = String.valueOf(v.getIdVehicle());
            data[barisTabel][1] = v.getNoPlat();
            data[barisTabel][2] = v.getMerek();
            data[barisTabel][3] = v.getTipe();
            data[barisTabel][4] = String.valueOf(v.getHargaSewa());
            data[barisTabel][5] = v.getStatus();
            barisTabel++;
        }

        String[] columnName = {"ID Kendaraan", "no_plat", "merek", "tipe", "harga_sewa", "status"};
        form.getjTableVehicle().setModel(new javax.swing.table.DefaultTableModel(data, columnName));
    }

    public void vehicleNext(view.VehicleForm form) {
        if (semuaDaftarVehicle == null) return;
        int totalHalaman = (int) Math.ceil((double) semuaDaftarVehicle.size() / LIMIT_PER_HALAMAN);
        if (halamanSekarang < totalHalaman) {
            halamanSekarang++;
            tampilkanKeTabelPaginasi(form);
        } else {
            javax.swing.JOptionPane.showMessageDialog(form, "Sudah mencapai halaman terakhir");
        }
    }

    public void vehiclePrev(view.VehicleForm form) {
        if (halamanSekarang > 1) {
            halamanSekarang--;
            tampilkanKeTabelPaginasi(form);
        } else {
            javax.swing.JOptionPane.showMessageDialog(form, "Ini sudah halaman pertama!");
        }
    }
    // ===================================================================

    public List<Vehicle> ambilDataKendaraan() {
        return vehicleDAO.getAllVehicles();
    }

    public boolean simpanKendaraan(Vehicle kendaraan) {
        if (kendaraan == null) return false;
        if (kendaraan.getNoPlat().trim().isEmpty()
                || kendaraan.getMerek().trim().isEmpty()
                || kendaraan.getTipe().trim().isEmpty()
                || kendaraan.getStatus().trim().isEmpty()
                || kendaraan.getHargaSewa() <= 0) {
            return false;
        }
        return vehicleDAO.simpanKendaraan(kendaraan);
    }

    public boolean updateKendaraan(Vehicle kendaraan, int id_kendaraan) {
        if (kendaraan == null || id_kendaraan == 0) return false;
        if (kendaraan.getNoPlat().trim().isEmpty()
                || kendaraan.getMerek().trim().isEmpty()
                || kendaraan.getTipe().trim().isEmpty()
                || kendaraan.getStatus().trim().isEmpty()
                || kendaraan.getHargaSewa() <= 0) {
            return false;
        }
        return vehicleDAO.updateKendaraan(kendaraan, id_kendaraan);
    }

    public boolean hapusKendaraan(int idVehicle) {
        if (idVehicle <= 0) return false;
        return vehicleDAO.hapusKendaraan(idVehicle);
    }

    public void cariKendaraanBagiPaginasi(String keyword, view.VehicleForm form) {
        if (keyword == null || keyword.trim().isEmpty()) {
            semuaDaftarVehicle = vehicleDAO.getAllVehicles();
        } else {
            semuaDaftarVehicle = vehicleDAO.cariKendaraan(keyword);
        }
        halamanSekarang = 1; // Reset halaman ke-1 saat pencarian baru
        tampilkanKeTabelPaginasi(form);
    }

    public void urutHargaNaikBagiPaginasi(view.VehicleForm form) {
        semuaDaftarVehicle = vehicleDAO.getAllVehicles();
        Collections.sort(semuaDaftarVehicle, Comparator.comparingInt(Vehicle::getHargaSewa));
        halamanSekarang = 1;
        tampilkanKeTabelPaginasi(form);
    }

    public void urutHargaTurunBagiPaginasi(view.VehicleForm form) {
        semuaDaftarVehicle = vehicleDAO.getAllVehicles();
        Collections.sort(semuaDaftarVehicle, (v1, v2) -> Integer.compare(v2.getHargaSewa(), v1.getHargaSewa()));
        halamanSekarang = 1;
        tampilkanKeTabelPaginasi(form);
    }
}