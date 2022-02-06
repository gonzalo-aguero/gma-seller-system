/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.user;

import com.gmasoftware.sellersystem.sales.*;
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
        title.setAlignmentX(2);
        return title;
    }
    
    
    /**
     * =================================================
     * ================== START TABLE ==================
     * =================================================
     */
    
    private void tableModelFactory(){
        tableModel = new DefaultTableModel(Users.getUsers(), tableHeader){
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
        
        //Save the table data with Ctrl + S.
        usersTable.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
                    addButtonHandler();
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
        tableModel.setDataVector(Users.getUsers(), tableHeader);
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
        var addButton = new JButton("Crear nuevo usuario");
        var selectAllButton = new JButton("Seleccionar todos"); 
        
        //Events of the buttons.
        addButton.addActionListener((ActionEvent arg0) -> {            
            addButtonHandler();
        });
        
        selectAllButton.addActionListener((ActionEvent arg0) -> {
            selectAllButtonHandler();
        });
        
        // Container of the buttons.
        optionsMenu = new JPanel();
        optionsMenu.add(addButton);
        optionsMenu.add(selectAllButton);
        
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
    
    /**
     * =================================================
     * ================== END BUTTONS ==================
     * =================================================
     */
}
