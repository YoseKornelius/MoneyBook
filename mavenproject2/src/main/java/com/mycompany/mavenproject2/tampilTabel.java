/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author ASUS
 */
public class tampilTabel {

    private String tgl;
    private String kategori;
    private String keterangan;
    private String nominal;
    private String tglPengembalian;
    private String lunas;

    public tampilTabel(String tgl, String kategori, String keterangan, String nominal, String tglPengembalian, String lunas) {
        this.tgl = tgl;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.nominal = nominal;
        this.tglPengembalian = tglPengembalian;
        this.lunas=lunas;
    }
    public tampilTabel(String tgl, String kategori, String keterangan, String nominal) {
        this.tgl = tgl;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    /**
     * @return the tglPengembalian
     */
    public String getTglPengembalian() {
        return tglPengembalian;
    }

    /**
     * @param tglPengembalian the tglPengembalian to set
     */
    public void setTglPengembalian(String tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    /**
     * @return the lunas
     */
    public String getLunas() {
        return lunas;
    }

    /**
     * @param lunas the lunas to set
     */
    public void setLunas(String lunas) {
        this.lunas = lunas;
    }
    

}





