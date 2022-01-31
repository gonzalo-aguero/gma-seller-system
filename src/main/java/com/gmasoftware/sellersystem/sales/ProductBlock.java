/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author GMA Software
 */
public class ProductBlock extends JPanel{
    private JLabel comboBoxTitle;
    private JComboBox<String> productComboBox;
    
    private JLabel  priceTitle;
    private JLabel price;
    
    private JLabel unitsTitle;
    private JTextField productUnits;
    
    private JLabel subtotalTitle;
    private JLabel subtotal;
    
    private GroupLayout productBlockLayout;
    
    public ProductBlock(){
        productComboBoxFactory();
        productPriceFactory();
        productUnitsFactory();
        productSubtotalFactory();
        
        productBlockLayoutFactory();
        setLayout(productBlockLayout);
    }
    
    /**
     * ComboBox for the product selection.
     */
    private void productComboBoxFactory(){
        productComboBox = new JComboBox<>();
        productComboBox.setModel(productComboboxModel());
       
        comboBoxTitle = new JLabel();
        comboBoxTitle.setForeground(new java.awt.Color(64, 64, 64));
        comboBoxTitle.setText("Product");
        
        productComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                productComboBoxIsChangedHandler(evt);
            }
        });
    }
    
    /**
     * Create the JCombobox Model for select a product.
     * It is used for the product block.
     */
    private DefaultComboBoxModel productComboboxModel(){
        var stock = new com.gmasoftware.sellersystem.stock.Stock();
        var products = stock.getProducts();
        var productsCount = products.length;
        String[] model = new String[productsCount +1];
        model[0] = "Seleccionar";
        for (int i = 0; i < productsCount; i++) {
            var p = products[i];
            model[i+1] = "("+p.getStock()+") "+ p.getName();
        }
        return new DefaultComboBoxModel<>(model);
    }
    
    private void productPriceFactory(){
        priceTitle = new JLabel();
        price = new JLabel();
        
        priceTitle.setForeground(new java.awt.Color(64, 64, 64));
        priceTitle.setText("Price");
        price.setText("0");
    }
    
    /**
     * Product units Input.
     */
    private void productUnitsFactory(){
        unitsTitle = new JLabel();
        unitsTitle.setForeground(new java.awt.Color(64, 64, 64));
        unitsTitle.setText("Units");
        productUnits = new JTextField();
    }
    
    private void productSubtotalFactory(){
        subtotalTitle = new JLabel();
        subtotalTitle.setText("Subtotal");
        subtotal = new JLabel();
        subtotal.setText("0");
    }
    
    /**
     * Create the product block layout and add the components.
     */
    private void productBlockLayoutFactory(){
        productBlockLayout = new GroupLayout(this);
        
        productBlockLayout.setHorizontalGroup(
            productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(productBlockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(productComboBox, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(price, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(comboBoxTitle, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(priceTitle, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(unitsTitle, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subtotalTitle, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(productUnits, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subtotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        productBlockLayout.setVerticalGroup(
            productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(productBlockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxTitle)
                    .addComponent(unitsTitle)
                    .addComponent(priceTitle)
                    .addComponent(subtotalTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(productComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(productUnits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(price, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(subtotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
    
    /**
     * Event when the product combo box is changed.
     * @param evt Item Event Data.
     */
    private void productComboBoxIsChangedHandler(ItemEvent evt){
        if (evt.getSource() == productComboBox) {
            String selectedItem = (String) productComboBox.getSelectedItem();
            comboBoxTitle.setText(selectedItem);
        }
    }
}
