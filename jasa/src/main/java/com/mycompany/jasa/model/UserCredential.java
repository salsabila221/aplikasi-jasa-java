package com.mycompany.jasa.model;

/** Data hasil query autentikasi; hash hanya dipakai controller dan tidak ditampilkan ke UI. */
public class UserCredential {

    private final User user;
    private final String passwordHash;

    public UserCredential(User user, String passwordHash) {
        this.user = user;
        this.passwordHash = passwordHash;
    }

    public User getUser() { return user; }
    public String getPasswordHash() { return passwordHash; }
}
