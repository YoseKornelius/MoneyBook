/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.File;
import java.io.FileOutputStream;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author YOSE
 */
public class LaporanBulananController implements Initializable {

    @FXML
    public void pindahHome(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), idUser, dompet);
        home.getNamaDompet(cbPilihDompet.getValue());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private ImageView imgHome;
    private String idUser, namaUser, dompet, idDompet, idKategori, tampNama, tampNominal, namaDompet;
    
    @FXML
    private Tab editPemasukkanTab, editPemasukkanTabHapus;
    
    @FXML
    private TextField txtKeteranganPemasukkan, txtNominalPemasukkan, txtEditNominal, txtEditKeterangan;
    @FXML
    private DatePicker tgl_pemasukkan, tglEditPemasukkan;

    @FXML
    public ComboBox<String> cbTambahKategori;

    ObservableList<String> listDompet = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> cbPilihDompet;
    
    @FXML
    private ComboBox<String> comboBulan;
    
    @FXML
    private TableView<tampilkanLaporanBulanan> tabelPemasukkan;
    
    @FXML
    private TableView<tampilkanLaporanBulanan> tabelPengeluaran;
    
    @FXML
    private TableView<tampilkanLaporanBulanan> tabelPeminjaman;
    
    @FXML
    private Label lbPemasukkan;
    
    @FXML
    private Label lbPengeluaran;
    
    @FXML
    private Label lbSisa;
    
    @FXML
    private Label lbPeringatanEksport;
    
    private String tampBulan;
    
    @FXML
    private TableColumn<tampilkanLaporanBulanan, String> colTanggalMasuk, colNamaMasuk, colNominalMasuk;
    @FXML
    private TableColumn<tampilkanLaporanBulanan, String> colTanggalKeluar, colNamaKeluar, colNominalKeluar;
    @FXML
    private TableColumn<tampilkanLaporanBulanan, String> colTanggalPinjam, colNamaPinjam, colNominalPinjam;
    @FXML
    private TableColumn<tampilkanLaporanBulanan, String> colTanggalPengembalian;
    @FXML
    private TableColumn<tampilkanLaporanBulanan, String> colLunas;
    
    ObservableList<String> listBulan = FXCollections.observableArrayList();
    
    @FXML
    private Label lbNama, lbId, lbNamaDompet;    
    /**
     * Initializes the controller class.
     */
        public void setIdandName(String iduser, String Username, String dompet) {
        this.idUser = iduser;
        this.namaUser = Username;
        lbNama.setText(namaUser);
        lbId.setText(iduser);
        this.dompet = dompet;
        try {
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where nama_dompet='" + namaDompet + "' and id_user = '" + idUser + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idDompet = rs.getString(1);
            }
            System.out.println("INI ID DOMPET: "+idDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
       
    }
    
        @FXML
    public void eksport(ActionEvent event) throws IOException {
        if (tabelPemasukkan.getItems().isEmpty()) {
            lbPeringatanEksport.setText("TIdak bisa Eksport Tabel Kosong!");
        } else {
            lbPeringatanEksport.setText("");
            final Stage primaryStage = null;
            Workbook workbook = new HSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int i = 0; i < tabelPemasukkan.getColumns().size(); i++) {
                row.createCell(i).setCellValue(tabelPemasukkan.getColumns().get(i).getText());
            }

            for (int i = 0; i < tabelPemasukkan.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabelPemasukkan.getColumns().size(); j++) {
                    if (tabelPemasukkan.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabelPemasukkan.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            FileChooser fileChooser = new FileChooser();
            //filter ekstensi yang mau
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);

            //ini buka file dialog
            File saveFile = fileChooser.showSaveDialog(tabelPemasukkan.getScene().getWindow());

            if (saveFile != null) {
                FileOutputStream fileout = new FileOutputStream(saveFile.getAbsolutePath());
                System.out.println(saveFile.getAbsolutePath());
                workbook.write(fileout);
                System.out.println("berhasil bambang");
            System.out.println("berhasil export");
            }                        
        }
        if (tabelPengeluaran.getItems().isEmpty()) {
            lbPeringatanEksport.setText("TIdak bisa Eksport Tabel Kosong!");
        } else {
            lbPeringatanEksport.setText("");
            final Stage primaryStage = null;
            Workbook workbook = new HSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int i = 0; i < tabelPengeluaran.getColumns().size(); i++) {
                row.createCell(i).setCellValue(tabelPengeluaran.getColumns().get(i).getText());
            }

            for (int i = 0; i < tabelPengeluaran.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabelPengeluaran.getColumns().size(); j++) {
                    if (tabelPengeluaran.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabelPengeluaran.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            FileChooser fileChooser = new FileChooser();
            //filter ekstensi yang mau
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);

            //ini buka file dialog
            File saveFile = fileChooser.showSaveDialog(tabelPengeluaran.getScene().getWindow());

            if (saveFile != null) {
                FileOutputStream fileout = new FileOutputStream(saveFile.getAbsolutePath());
                System.out.println(saveFile.getAbsolutePath());
                workbook.write(fileout);
                System.out.println("berhasil bambang");
            System.out.println("berhasil export");
            }                        
        }
        if (tabelPeminjaman.getItems().isEmpty()) {
            lbPeringatanEksport.setText("TIdak bisa Eksport Tabel Kosong!");
        } else {
            lbPeringatanEksport.setText("");
            final Stage primaryStage = null;
            Workbook workbook = new HSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int i = 0; i < tabelPeminjaman.getColumns().size(); i++) {
                row.createCell(i).setCellValue(tabelPeminjaman.getColumns().get(i).getText());
            }

            for (int i = 0; i < tabelPeminjaman.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabelPeminjaman.getColumns().size(); j++) {
                    if (tabelPeminjaman.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabelPeminjaman.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            FileChooser fileChooser = new FileChooser();
            //filter ekstensi yang mau
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);

            //ini buka file dialog
            File saveFile = fileChooser.showSaveDialog(tabelPeminjaman.getScene().getWindow());

            if (saveFile != null) {
                FileOutputStream fileout = new FileOutputStream(saveFile.getAbsolutePath());
                System.out.println(saveFile.getAbsolutePath());
                workbook.write(fileout);
                System.out.println("berhasil bambang");
            System.out.println("berhasil export");
            }                        
        }
    }
    
    @FXML
    public void getNamaDompet(String nama) {
        this.namaDompet = nama;
        cbPilihDompet.setValue(nama);
        System.out.println("nama ini dompet : " + namaDompet);
        System.out.println("nama checkbox dompet : " + cbPilihDompet.getValue());
        updateSaldo();
    }
    @FXML
    public void cekDompet(MouseEvent event) {
        listDompet.clear();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT nama_dompet from dompet where id_user='" + idUser + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                listDompet.add(rs.getString(1));
            }
            cbPilihDompet.setItems(listDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void pilihBulan(ActionEvent event){
        if (comboBulan.getValue() == "Januari") {
            tampBulan = "01";
        } else if (comboBulan.getValue() == "Februari") {
            tampBulan = "02";
        } else if (comboBulan.getValue() == "Maret") {
            tampBulan = "03";
        } else if (comboBulan.getValue() == "April") {
            tampBulan = "04";
        } else if (comboBulan.getValue() == "Mei") {
            tampBulan = "05";
        } else if (comboBulan.getValue() == "Juni") {
            tampBulan = "06";
        } else if (comboBulan.getValue() == "Juli") {
            tampBulan = "07";
        } else if (comboBulan.getValue() == "Agustus") {
            tampBulan = "08";
        } else if (comboBulan.getValue() == "September") {
            tampBulan = "09";
        } else if (comboBulan.getValue() == "Oktober") {
            tampBulan = "10";
        } else if (comboBulan.getValue() == "November") {
            tampBulan = "11";
        } else if (comboBulan.getValue() == "Desember") {
            tampBulan = "12";
        }
    }
    
    @FXML
    public void cekBulan(MouseEvent event){
        comboBulan.getItems().clear();
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
    public void pilih(ActionEvent event) {
        System.out.println("BERHASIL UPDATE");
        this.namaDompet = cbPilihDompet.getValue();
        updateSaldo();
    }
    
    public void updateSaldo(){
        if (cbPilihDompet.getValue() != null) {
            System.out.println("AWOAKWOKAWOKAOWK");
            int pemasukkan = 0, pengeluaran = 0, sisaAkhir = 0;
            String idDompet = "";
            try {
                Connection conn = sqliteConnect.connect().Connector();
                Statement stat = conn.createStatement();
                String qrIdDompet = "SELECT id_dompet from dompet WHERE nama_dompet = '" + cbPilihDompet.getValue() + "' AND id_user = '" + idUser + "'";
                System.out.println("nama dompet ke 2 : " + cbPilihDompet.getValue());
                ResultSet rsdompet = stat.executeQuery(qrIdDompet);
                while (rsdompet.next()) {
                    idDompet = rsdompet.getString(1);
                    System.out.println("id dompet " + namaDompet + "adalah : " + idDompet);
                }

                String qr = "SELECT nominal_pemasukkan FROM pemasukkan WHERE id_dompet = '" + idDompet + "'";
                ResultSet rs = stat.executeQuery(qr);
                while (rs.next()) {
                    pemasukkan += Integer.parseInt(rs.getString(1));
                    System.out.println(rs.getString(1));
                }

                String qrPengeluaran = "SELECT nominal_pengeluaran FROM pengeluaran WHERE id_dompet = '" + idDompet + "'";
                ResultSet rsPengeluaran = stat.executeQuery(qrPengeluaran);
                while (rsPengeluaran.next()) {
                    pengeluaran += Integer.parseInt(rsPengeluaran.getString(1));
                    System.out.println(rsPengeluaran.getString(1));
                }
                lbSisa.setText(Integer.toString(pemasukkan - pengeluaran));
                lbPemasukkan.setText(Integer.toString(pemasukkan));
                lbPengeluaran.setText(Integer.toString(pengeluaran));
                //lbJumlahPemasukkan.setText("10000");
            } catch (Exception e) {
            }
        }
    }
    @FXML
    public void LaporanBulanan(ActionEvent event){
        try {
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where nama_dompet='" + namaDompet + "' and id_user = '" + idUser + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idDompet = rs.getString(1);
            }
            System.out.println("INI ID DOMPET: "+idDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tabelPemasukkan.getColumns().clear();
            tabelPemasukkan.getColumns().addAll(colTanggalMasuk,colNamaMasuk, colNominalMasuk);
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from pemasukkan where id_dompet='"+idDompet+"'");
            rs = st.executeQuery("select * from pemasukkan where id_dompet='"+idDompet+"' and strftime('%m',tanggal_pemasukkan)='" + tampBulan + "'");

            ObservableList<tampilkanLaporanBulanan> isi2 = FXCollections.observableArrayList();
            while (rs.next()) {
                
                isi2.add(new tampilkanLaporanBulanan(rs.getString("tanggal_pemasukkan"),rs.getString("nama_pemasukkan"), rs.getString("nominal_pemasukkan")));
            }
            this.tabelPemasukkan.setItems(isi2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tabelPengeluaran.getColumns().clear();
            tabelPengeluaran.getColumns().addAll(colTanggalKeluar,colNamaKeluar, colNominalKeluar);
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from pengeluaran where id_dompet='"+idDompet+"'");
            rs = st.executeQuery("select * from pengeluaran where id_dompet='"+idDompet+"' and strftime('%m',tanggal_pengeluaran)='" + tampBulan + "'");

            ObservableList<tampilkanLaporanBulanan> isi2 = FXCollections.observableArrayList();
            while (rs.next()) {

                
                isi2.add(new tampilkanLaporanBulanan(rs.getString("tanggal_pengeluaran"),rs.getString("nama_pengeluaran"), rs.getString("nominal_pengeluaran")));
            }
            this.tabelPengeluaran.setItems(isi2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tabelPeminjaman.getColumns().clear();
            tabelPeminjaman.getColumns().addAll(colTanggalPinjam,colNamaPinjam, colNominalPinjam, colTanggalPengembalian, colLunas);
            Connection conn;
            Statement st;
            ResultSet rs;
            conn = sqliteConnect.connect().Connector();
            st = conn.createStatement();
            System.out.println("select * from peminjaman where id_dompet='"+idDompet+"'");
            rs = st.executeQuery("select * from peminjaman where id_dompet='"+idDompet+"' and strftime('%m',tanggal_pinjaman)='" + tampBulan + "'");

            ObservableList<tampilkanLaporanBulanan> isi2 = FXCollections.observableArrayList();
            while (rs.next()) {
                System.out.println(rs.getString("tanggal_pengembalian"));
                System.out.println(rs.getString("lunas"));
                
                isi2.add(new tampilkanLaporanBulanan(rs.getString("tanggal_pinjaman"), rs.getString("nama_pinjaman"), rs.getString("nominal_pinjaman"), rs.getString("tanggal_pengembalian"), rs.getString("lunas")));
            }
            this.tabelPeminjaman.setItems(isi2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.colLunas= new TableColumn("Pelunasan");
        this.colNamaKeluar= new TableColumn("Nama Pengeluaran");
        this.colNamaMasuk=new TableColumn("Nama Pemasukkan");
        this.colNamaPinjam=new TableColumn("Nama Peminjaman");
        this.colNominalKeluar=new TableColumn("Nominal");
        this.colNominalMasuk=new TableColumn("Nominal");
        this.colNominalPinjam=new TableColumn("Nominal");
        this.colTanggalKeluar=new TableColumn("Tanggal");
        this.colTanggalMasuk=new TableColumn("Tanggal");
        this.colTanggalPinjam=new TableColumn("Tanggal");
        this.colTanggalPengembalian=new TableColumn("Tanggal Pengembalian");
        this.colNamaMasuk.setCellValueFactory(new PropertyValueFactory("nama"));
        this.colTanggalMasuk.setCellValueFactory(new PropertyValueFactory("tanggal"));
        this.colNominalMasuk.setCellValueFactory(new PropertyValueFactory("nominal"));
        this.colNamaKeluar.setCellValueFactory(new PropertyValueFactory("nama"));
        this.colTanggalKeluar.setCellValueFactory(new PropertyValueFactory("tanggal"));
        this.colNominalKeluar.setCellValueFactory(new PropertyValueFactory("nominal"));
        this.colNamaPinjam.setCellValueFactory(new PropertyValueFactory("nama"));
        this.colTanggalPinjam.setCellValueFactory(new PropertyValueFactory("tanggal"));
        this.colNominalPinjam.setCellValueFactory(new PropertyValueFactory("nominal"));
        this.colTanggalPengembalian.setCellValueFactory(new PropertyValueFactory("tanggalPengembalian"));
        this.colLunas.setCellValueFactory(new PropertyValueFactory("lunas"));
    }    
    
}







































































