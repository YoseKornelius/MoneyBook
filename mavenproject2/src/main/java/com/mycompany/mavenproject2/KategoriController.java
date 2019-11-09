package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author YOSE
 */
public class KategoriController implements Initializable {

    @FXML
    private Label lbNama, lbIdUser;  
    
    @FXML
    private ImageView katergori, gmbrSetting;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void getNamaAndId(String username, String id){
        lbNama.setText(username);
        lbIdUser.setText(id);
    }
}
