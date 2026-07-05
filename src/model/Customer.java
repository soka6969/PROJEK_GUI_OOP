/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author evr
 */
public class Customer {
    private int id;
    private String nama;
    private String nomorKTP;
    private String jenisKelamin;
    private String alamat;
    private String nomorTelepon;
    private String tanggalDaftar;

    public Customer() {}

    public Customer(int id, String nama, String nomorKTP, String jenisKelamin, String alamat, String nomorTelepon, String tanggalDaftar) {
        this.id = id;
        this.nama = nama;
        this.nomorKTP = nomorKTP;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.tanggalDaftar = tanggalDaftar;
    }

    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNama() { 
        return nama; 
    }
    
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    
    public String getNomorKTP() { 
        return nomorKTP; 
    }
    public void setNomorKTP(String nomorKTP) { 
        this.nomorKTP = nomorKTP; 
    }

    public String getJenisKelamin() { 
        return jenisKelamin; 
    }
    
    public void setJenisKelamin(String jenisKelamin) { 
        this.jenisKelamin = jenisKelamin; 
    }

    public String getAlamat() { 
        return alamat; 
    }
    
    public void setAlamat(String alamat) { 
        this.alamat = alamat; 
    }

    public String getNomorTelepon() { 
        return nomorTelepon; 
    }
    
    public void setNomorTelepon(String nomorTelepon) { 
        this.nomorTelepon = nomorTelepon; 
    }

    public String getTanggalDaftar() { 
        return tanggalDaftar; 
    }
    
    public void setTanggalDaftar(String tanggalDaftar) { 
        this.tanggalDaftar = tanggalDaftar; 
    }
    
}
