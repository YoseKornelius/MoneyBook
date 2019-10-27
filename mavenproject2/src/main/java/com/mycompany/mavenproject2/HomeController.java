/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HomeController implements Initializable {
    @FXML
    private Label lbNama;    
    
    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void myFunction(String username){
        lbNama.setText(username);
    }
    
}
