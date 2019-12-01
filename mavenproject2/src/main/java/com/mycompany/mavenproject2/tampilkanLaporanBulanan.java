/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author msi
 */
public class tampilkanLaporanBulanan {
    private String tanggal;
    private String nama;
    private String nominal;
    private String tanggalPengembalian;
    private String lunas;
    public tampilkanLaporanBulanan(String tanggal, String nama, String nominal, String tanggalPengembalian, String lunas){
        this.tanggal=tanggal;
        this.nama=nama;
        this.nominal=nominal;
        this.tanggalPengembalian=tanggalPengembalian;
        this.lunas=lunas;
    }
    public tampilkanLaporanBulanan(String tanggal, String nama, String nominal){
        this.tanggal=tanggal;
        this.nama=nama;
        this.nominal=nominal;
    }
    
    /**
     * @return the tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the nominal
     */
    public String getNominal() {
        return nominal;
    }

    /**
     * @param nominal the nominal to set
     */
    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    /**
     * @return the tanggalPengembalian
     */
    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    /**
     * @param tanggalPengembalian the tanggalPengembalian to set
     */
    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
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






