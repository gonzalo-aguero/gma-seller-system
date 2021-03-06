package com.gmasoftware.sellersystem.database;


import com.gmasoftware.sellersystem.messages.Alert;
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
    private static final String[] MySQLQueries = {
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
    
    private static final String[] SQLiteQueries = {
        "CREATE TABLE users ("
            +"id INTEGER,"
            +"username TEXT,"
            +"password TEXT,"
            +"permissionLevel INTEGER,"
            +"PRIMARY KEY(id)"
        +");",
        
        "INSERT INTO users (`id`, `username`, `password`, `permissionLevel`) VALUES"
            +"(1, 'Root', 'molV_*Y890', 0);",
        
        "CREATE TABLE products ("
            +"id INTEGER,"
            +"name TEXT,"
            +"price TEXT,"
            +"description INTEGER,"
            +"stock TEXT,"
            +"salesCount TEXT,"
            +"PRIMARY KEY(id)"
        +");",

        "CREATE TABLE sales ("
            +"id INTEGER,"
            +"totalAmount TEXT,"
            +"totalProductCount TEXT,"
            +"date INTEGER,"
            +"productList TEXT,"
            +"productUnits TEXT,"
            +"user TEXT,"
            +"PRIMARY KEY(id)"
        +");",
    };
    
    protected static void checkIfTablesExist(){
        var db = DB.getInstance();
        if(!db.connected){
            db.connect();
        }
        
        try {
            PreparedStatement ps;
            if(DB.getInstance().DB_TYPE.equals("mysql")){
                ps = db.connection.prepareStatement("SHOW TABLES;");
            }else{
                //The db type is sqlite
                ps = db.connection.prepareStatement("SELECT * FROM sqlite_master WHERE type = \"table\";");
            }
            ResultSet result = ps.executeQuery();
            
            int tablesCount = 0;
            while(result.next()){
                tablesCount++;
            }
            
            if(tablesCount < 1 ){
                Model.executeQueries();
            }else if(tablesCount < DB.numberOfTables){
                Alert.alert(null, "Ha ocurrido un error. Es posible que se haya perdido informaci??n de la base de datos."
                        + "\nLe recomendamos comunicarse con soporte t??cnico para solucionar el problema.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            db.disconnect();
        }
    }
    
    /**
     * It creates all the necessary tables in the database.
     */
    private static void executeQueries(){
        var db = DB.getInstance();
        db.connect();
        db.setAutoDisconnect(false);
        if(DB.getInstance().DB_TYPE.equals("mysql")){
            for (String query : MySQLQueries) {
                System.out.println("query: "+query);
                db.execute(query);
            }
        }else{
            for (String query : SQLiteQueries) {
                System.out.println("query: "+query);
                db.execute(query);
            }
        }
        db.disconnect();
    }
}
