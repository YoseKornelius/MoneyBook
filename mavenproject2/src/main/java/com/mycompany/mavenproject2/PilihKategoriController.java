package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PilihKategoriController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Label lbNama, lbAdaDompet;    
    @FXML
    private Button btnTambahPengeluaran;       
    
    @FXML
    private ImageView imgPemasukkan;
    
    @FXML
    private ImageView dompet;
    
    @FXML
    private ImageView imgTambahPengeluaran, gmbrSetting, kategori;
    
    @FXML
    private String userName, idUser;
    
    @FXML
    private ComboBox<String> cbPilihDompet;
    
    ObservableList<String> listDompet = FXCollections.observableArrayList();
    public String namaDompet;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setLabelUsername(String username, String id, String dompet){
        userName = username;
        idUser = id;
        lbNama.setText(userName);
        cbPilihDompet.setValue(dompet);
        namaDompet=dompet;
    }
    
}
