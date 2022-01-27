/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

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
}
