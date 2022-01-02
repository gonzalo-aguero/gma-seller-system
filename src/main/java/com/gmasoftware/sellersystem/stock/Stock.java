/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import com.gmasoftware.sellersystem.database.DB;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author GMA Software
 */
public class Stock {
    
    private Product[] products = {};

    public Product[] getProducts() {
        return products;
    }
    
    public Stock(){
        this.products = loadStock();
    }
    
    /**
     * Get the products from the database.
     * @return Array of products.
     */
    private Product[] loadStock(){
        var db = new DB();
        db.connect();
        
        var result = db.get("products", new String[]{"*"}, "1");
        
        int resultCount = result.length;
        
        if(resultCount < 1){
            return null;
        }
        
        for (int i = 0; i < resultCount; i++) {

            products = new Product[resultCount]; //Recreate the variable and set its new size.
            
            try {
                products[i] = new Product(
                        Integer.parseInt(result[i][0]), // ID
                        result[i][1], // Name
                        Float.parseFloat(result[i][2]), // Price
                        result[i][3], // Description
                        result[i][4], // Image
                        Integer.parseInt(result[i][5]), // Stock
                        Integer.parseInt(result[i][6]) // Sales count
                );
            } catch (IOException ex) {
                Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return products;
    }
    
    /***
     * This function returns the stock as a String array.
     * @return Stock
     */
    public String[][] getStockAsArray(){
        if(products == null){
            String[][] emptyStock = {};
            return emptyStock;
        }
        
        var productsCount = products.length;
        String[][] stockArr = new String[productsCount][7];
        
        for (int i = 0; i < productsCount; i++) {
            String imageStr = (products[i].getImage() == null) ? "" : products[i].getImage().toString();
            
            stockArr[i][0] = String.valueOf(products[i].getId());
            stockArr[i][1] = products[i].getName();
            stockArr[i][2] = String.valueOf(products[i].getPrice());
            stockArr[i][3] = products[i].getDescription();
            stockArr[i][4] = String.valueOf(imageStr);
            stockArr[i][5] = String.valueOf(products[i].getStock());
            stockArr[i][6] = String.valueOf(products[i].getSalesCount());
        }
        
        return stockArr;
    }
}
