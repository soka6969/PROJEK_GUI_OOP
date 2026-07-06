package model;
import java.util.Date;

public class RentalTransaction {
    private int idCustomer;
    private int idVehicle;
    private int idUser;
    private Date tglPinjam;
    private int lamaSewa;
    private int totalHarga;
    // Constructor kosong
    public RentalTransaction() {}
    // Constructor lengkap
    public RentalTransaction(int idCustomer, int idVehicle, int idUser, Date tglPinjam, int lamaSewa, int totalHarga) {
        this.idCustomer = idCustomer;
        this.idVehicle = idVehicle;
        this.idUser = idUser;
        this.tglPinjam = tglPinjam;
        this.lamaSewa = lamaSewa;
        this.totalHarga = totalHarga;
    }
    // Getter dan Setter 
    public int getIdCustomer() { return idCustomer; }
    public void setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }
    public int getIdVehicle() { return idVehicle; }
    public void setIdVehicle(int idVehicle) { this.idVehicle = idVehicle; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public Date getTglPinjam() { return tglPinjam; }
    public void setTglPinjam(Date tglPinjam) { this.tglPinjam = tglPinjam; }
    public int getLamaSewa() { return lamaSewa; }
    public void setLamaSewa(int lamaSewa) { this.lamaSewa = lamaSewa; }
    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }
}