/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */

public class Vehicle {
    private int idVehicle;
    private String noPlat;
    private String merek;
    private String tipe;
    private int hargaSewa;
    private String status;

    // Constructor kosong
    public Vehicle() {}

    // Constructor lengkap
    public Vehicle(String noPlat, String merek, String tipe, int hargaSewa, String status) {
        this.idVehicle = idVehicle;
        this.noPlat = noPlat;
        this.merek = merek;
        this.tipe = tipe;
        this.hargaSewa = hargaSewa;
        this.status = status;
    }

    // Getter dan Setter
    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(int hargaSewa) {
        this.hargaSewa = hargaSewa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}