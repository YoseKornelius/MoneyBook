package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NewLoginController implements Initializable {
    @FXML
    private TextField tfUsername;
    
    @FXML
    private PasswordField tpPassword;
    
    @FXML
    private Button btnLogin;
    private Button btnRegister;
    
    @FXML
    private void tombolLogin(ActionEvent event) throws SQLException, IOException {
        
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        System.out.print("bisa akses db");
        String username = tfUsername.getText();
        String password = tpPassword.getText();
        String query = "SELECT * from user where username ='"+username+"' and password ='"+password+"'";
        ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));        
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
               /* notif.setText("Berhasil Masuk") */
            }else{

                /*notif.setText("Username atau Password anda Salah")*/
            }
    }
    
    @FXML
    public void Register(ActionEvent event) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     
}



