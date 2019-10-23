/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;
import java.sql.*;
/**
 *
 * @author msi
 */
public class sqliteConnect {
    
    public static sqliteConnect connect(){
        return new sqliteConnect();
    }
    
    public Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:MoneyBook.db");
            System.out.print(conn);
            return conn;
        }catch(Exception c){
            return null;
        }
    }
}














