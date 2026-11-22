package com.mycompany.jasa.model;

/** Model akun JSBJ; tidak memuat kode UI, SQL, atau aturan validasi. */
public class User {

    private int idUser;
    private String nama;
    private String email;
    private String noTelepon;
    private Role role;

    public User(int idUser, String nama, String email, String noTelepon, Role role) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
        this.role = role;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
