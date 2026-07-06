/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Ahmad
 */

import dao.UserDao;
import model.User;

public class UserController {
 
    // Role yang diperbolehkan pada sistem ini hanya dua ini.
    private static final String ROLE_USER    = "User";
    private static final String ROLE_PETUGAS = "Petugas";
 
    private final UserDao userDAO;
 
    // Session
    private static User currentUser;
 
    public UserController() {
        this.userDAO = new UserDao();
        userDAO.seedAdminIfNotExists();
    }
 
    // LOGIN
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException("Password tidak boleh kosong.");
 
        User user = userDAO.login(username.trim(), password.trim());
        if (user != null) {
            currentUser = user;
        }
        return user;
    }
 
    // REGISTER User baru (role hanya "User" atau "Petugas")
    public boolean register(String namaPetugas, String username,
                             String password, String role) {
        if (namaPetugas == null || namaPetugas.trim().isEmpty())
            throw new IllegalArgumentException("Nama petugas tidak boleh kosong.");
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        if (password == null || password.length() < 4)
            throw new IllegalArgumentException("Password minimal 4 karakter.");
        if (userDAO.usernameExists(username.trim()))
            throw new IllegalArgumentException("Username '" + username + "' sudah digunakan.");
        if (!ROLE_USER.equals(role) && !ROLE_PETUGAS.equals(role))
            throw new IllegalArgumentException("Role tidak valid. Pilih 'User' atau 'Petugas'.");
 
        return userDAO.createUser(namaPetugas.trim(), username.trim(),
                                   password.trim(), role);
    }
 
    // SESSION
    public static User getCurrentUser() { return currentUser; }
    public static void logout()         { currentUser = null; }
    public static boolean isLoggedIn()  { return currentUser != null; }
}
