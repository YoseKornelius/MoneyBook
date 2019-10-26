package com.mycompany.mavenproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.sql.*;
import javafx.scene.control.*;

public class FXMLController implements Initializable {
    
    @FXML
    private TextField user;
    
    @FXML
    private PasswordField pass;
    
    @FXML
    private Label Status;
    
    @FXML
    private TextArea txt;
    
    @FXML
    private void tombolLogin(ActionEvent event) throws SQLException {
        
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        System.out.print("bisa akses db");
        String username = user.getText();
        String password = pass.getText();
        String query = "SELECT * from user where username ='"+username+"' and password ='"+password+"'";
        ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                txt.setText(query);
                Status.setText("berhasil");
               /* notif.setText("Berhasil Masuk") */
            }else{
                txt.setText("GAGAL KONTOL");
                Status.setText("GAGAL SU...");
                /*notif.setText("Username atau Password anda Salah")*/
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}







































