/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.user;

import com.gmasoftware.sellersystem.messages.Alert;
import com.gmasoftware.sellersystem.messages.Confirm;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This is the User View class.
 * @author GMA Software
 */
public class UserView {
    private JScrollPane view;
    private JPanel content;
    private JLabel title;
    
    private JTable usersTable;
    private JScrollPane tableContainer;
    private final String[] tableHeader = {"ID", "Usuario", "Nivel de permisos"};
    private DefaultTableModel tableModel;
    private boolean allSelected = false;
    
    private JPanel optionsMenu;
    
    
    public UserView(){
        
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
        
        content.add(titleFactory());
        tableModelFactory();
        content.add(tableFactory());
        content.add(optionsMenuFactory());
        
        return content;
    }
    
    private JLabel titleFactory(){
        title = new JLabel("USUARIOS");
        title.setFont(new java.awt.Font("Ubuntu Light", 1, 20));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        return title;
    }
    
    
    /**
     * =================================================
     * ================== START TABLE ==================
     * =================================================
     */
    
    private void tableModelFactory(){
        tableModel = new DefaultTableModel(Users.getUsersForTable(), tableHeader){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
    }
    
    private JScrollPane tableFactory(){
        usersTable = new JTable(tableModel);
 
        tableContainer = new JScrollPane(usersTable);
        tableContainer.setLocation(0,0);
        
        usersTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                // Ctrl + N: Create a new user.
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
                }
                // Ctrl + Supr/Delete: Delete the selected users.
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE){
                    deleteButtonHandler();
                }
            }
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        // For select and edit an user.
        var userView = this;
        usersTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evnt){
                if (evnt.getClickCount() == 2){
                    String userID = usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString();
                    
                    // Open the form to see and edit the user.
                    var form = new EditUserForm(userView, Integer.parseInt(userID));
                    form.setVisible(true);
                    form.setLocationRelativeTo(null);
                }
            }
        });
        
        return tableContainer;
    }
    
    protected void reloadTable(){
        tableModel.setDataVector(Users.getUsersForTable(), tableHeader);
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
    
    private JPanel optionsMenuFactory(){
        
        // Buttons
        var addButton = new JButton("Crear usuario");
        var selectAllButton = new JButton("Seleccionar todos"); 
        var deleteButton = new JButton("Eliminar"); 
        
        Theme.Styles.applyGoodButtonColos(addButton);
        Theme.Styles.applyNormalButtonFont(addButton);
        
        Theme.Styles.applyNeutralButtonColors(selectAllButton);
        Theme.Styles.applyNormalButtonFont(selectAllButton);
        
        Theme.Styles.applyBadButtonColors(deleteButton);
        Theme.Styles.applyNormalButtonFont(deleteButton);
        
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
        
        // Container of the buttons.
        optionsMenu = new JPanel();
        optionsMenu.add(addButton);
        optionsMenu.add(selectAllButton);
        optionsMenu.add(deleteButton);
        Theme.Styles.applyOptionsBar(optionsMenu);
        
        return optionsMenu;
    }
    
    private void addButtonHandler(){
        // Open the form to create a new user.
        var form = new CreateUserForm(this);
        form.setVisible(true);
        form.setLocationRelativeTo(null);

        //Set focus on the new row
        var rowIndex = usersTable.getRowCount() -1;
        usersTable.changeSelection(rowIndex, 1, false, false);
    }
    
    private void selectAllButtonHandler(){
        if(allSelected){
            usersTable.setRowSelectionInterval(0, 0);
            allSelected = false;
        }else{
            usersTable.selectAll();
            allSelected = true;
        }
    }
    
    private void deleteButtonHandler(){
        var rows = usersTable.getSelectedRows();
        int rowsCount = rows.length;
        
        if(rowsCount < 1){
            Alert.alert(view, "Debe seleccionar uno o más usuarios.");
            return;
        }
        
        int[] userIDs = new int[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            int rowIndex = rows[i];
            
            Object idFromTable = usersTable.getValueAt(rowIndex, 0);//get id from table
            String idStr = String.valueOf(idFromTable);//Parse to String
            
            userIDs[i] = Integer.parseInt(idStr);//Parse to int.
        }
        
        String confirmTitle = "Se eliminarán los usuarios seleccionados.\n"
                + "¡Una vez hecho, no hay vuelta atrás!\n\n"
                + "¿Desea continuar?";
        String confirmMsg = "Confirmar";
        
        var answer = Confirm.deleteConfirm(confirmTitle, confirmMsg);

        if(answer == 1){
            Users.deleteUsers(userIDs);
        }
        
        reloadTable();
    }
    /**
     * =================================================
     * ================== END BUTTONS ==================
     * =================================================
     */
}
