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
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Label lbNama, lbAdaDompet;    
    @FXML
    private Button btnTambahPengeluaran;       
    
    @FXML
    private ImageView imgPemasukkan;
    
    @FXML
    private ImageView dompet;
    
    @FXML
    private ImageView imgTambahPengeluaran, gmbrSetting;
    
    @FXML
    private String userName, idUser;
    
    @FXML
    private ComboBox<String> cbPilihDompet;
    
    ObservableList<String> listDompet = FXCollections.observableArrayList();
    public String namaDompet;
    
    @FXML
    public void cekDompet(MouseEvent event){
Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT nama_dompet from dompet where id_user='"+idUser+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                listDompet.add(rs.getString(1));
            }
            cbPilihDompet.setItems(listDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    public void tambahDompet(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newDompet.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        NewDompetController dompet = loader.getController();
        dompet.setIdUser(lbNama.getText(),idUser,namaDompet);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void tambahPengeluaran(MouseEvent event) throws SQLException, IOException {
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from dompet where id_user = '"+idUser+"' ";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){
            lbAdaDompet.setText("ada dompet");
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newDompet.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
                    
            NewDompetController dompet = loader.getController();
            dompet.setIdUser(lbNama.getText(),idUser);
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
        String query = "SELECT * from user where id_user = '"+idUser+"' ";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingProfile.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
                    
            SettingProfileController profil = loader.getController();
            profil.getProfile(rs.getString(2), rs.getString(3), rs.getString(4), idUser,namaDompet);
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
    
    public void pilih(ActionEvent event){
        System.out.println("BERHASIL UPDATE");
        this.namaDompet=cbPilihDompet.getValue();
    }
    /**
     * Initializes the controller class.
     */  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLabelUsername(String username, String id, String dompet){
        userName = username;
        idUser = id;
        lbNama.setText(userName);
        cbPilihDompet.setValue(dompet);
        namaDompet=dompet;
    }
    public void setIdUser(String user){
        idUser = user;
    }
    
    

}
























