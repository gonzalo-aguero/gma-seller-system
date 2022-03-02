/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmasoftware.sellersystem.theme;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author GMA Software
 */
public class Icons {
    
    /**
     * Place the default frame icon in the JFrame passed as a parameter.
     * If the passed parameter is null, it will return the icon.
     * @param frame JFrame.
     * @return Image.
     */
    public static Image setDefaultFrameIcon(JFrame frame){
        String path = "img/icon-png.png";
        Image icon = new ImageIcon(path).getImage();
        if(frame != null){
            frame.setIconImage(icon);
        }
        return icon;
    }
    
    /**
     * @param path Image path.
     * @param button JButton.
     * @param width Width of the image.
     * @param height Height of the image.
     */
    public static void setButtonIcon(String path, javax.swing.JButton button, int width, int height){
        try {
            ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it 
            Image newImg = image.getScaledInstance(width, height, Image.SCALE_DEFAULT); // scale it the smooth way  
            imageIcon = new ImageIcon(newImg);
            button.setIcon(imageIcon);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * @param path Image path.
     * @param label JLabel.
     * @param width Width of the image.
     * @param height Height of the image.
     */
    public static void setLabelIcon(String path, javax.swing.JLabel label, int width, int height){
        try {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(newImg);
            label.setIcon(imageIcon);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
