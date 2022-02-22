/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import com.gmasoftware.sellersystem.database.DB;
import com.gmasoftware.sellersystem.messages.Alert;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * ~~~ Singleton ~~~.
 * @author GMA Software
 */
public class Stock {
    /** Single global instance **/
    private static Stock stockInstance;
    public static synchronized Stock getInstance(){
        if(stockInstance == null){
            stockInstance =  new Stock();
        }else{
            // Products are retrieved from the database again to ensure up-to-date information.
            stockInstance.setProducts(stockInstance.loadStock());
        }
        
        return stockInstance;
    }
    
    
    private Product[] products = null;

    public Product[] getProducts() {
        return products;
    }
    
    private void setProducts(Product[] products){
        this.products = products;
    }
    
    private Stock(){
        setProducts(loadStock());
    }
    
    /**
     * Get the products from the database.
     * @return Array of products.
     */
    private Product[] loadStock(){
        var db = DB.getInstance();
        db.connect();
        
        var result = db.get("products", new String[]{"*"}, "1");
        
        int resultCount = result.length;

        if(resultCount < 1){
            return null;
        }
        
        
        products = new Product[resultCount]; //Recreate the variable and set its new size.
        for (int i = 0; i < resultCount; i++) {
            try {
                products[i] = new Product(
                        Integer.parseInt(result[i][0]), // ID
                        result[i][1], // Name
                        Float.parseFloat(result[i][2]), // Price
                        result[i][3], // Description
                        Integer.parseInt(result[i][4]), // Stock
                        Integer.parseInt(result[i][5]) // Sales count
                );
            } catch (IOException ex) {
                Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return products;
    }
    
    public void reloadStock(){
        setProducts(loadStock());
    }
    
    /***
     * This function returns the stock as a String array (It's used for the stock table)
     * @return Stock as a String Array
     */
    public String[][] getStockAsArray(){
        if(products == null){
            String[][] emptyStock = {};
            return emptyStock;
        }
        
        var productsCount = products.length;
        String[][] stockArr = new String[productsCount][7];
        
        for (int i = 0; i < productsCount; i++) {
            stockArr[i][0] = String.valueOf(products[i].getId());
            stockArr[i][1] = products[i].getName();
            stockArr[i][2] = String.valueOf(products[i].getPrice());
            stockArr[i][3] = products[i].getDescription();
            stockArr[i][4] = String.valueOf(products[i].getStock());
            stockArr[i][5] = String.valueOf(products[i].getSalesCount());
        }
        
        return stockArr;
    }
    
    public void createNewProduct(String[] valuesToInsert){
        var db = DB.getInstance();
        db.connect();
        
        String[] keys = {"id","name","price","description","stock","salesCount"};
        
        db.insert("products", keys, valuesToInsert);
    }
    
    public boolean saveProducts(String[][] tableData){
        var db = DB.getInstance();
        db.setAutoDisconnect(false);//IMPORTANT: After updating, I will disconnect "manually".
        db.connect();
        
        String[] keys = {"name","price","description","stock","salesCount"};
        var tableDataCount = tableData.length;
        
        //Update the data of each product, one by one.
        for (int i = 0; i < tableDataCount; i++) {
            
            String whereCondition = "id = \""+ tableData[i][0] +"\"";
            String[] values = {
                tableData[i][1],// Product name
                tableData[i][2],// Product price
                tableData[i][3],// Product description
                tableData[i][4],// Product stock
                tableData[i][5]// Product sales count
            };
            
            db.update("products", keys, values, whereCondition);
        }
        
        db.disconnect();//Never forget ;)
        return true;
    }
    
    public boolean deleteProducts(int[] productIDs){
        var db = DB.getInstance();
        db.setAutoDisconnect(false);//IMPORTANT: After updating, I will disconnect "manually".
        db.connect();
        
        var productIDsCount = productIDs.length;
        
        //Delete the products, one by one.
        int deletedProductCounter = 0;
        for (int i = 0; i < productIDsCount; i++) {
            String whereCondition = "id = \""+ productIDs[i] +"\"";
            
            db.delete("products", whereCondition);
            System.out.println("Producto eliminado: "+productIDs[i]);
            deletedProductCounter++;
        }
        
        Alert.alert(null, deletedProductCounter + " productos eliminados con éxito.");
        db.disconnect();//Never forget ;)
        return true;
    }
}








