package com.gmasoftware.sellersystem.theme;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author GMA Software
 */
public class Styles {
    public static final String COLOR_1 = "#EDEDED";
    public static final String FONT_1 = "Ubuntu";
    public static final String FONT_2 = "Ubuntu Light";
    
    public static void applyNormalButtonFont(javax.swing.JButton btn){
        btn.setFont(new java.awt.Font("Ubuntu Light", 1, 16));
    }
    
    public static void applyNormalButtonColors(JButton component){
        component.setBackground(Color.decode(COLOR_1));
    }
    
    public static void applyGoodButtonColors(javax.swing.JButton btn){
        btn.setBackground(Color.decode("#29B500"));
        btn.setForeground(Color.WHITE);
    }
    
    public static void applyBadButtonColors(javax.swing.JButton btn){
        btn.setBackground(Color.decode("#FF0000"));
        btn.setForeground(Color.WHITE);
    }
    
    public static void applySafeButtonColors(javax.swing.JButton btn){
        btn.setBackground(Color.decode("#008BAB"));
        btn.setForeground(Color.WHITE);
    }
    
    public static void applyNeutralButtonColors(javax.swing.JButton btn){
        btn.setBackground(Color.decode("#6D6D6D"));
        btn.setForeground(Color.WHITE);
    }
    
    public static void applyOptionsBar(javax.swing.JPanel ob){
        ob.setBackground(Color.decode(COLOR_1));
    }
    
    public static void applyButtonStyle(javax.swing.JButton btn){
        btn.setPreferredSize(new java.awt.Dimension(100,25));
    }
    
    public static void applyTextStyle(javax.swing.JLabel label){
        label.setFont(new java.awt.Font(FONT_2, 0, 12));
    }
    
    /**
     * Apply the default title style.
     * @param label 
     */
    public static void applyTitle(javax.swing.JLabel label){
        label.setFont(new java.awt.Font(FONT_1, 1, 16));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        javax.swing.border.Border border = label.getBorder();
        javax.swing.border.Border margin = new javax.swing.border.EmptyBorder(20,10,13,10);
        label.setBorder(new javax.swing.border.CompoundBorder(border, margin));
    }
    
    /**
     * Apply the defualt view title style.
     * @param label 
     */
    public static void applyViewTitle(javax.swing.JLabel label){
        label.setFont(new java.awt.Font("Ubuntu Light", 0, 23));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    }
}
