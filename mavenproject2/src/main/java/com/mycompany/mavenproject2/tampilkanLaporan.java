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
public class tampilkanLaporan {

    private String pemasukkan;
    private String tanggal;
    private String nama;
    private String nominal;
    private String tglPeminjaman;
    private String tglPengembalian;
    private String status;

    public tampilkanLaporan(String Tanggal, String Nama, String Nominal) {
        this.tanggal = Tanggal;
        this.nama = Nama;
        this.nominal = Nominal;
    }

    public tampilkanLaporan(String tanggal, String nama, String Nominal, String tglPengembalian, String status) {
        this.nama = nama;
        this.nominal = Nominal;
        this.tanggal = tanggal;
        this.tglPengembalian = tglPengembalian;
        this.status = status;
    }

    /**
     * @return the pemasukkan
     */

    public String getTanggal() {
        return tanggal;
    }

    public String getTglPeminjaman() {
        return tglPeminjaman;
    }

    public String getTglPengembalian() {
        return tglPengembalian;
    }

    public String getNama() {
        return nama;
    }

    public String getNominal() {
        return nominal;
    }

    public String getStatus() {
        return status;
    }


    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setTglPeminjaman(String tglPeminjaman) {
        this.tglPeminjaman = tglPeminjaman;
    }

    public void setTglPengembalian(String tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
