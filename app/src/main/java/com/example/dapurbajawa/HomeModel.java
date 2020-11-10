package com.example.dapurbajawa;

public class HomeModel {
    String  nama,notelp;

    public HomeModel(String nama, String notelp) {
        this.nama = nama;
        this.notelp = notelp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
}
