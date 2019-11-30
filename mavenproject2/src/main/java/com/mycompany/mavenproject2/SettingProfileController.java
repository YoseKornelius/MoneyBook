package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SettingProfileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String idUser,dompet;
    
    @FXML
    private Label lbUsername, lbEmail, lbPassword;
    
    @FXML
    private TextField tfUsername, tfEmail;
    
    @FXML
    private PasswordField pfPassword, pfKonfirPassword;
    
    @FXML
    private Button btnEditProfil, btnHapusAkun, btnLogout, btnSelesai;
    
    @FXML
    public void editProfil(ActionEvent event) throws Exception{
        if(tfUsername.getText().equals("")|| tfEmail.getText().equals("")||pfPassword.getText().equals("")|| pfKonfirPassword.getText().equals("")){
        //TOLONG TAMBAHIN LABEL UNTUK ERROR HANDLING NYA JAVA KU TIBA TIVBA GAK MAU KEBUKA
        }
        else{
            Connection connection = sqliteConnect.connect().Connector();
            Statement statement = connection.createStatement();
            String email=tfEmail.getText();
            String username=tfUsername.getText();
            String query1 = "SELECT username from user where username ='"+username+"'";
            String query2 = "SELECT email from user where username ='"+email+"'";
            ResultSet rs = statement.executeQuery(query1);
            ResultSet rs2 = statement.executeQuery(query2);
            if(!rs.next() || !rs2.next()){
            if(isValid(email)){
            if(pfPassword.getText().equals(pfKonfirPassword.getText())){  
                try {
//                    Connection connection = sqliteConnect.connect().Connector();
//                    Statement statement = connection.createStatement();
                    String update = "UPDATE user SET username = '"+tfUsername.getText()+"', email = '"+tfEmail.getText()+"', password = '"+pfPassword.getText()+"' where id_user = '"+idUser+"'";
                    int hasil = statement.executeUpdate(update);
                    if(hasil==1){
                        lbUsername.setText(tfUsername.getText());
                        lbEmail.setText(tfEmail.getText());
                        lbPassword.setText(pfPassword.getText()); 
                        tfUsername.setText("");
                        tfEmail.setText("");
                        pfPassword.setText("");
                        pfKonfirPassword.setText("");
                        statement.close();
                        connection.close();                    
                    }

                } catch (Exception e) {
                    System.err.print( e.getClass().getName() + ": " + e.getMessage());
                }
            }
            }
            }
            
        }
        
//        if(pfPassword.getText().equals(pfKonfirPassword.getText())){  
//            Connection connection = sqliteConnect.connect().Connector();
//            Statement statement;
//            statement = connection.createStatement();
//            String query = "UPDATE user SET username = '"+tfUsername.getText()+"', email = '"+tfEmail.getText()+"', password = '"+pfPassword.getText()+"' where username = '"+lbUsername.getText()+"'";
//            int hasil = statement.executeUpdate(query);
//            System.out.println("berhasil update");
//            connection.close();
//            //balikkan lagi ke profile
//            if(hasil==1){
//                lbUsername.setText(tfUsername.getText());
//                lbEmail.setText(tfEmail.getText());
//                lbPassword.setText(pfPassword.getText());
//            }      
//       }else{
//            
//       }
      
        
        
    }
    
    @FXML
    public void hapusAkun(ActionEvent event) throws Exception{
        Connection connection = sqliteConnect.connect().Connector();
        Statement statement = connection.createStatement();
        String query = "DELETE FROM user where id_user='"+idUser+"'";
        int hasil = statement.executeUpdate(query);
        if(hasil==1){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newLogin.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void logout(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newLogin.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void selesai(ActionEvent event) throws Exception{                
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            HomeController home = loader.getController();
            home.setLabelUsername(lbUsername.getText(), idUser,dompet);
            home.updateSaldo();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void getProfile(String username, String email, String pass, String user, String dompet){
        lbUsername.setText(username);
        lbEmail.setText(email);
        lbPassword.setText(pass);
        idUser=user;
        this.dompet=dompet;
    }
    
    public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
    
}





