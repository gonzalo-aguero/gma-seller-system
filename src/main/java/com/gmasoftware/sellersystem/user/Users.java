/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.user;

import com.gmasoftware.sellersystem.database.DB;

/**
 *
 * @author GMA Software
 */
public class Users {
    private static Object[][] users;
    
    public static Object[][] getUsers(){
        loadUsers();
        return users;
    }
    
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
}
