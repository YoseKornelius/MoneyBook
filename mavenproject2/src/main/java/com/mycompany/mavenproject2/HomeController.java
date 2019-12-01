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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label lbNama, lbAdaDompet, lbJumlahPemasukkan, lbJumlahPengeluaran, lbJumlahSisaSaldo;
    @FXML
    private Button btnTambahPengeluaran, tombolCari;

    @FXML
    private ImageView dompet;

    @FXML
    private ImageView imgTambahPengeluaran, gmbrSetting, kategori, imgPemasukkan, imgPengeluaran, imgPeminjaman, imgKategori;

    @FXML
    private String userName, idUser, tampJenis, tampKategori;

    @FXML
    private String tampBulan;

    @FXML
    private ComboBox<String> cbPilihDompet;

    @FXML
    private ComboBox<String> comboJenis;

    @FXML
    private ComboBox<String> comboBulan;

    @FXML
    private ComboBox<String> comboKategori;

    @FXML
    TableView<dataPencarian> table;

    ObservableList<String> listDompet = FXCollections.observableArrayList();
    ObservableList<String> listJenis = FXCollections.observableArrayList();
    ObservableList<String> listBulan = FXCollections.observableArrayList();
    ObservableList<String> listKategori = FXCollections.observableArrayList();
    public String namaDompet;
    public int idDompet;
    public String lunas;

    ObservableList<dataPencarian> data = FXCollections.observableArrayList();

    TableColumn<dataPencarian, String> kolom1 = new TableColumn("Tanggal");
    TableColumn<dataPencarian, String> kolom2 = new TableColumn("Jenis Pencatatan");
    TableColumn<dataPencarian, String> kolom3 = new TableColumn("Kategori");
    TableColumn<dataPencarian, String> kolom4 = new TableColumn("Nominal");
    TableColumn<dataPencarian, String> kolom5 = new TableColumn("Status");
    TableColumn<dataPencarian, String> kolom6 = new TableColumn("Nama Barang");

    @FXML
    public void isiComboJenis(MouseEvent event) {
        listJenis.clear();
        listJenis.add("Pemasukkan");
        listJenis.add("Pengeluaran");
        listJenis.add("Peminjaman");
        comboJenis.setItems(listJenis);
    }

    @FXML
    public void pilihJenis(ActionEvent event) {
        tampJenis = comboJenis.getValue().toString();
    }

    @FXML
    public void isiComboBulan(MouseEvent event) {
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
    public void pilihBulan(ActionEvent event) {
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
    public void isiComboKategori(MouseEvent event) {
        listKategori.clear();
        comboKategori.autosize();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        try {
            statement = connection.createStatement();
            String query = "SELECT id_dompet from dompet where id_user='" + idUser + "' and nama_dompet='" + namaDompet + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                idDompet = Integer.parseInt(rs.getString(1));
                query = "SELECT nama_kategori from kategori where id_dompet='" + idDompet + "'";
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    listKategori.add(rs.getString(1));
                }
                comboKategori.setItems(listKategori);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PilihDompetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void pilihKategori(ActionEvent event) {
        this.tampKategori = comboKategori.getValue();
    }

    @FXML
    public void cari(ActionEvent event) throws SQLException {
        String jenis = null;
        table.setVisible(true);
        table.getColumns().remove(kolom5);
        table.getItems().clear();
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement = connection.createStatement();
        List<String> rowTgl = new ArrayList();
        List<String> rowNominal = new ArrayList();
        List<String> rowNama = new ArrayList();
        if (comboJenis.getValue().toString() == "Pemasukkan") {
            jenis = "Pemasukkan";
            String queryKategori = "SELECT id_kategori from kategori where id_dompet='" + idDompet + "' and nama_kategori='" + tampKategori + "'";
            ResultSet rs;
            rs = statement.executeQuery(queryKategori);
            while (rs.next()) {
                String idKategori = rs.getString(1).toString();
                String queryPemasukkan = "SELECT id_pemasukkan from pemasukkan where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                ResultSet rsP = statement.executeQuery(queryPemasukkan);
                while (rsP.next()) {
                    String idPemasukkan = rsP.getString(1).toString();
                    String query = "SELECT tanggal_pemasukkan from pemasukkan where strftime('%m',tanggal_pemasukkan)='" + tampBulan + "' and id_kategori='" + idKategori + "'";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        rowTgl.add(rs.getString(1).toString());
                    }
                    String query2 = "SELECT nominal_pemasukkan from pemasukkan where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs2 = statement.executeQuery(query2);
                    while (rs2.next()) {
                        rowNominal.add(rs.getString(1).toString());
                    }
                    String query3 = "SELECT nama_pemasukkan from pemasukkan where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs3 = statement.executeQuery(query3);
                    while (rs3.next()) {
                        rowNama.add(rs3.getString(1));
                    }
                }

            }

        } else if (comboJenis.getValue().toString() == "Pengeluaran") {
            jenis = "Pengeluaran";
            String queryKategori = "SELECT id_kategori from kategori where id_dompet='" + idDompet + "' and nama_kategori='" + tampKategori + "'";
            ResultSet rs;
            rs = statement.executeQuery(queryKategori);
            while (rs.next()) {
                String idKategori = rs.getString(1).toString();
                String queryPengeluaran = "SELECT id_pengeluaran from pengeluaran where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                ResultSet rsP = statement.executeQuery(queryPengeluaran);
                while (rsP.next()) {
                    String idPengeluaran = rsP.getString(1).toString();
                    String query = "SELECT tanggal_pengeluaran from pengeluaran where strftime('%m',tanggal_pengeluaran)='" + tampBulan + "' and id_kategori='" + idKategori + "'";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        rowTgl.add(rs.getString(1).toString());
                    }
                    String query2 = "SELECT nominal_pengeluaran from pengeluaran where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs2 = statement.executeQuery(query2);
                    while (rs2.next()) {
                        rowNominal.add(rs.getString(1).toString());
                    }
                    String query3 = "SELECT nama_pengeluaran from pengeluaran where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs3 = statement.executeQuery(query3);
                    while (rs3.next()) {
                        rowNama.add(rs3.getString(1));
                    }
                }
            }
        } else if (comboJenis.getValue().toString() == "Peminjaman") {
            jenis = "Peminjaman";
            table.getColumns().remove(kolom5);
            table.getColumns().add(kolom5);

            String queryKategori = "SELECT id_kategori from kategori where id_dompet='" + idDompet + "' and nama_kategori='" + tampKategori + "'";
            ResultSet rs;
            rs = statement.executeQuery(queryKategori);
            while (rs.next()) {
                String idKategori = rs.getString(1).toString();
                String queryPeminjaman = "SELECT id_pinjaman from peminjaman where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                ResultSet rsP = statement.executeQuery(queryPeminjaman);
                while (rsP.next()) {
                    String idPeminjaman = rsP.getString(1).toString();
                    String query = "SELECT tanggal_pinjaman from peminjaman where strftime('%m',tanggal_pinjaman)='" + tampBulan + "' and id_kategori='" + idKategori + "'";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        rowTgl.add(rs.getString(1).toString());
                    }
                    String query2 = "SELECT nominal_pinjaman from peminjaman where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs2 = statement.executeQuery(query2);
                    while (rs2.next()) {
                        rowNominal.add(rs.getString(1).toString());
                    }
                    String query3 = "SELECT tanggal_pengembalian from peminjaman where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "' and id_pinjaman='" + idPeminjaman + "'";
                    ResultSet rs3 = statement.executeQuery(query3);
                    rs3.next();
                    if (rs3.getString(1) != null) {
                        lunas = "LUNAS";
                    } else {
                        lunas = "BELUM LUNAS";
                    }
                    String query4 = "SELECT nama_pinjaman from peminjaman where id_dompet='" + idDompet + "' and id_kategori='" + idKategori + "'";
                    ResultSet rs4 = statement.executeQuery(query4);
                    while (rs4.next()) {
                        rowNama.add(rs4.getString(1));
                    }
                }
            }
        }
        System.out.println(rowTgl.size());
        System.out.println(rowNama.size());
        System.out.println(rowNominal.size());
        for (int i = 0; i < rowNama.size(); i++) {
            data.add(new dataPencarian(rowTgl.get(i), jenis, tampKategori, rowNama.get(i), Integer.parseInt(rowNominal.get(i)), lunas));
        }
        table.setItems(data);
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
    public void pindahPemasukkan(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pemasukkan.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        PemasukkanController masuk = loader.getController();
        masuk.setIdandName(idUser, lbNama.getText(), namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void pindahPengeluaran(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pengeluaran.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        PengeluaranController pengeluaran = loader.getController();
        System.out.println(namaDompet);
        pengeluaran.setIdandName(idUser, lbNama.getText(), namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void pindahAnggaran(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Anggaran.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        AnggaranController anggaran = loader.getController();
        System.out.println(namaDompet);
        anggaran.setIdandName(idUser, lbNama.getText(), namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void pindahPeminjaman(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/peminjaman.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        //PeminjamanController peminjaman = loader.getController();
        PeminjamanController peminjaman = loader.getController();
        System.out.println(namaDompet);
        peminjaman.setIdandName(idUser, lbNama.getText(), namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void pindahKategori(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Kategori.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        KategoriController kategori = loader.getController();
        kategori.getNamaAndId(lbNama.getText(), idUser, namaDompet);
        kategori.tampilkanTable();
        System.out.println(idDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void pindahLaporanBulanan(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/laporanBulanan.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        LaporanBulananController laporanBulanan = loader.getController();
//        laporanBulanan.getNamaAndId( lbNama.getText(), idUser, namaDompet);
//        laporanBulanan.tampilkanTable();
        laporanBulanan.setIdandName(idUser, userName, namaDompet);
        laporanBulanan.getNamaDompet(cbPilihDompet.getValue());
        laporanBulanan.updateSaldo();
        System.out.println(idDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML    
    public void pindahLaporan(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/laporan.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        LaporanController laporan = loader.getController();
        laporan.setIdandName(idUser, lbNama.getText(), cbPilihDompet, namaDompet);
        laporan.getNamaDompet(cbPilihDompet.getValue());
        laporan.updateSaldo();
        System.out.println(namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void tambahDompet(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newDompet.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        NewDompetController dompet = loader.getController();
        dompet.setIdUser(lbNama.getText(), idUser, namaDompet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void tambahPengeluaran(MouseEvent event) throws SQLException, IOException {
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from dompet where id_user = '" + idUser + "' ";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            lbAdaDompet.setText("ada dompet");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newDompet.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            NewDompetController dompet = loader.getController();
            dompet.setIdUser(lbNama.getText(), idUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        statement.close();
        connection.close();
        rs.close();
    }

    @FXML
    public void edit(MouseEvent event) throws Exception {
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT * from user where id_user = '" + idUser + "' ";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingProfile.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            SettingProfileController profil = loader.getController();
            profil.getProfile(rs.getString(2), rs.getString(3), rs.getString(4), idUser, namaDompet);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("gak ada user");
        }
        statement.close();
        connection.close();
        rs.close();
    }

    public void pilih(ActionEvent event) {
        System.out.println("BERHASIL UPDATE");
        this.namaDompet = cbPilihDompet.getValue();
        listKategori.clear();
        comboKategori.autosize();
        updateSaldo();
    }

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //namaDompet = cbPilihDompet.getValue();
        getNamaDompet(lunas);
        table.setVisible(false);
        kolom1.setMinWidth(120);
        kolom2.setMinWidth(120);
        kolom3.setMinWidth(120);
        kolom4.setMinWidth(120);
        kolom5.setMinWidth(90);
        kolom6.setMinWidth(120);
        kolom1.setCellValueFactory(new PropertyValueFactory("tanggal"));
        kolom2.setCellValueFactory(new PropertyValueFactory("jenis"));
        kolom3.setCellValueFactory(new PropertyValueFactory("kategori"));
        kolom4.setCellValueFactory(new PropertyValueFactory("nominal"));
        kolom5.setCellValueFactory(new PropertyValueFactory("lunas"));
        kolom6.setCellValueFactory(new PropertyValueFactory("nama"));
        table.getColumns().addAll(kolom1, kolom2, kolom3, kolom6, kolom4);

        //   System.out.println(namaDompet);
        //updateSaldo();
    }

    public void setLabelUsername(String username, String id, String dompet) {
        userName = username;
        idUser = id;
        lbNama.setText(userName);
        cbPilihDompet.setValue(dompet);
        //System.out.println(dompet);
        namaDompet = dompet;
        System.out.println(namaDompet);
    }

    public void setIdUser(String user) {
        idUser = user;
    }

    public void getNamaDompet(String nama) {
        this.namaDompet = nama;
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
                lbJumlahSisaSaldo.setText(Integer.toString(pemasukkan - pengeluaran));
                lbJumlahPemasukkan.setText(Integer.toString(pemasukkan));
                lbJumlahPengeluaran.setText(Integer.toString(pengeluaran));
                //lbJumlahPemasukkan.setText("10000");
            } catch (Exception e) {
            }
        }
    }

}


