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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private String iduser, namauser, dompet, idDompet, idKategori, tampNama, tampNominal;
    
    @FXML
    private Tab EditPeminjamantab, EditPeminjamantabHapus;
    
    @FXML
    private TextField txtKeteranganPeminjaman, txtNominalPeminjaman, txtEditNominal, txtEditKeterangan, txtLunas;
    @FXML
    private DatePicker tgl_peminjaman, tglEditPeminjaman, tglEditPeminjaman1;

    public ComboBox<String> cbTambahKategori;

   
    
    
    
    @FXML
    private Label lbNama, lbId, lbNamaDompet;

    @FXML
    private TableView<tampilTabel> peminjamantbl;
    @FXML
    private TableView<tampilTabel> peminjamantblHapus;

    @FXML
    private TableColumn<tampilTabel, String> colTanggal, colKeterangan, colKategori, colNominal, colTanggalPengembalian, colLunas;
    @FXML
    private TableColumn<tampilTabel, String> colTanggalHapus = new TableColumn("Tanggal");
    private TableColumn<tampilTabel, String> colKategoriHapus = new TableColumn("Kategori");
    private TableColumn<tampilTabel, String> colKeteranganHapus = new TableColumn("Keterangan");
    private TableColumn<tampilTabel, String> colNominalHapus = new TableColumn("Nominal");
    private TableColumn<tampilTabel, String> colTglPengembalianHapus = new TableColumn("T_Pengembalian");
    private TableColumn<tampilTabel, String> colPelunasanHapus = new TableColumn("Lunas");

    ObservableList<String> list = FXCollections.observableArrayList();
    
    private String kategoriHapus, tanggalHapus, keteranganHapus, nominalHapus, tglPengembalianHapus, lunasHapus;
    
    @FXML
    public void pindahHome(MouseEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), iduser, dompet);
        home.updateSaldo();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void setIdandName(String iduser, String Username, String dompet){
        this.iduser = iduser;
        this.namauser = Username;
        lbNama.setText(namauser);
        lbId.setText(iduser);
        this.dompet = dompet;
        System.out.println(dompet);
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
    
    
    @FXML
    public void setIsiKategori(MouseEvent event){
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            list.clear();
            statement = connection.createStatement();
            String query = "SELECT nama_kategori from kategori where id_dompet='" + idDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
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
        try {
            statement = connection.createStatement();
            String query = "SELECT id_kategori from kategori where nama_kategori ='" + cbTambahKategori.getValue() + "' and id_dompet = '" + idDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idKategori = rs.getString(1);
            }
            System.out.println("berhasil dapat id kategori : " + idKategori);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tambahPeminjaman (ActionEvent event) throws SQLException{ 
       // String ket = txtKeteranganPengeluaran.getText();
        try {
            Connection conn = sqliteConnect.connect().Connector();
            Statement statement;
            statement = conn.createStatement();
            String query = "INSERT INTO peminjaman(id_dompet, id_kategori, nama_pinjaman, nominal_pinjaman, tanggal_pinjaman) VALUES ('" + idDompet + "',"
                    + " '" + idKategori + "',"
                    + " '" + txtKeteranganPeminjaman.getText().toString() + "',"
                    + " '" + txtNominalPeminjaman.getText() + "',"
                    + " '" + tgl_peminjaman.getValue() + "' )";
            int hasil = statement.executeUpdate(query);
            if (hasil == 1) {
                System.out.println("berhasil tambah peminjaman");
                txtKeteranganPeminjaman.clear();
                txtNominalPeminjaman.clear();
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        
    }
    @FXML
    public void isitabel() {
        try {
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from peminjaman inner join kategori where peminjaman.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and peminjaman.id_kategori=kategori.id_kategori");
            rs = st.executeQuery("select * from peminjaman inner join kategori where peminjaman.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and peminjaman.id_kategori=kategori.id_kategori");

            ObservableList<tampilTabel> isi = FXCollections.observableArrayList();
            while (rs.next()) {
                isi.add(new tampilTabel(rs.getString("tanggal_pinjaman"), rs.getString("nama_kategori"), rs.getString("nama_pinjaman"), rs.getString("nominal_pinjaman"), rs.getString("tanggal_pengembalian"), rs.getString("lunas")));
            }
            this.peminjamantbl.setItems(isi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void isitabel2() {
        try {
            peminjamantblHapus.getColumns().clear();
            peminjamantblHapus.getColumns().addAll(colTanggalHapus,colKategoriHapus, colKeteranganHapus, colNominalHapus, colTglPengembalianHapus, colPelunasanHapus);
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from peminjaman inner join kategori where peminjaman.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and peminjaman.id_kategori=kategori.id_kategori");
            rs = st.executeQuery("select * from peminjaman inner join kategori where peminjaman.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and peminjaman.id_kategori=kategori.id_kategori");

            ObservableList<tampilTabel> isi2 = FXCollections.observableArrayList();
            while (rs.next()) {
                System.out.println(rs.getString("tanggal_pinjaman"));
                System.out.println(rs.getString("nama_kategori"));
                System.out.println(rs.getString("nama_pinjaman"));
                System.out.println(rs.getString("nominal_pinjaman"));
                
                isi2.add(new tampilTabel(rs.getString("tanggal_pinjaman"), rs.getString("nama_kategori"), rs.getString("nama_pinjaman"), rs.getString("nominal_pinjaman"), rs.getString("tanggal_pengembalian"), rs.getString("lunas")));
            }
            this.peminjamantblHapus.setItems(isi2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            this.colKategoriHapus.setCellValueFactory(new PropertyValueFactory("kategori"));
            this.colKeteranganHapus.setCellValueFactory(new PropertyValueFactory("keterangan"));
            this.colNominalHapus.setCellValueFactory(new PropertyValueFactory("nominal"));
            this.colTanggalHapus.setCellValueFactory(new PropertyValueFactory("tgl"));
            this.colTglPengembalianHapus.setCellValueFactory(new PropertyValueFactory("tglPengembalian"));
            this.colPelunasanHapus.setCellValueFactory(new PropertyValueFactory("lunas"));
            this.colKategoriHapus.setMinWidth(100);
            this.colKeteranganHapus.setMinWidth(100);
            this.colNominalHapus.setMinWidth(100);
            this.colTanggalHapus.setMinWidth(100);
            this.colTglPengembalianHapus.setMinWidth(100);
            this.colPelunasanHapus.setMinWidth(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            this.colKategori.setCellValueFactory(new PropertyValueFactory("kategori"));
            this.colKeterangan.setCellValueFactory(new PropertyValueFactory("keterangan"));
            this.colNominal.setCellValueFactory(new PropertyValueFactory("nominal"));
            this.colTanggal.setCellValueFactory(new PropertyValueFactory("tgl"));
            this.colTanggalPengembalian.setCellValueFactory(new PropertyValueFactory("tglPengembalian"));
            this.colLunas.setCellValueFactory(new PropertyValueFactory("lunas"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(this.namauser);
        System.out.println(this.dompet);
        System.out.println(this.lbNamaDompet.getText());
    }
    @FXML
    public void hapuspeminjaman(MouseEvent event){
        kategoriHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getKategori();
        tanggalHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getTgl();
        keteranganHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getKeterangan();
        nominalHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getNominal();
        tglPengembalianHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getTglPengembalian();
        lunasHapus = peminjamantblHapus.getSelectionModel().getSelectedItem().getLunas();
    }
    
    @FXML
    public void hapus(ActionEvent event) throws SQLException{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement=connection.createStatement();
        System.out.println(idDompet);
        String queryKategori="SELECT id_kategori from kategori where id_dompet='"+idDompet+"' and nama_kategori='"+kategoriHapus+"'";
        ResultSet rs = statement.executeQuery(queryKategori);
        while(rs.next()){
            String idKategori = rs.getString(1).toString();
            String queryPengeluaran="SELECT id_pinjaman from peminjaman where id_dompet='"+idDompet+"' and id_kategori='"+idKategori+"' and nama_pinjaman='"+keteranganHapus+"'";
            ResultSet rsP = statement.executeQuery(queryPengeluaran);
            while(rsP.next()){
                String idPengeluaran = rs.getString(1).toString();
                String queryHapus="DELETE from peminjaman where id_pinjaman='"+idPengeluaran+"' and id_dompet='"+idDompet+"' and id_kategori='"+idKategori+"' and nama_pinjaman='"+keteranganHapus+"'";
                int hasil=statement.executeUpdate(queryHapus);
                if(hasil==1){
                    System.out.println("HAPUS BERHASIL");
                    isitabel2();
                }else{
                    System.out.println("HAPUS TIDAK BERHASIL");
                }
            }
        }
    }
    
    @FXML
    public void editPeminjaman(MouseEvent event) throws Exception {

        String tanggal, keterangan, nominal, tanggalPengembalian,pelunasan;
        tanggal = peminjamantbl.getSelectionModel().getSelectedItem().getTgl();
        keterangan = peminjamantbl.getSelectionModel().getSelectedItem().getKeterangan();
        nominal = peminjamantbl.getSelectionModel().getSelectedItem().getNominal();
        tanggalPengembalian=peminjamantbl.getSelectionModel().getSelectedItem().getTglPengembalian();
        pelunasan=peminjamantbl.getSelectionModel().getSelectedItem().getLunas();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(tanggal, formatter);
        LocalDate localDate2;
        localDate2 = LocalDate.parse(tanggalPengembalian, formatter);
        tglEditPeminjaman.setValue(localDate);
        txtEditKeterangan.setText(keterangan);
        txtEditNominal.setText(nominal);
        tampNama = txtEditKeterangan.getText();
        tampNominal = txtEditNominal.getText();
        tglEditPeminjaman1.setValue(localDate2);
        txtLunas.setText(pelunasan);
    }
    @FXML
    public void UpdatePeminjaman(ActionEvent event) throws SQLException {
        try {
            Connection conn = sqliteConnect.connect().Connector();
            Statement statement;
            statement = conn.createStatement();
            String query = "UPDATE peminjaman SET tanggal_pinjaman = '" + tglEditPeminjaman.getValue() + "',"
                    + " nama_pinjaman = '" + txtEditKeterangan.getText() + "', "
                    + "nominal_pinjaman = '" + txtEditNominal.getText() + "', "
                    + "tanggal_pengembalian = '" + tglEditPeminjaman1.getValue() + "', "
                    + "lunas = '" + txtLunas.getText() + "' "
                    + "WHERE nama_pinjaman = '" + tampNama + "'"
                    + "AND nominal_pinjaman = '" + tampNominal + "'";
            int hasil = statement.executeUpdate(query);
            if (hasil == 1) {
                System.out.println("berhasil Update peminjaman");
                txtEditKeterangan.clear();
                txtEditNominal.clear();
                tglEditPeminjaman.setValue(null);
                isitabel();
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
}










































