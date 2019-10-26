package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RegisterController implements Initializable {
    @FXML
    private Label lbKembali;
    
    @FXML
    private Button btnKembali;
    
    @FXML
    private TextField user;
    
    @FXML
    private TextField e;
    
    @FXML
    private PasswordField pass;
    
    @FXML
    private PasswordField konfpass;
    
    @FXML
    private Label cek;
    /**
     * Initializes the controller class.
     * @param event
     * @throws java.lang.Exception
     */
    @FXML
    public void regis(ActionEvent event) throws Exception{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String username = user.getText();
        String email = e.getText();
        String password = pass.getText();
        String konfirmasi = konfpass.getText();
        if(password.equals(konfirmasi)){
            String query = "INSERT INTO user(username,email,password) VALUES('"+username+"','"+email+"','"+password+"')";
            int hasil = statement.executeUpdate(query);
            if(hasil==1){
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/newLogin.fxml"));        
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }else{
            cek.setText("Password tidak sesuai");
        }
        
        
 
    }
    
    public void klikKembali(ActionEvent event) throws Exception{                
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/newLogin.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }        
}














