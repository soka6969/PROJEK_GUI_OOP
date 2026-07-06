/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ASUS
 */

import dao.VehicleDAO;
import model.Vehicle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleController {

    private VehicleDAO vehicleDAO;

    public VehicleController() {
        vehicleDAO = new VehicleDAO();
    }

    // ===================== TAMPILKAN DATA =====================
    public List<Vehicle> ambilDataKendaraan() {
        return vehicleDAO.getAllVehicles();
    }

    // ===================== SIMPAN =====================
    public boolean simpanKendaraan(Vehicle kendaraan) {

        if (kendaraan == null) {
            return false;
        }

        if (kendaraan.getNoPlat().trim().isEmpty()
                || kendaraan.getMerek().trim().isEmpty()
                || kendaraan.getTipe().trim().isEmpty()
                || kendaraan.getStatus().trim().isEmpty()
                || kendaraan.getHargaSewa() <= 0) {

            return false;
        }

        return vehicleDAO.simpanKendaraan(kendaraan);
    }

    // ===================== UPDATE =====================
    public boolean updateKendaraan(Vehicle kendaraan, int id_kendaraan) {

        if (kendaraan == null) {
            return false;
        }

        if (id_kendaraan == 0) {
            return false;
        }

        if (kendaraan.getNoPlat().trim().isEmpty()
                || kendaraan.getMerek().trim().isEmpty()
                || kendaraan.getTipe().trim().isEmpty()
                || kendaraan.getStatus().trim().isEmpty()
                || kendaraan.getHargaSewa() <= 0) {

            return false;
        }

        return vehicleDAO.updateKendaraan(kendaraan, id_kendaraan);
    }

    // ===================== DELETE =====================
    public boolean hapusKendaraan(int idVehicle) {

        if (idVehicle <= 0) {
            return false;
        }

        return vehicleDAO.hapusKendaraan(idVehicle);
    }

    // ===================== SEARCH =====================
    public List<Vehicle> cariKendaraan(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ambilDataKendaraan();
        }

        return vehicleDAO.cariKendaraan(keyword);
    }

    // ===================== SORT TERMURAH =====================
    public List<Vehicle> urutHargaNaik() {

        List<Vehicle> list = vehicleDAO.getAllVehicles();

        Collections.sort(list, Comparator.comparingInt(Vehicle::getHargaSewa));

        return list;
    }

    // ===================== SORT TERMAHAL =====================
    public List<Vehicle> urutHargaTurun() {

        List<Vehicle> list = vehicleDAO.getAllVehicles();

        Collections.sort(list,
                (v1, v2) -> Integer.compare(v2.getHargaSewa(), v1.getHargaSewa()));

        return list;
    }
}