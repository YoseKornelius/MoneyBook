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
    private Button btnTambahPengeluaran, tombolCari;       
    
    @FXML
    private ImageView dompet;
    
    @FXML
    private ImageView imgTambahPengeluaran, gmbrSetting, kategori, imgPemasukkan, imgPengeluaran, imgPeminjaman, imgKategori;
    
    @FXML
    private String userName, idUser, tampJenis, tampKategori;
    
    @FXML
    private int tampBulan;
    
    @FXML
    private ComboBox<String> cbPilihDompet;
    
    @FXML
    private ComboBox<String> comboJenis;
    
    @FXML
    private ComboBox<String> comboBulan;
    
    @FXML
    private ComboBox<String> comboKategori;
    
    ObservableList<String> listDompet = FXCollections.observableArrayList();
    ObservableList<String> listJenis = FXCollections.observableArrayList();
    ObservableList<String> listBulan = FXCollections.observableArrayList();
    ObservableList<String> listKategori = FXCollections.observableArrayList();
    public String namaDompet;
    public int idDompet;
    
    @FXML
    public void isiComboJenis(MouseEvent event){
        listJenis.clear();
        listJenis.add("Pemasukkan");
        listJenis.add("Pengeluaran");
        listJenis.add("Peminjaman");
        comboJenis.setItems(listJenis);
    }
    @FXML
    public void pilihJenis(ActionEvent event){
        tampJenis = comboJenis.getValue().toString();
    }
    
    @FXML
    public void isiComboBulan(MouseEvent event){
        listBulan.clear();
        listBulan.add("Januari");
        listBulan.add("Februari");
        listBulan.add("Maret");
        listBulan.add("April");
        listBulan.add("Mei");
        listBulan.add("Juni");
        listBulan.add("Juli");
        listBulan.add("Agustus");
        listBulan.add("September");
        listBulan.add("Oktober");
        listBulan.add("November");
        listBulan.add("Desember");
        comboBulan.setItems(listBulan);
    }
    @FXML
    public void pilihBulan(ActionEvent event){
        if(comboJenis.getValue()=="Januari"){
            tampBulan = 1;
        }else if(comboJenis.getValue()=="Februari"){
            tampBulan = 2;
        }else if(comboJenis.getValue()=="Maret"){
            tampBulan = 3;
        }else if(comboJenis.getValue()=="April"){
            tampBulan = 4;
        }else if(comboJenis.getValue()=="Mei"){
            tampBulan = 5;
        }else if(comboJenis.getValue()=="Juni"){
            tampBulan = 6;
        }else if(comboJenis.getValue()=="Juli"){
            tampBulan = 7;
        }else if(comboJenis.getValue()=="Agustus"){
            tampBulan = 8;
        }else if(comboJenis.getValue()=="September"){
            tampBulan = 9;
        }else if(comboJenis.getValue()=="Oktober"){
            tampBulan = 10;
        }else if(comboJenis.getValue()=="November"){
            tampBulan = 11;
        }else if(comboJenis.getValue()=="Desember"){
            tampBulan = 12;
        }
    }
    
    @FXML
    public void isiComboKategori(MouseEvent event){
        listKategori.clear();
        comboKategori.autosize();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where id_user='"+idUser+"' and nama_dompet='"+namaDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                idDompet = Integer.parseInt(rs.getString(1));
                query = "SELECT nama_kategori from kategori where id_dompet='"+idDompet+"'";
                rs = statement.executeQuery(query);
                while(rs.next()){
                    listKategori.add(rs.getString(1));
                }
                comboKategori.setItems(listKategori);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void pilihKategori(ActionEvent event){
        this.tampKategori = comboKategori.getValue();
    }
    
    @FXML
    public void cari(ActionEvent event){
        
    }
    
    @FXML
    public void cekDompet(MouseEvent event){
        listDompet.clear();
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
    public void pindahPemasukkan(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pemasukkan.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        PemasukkanController pemasukkan = loader.getController();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void pindahPengeluaran(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pengeluaran.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        PengeluaranController pengeluaran = loader.getController();
        System.out.println(namaDompet);
        pengeluaran.setIdandName(idUser, lbNama.getText(), namaDompet);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void pindahPeminjaman(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/peminjaman.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        //PeminjamanController peminjaman = loader.getController();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void pindahKategori(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Kategori.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        //PeminjamanController peminjaman = loader.getController();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        listKategori.clear();
        comboKategori.autosize();
    }
    /**
     * Initializes the controller class.
     */  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        namaDompet = cbPilihDompet.getValue();
     //   System.out.println(namaDompet);
    }    

    public void setLabelUsername(String username, String id, String dompet){
        userName = username;
        idUser = id;
        lbNama.setText(userName);
        cbPilihDompet.setValue(dompet);
        //System.out.println(dompet);
        namaDompet=dompet;
        System.out.println(namaDompet);
    }
    public void setIdUser(String user){
        idUser = user;
    }
    
    

}













































