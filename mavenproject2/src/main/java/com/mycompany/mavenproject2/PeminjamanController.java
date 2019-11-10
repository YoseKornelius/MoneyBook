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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.Action;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PeminjamanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private ImageView imgHome;
    private String iduser,namauser,dompet,idDompet,idKategori;
    
    @FXML
    private TextField txtKeteranganPeminjaman, txtNominalPeminjaman;
    @FXML
    private DatePicker tgl_peminjaman;
    @FXML
    public ComboBox<String> cbTambahKategori;
    
    @FXML
    private Label lbNama, lbId, lbNamaDompet;
    
    ObservableList<String> list = FXCollections.observableArrayList();
    
    @FXML
    public void pindahHome(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), iduser, dompet);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void setIdandName(String iduser, String Username, String dompet){
        this.iduser = iduser;
        this.namauser = Username;
        lbNama.setText(namauser);
        lbId.setText(this.iduser);
        this.dompet = dompet;
        lbNamaDompet.setText(this.dompet);
         System.out.println(iduser +"a");
            System.out.println("/n");
            System.out.println(this.dompet);
        
        
    }
    
    
    @FXML
    public void setIsiKategori(MouseEvent event){
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try{
            statement = connection.createStatement();
            System.out.println(iduser +"a");
            System.out.println("/n");
            System.out.println(this.dompet);
            
            
//            System.out.println(IbNamaDompet);

            String query = "SELECT id_dompet from dompet where nama_dompet='"+lbNamaDompet.getText()+"' and id_user = '"+iduser+"'";
            
            System.out.println("3a");
            ResultSet rs = statement.executeQuery(query);
//            if(rs.next()){
//                System.out.println("abs");}
            System.out.println("3b");
            
            while(rs.next()){
                idDompet = rs.getString(1);
                System.out.println(rs.getString(1)+"3");
                //String tamp = rs.getString(1);
                
            }
            
        }catch (SQLException ex){
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list.clear();
            statement = connection.createStatement();
            String query = "SELECT nama_kategori from kategori where id_dompet='"+idDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                list.add(rs.getString(1));
            }
            cbTambahKategori.setItems(list);            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void getIdKategori(ActionEvent event) throws Exception{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try{
            statement = connection.createStatement();
            String query = "SELECT id_kategori from kategori where nama_kategori ='"+cbTambahKategori.getValue()+"' and id_dompet = '"+idDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                idKategori = rs.getString(1);
            }
            System.out.println("berhasil dapat id kategori : " + idKategori);
        }catch (SQLException ex){
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tambahPeminjaman (ActionEvent event) throws SQLException{ 
       // String ket = txtKeteranganPengeluaran.getText();
        try{           
            Connection conn = sqliteConnect.connect().Connector();
            System.out.println("tes1");
            Statement statement;
            System.out.println("tes2");
            statement = conn.createStatement();
            System.out.println("tes3");
            String query = "INSERT INTO peminjaman(id_dompet, id_kategori, nama_pinjaman, nominal_pinjaman, tanggal_pinjaman) VALUES ('"+idDompet+"',"
                    + " '"+idKategori+"',"
                    + " '"+txtKeteranganPeminjaman.getText().toString()+"',"
                    + " '"+txtNominalPeminjaman.getText()+"',"
                    + " '"+tgl_peminjaman.getValue()+"' )";
            System.out.println("tes4");
            System.out.println(query);
            int hasil = statement.executeUpdate(query);
            System.out.println("tes5");
            if(hasil == 1){
                System.out.println("berhasil tambah pinjaman");
                txtKeteranganPeminjaman.clear();
                txtNominalPeminjaman.clear();
            }            
        }catch (SQLException ex){
            ex.getMessage();
            
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
}
