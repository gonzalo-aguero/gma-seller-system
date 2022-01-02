/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

/**
 *
 * @author GMA Software
 */
public class View {
    
    private JScrollPane view;
    private JPanel content;
    private JLabel viewTitle;
    private JTable stockTable;
    private Stock stockClass;
    private JScrollPane tableContainer;
    private JPanel optionsMenu;
    
    public View(){
        this.stockClass = new Stock();
    }
    
    public JScrollPane getComponents(){
        view = new JScrollPane(content());
        view.setLocation(0,0);
        return view;
    }
    
    private JPanel content(){
        content = new JPanel();
        content.setLocation(0,0);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        
        content.add(viewTitle());
        content.add(stockTable());
        content.add(optionsMenu());
        
        return content;
    }
    
    private JLabel viewTitle(){
        viewTitle = new JLabel("Productos");
        return viewTitle;
    }
    
    private JScrollPane stockTable(){
        String[] header = {"ID", "Nombre", "Valor", "Descripción", "Imagen", "Stock", "Ventas"};
        
        var model = new DefaultTableModel(stockClass.getStockAsArray(), header){
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 0;
            }
        };
        stockTable = new JTable(model);
 
        tableContainer = new JScrollPane(stockTable);
        tableContainer.setLocation(0,0);
        
        return tableContainer;
    }
    
    private JPanel optionsMenu(){
        var btn1 = new JButton("Añadir");
        var btn2 = new JButton("Seleccionar todo"); 
        var btn3 = new JButton("Eliminar");
        var btn4 = new JButton("Guardar");
        
        btn2.addActionListener((ActionEvent arg0) -> {
            stockTable.selectAll();
        });
        
        btn4.addActionListener((var arg0) -> {
            var stock = stockClass.getStockAsArray();
            var rows = stockTable.getSelectedRows();
            
            for (int i = 0; i < rows.length; i++) {
                var row = i;
                var value1 = stockTable.getValueAt(row, 0);
                var value2 = stockTable.getValueAt(row, 1);
                var value3 = stockTable.getValueAt(row, 2);  
                stock[row][0] = String.valueOf(value1);
                stock[row][1] = String.valueOf(value2);
                stock[row][2] = String.valueOf(value3);
            }
        });

        // Container
        optionsMenu = new JPanel();
        optionsMenu.add(btn1);
        optionsMenu.add(btn2);
        optionsMenu.add(btn3);
        optionsMenu.add(btn4);
        
        return optionsMenu;
    }
    
}
