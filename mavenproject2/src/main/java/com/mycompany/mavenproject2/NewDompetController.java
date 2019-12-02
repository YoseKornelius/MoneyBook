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
    private String user;
    private String dompet;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnTambahDompet, btnKembali;
    
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            HomeController home = loader.getController();
            home.setLabelUsername(user,idUser,dompet);
            home.updateSaldo();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();        
        }else{
            
        }
        statement.close();
        connection.close();
    }
    
    public void kembali(ActionEvent event) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            HomeController home = loader.getController();
            home.setLabelUsername(user, idUser,dompet);
            home.updateSaldo();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setIdUser(String user, String iduser, String dompet){
        this.idUser = iduser;
        this.user=user;
        this.dompet=dompet;
    }
    public void setIdUser(String user, String iduser){
        this.idUser = iduser;
        this.user=user;
    }
    
}






