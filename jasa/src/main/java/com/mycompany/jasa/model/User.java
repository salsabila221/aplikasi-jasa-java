package com.mycompany.jasa.model;

/** Data akun pengguna JSBJ. Password tidak disimpan di dalam object model. */
public class User {

    public enum Role {
        PELANGGAN("Pelanggan"), DRIVER("Driver"), ADMIN("Admin");

        private final String databaseValue;

        Role(String databaseValue) {
            this.databaseValue = databaseValue;
        }

        public String getDatabaseValue() {
            return databaseValue;
        }

        public static Role fromDatabase(String value) {
            for (Role role : values()) {
                if (role.databaseValue.equalsIgnoreCase(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Role tidak dikenal: " + value);
        }
    }

    private final int idUser;
    private String nama;
    private String email;
    private String noTelepon;
    private final Role role;

    public User(int idUser, String nama, String email, String noTelepon, Role role) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
        this.role = role;
    }

    public int getIdUser() { return idUser; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public Role getRole() { return role; }
}
