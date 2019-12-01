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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
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

/**
 * FXML Controller class
 *
 * @author YOSE
 */
public class PengeluaranController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView imgHome;
    private String iduser, namauser, dompet, idDompet, idKategori, tampNama, tampNominal;
    
    @FXML
    private Tab EditPengeluarantab, EditPengeluarantabHapus;
    
    @FXML
    private TextField txtKeteranganPengeluaran, txtNominalPengeluaran, txtEditNominal, txtEditKeterangan;
    @FXML
    private DatePicker tgl_pengeluaran, tglEditPengeluaran;

    public ComboBox<String> cbTambahKategori;

   
    
    
    
    @FXML
    private Label lbNama, lbId, lbNamaDompet;

    @FXML
    private TableView<tampilTabel> pengeluarantbl;
    @FXML
    private TableView<tampilTabel> pengeluarantblHapus;

    @FXML
    private TableColumn<tampilTabel, String> colTanggal, colKeterangan, colKategori, colNominal;
    @FXML
    private TableColumn<tampilTabel, String> colTanggalHapus = new TableColumn("Tanggal");
    private TableColumn<tampilTabel, String> colKategoriHapus = new TableColumn("Kategori");
    private TableColumn<tampilTabel, String> colKeteranganHapus = new TableColumn("Keterangan");
    private TableColumn<tampilTabel, String> colNominalHapus = new TableColumn("Nominal");

    ObservableList<String> list = FXCollections.observableArrayList();
    
    private String kategoriHapus, tanggalHapus, keteranganHapus, nominalHapus;
    
    @FXML
    public void pindahHome(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), iduser, dompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setIsiKategori(MouseEvent event) {
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
    @FXML
    public void getIdKategori(ActionEvent event) throws Exception {
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
    @FXML
    public void tambahPengeluaran(ActionEvent event) throws SQLException {
        // String ket = txtKeteranganPengeluaran.getText();
        try {
            Connection conn = sqliteConnect.connect().Connector();
            Statement statement;
            statement = conn.createStatement();
            String query = "INSERT INTO pengeluaran(id_dompet, id_kategori, nama_pengeluaran, nominal_pengeluaran, tanggal_pengeluaran) VALUES ('" + idDompet + "',"
                    + " '" + idKategori + "',"
                    + " '" + txtKeteranganPengeluaran.getText().toString() + "',"
                    + " '" + txtNominalPengeluaran.getText() + "',"
                    + " '" + tgl_pengeluaran.getValue() + "' )";
            int hasil = statement.executeUpdate(query);
            if (hasil == 1) {
                System.out.println("berhasil tambah pengeluaran");
                txtKeteranganPengeluaran.clear();
                txtNominalPengeluaran.clear();
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
            System.out.println("select * from pengeluaran inner join kategori where pengeluaran.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and pengeluaran.id_kategori=kategori.id_kategori");
            rs = st.executeQuery("select * from pengeluaran inner join kategori where pengeluaran.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and pengeluaran.id_kategori=kategori.id_kategori");

            ObservableList<tampilTabel> isi = FXCollections.observableArrayList();
            while (rs.next()) {
                isi.add(new tampilTabel(rs.getString("tanggal_pengeluaran"), rs.getString("nama_kategori"), rs.getString("nama_pengeluaran"), rs.getString("nominal_pengeluaran")));
            }
            this.pengeluarantbl.setItems(isi);
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
            pengeluarantblHapus.getColumns().clear();
            pengeluarantblHapus.getColumns().addAll(colTanggalHapus,colKategoriHapus, colKeteranganHapus, colNominalHapus);
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from pengeluaran inner join kategori where pengeluaran.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and pengeluaran.id_kategori=kategori.id_kategori");
            rs = st.executeQuery("select * from pengeluaran inner join kategori where pengeluaran.id_dompet=(select id_dompet from dompet where nama_dompet='" + this.lbNamaDompet.getText() + "') and pengeluaran.id_kategori=kategori.id_kategori");

            ObservableList<tampilTabel> isi2 = FXCollections.observableArrayList();
            while (rs.next()) {
                System.out.println(rs.getString("tanggal_pengeluaran"));
                System.out.println(rs.getString("nama_kategori"));
                System.out.println(rs.getString("nama_pengeluaran"));
                System.out.println(rs.getString("nominal_pengeluaran"));
                
                isi2.add(new tampilTabel(rs.getString("tanggal_pengeluaran"), rs.getString("nama_kategori"), rs.getString("nama_pengeluaran"), rs.getString("nominal_pengeluaran")));
            }
            this.pengeluarantblHapus.setItems(isi2);
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
            this.colKategoriHapus.setMinWidth(120);
            this.colKeteranganHapus.setMinWidth(120);
            this.colNominalHapus.setMinWidth(120);
            this.colTanggalHapus.setMinWidth(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            this.colKategori.setCellValueFactory(new PropertyValueFactory("kategori"));
            this.colKeterangan.setCellValueFactory(new PropertyValueFactory("keterangan"));
            this.colNominal.setCellValueFactory(new PropertyValueFactory("nominal"));
            this.colTanggal.setCellValueFactory(new PropertyValueFactory("tgl"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(this.namauser);
        System.out.println(this.dompet);
        System.out.println(this.lbNamaDompet.getText());

    }
    
    @FXML
    public void hapusPengeluaran(MouseEvent event){
        kategoriHapus = pengeluarantblHapus.getSelectionModel().getSelectedItem().getKategori();
        tanggalHapus = pengeluarantblHapus.getSelectionModel().getSelectedItem().getTgl();
        keteranganHapus = pengeluarantblHapus.getSelectionModel().getSelectedItem().getKeterangan();
        nominalHapus = pengeluarantblHapus.getSelectionModel().getSelectedItem().getNominal();
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
            String queryPengeluaran="SELECT id_pengeluaran from pengeluaran where id_dompet='"+idDompet+"' and id_kategori='"+idKategori+"' and nama_pengeluaran='"+keteranganHapus+"'";
            ResultSet rsP = statement.executeQuery(queryPengeluaran);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog"); 
            alert.setHeaderText(null);
            alert.setContentText("are you sure want to delete");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK){
                while(rsP.next()){
                    String idPengeluaran = rs.getString(1).toString();
                    String queryHapus="DELETE from pengeluaran where id_pengeluaran='"+idPengeluaran+"' and id_dompet='"+idDompet+"' and id_kategori='"+idKategori+"' and nama_pengeluaran='"+keteranganHapus+"'";
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
    }
    
    @FXML
    public void editPengeluaran(MouseEvent event) throws Exception {

        String tanggal, keterangan, nominal;
        tanggal = pengeluarantbl.getSelectionModel().getSelectedItem().getTgl();
        keterangan = pengeluarantbl.getSelectionModel().getSelectedItem().getKeterangan();
        nominal = pengeluarantbl.getSelectionModel().getSelectedItem().getNominal();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(tanggal, formatter);
        tglEditPengeluaran.setValue(localDate);
        txtEditKeterangan.setText(keterangan);
        txtEditNominal.setText(nominal);
        tampNama = txtEditKeterangan.getText();
        tampNominal = txtEditNominal.getText();
    }

    public void UpdatePengeluaran(ActionEvent event) throws SQLException {
        try {
            Connection conn = sqliteConnect.connect().Connector();
            Statement statement;
            statement = conn.createStatement();
            String query = "UPDATE pengeluaran SET tanggal_pengeluaran = '" + tglEditPengeluaran.getValue() + "',"
                    + " nama_pengeluaran = '" + txtEditKeterangan.getText() + "', "
                    + "nominal_pengeluaran = '" + txtEditNominal.getText() + "' "
                    + "WHERE nama_pengeluaran = '" + tampNama + "'"
                    + "AND nominal_pengeluaran = '" + tampNominal + "'";
            int hasil = statement.executeUpdate(query);
            if (hasil == 1) {
                System.out.println("berhasil Update pengeluaran");
                txtEditKeterangan.clear();
                txtEditNominal.clear();
                isitabel();
                tglEditPengeluaran.setValue(null);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        
    }

    public void setIdandName(String iduser, String Username, String dompet) {
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

}
























