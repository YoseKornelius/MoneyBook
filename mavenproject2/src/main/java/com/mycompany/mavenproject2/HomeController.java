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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    
    private String idUser;
    
    @FXML
    public void tambahPengeluaran(ActionEvent event) throws SQLException, IOException {
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from dompet where id_user = '"+lbIdUser.getText()+"' ";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){
            lbAdaDompet.setText("ada dompet");
        }else{
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/newDompet.fxml"));        
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add("/styles/Styles.css");
//            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            window.setScene(scene);
//            window.show();
            System.out.println("Home"+lbIdUser.getText());
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
        connection.close();
    }
    
    /**
     * Initializes the controller class.
     */  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLabelUsername(String username, String id){
        lbNama.setText(username);
        lbIdUser.setText(id);
    }
    public void setIdUser(String user){
        idUser = user;
    }
    
}
