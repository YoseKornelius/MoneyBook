/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static java.awt.SystemColor.window;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YOSE
 */
public class LaporanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbId, lbNama, lbPemasukkan, lbPengeluaran, lbTotal, lbJumlahSisaSaldo, lbJumlahPemasukkan, lbJumlahPengeluaran;

    @FXML
    private ComboBox<String> cbPilihDompet;
    @FXML
    private ComboBox<String> comboKategori;
    @FXML
    private ComboBox<String> cbDetail;

    @FXML
    private TableView<tampilkanLaporan> table;
    @FXML
    private Button tombolConf;

    @FXML
    DatePicker tglAwal, tglAkhir;

    @FXML
    Button btnEksport;

    private String jenis;

    public String namaDompet, user, idUser, idDompet;

    @FXML
    private TableColumn<tampilkanLaporan, String> colTanggalPemasukkan = new TableColumn("Tanggal");
    private TableColumn<tampilkanLaporan, String> colNamaPemasukkan = new TableColumn("Nama");
    private TableColumn<tampilkanLaporan, String> colNominalPemasukkan = new TableColumn("Nominal");
    private TableColumn<tampilkanLaporan, String> colTglPengembalian = new TableColumn("Tgl Pengembalian");
    private TableColumn<tampilkanLaporan, String> colStatus = new TableColumn("Status");

    ObservableList<tampilkanLaporan> data = FXCollections.observableArrayList();

    ObservableList<String> listDompet = FXCollections.observableArrayList();
    ObservableList<String> listDetail = FXCollections.observableArrayList();
    ObservableList<String> listKategori = FXCollections.observableArrayList();

    @FXML
    public void laporanTabel(ActionEvent event) throws SQLException {
        table.getColumns().clear();
        table.getItems().clear();
        List<String> rowTgl = new ArrayList();
        List<String> rowNominal = new ArrayList();
        List<String> rowNama = new ArrayList();
        ObservableList<tampilkanLaporan> isi = FXCollections.observableArrayList();
        Connection conn = sqliteConnect.connect().Connector();
        Statement statement;
        statement = conn.createStatement();
        if (cbDetail.getValue() == "Pemasukkan") {
            table.getColumns().addAll(colTanggalPemasukkan, colNamaPemasukkan, colNominalPemasukkan);
            System.out.println(idDompet);
            System.out.println(tglAwal.getValue());
            String query = "select tanggal_pemasukkan, nama_pemasukkan, nominal_pemasukkan from pemasukkan where id_dompet='" + idDompet + "' and (tanggal_pemasukkan between '" + tglAwal.getValue() + "' and '" + tglAkhir.getValue() + "')";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tanggal_pemasukkan");
                String nama = rs.getString("nama_pemasukkan");
                String nominal = rs.getString("nominal_pemasukkan");
                data.add(new tampilkanLaporan(tanggal, nama, nominal));
            }
            this.table.setItems(data);
        } else if (cbDetail.getValue() == "Pengeluaran") {
            table.getColumns().addAll(colTanggalPemasukkan, colNamaPemasukkan, colNominalPemasukkan);
            String query = "select tanggal_pengeluaran, nama_pengeluaran, nominal_pengeluaran from pengeluaran where id_dompet='" + idDompet + "' and (tanggal_pengeluaran between '" + tglAwal.getValue() + "' and '" + tglAkhir.getValue() + "')";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tanggal_pengeluaran");
                String nama = rs.getString("nama_pengeluaran");
                String nominal = rs.getString("nominal_pengeluaran");
                data.add(new tampilkanLaporan(tanggal, nama, nominal));
            }
            this.table.setItems(data);
        } else if (cbDetail.getValue() == "Peminjaman") {
            System.out.println(idDompet);
            table.getColumns().addAll(colTanggalPemasukkan, colNamaPemasukkan, colNominalPemasukkan, colTglPengembalian, colStatus);
            String query = "SELECT nama_pinjaman, nominal_pinjaman, tanggal_pinjaman, tanggal_pengembalian, lunas FROM peminjaman WHERE id_dompet = '" + idDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tanggal_pinjaman");
                String tanggalPengeluaran = rs.getString("tanggal_pengembalian");
                System.out.println(tanggalPengeluaran);
                String nama = rs.getString("nama_pinjaman");
                String nominal = rs.getString("nominal_pinjaman");
                String status = rs.getString("lunas");
                data.add(new tampilkanLaporan(tanggal, nama, nominal, tanggalPengeluaran, status));
            }
            this.table.setItems(data);
        }
    }

    @FXML
    public void eksport(ActionEvent event) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet spreadsheet = workbook.createSheet("sample");
        Row row = spreadsheet.createRow(0);

        for (int i = 0; i < table.getColumns().size(); i++) {
            row.createCell(i).setCellValue(table.getColumns().get(i).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if (table.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }   
//        try {
//            File file = FileChooser.showOpenDialog(stage);
//        } catch (Exception e) {
//        }

        FileOutputStream fileout = new FileOutputStream("D:" + "\\" + "contoh" + ".xls");
        workbook.write(fileout);

        //System.out.println(path + "\\" + this.namatxt.getText() + ".xlsx");
        System.out.println("berhasil bambang");
        System.out.println("berhasil export");
    }

    @FXML
    public void pilihJenis(ActionEvent event) {
        this.jenis = cbDetail.getValue().toString();
    }

    @FXML
    public void cekJenis(MouseEvent event) {
        listDetail.clear();
        listDetail.add("Pemasukkan");
        listDetail.add("Pengeluaran");
        listDetail.add("Peminjaman");
        cbDetail.setItems(listDetail);
    }

    @FXML
    public void pindahHome(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        HomeController home = loader.getController();
        home.setLabelUsername(lbNama.getText(), lbId.getText(), cbPilihDompet.getValue());
        home.updateSaldo();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void pilih(ActionEvent event) {
        System.out.println("BERHASIL UPDATE");
        this.namaDompet = cbPilihDompet.getValue();
        listKategori.clear();
        updateSaldo();
    }

    @FXML
    public void cekDompet(MouseEvent event) {
        listDompet.clear();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT nama_dompet from dompet where id_user='" + lbId.getText() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                listDompet.add(rs.getString(1));
            }
            cbPilihDompet.setItems(listDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.colTanggalPemasukkan.setCellValueFactory(new PropertyValueFactory("Tanggal"));
        this.colNamaPemasukkan.setCellValueFactory(new PropertyValueFactory("Nama"));
        this.colNominalPemasukkan.setCellValueFactory(new PropertyValueFactory("Nominal"));
        this.colTglPengembalian.setCellValueFactory(new PropertyValueFactory("tglPengembalian"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory("Status"));

        this.colNamaPemasukkan.setMinWidth(100);
        this.colTanggalPemasukkan.setMinWidth(80);
        this.colNominalPemasukkan.setMinWidth(80);
        this.colTglPengembalian.setMinWidth(80);
        this.colStatus.setMinWidth(60);

    }

    public void setIdandName(String iduser, String Username, ComboBox<String> dompet, String namaDompet) {
        this.lbId.setText(iduser);
        this.lbNama.setText(Username);
        // this.cbPilihDompet = dompet;
        //this.cbPilihDompet.setValue(namaDompet);
        this.namaDompet = namaDompet;
        this.idUser = iduser;
        this.user = Username;
        try {
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where nama_dompet='" + namaDompet + "' and id_user = '" + iduser + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idDompet = rs.getString(1);
            }
            System.out.println(idDompet);
        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getNamaDompet(String nama) {
        this.namaDompet = nama;
        cbPilihDompet.setValue(nama);
        System.out.println("nama ini dompet : " + namaDompet);
        System.out.println("nama checkbox dompet : " + cbPilihDompet.getValue());
        updateSaldo();
    }

    public void updateSaldo() {
        if (cbPilihDompet.getValue() != null) {
            int pemasukkan = 0, pengeluaran = 0, sisaAkhir = 0;            
            try {
                Connection conn = sqliteConnect.connect().Connector();
                Statement stat = conn.createStatement();
                String qrIdDompet = "SELECT id_dompet from dompet WHERE nama_dompet = '" + cbPilihDompet.getValue() + "' AND id_user = '" + lbId.getText() + "'";
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
                lbJumlahSisaSaldo.setText(Integer.toString(pemasukkan - pengeluaran));
                lbJumlahPemasukkan.setText(Integer.toString(pemasukkan));
                lbJumlahPengeluaran.setText(Integer.toString(pengeluaran));
                //lbJumlahPemasukkan.setText("10000");
            } catch (Exception e) {
            }
        }
    }

}
