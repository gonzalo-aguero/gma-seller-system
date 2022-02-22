package Theme;

import java.awt.Color;
import javax.swing.JButton;

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
    
    public static void applyNormalButtonFont(javax.swing.JButton btn){
        btn.setFont(new java.awt.Font("Ubuntu Light", 1, 16));
    }
    
    public static void applyNormalButtonColors(JButton component){
        component.setBackground(Color.decode(COLOR_1));
    }
    
    public static void applyGoodButtonColos(javax.swing.JButton btn){
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
}
