/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import com.gmasoftware.sellersystem.database.DB;
import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import com.gmasoftware.sellersystem.sales.ImportProducts;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class StockView {
    
    private JScrollPane view;
    private JPanel content;
    private JLabel title;
    private JTable stockTable;
    private final String[] tableHeader = {"ID", "Nombre", "Valor", "Descripción", "Stock", "Ventas"};
    private DefaultTableModel tableModel;
    private boolean allSelected = false;
    private JScrollPane tableContainer;
    private JPanel optionsMenu;
    
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
        title = new JLabel("PRODUCTOS");
        title.setFont(new java.awt.Font("Ubuntu Light", 1, 20));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        return title;
    }
    
    private void tableModelFactory(){
        final String[][] stock = Stock.getInstance().getStockAsArray();
        tableModel = new DefaultTableModel(stock, tableHeader){
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
    
    private void reloadTable(){
        //Update table content.
        final String[][] stock = Stock.getInstance().getStockAsArray();
        tableModel.setDataVector(stock, tableHeader);

        //Set focus to last row
        final var rowIndex = stockTable.getRowCount() -1;
        stockTable.changeSelection(rowIndex, 1, false, false);
    }
    
    private JPanel optionsMenu(){
        
        // Buttons
        var addButton = new JButton("Crear producto");
        var selectAllButton = new JButton("Seleccionar todo"); 
        var deleteButton = new JButton("Eliminar");
        var saveButton = new JButton("Guardar");
        var importButton = new JButton("Importar");
        
        Theme.Styles.applyGoodButtonColos(addButton);
        Theme.Styles.applyNormalButtonFont(addButton);
        
        Theme.Styles.applyNeutralButtonColors(selectAllButton);
        Theme.Styles.applyNormalButtonFont(selectAllButton);
        
        Theme.Styles.applyBadButtonColors(deleteButton);
        Theme.Styles.applyNormalButtonFont(deleteButton);
        
        Theme.Styles.applySafeButtonColors(saveButton);
        Theme.Styles.applyNormalButtonFont(saveButton);
        
        Theme.Styles.applyNeutralButtonColors(importButton);
        Theme.Styles.applyNormalButtonFont(importButton);
        
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
        
        importButton.addActionListener((ActionEvent arg0) -> {
            importButtonHandler();
        });

        // Container of the buttons.
        optionsMenu = new JPanel();
        optionsMenu.add(addButton);
        optionsMenu.add(selectAllButton);
        optionsMenu.add(deleteButton);
        optionsMenu.add(saveButton);
        optionsMenu.add(importButton);
        Theme.Styles.applyOptionsBar(optionsMenu);
        
        return optionsMenu;
    }
    
    private void addButtonHandler(){
        //Create a new product in the DB.
        final var db = DB.getInstance();
        final var newID = db.calculateID("products", db.HIGHEST_VALUE);
        String[] newProductModel = {
            newID,
            "Nuevo producto",// product name
            "0", //product price
            "Sin descripción",//product description
            "0", //product stock
            "0" //product sales count
        };
        Stock.getInstance().createNewProduct(newProductModel);

        reloadTable();
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
            
            tableData[row][0] = String.valueOf(stockTable.getValueAt(row, 0));//id
            tableData[row][1] = String.valueOf(stockTable.getValueAt(row, 1));//name
            tableData[row][2] = priceValue;//price
            tableData[row][3] = String.valueOf(stockTable.getValueAt(row, 3));//description 
            tableData[row][4] = stockValue;//stock
            tableData[row][5] = String.valueOf(stockTable.getValueAt(row, 5));//salesCount
        }

        //Save data, reload product stock, and repaint.
        final Stock SI = Stock.getInstance();
        if(SI.saveProducts(tableData)){
            Alert.alert(view, "Productos guardados correctamente.");
        }
        SI.reloadStock();
        tableModel.setDataVector(SI.getStockAsArray(), tableHeader);
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

        final Stock SI = Stock.getInstance();
        if(answer == 1){
            SI.deleteProducts(productIDs);
        }
        
        SI.reloadStock();
        tableModel.setDataVector(SI.getStockAsArray(), tableHeader);
    }
    
    /**
     * Import products from CSV file.
     */
    private void importButtonHandler(){
        var fileChooser = new ImportProducts();
        fileChooser.setLocationRelativeTo(view);
        fileChooser.setVisible(true);
//        fileChooser.getSelectedFile();
        /*Path pathToFile = Paths.get();

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader reader = Files.newBufferedReader(pathToFile,
                StandardCharsets.UTF_8)) {

            // read the first line from the text file
            int lineCounter = 0;
            String line = reader.readLine();
            
            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                var db = DB.getInstance();
                var newID = db.calculateID("products", db.HIGHEST_VALUE);
                String[] newProductModel = {
                    newID,//id
                    String.valueOf(attributes[0]),// product name
                    String.valueOf(attributes[1]), //product price
                    String.valueOf(attributes[2]),//product description
                    String.valueOf(attributes[3]), //product stock
                    String.valueOf(attributes[4]) //product sales count
                };
                Stock.getInstance().createNewProduct(newProductModel);

                lineCounter++;

                // read next line before looping
                // if end of file reached, line would be null
                line = reader.readLine();
            }
            
            Alert.alert(null, lineCounter + " productos importados con éxito.");
            reloadTable();
            
        } catch (IOException ioe) {
            Alert.alert(null, "Ha ocurrido un error al importar los productos."
                    + "\nVerifica que el formato es correcto.");
            ioe.printStackTrace();
        }*/
    }
}   