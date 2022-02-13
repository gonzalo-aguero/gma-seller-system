/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gmasoftware.sellersystem.user;

import com.gmasoftware.sellersystem.messages.Alert;

/**
 *
 * @author GMA Software
 */
public class CreateUserForm extends javax.swing.JFrame {
    private final UserView userView;
    
    /**
     * Creates new form CreateUserForm
     * @param userView
     */
    public CreateUserForm(UserView userView) {
        this.userView = userView;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameInput = new javax.swing.JTextField();
        usernameTitle = new javax.swing.JLabel();
        passwordTitle = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        passwordTitle1 = new javax.swing.JLabel();
        passwordInput1 = new javax.swing.JPasswordField();
        permissionLevelTitle = new javax.swing.JLabel();
        permissionLevelInput = new javax.swing.JComboBox<>();
        createUserButton = new javax.swing.JButton();

        setTitle("Create User");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CREAR USUARIO");

        usernameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameInputActionPerformed(evt);
            }
        });

        usernameTitle.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        usernameTitle.setText("Nombre de usuario");

        passwordTitle.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        passwordTitle.setText("Contraseña");

        passwordTitle1.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        passwordTitle1.setText("Repetir contraseña");

        permissionLevelTitle.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        permissionLevelTitle.setText("Nivel de permisos");

        permissionLevelInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Gestor de Stock", "Gestor de Ventas" }));

        createUserButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        createUserButton.setText("CREAR USUARIO");
        createUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameInput)
                    .addComponent(usernameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordInput)
                    .addComponent(passwordTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordInput1)
                    .addComponent(permissionLevelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(permissionLevelInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(usernameTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(permissionLevelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(permissionLevelInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameInputActionPerformed

    private void createUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserButtonActionPerformed
        createUserButtonHandler();
    }//GEN-LAST:event_createUserButtonActionPerformed

    private void createUserButtonHandler(){
        String username = usernameInput.getText();
        int permissionLevel = permissionLevelInput.getSelectedIndex() + 1;
        char[] password = passwordInput.getPassword();
        char[] repeatedPassword = passwordInput1.getPassword();
        
        String passwordStr = "";
        String repeatedPasswordStr = "";
        for (char c : password) {
            passwordStr += c;
        }
        for (char c : repeatedPassword) {
            repeatedPasswordStr += c;
        }
        
        if(!repeatedPasswordStr.equals(passwordStr)){
            Alert.alert(rootPane, "Las contraseñas ingresadas no coinciden.");
            return;
        }
        
        Users.createUser(username, passwordStr, String.valueOf(permissionLevel));
        
        // I do it for a recommendation in getPassword() documentation.
        passwordStr = null;
        repeatedPasswordStr = null;
        for (int i = 0; i < password.length; i++) {
            password[i] = 0;
            repeatedPassword[i] = 0;
        }
        
        this.setVisible(false);
        userView.reloadTable();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUserForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createUserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JPasswordField passwordInput1;
    private javax.swing.JLabel passwordTitle;
    private javax.swing.JLabel passwordTitle1;
    private javax.swing.JComboBox<String> permissionLevelInput;
    private javax.swing.JLabel permissionLevelTitle;
    private javax.swing.JTextField usernameInput;
    private javax.swing.JLabel usernameTitle;
    // End of variables declaration//GEN-END:variables
}
