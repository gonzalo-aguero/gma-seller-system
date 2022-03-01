/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.stock.Product;
import com.gmasoftware.sellersystem.stock.Stock;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.IntStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author GMA Software
 */
public class ProductBlock extends JPanel{
    private JLabel comboBoxTitle;
    private JComboBox<String> productComboBox;
    
    private JLabel  productPriceTitle;
    private JLabel productPrice;
    
    private JLabel productUnitsTitle;
    private JTextField productUnits;
    private final String maximumTextInUnits = null;
    
    private JLabel productSubtotalTitle;
    private JLabel productSubtotal;
    
    private JButton removeButton;
    
    private GroupLayout productBlockLayout;
    
    private Product selectedProduct;
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    
    private float calculatedSubtotal;
    
    private final RegisterSaleForm generalForm;
    private int indexInContainer;

    public int getIndexInContainer() {
        return indexInContainer;
    }
    public void setIndexInContainer(int indexInContainer) {
        this.indexInContainer = indexInContainer;
    }
    
    public ProductBlock(RegisterSaleForm generalForm){
        this.generalForm = generalForm;
        
        productComboBoxFactory();
        productPriceFactory();
        productUnitsFactory();
        productSubtotalFactory();
        removeButtonFactory();
        
        productBlockLayoutFactory();
        setLayout(productBlockLayout);
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
                        .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(productComboBox, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productPrice, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(comboBoxTitle, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productPriceTitle, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(productUnitsTitle, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productSubtotalTitle, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addComponent(productUnits, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productSubtotal, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        productBlockLayout.setVerticalGroup(
            productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(productBlockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxTitle)
                    .addComponent(productUnitsTitle)
                    .addComponent(productPriceTitle)
                    .addComponent(productSubtotalTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(productBlockLayout.createSequentialGroup()
                        .addGroup(productBlockLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addGap(100 ,100, 100)
                            .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addGap(100 ,100, 100)
                            .addComponent(productComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(productUnits, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(productPrice, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(productSubtotal, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }
    
    
    
    // ===================================
    // ============ COMBO BOX ============
    // ===================================
    
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
        var stock = Stock.getInstance();
        var products = stock.getProducts();
        if(products == null){
            Alert.alert(null, "Para registrar una venta debes crear un producto.");
            return null;
        }
        
        var productsCount = products.length;
        String[] model = new String[productsCount +1];
        model[0] = "None";
        for (int i = 0; i < productsCount; i++) {
            var p = products[i];
            model[i+1] = p.getId()+": "+ p.getName() +" ("+p.getStock()+")";
        }
        return new DefaultComboBoxModel<>(model);
    }
    
    /**
     * Event when the product combo box is changed.
     * @param evt Item Event Data.
     */
    private void productComboBoxIsChangedHandler(ItemEvent evt){
        if (evt.getSource() == productComboBox) {
            String selectedItem = (String) productComboBox.getSelectedItem();
            
            if(!"None".equals(selectedItem)){
                // A valid product has been selected
                this.productUnits.setEnabled(true);
                
                String[] productIdAndName = selectedItem.split(":");
                String productId = productIdAndName[0];

                var products = Stock.getInstance().getProducts();

                int elementToFind = Integer.parseInt(productId);
                int index = IntStream.range(0, products.length).
                    filter(i -> elementToFind == products[i].getId()).
                    findFirst().orElse(-1);

                selectedProduct = products[index];
                
                float price = selectedProduct.getPrice();
                this.productPrice.setText(String.valueOf(price));
                
                calculateSubtotal();
                
                productUnits.requestFocus();
                productUnits.selectAll();
            }else{
                // No product has been selected.
                this.productUnits.setEnabled(false);
                this.productPrice.setText("0");
                this.productSubtotal.setText("0");
                this.productUnits.setText("");
            }
        }
    }
    
    public JComboBox<String> getProductComboBox() {
        return productComboBox;
    }
    
    // =======================================
    // ============ END COMBO BOX ============
    // =======================================
    
    
    
    // ===============================
    // ============ PRICE ============
    // ===============================
    
    private void productPriceFactory(){
        productPriceTitle = new JLabel();
        productPrice = new JLabel();
        
        productPriceTitle.setForeground(new java.awt.Color(64, 64, 64));
        productPriceTitle.setText("Price");
        productPrice.setText("0");
    }
    
    // ===================================
    // ============ END PRICE ============
    // ===================================
    
    
    
    // ===============================
    // ============ UNITS ============
    // ===============================

    /**
     * Product units Input.
     */
    private void productUnitsFactory(){
        productUnitsTitle = new JLabel();
        productUnitsTitle.setForeground(new java.awt.Color(64, 64, 64));
        productUnitsTitle.setText("Units");
        
        productUnits = new JTextField();
        productUnits.setEnabled(false);
        
        //Event for when the user is typing in the units input.
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {}

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                calculateSubtotal();
            }
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                calculateSubtotal();
            }
        };
        
        productUnits.getDocument().addDocumentListener(documentListener);
        productUnits.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                if (productUnits.getText().length() >= 7 && maximumTextInUnits == null){
                    ke.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent ke) {}
            @Override
            public void keyReleased(KeyEvent ke) {}
        });
    }
    
    public JTextField getProductUnits() {
        return productUnits;
    }
    
    // ===================================
    // ============ END UNITS ============
    // ===================================
    
    
    
    // ==================================
    // ============ SUBTOTAL ============
    // ==================================
    
    private void productSubtotalFactory(){
        productSubtotalTitle = new JLabel();
        productSubtotalTitle.setText("Subtotal");
        productSubtotal = new JLabel();
        productSubtotal.setBackground(Color.yellow);
        productSubtotal.setText("0");
    }
    
    /**
     * Calculate the product subtotal, set the value to "calculatedSubtotal" and print it
     * in subtotal JLabel.
     */
    protected void calculateSubtotal(){
        int units = 0;
        String unitsStr = productUnits.getText();
        float price = selectedProduct.getPrice();
        
        if("".equals(unitsStr)){
            //when the input is empty
            units = 0;
        }else{
            if(isNumeric(unitsStr)){
                units = Integer.parseInt(unitsStr);
            }else{
                //when the input has non-numeric characters
                Alert.alert(productUnits, "El valor a ingresar en el campo \"unidades\" debe ser un número entero."
                        + "\nVer en el producto \""+ selectedProduct.getName() +"\".");
                productUnits.requestFocus();
                units = 0;
            }
        }
        
        calculatedSubtotal = units * price;
        productSubtotal.setText(String.valueOf(calculatedSubtotal));
        
        generalForm.calculateTotal();
    }
    
    public float getCalculatedSubtotal() {
        return calculatedSubtotal;
    }
    
    // ======================================
    // ============ END SUBTOTAL ============
    // ======================================
    
    
    
    // =======================================
    // ============ REMOVE BUTTON ============
    // =======================================
    
    private void removeButtonFactory(){
        removeButton = new JButton();
        com.gmasoftware.sellersystem.theme.Icons.setButtonIcon(
                "img/remove.png",
                removeButton,
                20,20
        );
    }
    
    protected JButton getRemoveButton() {
        return removeButton;
    }
    
    // ===========================================
    // ============ END REMOVE BUTTON ============
    // ===========================================
    
    
    
    /**
     * Return True if the String is a numeric value.
     * @param value
     * @return Return True if the String is a numeric value.
     */
    public static boolean isNumeric(String value){
	try {
            Float.parseFloat(value);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }
}
