/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import com.gmasoftware.sellersystem.database.DB;

/**
 *
 * @author GMA Software
 */
public class Sales {
    
    private Sale[] sales = null;

    public Sales(){
        setSales(loadSales());
    }
    
    public Sale[] getSales() {
        return sales;
    }
    
    private void setSales(Sale[] sales){
        this.sales = sales;
    }
    
    /**
     * Get the sales from the database.
     * @return Array of sales.
     */
    private Sale[] loadSales(){
        var db = new DB();
        db.connect();
        
        var result = db.get("sales", new String[]{"*"}, "1");
        
        int resultCount = result.length;

        if(resultCount < 1){
            return null;
        }
        
        
        sales = new Sale[resultCount]; //Recreate the variable and set its new size.
        for (int i = 0; i < resultCount; i++) {
            //Convert string with the IDs of the products to an int[] array.
            String[] productListStr = result[i][4].split(",");
            var productListCount = productListStr.length;
            int[] productList = new int[productListCount];
            for (int j = 0; j < productListCount; j++) {
                var productID = productListStr[j];
                productList[j] = Integer.parseInt(productID);
            }

            //Convert string with the units of each product to an int[] array.
            String[] productUnitsStr = result[i][5].split(",");
            int[] productUnits = new int[productListCount];
            for (int j = 0; j < productListCount; j++) {
                var units = productUnitsStr[j];
                productUnits[j] = Integer.parseInt(units);
            }

            //Create the new instance for the current sale.
            sales[i] = new Sale(
                    Integer.parseInt(result[i][0]), // ID
                    Float.parseFloat(result[i][1]), // Total Amount
                    Integer.parseInt(result[i][2]), // Total Product Count
                    result[i][3], // Date
                    productList, // Product List
                    productUnits, // Product Units
                    result[i][6] // User
            );
        }
        
        return sales;
    }
    
    public void reloadSales(){
        setSales(loadSales());
    }
    
    /***
     * This function returns the sales as a String array (It's used for the table model).
     * @return Sales as a String Array.
     */
    public String[][] getSalesAsArray(){
        if(sales == null){
            String[][] withoutSales = {};
            return withoutSales;
        }
        
        var salesCount = sales.length;
        String[][] salesArr = new String[salesCount][7];
        
        for (int i = 0; i < salesCount; i++) {

            var productList = sales[i].getProductList();
            var productUnits = sales[i].getProductUnits();
            var productCount = productList.length;
            var products = "";
            for (int j = 0; j < productCount; j++) {
                products += "\n"+ productList[j] + " ---> " + productUnits[j];
            }

            salesArr[i][0] = String.valueOf(sales[i].getId());
            salesArr[i][1] = String.valueOf(sales[i].getTotalAmount());
            salesArr[i][2] = String.valueOf(sales[i].getTotalProductCount());
            salesArr[i][3] = String.valueOf(sales[i].getDate());
            salesArr[i][4] = products;
            salesArr[i][5] = sales[i].getUser();
        }
        
        return salesArr;
    }
    
    public boolean deleteProducts(int[] productIDs){
        var db = new DB();
        db.setAutoDisconnect(false);//IMPORTANT: After updating, I will disconnect "manually".
        db.connect();
        
        var productIDsCount = productIDs.length;
        
        //Delete the sales, one by one.
        for (int i = 0; i < productIDsCount; i++) {
            String whereCondition = "id = \""+ productIDs[i] +"\"";
            
            db.delete("products", whereCondition);
            System.out.println("Producto eliminado: "+productIDs[i]);
        }
        
        db.disconnect();//Never forget ;)
        return true;
    }
}
