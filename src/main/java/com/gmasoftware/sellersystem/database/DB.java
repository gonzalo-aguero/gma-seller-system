/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.database;

import com.gmasoftware.sellersystem.messages.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author GMA Software
 */
public class DB {
    private final String username = "root";
    private final String password = "";
    private final String dbName = "gma_sellersystem";
    private final String url = "jdbc:mysql://localhost/" + dbName;
    private Connection connection;
    private boolean  connected;
    private boolean autoDisconnect; //Automatically disconnect after request.
    
    public DB(){
        connected = false;
        autoDisconnect = true;
    }
    
    /**
     * TRUE for automatically disconnect after request.
     * @param autoDisconnect 
     */
    public void setAutoDisconnect(boolean autoDisconnect) {
        this.autoDisconnect = autoDisconnect;
    }
    
    public void connect(){
        try{
            connection = DriverManager.getConnection(url, username, password);
            connected = true;
        } catch (SQLException e) {
            Alert.alert(null,
                    "No se ha podido conectar a la base de datos."
                    + "\nSQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode()
                );
            throw new Error(new IllegalStateException("Cannot connect to the database.", e));
        }
    }
    
    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param table Table name.
     * @param keys Example: ["name","age"].
     * @param values Example: ["Mike","31"].
     */
    public void insert(String table, String[] keys, String[] values){
        if(!connected){
            System.out.println("Database connection not established.");
            return;
        }
        
        String keysStr = "";
        String valuesStr = "";
        int keysCount = keys.length;
        for (int i = 0; i < keysCount; i++) {
            if(i < (keysCount - 1)){
                //If it is not the last item.
                keysStr += keys[i] + ", ";
                valuesStr += "?, ";
            }else{
                //It is not necessary to add a "," to the last item.
                keysStr += keys[i];
                valuesStr += "?";
            }
        }
        
        String sql = "INSERT INTO " + table + " ("+ keysStr +") VALUES ("+ valuesStr +")";
        System.out.println(sql);

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            
            for (int i = 0; i < keysCount; i++) {
                ps.setString( (i+1), values[i]);
            }

            ps.execute();
            
            System.out.println("Successful operation");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(autoDisconnect){
                disconnect();
            }
        }
    }
    
    /**
     * @param table Table name
     * @param valuesToGet Example: ["name", "age"]. You can also use ["*", "numberOfColumns"].
     * @param whereCondition Examples: "1", "id = 25", "name = "Eduardo"
     * @return 
     */
    public String[][] get(String table, String[] valuesToGet, String whereCondition ){
        
        String[][] resultArr = {};
        
        if(!connected){
            System.out.println("Database connection not established.");
        }else{
            String valuesToGetStr = "";
            ResultSet result;
            int valuesToGetCount = 0;
            
            //The column names as a String for the SQL query.
            if("*".equals(valuesToGet[0])){
                valuesToGetStr = "*";
            }else{
                valuesToGetCount = valuesToGet.length;
                for (int i = 0; i < valuesToGetCount; i++) {
                    if(i < (valuesToGetCount - 1)){
                        //If it is not the last item.
                        valuesToGetStr += valuesToGet[i] + ", ";
                    }else{
                        //It is not necessary to add a "," to the last item.
                        valuesToGetStr += valuesToGet[i];
                    }
                }
            }

            String sql = "SELECT "+ valuesToGetStr +" FROM "+ table +" WHERE "+ whereCondition +";";
            
            try {
                PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
                
                result = ps.executeQuery();
                
                // Get the column count.
                if("*".equals(valuesToGet[0])){
                    valuesToGetCount = result.getMetaData().getColumnCount();
                }
                
                // Get the number of rows.
                var rowCount = 0;
                while (result.next()) {
                    rowCount++;
                }  
                result.beforeFirst();
                
                if(rowCount > 0){
                    var currentRow = 0;
                    resultArr = new String[rowCount][valuesToGetCount];
                    
                    //Get the name of the table columns.
                    if("*".equals(valuesToGet[0])){
                        valuesToGet = new String[valuesToGetCount]; //Recreate the variable and set its new size.
                        for (int i = 0; i < valuesToGetCount; i++) {
                            valuesToGet[i] = result.getMetaData().getColumnName(i + 1);
                        }
                    }
                    
                    while(result.next()){
                        for (int i = 0; i < valuesToGetCount; i++) {
                            var value = result.getString(valuesToGet[i]);
                            resultArr[currentRow][i] = value;     
                        }
                        currentRow++;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if(autoDisconnect){
                    disconnect();
                }
            }
        }
        
        return resultArr;
    }
}
