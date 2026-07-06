package controller;

import dao.RentalDAO;
import java.text.SimpleDateFormat;
import model.RentalTransaction;
import java.util.List;

/**
 * @author Abin Tara
 */
public class RentalController {
    private final RentalDAO rentalDAO;

    public RentalController() {
        this.rentalDAO = new RentalDAO(); 
    }

    // Fungsi asli kelompok untuk menarik data asli dari SQL ke JTable/ComboBox
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
        // Bersihkan spasi kosong jika ada user yang typo ketik spasi
        int lamaSewa = Integer.parseInt(lamaSewaStr.trim());
        int hargaSewa = Integer.parseInt(hargaSewaStr.trim());
        
        // Lakukan kalkulasi di sini
        int total = lamaSewa * hargaSewa;
        return String.valueOf(total);
    } catch (NumberFormatException e) {
        // Jika textbox masih kosong atau belum diisi angka, kembalikan 0
        return "0";
    }
}

    //PROSES SIMPAN TRANSRAKSI (MVC CLEAN CODE): Menangani logika berat, parsing teks, dan tanggal dari Form
    public boolean prosesSimpanTransaksi(String customerRaw, String vehicleRaw, int idUser, String lamaSewaStr, String totalHargaStr, String tglPinjamStr) {
        try {
            // 1. Ekstrak ID dari teks ComboBox 
            int idCust = Integer.parseInt(customerRaw.split(" - ")[0]);
            int idMobil = Integer.parseInt(vehicleRaw.split(" - ")[0]);
            
            // 2. Parsing data numerik mentah dari form
            int lamaSewa = Integer.parseInt(lamaSewaStr);
            int totalHarga = Integer.parseInt(totalHargaStr);
            
            // 3. Bungkus semua data ke dalam Objek Model
            RentalTransaction rentalBaru = new RentalTransaction();
            rentalBaru.setIdCustomer(idCust);
            rentalBaru.setIdVehicle(idMobil);
            rentalBaru.setIdUser(idUser); // Otomatis sinkron dengan ID dari login Ahmad
            rentalBaru.setLamaSewa(lamaSewa);
            rentalBaru.setTotalHarga(totalHarga);
            
            // 4. Parsing format String Tanggal ke tipe java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            rentalBaru.setTglPinjam(sdf.parse(tglPinjamStr));
            
            // 5. Tembak ke database menggunakan method DAO kelompok
            return rentalDAO.simpanTransaksi(rentalBaru);
            
        } catch (Exception e) {
            System.out.println("Eror Validasi/Parsing di Controller: " + e.getMessage());
            return false;
        }
    }

    // Meneruskan perintah hapus dari tombol delete ke SQL
    public boolean hapusTransaksi(int idRental) {
        return rentalDAO.hapusTransaksi(idRental);
    }
}