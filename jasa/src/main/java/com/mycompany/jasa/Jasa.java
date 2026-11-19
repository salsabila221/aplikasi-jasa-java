/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jasa;

import com.mycompany.jasa.view.LoginFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class Jasa {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
