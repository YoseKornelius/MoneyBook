package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class PilihDompetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public ComboBox<String> dompet;
    
    ObservableList<String> list = FXCollections.observableArrayList();
    
    public String user, idUser, namaDompet;
    
    @FXML
    public void update(ActionEvent event){
        this.namaDompet = dompet.getValue();
        System.out.println(namaDompet);
    }
    
    @FXML
    public void tombolPilih(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            HomeController home = loader.getController();
            home.setLabelUsername(user, idUser,namaDompet);
            home.getNamaDompet(dompet.getValue());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void setIsi(MouseEvent event){
        list.clear();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT nama_dompet from dompet where id_user='"+idUser+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                list.add(rs.getString(1));
            }
            dompet.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    public void setUsername(String user, String idUser){
        this.user = user;
        this.idUser = idUser;
    }
    
}















