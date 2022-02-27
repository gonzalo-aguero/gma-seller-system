package com.gmasoftware.sellersystem.database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMA Software
 */
public class Model {
    private static final String[] queries = {
        "CREATE TABLE `users` ("
            +"`id` int(11) NOT NULL,"
            +"`username` varchar(50) COLLATE utf8_bin NOT NULL,"
            +"`password` varchar(500) COLLATE utf8_bin NOT NULL,"
            +"`permissionLevel` int(5) NOT NULL"
        +") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;",

        "ALTER TABLE `users`"
            +"ADD PRIMARY KEY (`id`);",
        
        "INSERT INTO `users` (`id`, `username`, `password`, `permissionLevel`) VALUES"
            +"(1, 'Root', 'molV_*Y890', 0);",
            
        "CREATE TABLE `products` ("
            +"`id` int(10) NOT NULL,"
            +"`name` varchar(100) COLLATE utf8_bin NOT NULL,"
            +"`price` varchar(100) COLLATE utf8_bin NOT NULL,"
            +"`description` varchar(500) COLLATE utf8_bin NOT NULL,"
            +"`stock` varchar(10) COLLATE utf8_bin NOT NULL,"
            +"`salesCount` varchar(10) COLLATE utf8_bin NOT NULL"
        +") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;",
        
        "ALTER TABLE `products`"
            + "ADD PRIMARY KEY (`id`);",
        
        "CREATE TABLE `sales` ("
            +"`id` int(11) NOT NULL,"
            +"`totalAmount` varchar(50) COLLATE utf8_bin NOT NULL,"
            +"`totalProductCount` int(11) NOT NULL,"
            +"`date` datetime DEFAULT current_timestamp(),"
            +"`productList` varchar(150) COLLATE utf8_bin NOT NULL,"
            +"`productUnits` varchar(150) COLLATE utf8_bin NOT NULL,"
            +"`user` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'User who registered the sale'"
        +") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;",
        
        "ALTER TABLE `sales`"
            +"ADD PRIMARY KEY (`id`);"
    };
    
    protected static void checkIfTablesExist(){
        var db = DB.getInstance();
        if(!db.connected){
            db.connect();
        }
        
        try {
            PreparedStatement ps = db.connection.prepareStatement("SHOW TABLES;");
            ResultSet result = ps.executeQuery();
            
            int tablesCount = 0;
            while(result.next()){
                var table = result.getString(1);
                tablesCount++;
            }
            
            if(tablesCount > 0){
                Model.createModel();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            db.disconnect();
        }
    }
    
    private static void createModel(){
        executeQueries();
    }
    
    private static void executeQueries(){
        for (String query : queries) {
            System.out.println("SQL: "+query);
        }
    }
}
