package com.mycompany.jasa.model;

/** Role pengguna yang disimpan di kolom users.role. */
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
            if (role.databaseValue.equalsIgnoreCase(value)) return role;
        }
        throw new IllegalArgumentException("Role tidak dikenal: " + value);
    }
}
