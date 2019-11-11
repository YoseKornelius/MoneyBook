/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.util.Date;

/**
 *
 * @author msi
 */
public class dataPencarian {
    private String tanggal, jenis, kategori, nama, lunas;
    private int nominal;
    public dataPencarian(String tanggal, String jenis, String kategori,String nama, int nominal){
        this.tanggal=tanggal;
        this.jenis=jenis;
        this.kategori=kategori;
        this.nama=nama;
        this.nominal=nominal;
    }
    public dataPencarian(String tanggal, String jenis, String kategori, String nama, int nominal, String lunas){
        this.tanggal=tanggal;
        this.jenis=jenis;
        this.kategori=kategori;
        this.nominal=nominal;
        this.nama=nama;
        this.lunas=lunas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
   
    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
    
    public String getLunas() {
        return lunas;
    }

    public void setLunas(String lunas) {
        this.lunas = lunas;
    }
}








