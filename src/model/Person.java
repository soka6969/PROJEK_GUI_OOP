/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author evr
 */
public class Person {
      protected String id;
    protected String nama;
    protected String alamat;
    protected String telepon;
    
    public Person() {}

    public Person(String id, String nama, String alamat, String telepon) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
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

    public String getAlamat() { 
        return alamat; 
    }
    
    public void setAlamat(String alamat) { 
        this.alamat = alamat; 
    }

    public String getTelepon() { 
        return telepon; 
    }
    
    public void setTelepon(String telepon) { 
        this.telepon = telepon; }
    
}
