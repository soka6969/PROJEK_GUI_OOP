package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import model.RentalTransaction;
import config.DBConnection; 
import java.util.List;      
import java.util.ArrayList;

public class RentalDAO {
    private Connection connection;

    public RentalDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public boolean hapusTransaksi(int idRental) {
    PreparedStatement ps = null;
    String sql = "DELETE FROM rentals WHERE id_rental = ?";
    
    try {
        ps = connection.prepareStatement(sql);
        ps.setInt(1, idRental);
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; 
    } catch (SQLException e) {
        System.out.println("Gagal hapus data di SQL: " + e.getMessage());
        return false;
    } finally {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    public List<String[]> getAvailableVehicles() {
        List<String[]> listKendaraan = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id_vehicle, merek, no_plat, harga_sewa FROM vehicles WHERE status = 'Tersedia'";
    
    try {
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String id = String.valueOf(rs.getInt("id_vehicle"));
            String merk = rs.getString("merek");    
            String plat = rs.getString("no_plat");  
            String harga = String.valueOf(rs.getInt("harga_sewa"));
            
            listKendaraan.add(new String[]{id, merk, plat, harga});
        }
    } catch (SQLException e) {
        System.out.println("Gagal ambil data kendaraan: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listKendaraan;
}

    public List<String[]> getCustomerList() {
        List<String[]> listCustomer = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id_customer, nama FROM customers";
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id_customer"));
                String nama = rs.getString("nama");
                listCustomer.add(new String[]{id, nama});
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data customer: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listCustomer;
    }
    
    public boolean simpanTransaksi(RentalTransaction rental) {
        PreparedStatement psRental = null;
        PreparedStatement psVehicle = null;
        
        String sqlRental = "INSERT INTO rentals (id_customer, id_vehicle, id_user, tgl_pinjam, lama_sewa, total_harga) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdateVehicle = "UPDATE vehicles SET status = 'Disewa' WHERE id_vehicle = ?";
        
        try {
            connection.setAutoCommit(false); 
            
            psRental = connection.prepareStatement(sqlRental);
            psRental.setInt(1, rental.getIdCustomer());
            psRental.setInt(2, rental.getIdVehicle());
            psRental.setInt(3, rental.getIdUser());
            psRental.setDate(4, new java.sql.Date(rental.getTglPinjam().getTime()));
            psRental.setInt(5, rental.getLamaSewa());
            psRental.setInt(6, rental.getTotalHarga());
            psRental.executeUpdate();
            
            psVehicle = connection.prepareStatement(sqlUpdateVehicle);
            psVehicle.setInt(1, rental.getIdVehicle());
            psVehicle.executeUpdate();
            
            connection.commit();
            return true;
                
            
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); 
                }
            } catch (SQLException ex) {
                System.out.println("Rollback gagal: " + ex.getMessage());
            }
            System.out.println("Transaksi gagal disave: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psRental != null) psRental.close();
                if (psVehicle != null) psVehicle.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<String[]> getRentalTransactionList() {
    List<String[]> listTransaksi = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;
    // menggabungkan tabel rentals, customers, dan vehicles sesuai database kelompokmu
    String sql = "SELECT r.id_rental, c.nama, v.no_plat, r.tgl_pinjam, r.lama_sewa, r.total_harga " +
                 "FROM rentals r " +
                 "JOIN customers c ON r.id_customer = c.id_customer " +
                 "JOIN vehicles v ON r.id_vehicle = v.id_vehicle " +
                 "ORDER BY r.id_rental DESC";
        try {
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String nota = String.valueOf(rs.getInt("id_rental"));
            String pelanggan = rs.getString("nama");
            String kendaraan = rs.getString("no_plat");
            String tgl = String.valueOf(rs.getDate("tgl_pinjam"));
            String lama = String.valueOf(rs.getInt("lama_sewa")) + " Hari";
            String total = String.valueOf(rs.getInt("total_harga"));
            listTransaksi.add(new String[]{nota, pelanggan, kendaraan, tgl, lama, total});
        }
    } catch (SQLException e) {
        System.out.println("Gagal ambil data transaksi: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listTransaksi;
    }
}