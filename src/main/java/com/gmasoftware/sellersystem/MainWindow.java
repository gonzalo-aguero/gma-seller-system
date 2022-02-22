/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gmasoftware.sellersystem;

import com.gmasoftware.sellersystem.stock.Stock;
import com.gmasoftware.sellersystem.user.User;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
/**
 *
 * @author GMA Software
 */
public class MainWindow extends javax.swing.JFrame {
    
    /** Single global instance. **/
    private static MainWindow mainWindowInstance;
    public static synchronized MainWindow getInstance(){
        if(mainWindowInstance == null){
            mainWindowInstance =  new MainWindow();
        }
        return mainWindowInstance;
    }
    
    /**
     * Creates new form MainWindow
     */
    private MainWindow() {
        initComponents();

        String path = "./img/favicon.png";
        Image icon = new ImageIcon(path).getImage();
        this.setIconImage(icon);
        
        ImageIcon imageIcon = new ImageIcon("./img/remove.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newImg);
        salesButon.setIcon(imageIcon);

        this.setExtendedState(6);
        contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
        
        title.setText("Hola, " + User.getInstance().getUsername() +"!");
        
        var pl = User.getInstance().getPermissionLevel();
        if(pl > 1){
            switch (pl) {
                case 2:
                    usersButon.setEnabled(false);
                    break;
                case 3:
                    stockButon.setEnabled(false);
                    usersButon.setEnabled(false);
                    break;
                default:
                    stockButon.setEnabled(false);
                    salesButon.setEnabled(false);
                    usersButon.setEnabled(false);
            }
        }
        
        Theme.Styles.applyNormalButtonColors(salesButon);
        Theme.Styles.applyNormalButtonColors(stockButon);
        Theme.Styles.applyNormalButtonColors(usersButon);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JPanel();
        salesButon = new javax.swing.JButton();
        stockButon = new javax.swing.JButton();
        usersButon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GMA Seller System");
        setBackground(new java.awt.Color(23, 23, 23));
        setResizable(false);

        title.setFont(new java.awt.Font("Ubuntu Light", 0, 32)); // NOI18N
        title.setText("BIENVENIDO");

        contentPanel.setBorder(null);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 691, Short.MAX_VALUE)
        );

        salesButon.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        salesButon.setText("VENTAS");
        salesButon.setMargin(new java.awt.Insets(20, 0, 20, 0));
        salesButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesButonActionPerformed(evt);
            }
        });

        stockButon.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        stockButon.setText("PRODUCTOS");
        stockButon.setMargin(new java.awt.Insets(20, 0, 20, 0));
        stockButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockButonActionPerformed(evt);
            }
        });

        usersButon.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        usersButon.setText("USUARIOS");
        usersButon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        usersButon.setMargin(new java.awt.Insets(20, 0, 20, 0));
        usersButon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        usersButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersButonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesButon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(stockButon, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(usersButon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salesButon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockButon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usersButon)
                .addContainerGap(449, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(mainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void stockButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockButonActionPerformed
        var stockView = new com.gmasoftware.sellersystem.stock.StockView();
        var view = stockView.getView();
        this.setNewView(view);
    }//GEN-LAST:event_stockButonActionPerformed

    private void usersButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersButonActionPerformed
        var pl = User.getInstance().getPermissionLevel();
        if(pl == 0 || pl == 1){
            var userView = new com.gmasoftware.sellersystem.user.UserView();
            var view = userView.getView();
            this.setNewView(view);
        }
    }//GEN-LAST:event_usersButonActionPerformed

    private void salesButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesButonActionPerformed
        var salesView = new com.gmasoftware.sellersystem.sales.SalesView();
        var view = salesView.getView();
        this.setNewView(view);
    }//GEN-LAST:event_salesButonActionPerformed
    
    /**
     * Put a new view in the content panel.
     * @param view 
     */
    private void setNewView(JScrollPane view){
        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(view);
        contentPanel.setVisible(true);
    }

    public JButton getSalesButon() {
        return salesButon;
    }

    public JButton getStockButon() {
        return stockButon;
    }

    public JButton getUsersButon() {
        return usersButon;
    }
    
    public void buttonsAreEnabled(boolean isEnabled){
        salesButon.setEnabled(isEnabled);
        stockButon.setEnabled(isEnabled);
        usersButon.setEnabled(isEnabled);
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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton salesButon;
    private javax.swing.JButton stockButon;
    private javax.swing.JLabel title;
    private javax.swing.JButton usersButon;
    // End of variables declaration//GEN-END:variables
}
