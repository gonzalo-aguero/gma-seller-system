/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.database;

import com.gmasoftware.sellersystem.messages.Alert;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  ~~~ Singleton ~~~.
 * @author GMA Software
 */
public class DB {
    /** Single global instance **/
    private static DB DB_Instance;
    protected static final int numberOfTables = 3;
    
    protected final String DB_TYPE;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    private String DB_NAME;
    private String DB_SERVER;
    private String DB_LINK;
    
    protected Connection connection;
    protected boolean  connected;
    protected boolean autoDisconnect; //Automatically disconnect after request.
    
    // CALCULATE ID METHODS.
    public final String HIGHEST_VALUE = "HIGHEST_VALUE";
    public final String FIRST_POSSIBLE_VALUE = "FIRST_POSSIBLE_VALUE";
    
    public static synchronized DB getInstance(){
        if(DB_Instance == null){
            DB_Instance =  new DB();
            Model.checkIfTablesExist();
        }
        return DB_Instance;
    }
    
    private DB(){
        /**
         * Get the database credentials from the .env file.
         */
        Dotenv env = Dotenv.load();
        String db_type = env.get("DB_TYPE");
        String db_username = env.get("DB_USERNAME");
        String db_password = env.get("DB_PASSWORD");
        String db_name = env.get("DB_NAME");
        String db_server = env.get("DB_SERVER");
        
        if(!"null".equals(db_type) && !"".equals(db_type) && db_type != null){
            DB_TYPE = db_type;
        }else{
            DB_TYPE = "sqlite";
        }
        
        if("mysql".equals(DB_TYPE)){
            if(!"null".equals(db_username) && !"".equals(db_username) && db_username != null){
                DB_USERNAME = db_username;
            }else{
                DB_USERNAME = "root";
            }
            if(!"null".equals(db_password) && !"".equals(db_password) && db_password != null){
                DB_PASSWORD = db_password;
            }else{
                DB_PASSWORD = "";
            }
            if(!"null".equals(db_name) && !"".equals(db_name) && db_name != null){
                DB_NAME = db_name;
            }else{
                DB_NAME = "gma_sellersystem";
            }
            if(!"null".equals(db_server) && !"".equals(db_server) && db_server != null){
                DB_SERVER = db_server;
            }else{
                DB_SERVER = "localhost";
            }
            DB_LINK = "jdbc:mysql://"+ DB_SERVER +"/"+ DB_NAME; 
            
        }else if("sqlite".equals(DB_TYPE)){
            DB_LINK = "jdbc:sqlite:database.db";
        }
        
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
            if("mysql".equals(DB_TYPE)){
                connection = DriverManager.getConnection(DB_LINK, DB_USERNAME, DB_PASSWORD);
                connected = true;
            }else if("sqlite".equals(DB_TYPE)){
                connection = DriverManager.getConnection(DB_LINK);
                connected = true;
            }
            if(connected){
                System.out.println("Connected.");
            }
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
            connected = false;
            
            // To prevent a distracted person from forgetting to activate the automatic disconnection again O_o
            setAutoDisconnect(true);
            System.out.println("Connection closed");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Execute the "INSERT" SQL statement to add a new row to the table specified in the parameters.
     * @param table Table name.
     * @param keys Keys or columns of the values to insert. Example: {"name","age"}.
     * @param values Values to insert. Example: {"Mike","31"}.
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
     * Execute the "SELECT" SQL statement to get the data specified in the parameters.
     * @param table Table name
     * @param valuesToGet Keys or columns to get. Example: ["name", "age"]. You can also use ["*"].
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

            String sql_0 = "SELECT count(*) FROM "+ table +" WHERE "+ whereCondition +";";
            String sql_1 = "SELECT "+ valuesToGetStr +" FROM "+ table +" WHERE "+ whereCondition +";";
            
            try {
                // Get the number of rows.
                PreparedStatement ps = connection.prepareStatement(sql_0);
                result = ps.executeQuery();
                result.next();
                int rowCount = Integer.parseInt(result.getString(1));
                
//                PreparedStatement ps = connection.prepareStatement(sql_1, ResultSet.TYPE_SCROLL_SENSITIVE, 
//                        ResultSet.CONCUR_UPDATABLE);
                ps = connection.prepareStatement(sql_1);
                
                result = ps.executeQuery();
                
                // Get the column count.
                if("*".equals(valuesToGet[0])){
                    valuesToGetCount = result.getMetaData().getColumnCount();
                }

                if(rowCount > 0){
                    var currentRow = 0;
                    resultArr = new String[rowCount][valuesToGetCount];
                    
                    // Get the name of the table columns if you want to select all the tables.
                    // ...and assign it to the valuesToGet array :)
                    if("*".equals(valuesToGet[0])){
                        valuesToGet = new String[valuesToGetCount]; //Recreate the variable and set its new size.
                        for (int i = 0; i < valuesToGetCount; i++) {
                            valuesToGet[i] = result.getMetaData().getColumnName(i + 1);
                        }
                    }
                    
                    //Assign the result data to the final array.
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
        
        //return the final array :)
        return resultArr;
    }
    
    /**
     * Execute the "UPDATE" SQL statement to update the data specified in the parameters.
     * @param table Table name.
     * @param keys Keys or columns to update. Example: ["name", "age"]. You can also use ["*"].
     * @param values Values to update. Example: ["John", "32"].
     * @param whereCondition Examples: "1", "id = 25", "name = "Eduardo".
     */
    public void update(String table, String[] keys, String[] values, String whereCondition ){
        if(!connected){
            System.out.println("Database connection not established.");
            return;
        }
        
        String placesForValues = "";
        int keysCount = keys.length;
        for (int i = 0; i < keysCount; i++) {
            if(i < (keysCount - 1)){
                //If it is not the last item.
                placesForValues += keys[i] +" = ?, ";
            }else{
                //It is the last item, so it is not necessary to add a ",".
                placesForValues += keys[i] +" = ?";
            }
        }
        
        String sql = "UPDATE "+ table +" SET "+placesForValues+" WHERE "+ whereCondition +";";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            
            for (int i = 0; i < keysCount; i++) {
                ps.setString((i+1), values[i]);
            }

            ps.executeUpdate();
            
            System.out.println("Successful operation");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(autoDisconnect){
                disconnect();
            }
        }
    }
    
    public void delete(String table, String whereCondition){
        if(!connected){
            System.out.println("Database connection not established.");
            return;
        }
        
        String sql = "DELETE FROM "+ table +" WHERE "+ whereCondition +";";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

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
    
    public String calculateID(String table, String method){
        connect();
        autoDisconnect = false;
        
        if(HIGHEST_VALUE.equals(method)){
            // The new ID will be the highest possible value.
            // For example: 1, 2, 5, 10, 11, (12) <--- This will be the new ID.
            var result = get(table, new String[]{"id"}, "1");
            int highestValue = 0;
            
            //Get the highest ID
            for (String[] row : result) {
                int rowID = Integer.parseInt(row[0]);
                if(rowID > highestValue){
                    highestValue = rowID;
                }
            }
            
            disconnect();
            return String.valueOf(highestValue +1);
            
        }else if(FIRST_POSSIBLE_VALUE.equals(method)){
            // The new ID will be the lowest possible value.
            // For example: 1, 2, (3), 5, 10, 11, 12 <--- This will be the new ID.
            int newId = 1;
            String[][] result;
            
            //Get the first available value for the new ID
            System.out.println("id: "+newId);
            result = get(table, new String[]{"id"}, "id = \""+ newId +"\"");
            if(result.length > 0){
                do{
                    newId++;
                    System.out.println("id: "+newId);
                    result = get(table, new String[]{"id"}, "id = \""+ newId +"\"");
                }while(result.length > 0);
            }
                    
            disconnect();
            this.autoDisconnect = true;
            return String.valueOf(newId);
        }
        
        return calculateID(table, HIGHEST_VALUE);
    }
    
    protected void execute(String query){
        if(!connected){
            System.out.println("Database connection not established.");
            return;
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
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
    
    protected ResultSet executeQuery(String query){
        if(!connected){
            System.out.println("Database connection not established.");
            return null;
        }
        
        ResultSet result = null;
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            result = ps.executeQuery(query);
            System.out.println("Successful operation");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally{
            if(autoDisconnect){
                disconnect();
            }
        }
        
        return result;
    }
}
