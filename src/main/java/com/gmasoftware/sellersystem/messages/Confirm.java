/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gmasoftware.sellersystem.messages;

import javax.swing.JOptionPane;

/**
 *
 * @author gonzalo90fa
 */
public class Confirm {
    public static int deleteConfirm(String title, String confirmMsg){
        return JOptionPane.showOptionDialog(
                null, 
                title,
                confirmMsg,
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.WARNING_MESSAGE,
                null,
                new Object[] { "Cancelar", "Confirmar"},
                "Cancelar"
        );
    }
}
