package com.example.sakatap.Models;

import com.google.firebase.firestore.Exclude;

public class Bangunan {
    private String bangunanid;
    private String judul;
    private String narasi;
    private String guide;

    public Bangunan() {

    }

    public Bangunan(String judul, String narasi) {
        this.judul = judul;
        this.narasi = narasi;
        this.guide = guide;
    }

    @Exclude
    public String getBangunanid() {
        return bangunanid;
    }

    public void setBangunanid(String bangunanid) {
        this.bangunanid = bangunanid;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNarasi() {
        return narasi;
    }

    public void setNarasi(String narasi) {
        this.narasi = narasi;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
