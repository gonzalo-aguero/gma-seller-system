/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import com.gmasoftware.sellersystem.database.DB;
import com.gmasoftware.sellersystem.stock.Product;
import com.gmasoftware.sellersystem.stock.Stock;

/**
 * Structure: 
 * id, totalAmount, totalProductCount, date, productList (IDs), productUnits, user.
 * @author GMA Softare
 */
public class Sale {
    private final int id;
    private final float totalAmount;
    private final int totalProductCount;
    private final String date;
    private final int[] productList;
    private final int[] productUnits;
    private final String user;
    
    public Sale(
            int id,
            float totalAmount,
            int totalProductCount,
            String date,
            int[] productList,
            int[] productUnits,
            String user 
        ){
        
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalProductCount = totalProductCount;
        this.date = date;
        this.productList = productList;
        this.productUnits = productUnits;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public int getTotalProductCount() {
        return totalProductCount;
    }

    public String getDate() {
        return date;
    }

    public int[] getProductList() {
        return productList;
    }

    public int[] getProductUnits() {
        return productUnits;
    }

    public String getUser() {
        return user;
    }
    
    /**
     * Undo the sale.
     * @return 
     */
    public boolean undoSale(){
        // First, return units sold to the stock of each product.
        // Iterate over the products IDs involved.
        for (int i = 0; i < this.productList.length; i++) {
            int involvedProductID = this.productList[i];
            var stock = Stock.getInstance().getProducts();
            
            // Iterate over all products to check ID match and process each product involved.
            for (Product product : stock){
                if(product.getId() == involvedProductID){
                    int soldUnits = this.productUnits[i];
                    product.recoverProductStock(soldUnits);
                }
            }
        }
        
        // Then, delete the sale record.
        String whereCondition = "id = \""+ this.id +"\"";
        var db = DB.getInstance();
        db.connect();
        db.delete("sales", whereCondition);
        return true;
    }
}
