
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.Action;


public class AnggaranController implements Initializable {
    
    @FXML
    private ImageView imgHome;
    private String  idDompet,namaUser, dompet, idKategori, tampNama, tampNominal;
    
    @FXML
    private String userName, idUser, tampJenis, tampKategori,tampKategori1;
    @FXML
    private Label lbNama, lbId, lbNamaDompet;

    
    @FXML
    private TextField txtNominalAnggaran,editAnggaran;

    @FXML
    TableColumn<TampilanAnggaran, String> nominal = new TableColumn("Nominal");
    
    @FXML
    TableColumn<TampilanAnggaran, String> namaKategori = new TableColumn("Nama kategori");
    
    @FXML
    private ComboBox<String> cbPilihDompet;
    
    @FXML
    private ComboBox<String> comboKategori;
    
    @FXML
    private ComboBox<String> comboKategori1;
    
    @FXML
    private TableView<TampilanAnggaran> tblAnggaran;
    
    

   ObservableList<String> listKategori = FXCollections.observableArrayList();
   ObservableList<String> listKategori1 = FXCollections.observableArrayList();
   ObservableList<String> listDompet = FXCollections.observableArrayList();
   ObservableList<TampilanAnggaran> list = FXCollections.observableArrayList();
   
   public String namaDompet, kategoriHapus;
   public int idDompet1;
    
    public void getNamaDompet(String nama){
        this.namaDompet = nama;
        System.out.println("nama ini dompet : " + namaDompet);
        System.out.println("nama checkbox dompet : "+ cbPilihDompet.getValue());
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
    
    public void pilih(ActionEvent event){
        System.out.println("BERHASIL UPDATE");
        this.namaDompet=cbPilihDompet.getValue();
        listKategori.clear();
        comboKategori.autosize();
        
        //updateSaldo();
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
            System.out.println(query);
            while(rs.next()){
                idDompet1 = Integer.parseInt(rs.getString(1));
                query = "SELECT nama_kategori from kategori where id_dompet='"+idDompet+"'";
                rs = statement.executeQuery(query);
                System.out.println(query);
                while(rs.next()){
                    listKategori.add(rs.getString(1));
                    
                    System.out.println("7");
                }
                comboKategori.setItems(listKategori);
                System.out.println(listKategori);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void pilihKategori(ActionEvent event){
        System.out.println("70");
        this.tampKategori = comboKategori.getValue();
        System.out.println(tampKategori);
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT id_kategori from kategori where nama_kategori ='" + comboKategori.getValue() + "' and id_dompet = '" + idDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idKategori = rs.getString(1);
            }
            System.out.println("berhasil dapat id kategori : " + idKategori);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void isiComboKategori1(MouseEvent event){
        listKategori1.clear();
        comboKategori1.autosize();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where id_user='"+idUser+"' and nama_dompet='"+namaDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                idDompet1 = Integer.parseInt(rs.getString(1));
                query = "SELECT nama_kategori from kategori where id_dompet='"+idDompet+"'";
                rs = statement.executeQuery(query);
                while(rs.next()){
                    listKategori1.add(rs.getString(1));
                }
                comboKategori1.setItems(listKategori);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void pilihKategori1(ActionEvent event){
        System.out.println("8");
        this.tampKategori1 = comboKategori1.getValue();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT id_kategori from kategori where nama_kategori ='" + comboKategori.getValue() + "' and id_dompet = '" + idDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idKategori = rs.getString(1);
            }
            System.out.println("berhasil dapat id kategori : " + idKategori);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML
    public void tambahAnggaran(ActionEvent event) throws SQLException {
        // String ket = txtKeteranganPengeluaran.getText();
        try {
            Connection conn = sqliteConnect.connect().Connector();
            System.out.println("1");
            Statement statement;
            System.out.println("2");
            statement = conn.createStatement();
            System.out.println("3");
            String query = "INSERT INTO anggaran(Id_kategori,nominal, Id_dompet) VALUES ('" + idKategori + "',"
               
                   
                    + " '" + txtNominalAnggaran.getText() + "',"
                    + " '" +  idDompet + "' )";
            System.out.println(query);
            int hasil = statement.executeUpdate(query);
            if (hasil == 1) {
                System.out.println("berhasil tambah anggaran");
                txtNominalAnggaran.clear();
                
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    @FXML
    public void hapusanggaran(MouseEvent event){
        kategoriHapus = tblAnggaran.getSelectionModel().getSelectedItem().getNominal();
        editAnggaran.setText(kategoriHapus);
    }
    
    @FXML
    public void hapusAnggaran(ActionEvent event) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog"); 
        alert.setHeaderText(null);
        alert.setContentText("are you sure want to delete");
        Optional <ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK){
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement=connection.createStatement();
            String query="DELETE from anggaran where id_dompet='"+idDompet+"' AND nominal='"+kategoriHapus+"'";
            int hasil = statement.executeUpdate(query);
            if(hasil==1){
                System.out.println("Berhasil");
                tampilkanTable();
            }else{
                System.out.println("GAGAL");
            }
        }
    }
    
    @FXML
    public void editAnggaran(ActionEvent event) throws SQLException{
        System.out.println("editAnggaran.");
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement=connection.createStatement();
        String yangDiEdit = editAnggaran.getText();
        System.out.println(yangDiEdit);
        System.out.println(editAnggaran.getText());
        String query="UPDATE anggaran SET nominal='"+yangDiEdit+"' where id_dompet='"+idDompet+"' and nominal='"+kategoriHapus+"'";
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
            String query = "SELECT id_dompet from dompet where id_user='"+lbId.getText()+"' and nama_dompet='"+namaDompet+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                idDompet1 = Integer.parseInt(rs.getString(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
            list.clear();
            tblAnggaran.getItems().clear();
            tblAnggaran.getColumns().clear();
            tblAnggaran.getColumns().addAll(nominal,namaKategori);
            String hasil="SELECT nominal from anggaran where id_dompet='"+idDompet1+"'";
            
            
            ResultSet rs = statement.executeQuery(hasil);
            while(rs.next()){
                String hasil1 = "SELECT nama_kategori from kategori where id_dompet='"+idDompet1+"'and id_kategori='"+namaDompet+"'";
                ResultSet rsp = statement.executeQuery(hasil1);
                while (rsp.next()) {
                    
                list.add(new TampilanAnggaran(rs.getString("nominal"), rsp.getString("nama_kategori")));    
                }
                
            }
            tblAnggaran.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(KategoriController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    

    public void setIdandName(String iduser, String Username, String dompet) {
        this.idUser = iduser;
        this.namaUser = Username;
        lbNama.setText(namaUser);
        lbId.setText(iduser);
        this.dompet = dompet;
        lbNamaDompet.setText(this.dompet);
        try {
           
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where nama_dompet='" + lbNamaDompet.getText() + "' and id_user = '" + iduser + "'";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                idDompet = rs.getString(1);
            }
            System.out.println(idDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
       
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         this.nominal.setMinWidth(130);
         this.nominal.setCellValueFactory(new PropertyValueFactory("nominal"));
    }
}
