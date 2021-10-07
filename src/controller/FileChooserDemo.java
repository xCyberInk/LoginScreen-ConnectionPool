/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Component;
import javax.swing.JFileChooser;

/**
 *
 * @author andres
 */
class FileChooserDemo {
    final JFileChooser fc = new JFileChooser();
    int returnVal;

    FileChooserDemo() {
        Component aComponent = null;
        this.returnVal = fc.showOpenDialog(aComponent);
    }
}
