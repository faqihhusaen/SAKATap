package com.example.sakatap.Models;

public class Souvenir {

    private String nama;
    private String image;
    private String animasi;
    private int harga;

    public Souvenir() {

    }

    public Souvenir(String nama, String image, int harga, String animasi) {
        this.nama = nama;
        this.image = image;
        this.harga = harga;
        this.animasi = animasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getAnimasi() {
        return animasi;
    }

    public void setAnimasi(String animasi) {
        this.animasi = animasi;
    }
}
