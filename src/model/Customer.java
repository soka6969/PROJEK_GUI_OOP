/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author evr
 */
public class Customer extends Person {
 
    private String id;
    private String nama;
    private String nomorKTP;
    private String alamat;
    private String nomorTelepon;

    public Customer() {
    }
    
    public Customer(String nama, String alamat, String noTelepon, String noKtp) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = noTelepon;
        this.nomorKTP = noKtp;
    }

    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
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

    public String getNoTelepon() {
        return nomorTelepon;
    }

    public String getNoKtp() {
        return nomorKTP;
    }   
}
