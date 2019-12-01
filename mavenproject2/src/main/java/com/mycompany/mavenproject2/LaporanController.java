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
import java.time.LocalDate;
import java.time.Month;
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
    private TableView table;
    @FXML
    private Button tombolConf;
    
    @FXML
    DatePicker tglAwal, tglAkhir;
    
    private String jenis;
    
    public String namaDompet, user, idUser,idDompet;
    
    @FXML
    private TableColumn<tampilkanLaporan, String> colPemasukkan = new TableColumn("Pemasukkan");

    ObservableList<String> listDompet = FXCollections.observableArrayList();
    ObservableList<String> listDetail = FXCollections.observableArrayList();
    ObservableList<String> listKategori = FXCollections.observableArrayList();
    
    @FXML
    public void laporanTabel(ActionEvent event) throws SQLException{
        table.getColumns().clear();
        table.getColumns().addAll(colPemasukkan);
        ObservableList<tampilkanLaporan> isi = FXCollections.observableArrayList();
        Connection conn = sqliteConnect.connect().Connector();
        Statement statement;
        statement = conn.createStatement();
        if(cbDetail.getValue()=="Pemasukkan"){
            System.out.println(idDompet);
            System.out.println(tglAwal.getValue());
            String query="select nominal_pemasukkan from pemasukkan where id_dompet='"+idDompet+"' and (tanggal_pemasukkan between '"+tglAwal.getValue()+"' and '"+tglAkhir.getValue()+"')";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                isi.add(new tampilkanLaporan(rs.getString("nominal_pemasukkan")));
            }
            this.table.setItems(isi);
        }
    }
    
    @FXML
    public void pilihJenis(ActionEvent event){
        this.jenis=cbDetail.getValue().toString();
    }
    
    @FXML
    public void cekJenis(MouseEvent event){
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
        this.colPemasukkan.setCellValueFactory(new PropertyValueFactory("pemasukkan"));
        this.colPemasukkan.setMinWidth(110);
    }

    public void setIdandName(String iduser, String Username, ComboBox<String> dompet, String namaDompet) {
        this.lbId.setText(iduser);
        this.lbNama.setText(Username);
        // this.cbPilihDompet = dompet;
        //this.cbPilihDompet.setValue(namaDompet);
        this.namaDompet=namaDompet;
        this.idUser=iduser;
        this.user=Username;
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
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
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
            String idDompet = "";
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





















