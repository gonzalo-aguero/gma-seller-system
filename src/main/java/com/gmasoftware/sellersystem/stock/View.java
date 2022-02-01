/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private final String[] tableHeader = {"ID", "Nombre", "Valor", "Descripción", "Imagen", "Stock", "Ventas"};
    private DefaultTableModel tableModel;
    private boolean allSelected = false;
    private final Stock stockInstance;
    private JScrollPane tableContainer;
    private JPanel optionsMenu;
    
    public View(){
        this.stockInstance = Stock.getInstance();
    }
    
    public JScrollPane getView(){
        view = new JScrollPane(getContent());
        view.setLocation(0,0);
        return view;
    }
    
    private JPanel getContent(){
        content = new JPanel();
        content.setLocation(0,0);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        
        content.add(viewTitle());
        tableModelFactory();
        content.add(stockTable());
        content.add(optionsMenu());
        
        return content;
    }
    
    private JLabel viewTitle(){
        viewTitle = new JLabel("Productos");
        return viewTitle;
    }
    
    private void tableModelFactory(){
        tableModel = new DefaultTableModel(stockInstance.getStockAsArray(), tableHeader){
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 0;
            }
        };
    }
    
    private JScrollPane stockTable(){
        stockTable = new JTable(tableModel);
 
        tableContainer = new JScrollPane(stockTable);
        tableContainer.setLocation(0,0);
        
        //Save the table data with Ctrl + S.
        stockTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
                    saveButtonHandler();
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE){
                    deleteButtonHandler();
                }
            }
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        return tableContainer;
    }
    
    private JPanel optionsMenu(){
        
        // Buttons
        var addButton = new JButton("Añadir");
        var selectAllButton = new JButton("Seleccionar todo"); 
        var deleteButton = new JButton("Eliminar");
        var saveButton = new JButton("Guardar");
        
        //Events of the buttons.
        addButton.addActionListener((ActionEvent arg0) -> {            
            addButtonHandler();
        });
        
        selectAllButton.addActionListener((ActionEvent arg0) -> {
            selectAllButtonHandler();
        });
        
        deleteButton.addActionListener((ActionEvent arg0) -> {
            deleteButtonHandler();
        });
                
        saveButton.addActionListener((ActionEvent arg0) -> {
            saveButtonHandler();
        });

        // Container of the buttons.
        optionsMenu = new JPanel();
        optionsMenu.add(addButton);
        optionsMenu.add(selectAllButton);
        optionsMenu.add(deleteButton);
        optionsMenu.add(saveButton);
        
        return optionsMenu;
    }
    
    private void addButtonHandler(){
        //Create a new product in the DB.
        String[] newProductModel = {"Nuevo producto", "0", "Sin descripción", null, "0", "0"};
        stockInstance.createNewProduct(newProductModel);

        //Update table content.
        stockInstance.reloadStock();
        tableModel.setDataVector(stockInstance.getStockAsArray(), tableHeader);

        //Set focus on the new row
        var rowIndex = stockTable.getRowCount() -1;
        stockTable.changeSelection(rowIndex, 1, false, false);
    }
    private void selectAllButtonHandler(){
        if(allSelected){
            stockTable.setRowSelectionInterval(0, 0);
            allSelected = false;
        }else{
            stockTable.selectAll();
            allSelected = true;
        }
    }
    private void saveButtonHandler(){
        var numOfRows = stockTable.getRowCount();
        String[][] tableData = new String[numOfRows][7];

        //Get the data from the table and save it to an array of strings.
        for (int i = 0; i < numOfRows; i++) {
            var row = i;
            
            //VALIDATE PRICE
            var priceValue = String.valueOf(stockTable.getValueAt(row, 2));
            float priceValueFloat = (float) 0.0;
            
            try{
                priceValueFloat = Float.parseFloat(priceValue);
            }catch(NumberFormatException ex){
                stockTable.changeSelection(row, 2, false, false);
                Alert.alert(view, "El precio debe ser un valor numérico (Ver fila N°"+ (row+1) +").\n\n"
                        + "Asegúrese de cumplir con el formato \"entero.decimal\", por ejemplo: 3399.99\n\n"+ ex);
                return;
            }finally{
                priceValue = String.valueOf(priceValueFloat);
            }
            
            //VALIDATE STOCK
            var stockValue = String.valueOf(stockTable.getValueAt(row, 5));
            int stockValueInt = (int) 0;
            
            try{
                stockValueInt = Integer.parseInt(stockValue);
            }catch(NumberFormatException ex){
                stockTable.changeSelection(row, 5, false, false);
                Alert.alert(view, "El stock del producto debe ser un número entero (Ver fila N°"+ (row+1) +").\n\n"
                        + "Asegúrese de cumplir con el formato \"entero\", por ejemplo: 3286\n\n"+ ex);
                return;
            }finally{
                stockValue = String.valueOf(stockValueInt);
            }
            

            //VALIDATE IMAGE
            Object image = stockTable.getValueAt(row, 4);
            String imageStr = 
                    (String.valueOf(image).equals("")) 
                    ? null 
                    : image.toString();
            
            tableData[row][0] = String.valueOf(stockTable.getValueAt(row, 0));//id
            tableData[row][1] = String.valueOf(stockTable.getValueAt(row, 1));//name
            tableData[row][2] = priceValue;//price
            tableData[row][3] = String.valueOf(stockTable.getValueAt(row, 3));//description 
            tableData[row][4] = imageStr;//image
            tableData[row][5] = stockValue;//stock
            tableData[row][6] = String.valueOf(stockTable.getValueAt(row, 6));//salesCount
        }

        //Save data, reload product stock, and repaint.
        if(stockInstance.saveProducts(tableData)){
            Alert.alert(view, "Productos guardados correctamente.");
        }
        stockInstance.reloadStock();
        tableModel.setDataVector(stockInstance.getStockAsArray(), tableHeader);
    }
    
    private void deleteButtonHandler(){
        var rows = stockTable.getSelectedRows();
        int rowsCount = rows.length;
        
        if(rowsCount < 1){
            Alert.alert(view, "Debe seleccionar uno o más productos.");
            return;
        }
        
        int[] productIDs = new int[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            int rowIndex = rows[i];
            
            Object idFromTable = stockTable.getValueAt(rowIndex, 0);//get id from table
            String idStr = String.valueOf(idFromTable);//Parse to String
            
            productIDs[i] = Integer.parseInt(idStr);//Parse to int.
        }
        
        String title = "Se eliminarán los productos seleccionados.\n"
                + "¡Una vez hecho, no hay vuelta atrás!\n\n"
                + "¿Desea continuar?";
        String confirmMsg = "Confirmar";
        
        var answer = Confirm.deleteConfirm(title, confirmMsg);

        if(answer == 1){
            stockInstance.deleteProducts(productIDs);
        }
        
        stockInstance.reloadStock();
        tableModel.setDataVector(stockInstance.getStockAsArray(), tableHeader);
    }
}   