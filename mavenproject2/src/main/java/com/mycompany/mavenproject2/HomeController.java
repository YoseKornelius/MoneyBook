/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HomeController implements Initializable {
    @FXML
    private Label lbNama, lbIdUser, lbAdaDompet;    
    @FXML
    private Button btnTambahPengeluaran;       
    
    @FXML
    private ImageView imgPemasukkan;
    
    @FXML
    private ImageView imgTambahPengeluaran, gmbrSetting;
    
    private String userName, idUser;
    
    
    
    @FXML
    public void tambahPengeluaran(MouseEvent event) throws SQLException, IOException {
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from dompet where id_user = '"+lbIdUser.getText()+"' ";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){
            lbAdaDompet.setText("ada dompet");
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newDompet.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
                    
            NewDompetController dompet = loader.getController();
            dompet.setIdUser(lbIdUser.getText());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        statement.close();
        connection.close();
        rs.close();
    }
    
    @FXML
    public void edit(MouseEvent event) throws Exception{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from user where id_user = '"+lbIdUser.getText()+"' ";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){                    
            System.out.println("Home"+lbIdUser.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingProfile.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
                    
            SettingProfileController profil = loader.getController();
            profil.getProfile(rs.getString(2), rs.getString(3), rs.getString(4), idUser);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else{
            System.out.println("gak ada user");
        }
        statement.close();
        connection.close();
        rs.close();
    }
    
    /**
     * Initializes the controller class.
     */  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLabelUsername(String username, String id){
        userName = username;
        idUser = id;
        lbNama.setText(userName);
        lbIdUser.setText(id);
    }
    public void setIdUser(String user){
        idUser = user;
    }
    
    

}

