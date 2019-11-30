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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        home.updateSaldo();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private ImageView imgHome;
    private String idUser, namaUser, dompet, idDompet, idKategori, tampNama, tampNominal;
    
    @FXML
    private Tab editPemasukkanTab, editPemasukkanTabHapus;
    
    @FXML
    private TextField txtKeteranganPemasukkan, txtNominalPemasukkan, txtEditNominal, txtEditKeterangan;
    @FXML
    private DatePicker tgl_pemasukkan, tglEditPemasukkan;

    @FXML
    public ComboBox<String> cbTambahKategori;

   
    
    
    
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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

