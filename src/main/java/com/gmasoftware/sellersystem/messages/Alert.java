/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.messages;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author GMA Software
 */
public class Alert {
    public static void alert(Component parentComponent, Object message){
        JOptionPane.showMessageDialog(parentComponent, message);
    }
}
