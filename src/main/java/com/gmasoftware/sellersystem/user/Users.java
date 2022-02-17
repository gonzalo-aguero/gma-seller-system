/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.user;

import com.gmasoftware.sellersystem.database.DB;
import com.gmasoftware.sellersystem.messages.Alert;

/**
 *
 * @author GMA Software
 */
public class Users {
    private static Object[][] users;
    
    /**
     * Load an get all users.
     * @return 
     */
    public static Object[][] getUsers(){
        loadUsers();
        return users;
    }
    
    public static Object[][] getUsersForTable(){
        var users_ = getUsers();
        
        for (Object[] user : users_) {
            var pl = String.valueOf(user[2]); //permissionLevel
            switch (pl) {
                case "0":
                    user[2] = "Root";
                    break;
                case "1":
                    user[2] = "Administrador";
                    break;
                case "2":
                    user[2] = "Gestor de Stock";
                    break;
                case "3":
                    user[2] = "Gestor de Ventas";
                    break;
            }
        }
        
        return users;
    }
    
    /**
     * Load all the users from the database and set it to "this.users".
     */
    private static void loadUsers(){
        var valuesToGet = new String[]{
            "id",
            "username",
            "permissionLevel"
        };
        var whereCondition = "1";
        
        var db = DB.getInstance();
        db.connect();
        var result = db.get("users", valuesToGet, whereCondition);
        
        users = new Object[result.length][3];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i];
            users[i][0] = row[0];// user ID
            users[i][1] = row[1];// username
            users[i][2] = row[2];// permission level
        }
    }
    
    /**
     * Create a new user.
     * @param username
     * @param password
     * @param permissionLevel 
     */
    public static void createUser(String username, String password, String permissionLevel){
        var db = DB.getInstance();
        
        var keys = new String[]{"id", "username", "password", "permissionLevel"};
        var values = new String[]{
            db.calculateID("users", db.FIRST_POSSIBLE_VALUE),
            username,
            password,
            permissionLevel
        };
        
        db.connect();
        db.insert("users", keys, values);
    }
    
    /**
     * Get a specific user by ID.
     * @param userID
     * @return userID, username, permissionLevel.
     */
    public static Object[] getUser(int userID){
        var valuesToGet = new String[]{
            "id",
            "username",
            "permissionLevel"
        };
        
        var whereCondition = "id = \""+ userID +"\"";
        
        var db = DB.getInstance();
        db.connect();
        var result = db.get("users", valuesToGet, whereCondition);
        
        return new String[]{
            result[0][0],// user ID
            result[0][1],// username
            result[0][2]// permission level
        };
    }
    
    /**
     * Update the user data.
     * @param userID ID of the user to update.
     * @param username
     * @param password The new password. In case you don't want to edit the password it will be null.
     * @param permissionLevel
     */
    public static void updateUser(int userID, String username, String password, String permissionLevel){
        var db = DB.getInstance();
        String[] keys;
        String[] values;
        
        if(password == null){
            keys = new String[]{"username", "permissionLevel"};
            values = new String[]{
                username,
                permissionLevel
            };
        }else{
            keys = new String[]{"username", "password", "permissionLevel"};
            values = new String[]{
                username,
                password,
                permissionLevel
            };
        }
        
        String whereCondition = "id = \""+ userID +"\"";
        
        db.connect();
        db.update("users", keys, values, whereCondition);
    }
    
    /**
     * Delete the users from a IDs array, one by one.
     * @param userIDs 
     */
    public static void deleteUsers(int[] userIDs){
        loadUsers();
        
        for (int userID : userIDs) {
            boolean isRootUser = true;
            
            //To prevent the root user from being removed.
            for (Object[] user : users) {
                var id = (String) user[0];
                
                if(Integer.parseInt(id) == userID){
                    var ps = user[2]; //permissionLevel.
                    isRootUser = ps.equals("0");
                }
            }
            
            if(!isRootUser){
                var db = DB.getInstance();
                String whereCondition = "id = \""+userID+"\"";
                db.connect();
                db.delete("users", whereCondition);
            }else{
                Alert.alert(null, "No puedes eliminar el usuario Root.");
                System.out.println("The root user cannot be removed.");
                return;
            }
        }
    }
}
