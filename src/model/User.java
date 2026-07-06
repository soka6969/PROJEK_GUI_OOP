/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class User {

    private int    idUser;
    private String namaPetugas;
    private String username;
    private String password;
    private String role;
 
    public User(int idUser, String namaPetugas,
                String username, String password, String role) {
        this.idUser      = idUser;
        this.namaPetugas = namaPetugas;
        this.username    = username;
        this.password    = password;
        this.role        = role;
    }
 
    public int    getIdUser()      { return idUser; }
    public String getNamaPetugas() { return namaPetugas; }
    public String getUsername()    { return username; }
    public String getPassword()    { return password; }
    public String getRole()        { return role; }
 

    public boolean isPetugas() { return "Petugas".equals(role); }
    public boolean isUser()    { return "User".equals(role); }
 
    @Override
    public String toString() { return namaPetugas + " (" + role + ")"; }
    
}
