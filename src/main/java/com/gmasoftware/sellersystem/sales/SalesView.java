/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.sales;

import com.gmasoftware.sellersystem.MainWindow;
import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import com.gmasoftware.sellersystem.theme.Styles;
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
     * @return Container with the view components.
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
        content.add(shortcutsText());
        
        return content;
    }
    
    private JLabel title(){
        title = new JLabel("VENTAS");
        Styles.applyViewTitle(title);
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
        
        //Keyboard shortcuts
        salesTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                //Ctrl + N ---> New Sale
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
                }
                //Ctrl + supr/delete ---> Undo selected sales
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE){
                    undoButtonHandler();
                }
            }
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        //Set the size of each column in the table.
        salesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        salesTable.getColumnModel().getColumn(0).setPreferredWidth(40);//id column
        salesTable.getColumnModel().getColumn(1).setPreferredWidth(80);//total column
        salesTable.getColumnModel().getColumn(2).setPreferredWidth(80);//total products count
        salesTable.getColumnModel().getColumn(3).setPreferredWidth(150);//date
        salesTable.getColumnModel().getColumn(4).setMinWidth(550);//product list
        salesTable.getColumnModel().getColumn(5).setPreferredWidth(80);//user
        
        salesTable.getTableHeader().setReorderingAllowed(false);
        
        return tableContainer;
    }
    
    protected void reloadTable(){
        salesClass.reloadSales();
        tableModel.setDataVector(salesClass.getSalesAsArray(), tableHeader);
        
        //Set the size of each column in the table.
        salesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        salesTable.getColumnModel().getColumn(0).setPreferredWidth(40);//id column
        salesTable.getColumnModel().getColumn(1).setPreferredWidth(80);//total column
        salesTable.getColumnModel().getColumn(2).setPreferredWidth(80);//total products count
        salesTable.getColumnModel().getColumn(3).setPreferredWidth(150);//date
        salesTable.getColumnModel().getColumn(4).setMinWidth(550);//product list
        salesTable.getColumnModel().getColumn(5).setPreferredWidth(80);//user
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
        var addButton = new JButton("Crear venta");
        com.gmasoftware.sellersystem.theme.Styles.applyGoodButtonColors(addButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(addButton);
        
        var selectAllButton = new JButton("Seleccionar todo"); 
        com.gmasoftware.sellersystem.theme.Styles.applyNeutralButtonColors(selectAllButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(selectAllButton);
        
        var undoButton = new JButton("Deshacer venta");
        com.gmasoftware.sellersystem.theme.Styles.applyBadButtonColors(undoButton);
        com.gmasoftware.sellersystem.theme.Styles.applyNormalButtonFont(undoButton);
        
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
        com.gmasoftware.sellersystem.theme.Styles.applyOptionsBar(optionsMenu);
        
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
    
    
    
    
    
    // ===========================================
    // ================== NOTES ==================
    // ===========================================
    
    /**
     *  It returns a JPanel with text about the keyboard shortcuts.
     */
    private JPanel shortcutsText(){
        JLabel[] labels = new JLabel[8];
        labels[0] = new JLabel("Atajos del teclado");
        labels[1] = new JLabel("Una vez seleccionada una fila cualquiera de la tabla:");
        labels[2] = new JLabel("TAB para cambiar a la celda de la derecha.");
        labels[3] = new JLabel("SHIFT + TAB para cambiar a la celda de la izquierda.");
        labels[4] = new JLabel("ENTER para cambiar a la celda de abajo.");
        labels[5] = new JLabel("SHIFT + ENTER para cambiar a la celda de arriba.");
        labels[6] = new JLabel("CTRL + N para registrar una nueva venta.");
        labels[7] = new JLabel("CTRL + DELETE o CTRL + SUPR para deshacer las ventas seleccionadas.");
        
        javax.swing.Box box = javax.swing.Box.createVerticalBox();
        
        for (JLabel label : labels) {
            Styles.applyTextStyle(label);
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            box.add(label);
        }
        
        Styles.applyTitle(labels[0]);//Title
        
        box.add(labels[0]);
        box.add(labels[1]);
        box.add(javax.swing.Box.createVerticalStrut(15));
        box.add(labels[2]);
        box.add(labels[3]);
        box.add(javax.swing.Box.createVerticalStrut(15));
        box.add(labels[4]);
        box.add(labels[5]);
        box.add(javax.swing.Box.createVerticalStrut(15));
        box.add(labels[6]);
        box.add(labels[7]);
        
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
