/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.stock;
import com.gmasoftware.sellersystem.MainWindow;
import com.gmasoftware.sellersystem.database.DB;
import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import com.gmasoftware.sellersystem.theme.Styles;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private boolean tableIsSaved = true;
    
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
        content.add(notes());
        content.add(shortcutsText());
        
        return content;
    }
    
    private JLabel viewTitle(){
        title = new JLabel("PRODUCTOS");
        Styles.applyViewTitle(title);
        return title;
    }
    
    
    
    
    
    // ===========================================
    // ================== TABLE ==================
    // ===========================================
    
    private void tableModelFactory(){
        final String[][] stock = Stock.getInstance().getStockAsArray();
        tableModel = new DefaultTableModel(stock, tableHeader){
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 0;
            }
        };
        
        tableModel.addTableModelListener((var evt) -> {
            if(tableIsSaved){
                setTableIsSaved(false);
            }
        });
    }
    
    private JScrollPane stockTable(){
        stockTable = new JTable(tableModel);
 
        tableContainer = new JScrollPane(stockTable);
        tableContainer.setLocation(0,0);
        
        //Keyboard shortcuts
        stockTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                // Ctrl + S ---> Save the changes.
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
                    saveButtonHandler(true);
                }
                // Ctrl + N ---> Create new product.
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
                }
                // Ctrl + Supr/Delete ---> Delete selected products.
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE){
                    deleteButtonHandler();
                }
            }
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        //Set the size of each column in the table.
        stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(40);//id column
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(200);//name
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(70);//price
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(650);//description
        stockTable.getColumnModel().getColumn(4).setPreferredWidth(55);//stock
        stockTable.getColumnModel().getColumn(5).setPreferredWidth(55);//sales
        
        stockTable.getTableHeader().setReorderingAllowed(false);
        
        return tableContainer;
    }
    
    private void reloadTable(){
        //Update table content.
        final String[][] stock = Stock.getInstance().getStockAsArray();
        tableModel.setDataVector(stock, tableHeader);
        
        //Set focus to last row
        final var rowIndex = stockTable.getRowCount() -1;
        stockTable.changeSelection(rowIndex, 1, false, false);
        
        setTableIsSaved(true);
        
        //Set the size of each column in the table.
        stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(40);//id column
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(200);//name
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(70);//price
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(650);//description
        stockTable.getColumnModel().getColumn(4).setPreferredWidth(55);//stock
        stockTable.getColumnModel().getColumn(5).setPreferredWidth(55);//sales
    }
    
    private void setTableIsSaved(boolean isSaved){
        final var MW = MainWindow.getInstance();
                
        if(isSaved){
            //Once the changes have been saved, you can change the view.
            MW.buttonsAreEnabled(true);
            tableIsSaved = true;
        }else{
            //To prevent the user from switching views and losing unsaved changes.
            MW.buttonsAreEnabled(false);
            tableIsSaved = false;
        }
    }
    
    // ===============================================
    // ================== END TABLE ==================
    // ===============================================
    
    
    
    
    
    // =============================================
    // ================== BUTTONS ==================
    // =============================================
    
    private JPanel optionsMenu(){
        
        // Buttons
        var addButton = new JButton("Crear producto");
        var selectAllButton = new JButton("Seleccionar todo"); 
        var deleteButton = new JButton("Eliminar");
        var saveButton = new JButton("Guardar");
        var importButton = new JButton("Importar");
        
        com.gmasoftware.sellersystem.theme.Styles.applyGoodButtonColors(addButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(addButton);
        
        com.gmasoftware.sellersystem.theme.Styles.applyNeutralButtonColors(selectAllButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(selectAllButton);
        
        com.gmasoftware.sellersystem.theme.Styles.applyBadButtonColors(deleteButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(deleteButton);
        
        com.gmasoftware.sellersystem.theme.Styles.applySafeButtonColors(saveButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(saveButton);
        
        com.gmasoftware.sellersystem.theme.Styles.applyNeutralButtonColors(importButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(importButton);
        
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
            saveButtonHandler(true);
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
        com.gmasoftware.sellersystem.theme.Styles.applyOptionsBar(optionsMenu);
        
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
    
    private void saveButtonHandler(boolean showAlerts){
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
            var stockValue = String.valueOf(stockTable.getValueAt(row, 4));
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
            if(showAlerts){
                Alert.alert(view, "Productos guardados correctamente.");
            }
        }
        reloadTable();
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
        
        String confirmTitle = "Se eliminarán los productos seleccionados.\n"
                + "¡Una vez hecho, no hay vuelta atrás!\n\n"
                + "¿Desea continuar?";
        String confirmMsg = "Confirmar";
        
        var answer = Confirm.deleteConfirm(confirmTitle, confirmMsg);

        final Stock SI = Stock.getInstance();
        if(answer == 1){
            SI.deleteProducts(productIDs);
        }
        
        reloadTable();
    }
    
    /**
     * Open the CSV file chooser.
     */
    private void importButtonHandler(){
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        FileFilter fileFilter = new FileNameExtensionFilter("Archivo de valores separados por comas (.csv)", "csv");
        fc.setFileFilter(fileFilter);
        fc.setFont(new java.awt.Font("Ubuntu Light", 0, 15));
        fc.setDragEnabled(true);
        int selection = fc.showOpenDialog(null);
        
        //if click on accept
        String path;
        if(selection == JFileChooser.APPROVE_OPTION){
            path = fc.getSelectedFile().getPath();
            importFromThisFile(path);
        }
    }
    
    /**
     * Import products from file passed as parameter.
     * @param path Path to the file.
     */
    private void importFromThisFile(String path)    {
        Path pathToFile = Paths.get(path);
        
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
        }
    }
    
    // =================================================
    // ================== END BUTTONS ==================
    // =================================================
    
    
    
    
    
    // ===========================================
    // ================== NOTES ==================
    // ===========================================
    
    /**
     *  It returns a JPanel with text about this view.
     */
    private JPanel notes(){
        JLabel[] labels = new JLabel[6];
        labels[0] = new JLabel("Como importar productos a través de un archivo CSV");
        labels[1] = new JLabel("Para importar productos deberá tener un archivo de valores separados por coma (.csv).");
        labels[2] = new JLabel("Si usted tiene sus productos en un archivo de Microsoft Excel o Google Sheets, ambos programas le darán la opción");
        labels[3] = new JLabel(" de \"Guardar como CSV\" o \"Descargar como CSV\" (puede que diga \"Valores separados por comas\" en lugar de CSV).");
        labels[4] = new JLabel("El formato para los productos deberá ser de la siguiente forma:");
        labels[5] = new JLabel("Nombre,Precio,Descripción,Stock disponible,Número de ventas");
        
        Box box = Box.createVerticalBox();
        
        for (JLabel label : labels) {
            Styles.applyTextStyle(label);
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            box.add(label);
        }
        
        Styles.applyTitle(labels[0]);//Title
        labels[5].setFont(new java.awt.Font(Styles.FONT_2, 1, 12));//label with the format to import
        javax.swing.border.Border border = labels[5].getBorder();
        javax.swing.border.Border margin = new javax.swing.border.EmptyBorder(12,10,10,10);
        labels[5].setBorder(new javax.swing.border.CompoundBorder(border, margin));
        
        box.add(labels[0]);
        box.add(labels[1]);
        box.add(labels[2]);
        box.add(labels[3]);
        box.add(Box.createVerticalStrut(15));
        box.add(labels[4]);
        box.add(labels[5]);
        
        JPanel tc = new JPanel();//text container
        tc.setLocation(0,0);
        tc.setPreferredSize(new java.awt.Dimension(
                MainWindow.getInstance().getContentPanelWidth(),
                200
        ));
        
        tc.add(box);
        
        return tc;
    }
    
    /**
     *  It returns a JPanel with text about the keyboard shortcuts.
     */
    private JPanel shortcutsText(){
        JLabel[] labels = new JLabel[9];
        labels[0] = new JLabel("Atajos del teclado");
        labels[1] = new JLabel("Una vez seleccionada una fila cualquiera de la tabla:");
        labels[2] = new JLabel("TAB para cambiar a la celda de la derecha.");
        labels[3] = new JLabel("SHIFT + TAB para cambiar a la celda de la izquierda.");
        labels[4] = new JLabel("ENTER para cambiar a la celda de abajo.");
        labels[5] = new JLabel("SHIFT + ENTER para cambiar a la celda de arriba.");
        labels[6] = new JLabel("CTRL + S para guardar los cambios.");
        labels[7] = new JLabel("CTRL + N para crear un nuevo producto.");
        labels[8] = new JLabel("CTRL + DELETE o CTRL + SUPR para eliminar los productos seleccionados.");
        
        Box box = Box.createVerticalBox();
        
        for (JLabel label : labels) {
            Styles.applyTextStyle(label);
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            box.add(label);
        }
        
        Styles.applyTitle(labels[0]);//Title
        
        box.add(labels[0]);
        box.add(labels[1]);
        box.add(Box.createVerticalStrut(15));
        box.add(labels[2]);
        box.add(labels[3]);
        box.add(Box.createVerticalStrut(15));
        box.add(labels[4]);
        box.add(labels[5]);
        box.add(Box.createVerticalStrut(15));
        box.add(labels[6]);
        box.add(labels[7]);
        box.add(labels[8]);
        
        JPanel tc = new JPanel();//text container
        tc.setLocation(0,0);
        tc.setPreferredSize(new java.awt.Dimension(
                MainWindow.getInstance().getContentPanelWidth(),
                300
        ));
        
        tc.add(box);
        
        return tc;
    }
    // ===============================================
    // ================== END NOTES ==================
    // ===============================================
}   