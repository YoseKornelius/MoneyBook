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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.font.TextLabel;

/**
 * FXML Controller class
 *
 * @author YOSE
 */
public class KategoriController implements Initializable {

    @FXML
    private Label lbNama, lbIdUser;
    private String namaDompet, kategoriHapus;
    
    @FXML
    private TextField tambahKategori, editKategori;
    
    @FXML
    private Button btnHapus, btnTambah, btnEdit;
    
    @FXML
    TableColumn<TampilanKategori, String> nama = new TableColumn("Nama Kategori");
    
    ObservableList<TampilanKategori> list = FXCollections.observableArrayList();
    
    @FXML
    private TableView<TampilanKategori> tblKategori;
    
    private int counter, idDompet;
    
    @FXML
    private ImageView katergori;
    
    public void pindahHome(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), lbIdUser.getText(), namaDompet);
        home.updateSaldo();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void hapuskategori(MouseEvent event){
        kategoriHapus = tblKategori.getSelectionModel().getSelectedItem().getNama();
        editKategori.setText(kategoriHapus);
    }
    @FXML
    public void hapus(ActionEvent event) throws SQLException{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement=connection.createStatement();
        String query="DELETE from kategori where id_dompet='"+idDompet+"' AND nama_kategori='"+kategoriHapus+"'";
        int hasil = statement.executeUpdate(query);
        if(hasil==1){
            System.out.println("Berhasil");
            tampilkanTable();
        }else{
            System.out.println("GAGAL");
        }
    }
    
    @FXML
    public void tambah(ActionEvent event) throws SQLException{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement=connection.createStatement();
        String yangDiTambah = tambahKategori.getText();
        String query="INSERT INTO kategori(id_dompet, nama_kategori) VALUES('"+idDompet+"','"+yangDiTambah+"')";
        int hasil = statement.executeUpdate(query);
        if(hasil==1){
            System.out.println("Berhasil");
            tampilkanTable();
            tambahKategori.setText("");
        }else{
            System.out.println("GAGAL");
        }
    }
    
    @FXML
    public void edit(ActionEvent event) throws SQLException{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement=connection.createStatement();
        String yangDiEdit = editKategori.getText();
        String query="UPDATE kategori SET nama_kategori='"+yangDiEdit+"' where id_dompet='"+idDompet+"' and nama_kategori='"+kategoriHapus+"'";
        int hasil = statement.executeUpdate(query);
        if(hasil==1){
            System.out.println("Berhasil");
            tampilkanTable();
        }else{
            System.out.println("GAGAL");
        }
    }
    

    public void tampilkanTable(){
        try {
            // TODO
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement = connection.createStatement();
        try {
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where id_user='"+lbIdUser.getText()+"' and nama_dompet='"+namaDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                idDompet = Integer.parseInt(rs.getString(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
            list.clear();
            tblKategori.getItems().clear();
            tblKategori.getColumns().clear();
            tblKategori.getColumns().addAll(nama);
            String hasil="SELECT nama_kategori from kategori where id_dompet='"+idDompet+"'";
            
            ResultSet rs = statement.executeQuery(hasil);
            while(rs.next()){
                list.add(new TampilanKategori(rs.getString(1)));
            }
            tblKategori.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(KategoriController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            this.nama.setMinWidth(130);
            this.nama.setCellValueFactory(new PropertyValueFactory("nama"));
    }    
    
    public void getNamaAndId(String username, String id, String namaDompet){
        this.lbNama.setText(username);
        this.lbIdUser.setText(id);
        this.namaDompet = namaDompet;
        System.out.println(username);
    }
}















































































