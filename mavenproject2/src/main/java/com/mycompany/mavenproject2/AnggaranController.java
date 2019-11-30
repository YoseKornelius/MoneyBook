
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
