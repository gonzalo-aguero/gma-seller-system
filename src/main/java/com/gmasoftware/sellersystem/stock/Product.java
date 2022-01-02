/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;

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
    private ImageIcon image;
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

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
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
            String imageBlob,
            int stock,
            int salesCount ) throws IOException{
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.salesCount = salesCount;
        
        // Product image
//        try{
//            byte bi[] = Byte.parseByte(imageBlob);
//            BufferedImage image = null;
//            InputStream in = new ByteArrayInputStream(bi);
//            image = ImageIO.read(in);
//            ImageIcon imgIcon = new ImageIcon(image.getScaledInstance(60, 60, 0));
//            this.image = imgIcon;
//        }catch(Error e){
//            System.out.println("ERROR: "+e);
//            this.image = null;
//        }
        this.image = null;
    }
}
