package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NewDompetController implements Initializable {
    private String idUser;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnTambahDompet;
    
    @FXML
    private TextField tfNamaDompet;
    
    
    
    @FXML
    public void tambahDompet(ActionEvent event) throws Exception{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String namaDompet = tfNamaDompet.getText();   
        String id = idUser;
        String query = "INSERT INTO dompet(id_user, nama_dompet) VALUES('"+id+"','"+namaDompet+"')";
        int hasil = statement.executeUpdate(query);
        if(hasil==1){
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));        
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();        
        }else{
            
        }
        connection.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setIdUser(String user){
        idUser = user;
    }
    
}
