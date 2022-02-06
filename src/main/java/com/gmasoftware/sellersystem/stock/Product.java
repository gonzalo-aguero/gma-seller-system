/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;

import com.gmasoftware.sellersystem.database.DB;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 *
 * @author GMA Software
 */
public class Product {
    
    private final int id;
    private String name;
    private float price;
    private String description;
    private int stock;
    private int salesCount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }
    
    public Product(
            int id,
            String name,
            float price,
            String description,
            int stock,
            int salesCount ) throws IOException{
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.salesCount = salesCount;
    }
    
    public void removeProductStockAfterSale(int soldUnits){
        int newStock = this.stock - soldUnits;
        int newSalesCount = this.salesCount + soldUnits;
        
        var db = DB.getInstance();
        db.connect();
        
        var keys = new String[]{"stock","salesCount"};
        var values = new String[]{String.valueOf(newStock), String.valueOf(newSalesCount)};
        var whereCondition = "id = \""+ this.id +"\"";
        
        db.update("products", keys, values, whereCondition);
    }
    
    public void recoverProductStock(int soldUnits){
        var newStock = this.stock + soldUnits;
        var newSalesCount = this.salesCount - soldUnits;
                
        var keys = new String[]{"stock", "salesCount"};
        var values = new String[]{
            String.valueOf(newStock),
            String.valueOf(newSalesCount)
        };
        String whereCondition = "id = \""+ this.id +"\"";
     
        var db = DB.getInstance();
        db.connect();
        db.update("products", keys, values, whereCondition);
    }
}
