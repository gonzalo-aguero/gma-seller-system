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
public class User {
    private static User userInstance;
    private int userID;
    private String username;
    private boolean authenticated;
    
    private User(){
        authenticated = false;
    }
    
    public static synchronized User getInstance(){
        if(userInstance == null){
            userInstance =  new User();
        }
        
        return userInstance;
    }
    
    /**
     * Authenticate the user.
     * @param typedUsername Username
     * @param typedPassword Password
     * @return Returns if the user is authenticated.
     */
    public boolean authenticate(String typedUsername, String typedPassword){
        try{
            DB db = new DB();
            db.connect();
            String[] valuesToGet = {"id", "password"};
            String whereCondition = "username = '"+ typedUsername +"'";

            var result = db.get("users",valuesToGet, whereCondition);

            if(result.length <= 0){
                throw new Error("no-user");
            }
            
            if(typedPassword.equals(result[0][1])){
                authenticated = true;
                String userIDStr = result[0][0];
                this.userID = Integer.parseInt(userIDStr);
                this.username = typedUsername;
            }else{
                throw new Error("no-password");
            }
        }catch(Error e){
            System.out.println("ERROR: " + e);
            if("java.lang.Error: java.lang.IllegalStateException: Cannot connect to the database.".equals(String.valueOf(e))){
                throw new Error("cannot-connect-db");
            }else{
                throw e;
            }
        }
        
        
        return authenticated;
    }
    
    private void createUser(String username, String password){
        try{
            DB db = new DB();
            db.connect();
            String[] keys = {"username","password"};
            String[] values = {username, password};
            db.insert("users", keys, values);
        }catch (Error e){
            System.out.println(e);
        }
    }
    
    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
