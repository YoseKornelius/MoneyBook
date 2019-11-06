/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class listDompet {
    public ArrayList<String> dompet = new ArrayList<String>();
    public void tambahListDompet(String id, ComboBox combobox){            
        //ResultSet rs;
        try (Connection connection = sqliteConnect.connect().Connector()) {
            Statement statement;
            statement = connection.createStatement();          
            String query = "SELECT nama_dompet from dompet where id_user ='"+id+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                dompet.add(rs.getString("nama_dompet"));
            }
            statement.close();
            rs.close();
        }catch(Exception e){
            e.getMessage();
        }
        combobox.setItems((ObservableList) dompet);
    }
}
