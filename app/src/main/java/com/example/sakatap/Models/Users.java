package com.example.sakatap.Models;

public class Users {
    private int poin;
    private String nama;
    private String kelamin;
    private String TTL;
    private String username;
    private String email;


    public Users () {}

    public Users(String username, String email, String nama, String kelamin, String TTL, int poin) {
        this.poin = poin;
        this.username = username;
        this.email = email;
        this.nama = nama;
        this.kelamin = kelamin;
        this.TTL = TTL;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getTTL() {
        return TTL;
    }

    public void setTTL(String TTL) {
        this.TTL = TTL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
