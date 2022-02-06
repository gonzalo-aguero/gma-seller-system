/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import com.gmasoftware.sellersystem.user.User;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This is the Sales SalesView class.
 * @author GMA Software
 */
public class SalesView {
    private final User user;
    private JScrollPane view;
    private JPanel content;
    private JLabel title;
    
    private JTable salesTable;
    private JScrollPane tableContainer;
    private final String[] tableHeader = {"ID", "Total", "Cant. Total", "Fecha", "Productos", "Usuario"};
    private DefaultTableModel tableModel;
    private boolean allSelected = false;

    private final Sales salesClass;
    
    private JPanel optionsMenu;
    
    
    public SalesView(){
        user = User.getInstance();
        this.salesClass = new Sales();
    }
    
    /**
     * @return Container with its components.
     */
    public JScrollPane getView(){
        view = new JScrollPane(getContent());
        view.setLocation(0,0);
        return view;
    }
    
    private JPanel getContent(){
        content = new JPanel();
        content.setLocation(0,0);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        
        content.add(title());
        tableModelFactory();
        content.add(salesTable());
        content.add(optionsMenu());
        
        return content;
    }
    
    private JLabel title(){
        title = new JLabel("Ventas");
        return title;
    }
    
    
    /**
     * =================================================
     * ================== START TABLE ==================
     * =================================================
     */
    private void tableModelFactory(){
        tableModel = new DefaultTableModel(salesClass.getSalesAsArray(), tableHeader){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
    }
    
    private JScrollPane salesTable(){
        salesTable = new JTable(tableModel);
 
        tableContainer = new JScrollPane(salesTable);
        tableContainer.setLocation(0,0);
        
        //Save the table data with Ctrl + S.
        salesTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE){
                    undoButtonHandler();
                }
            }
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        return tableContainer;
    }
    
    protected void reloadTable(){
        salesClass.reloadSales();
        tableModel.setDataVector(salesClass.getSalesAsArray(), tableHeader);
    }
    /**
     * =================================================
     * =================== END TABLE ===================
     * =================================================
     */
    
    
    
    
    
    /**
     * ===================================================
     * ================== START BUTTONS ==================
     * ===================================================
     */
    
    private JPanel optionsMenu(){
        
        // Buttons
        var addButton = new JButton("Registrar nueva venta");
        var selectAllButton = new JButton("Seleccionar todo"); 
        var undoButton = new JButton("Deshacer venta");
        
        //Events of the buttons.
        addButton.addActionListener((ActionEvent arg0) -> {            
            addButtonHandler();
        });
        
        selectAllButton.addActionListener((ActionEvent arg0) -> {
            selectAllButtonHandler();
        });
        
        undoButton.addActionListener((ActionEvent arg0) -> {
            undoButtonHandler();
        });
                
        // Container of the buttons.
        optionsMenu = new JPanel();
        optionsMenu.add(addButton);
        optionsMenu.add(selectAllButton);
        optionsMenu.add(undoButton);
        
        return optionsMenu;
    }
    
    private void addButtonHandler(){
        //Open the form to register a new sale
        new RegisterSaleForm(this).setVisible(true);

        //Update table content.
        reloadTable();

        //Set focus on the new row
        var rowIndex = salesTable.getRowCount() -1;
        salesTable.changeSelection(rowIndex, 1, false, false);
    }
    
    private void selectAllButtonHandler(){
        if(allSelected){
            salesTable.setRowSelectionInterval(0, 0);
            allSelected = false;
        }else{
            salesTable.selectAll();
            allSelected = true;
        }
    }
    
    private void undoButtonHandler(){
        var rows = salesTable.getSelectedRows();
        int rowsCount = rows.length;
        
        if(rowsCount < 1){
            Alert.alert(view, "Debe seleccionar una o más ventas.");
            return;
        }
        
        int[] salesIDs = new int[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            int rowIndex = rows[i];
            
            Object idFromTable = salesTable.getValueAt(rowIndex, 0);//get id from table
            String idStr = String.valueOf(idFromTable);//Parse to String
            
            salesIDs[i] = Integer.parseInt(idStr);//Parse to int.
        }
        
        String title = "Se eliminarán las ventas seleccionadas.\n"
                + "Esto devolverá las unidades al stock de cada producto.\n\n"
                + "¿Desea continuar?";
        String confirmMsg = "Confirmar";
        
        var answer = Confirm.deleteConfirm(title, confirmMsg);

        if(answer == 1){
            salesClass.undoSales(salesIDs);
        }
        
        reloadTable();
    }
    
    /**
     * =================================================
     * ================== END BUTTONS ==================
     * =================================================
     */
}
