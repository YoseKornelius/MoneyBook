/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author YOSE
 */
public class TampilanAnggaran {
    private String nominal;
    private String kategori;
  
    public String getNominal() {
        return nominal;
    }

  
    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public TampilanAnggaran(String nominal, String kategori){
        this.nominal=nominal;
        this.kategori=kategori;
    }
}
