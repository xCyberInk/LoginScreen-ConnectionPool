/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ingsoftwareordenamiento.Ordenador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author andres
 */
public class ViewPrincipalController implements Initializable {

    public String dir;
    @FXML
    private TextArea txtOut;
    @FXML
    private ScrollBar scrollbar;

    public ViewPrincipalController() throws IOException {
        this.dir = cargar();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btn_QuickSort(ActionEvent event) throws IOException {

        //TimerTask tarea = new TimerTask() {
        //@Override
        //public void run() {
        //Variables
        ArrayList numeros = new ArrayList();
        int[] toOrder = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        File archivo = null;
        File fileSalida = null;
        FileReader fr = null;
        BufferedReader br = null;
        Ordenador o = new Ordenador();
        BufferedWriter bw = null;
        PrintWriter pw = null;
        FileWriter fw = null;
        Timer timer = new Timer();
        try {

            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(this.dir);
            if (archivo.exists()) {
                //System.out.println("Se encontró el archivo");
            } else {
                //System.out.println("No se encontró el archivo");
            }

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            try {
                fileSalida = new File("C:\\Users\\andre\\Desktop\\Sort.txt");
                if (!fileSalida.exists()) {
                    try {
                        fileSalida.createNewFile();
                    } catch (IOException e) {

                    }

                } else {
                    //System.out.println("Archivo Ordenado Encontrado, limpiando fichero");
                }
                bw = new BufferedWriter(new FileWriter(fileSalida));
            } catch (Exception e) {
            }

            int aux;
            // Lectura del fichero
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] demo = linea.split(" ");
                for (int i = 0; i < demo.length; i++) {
                    numeros.add(demo[i]);

                }

            }
            long inicio = System.nanoTime();
            //numeros.size()
            for (int i = 0; i < 10000; i++) {
                String x = (String) numeros.get(i);
                int cont = 1;
                while (cont > 0) {
                    for (int j = 0; j < x.length(); j++) {
                        int aux2 = Integer.parseInt(String.valueOf(x.charAt(j)));
                        toOrder[j] = aux2;
                    }
                    cont = -1;
                }

                o.ordenarQuickSort(toOrder);

                String data = Arrays.toString(toOrder);
                fw = new FileWriter(fileSalida.getAbsoluteFile(), true);
                pw = new PrintWriter(fw);
                
                
                pw.write("\n" + data);
                
                pw.close();
                fw.close();
            }
            long fin = System.nanoTime();
            
            double diff = (double) (fin - inicio) * 1.0e-9;
            //System.out.println("El ordenamiento duró " + diff + " segundos");
            JOptionPane.showMessageDialog(null, "El ordenamiento duró " + diff + " segundos");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //}
        //};

    }

    @FXML
    private void btn_Shell(ActionEvent event) {
    }

    @FXML
    private void btn_Seleccion(ActionEvent event) {
    }

    @FXML
    private void btn_BubbleSort(ActionEvent event) {
    }

    @FXML
    private void btn_Insercion(ActionEvent event) {
    }

    @FXML
    private void btn_Fusion(ActionEvent event) {
    }

    public String cargar() throws FileNotFoundException, IOException {

        String dir;
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        dir = JOptionPane.showInputDialog(null, "Paste directory", "Direccion del archivo", 3);
        archivo = new File(dir);
        boolean exists = false;
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "La ruta especificada no contiene ningún archivo");
            while (exists != true) {
                dir = JOptionPane.showInputDialog(null, "Paste directory", "Direccion del archivo", 3);
                if (archivo.exists()){
                    exists = true;
                }else{
                    JOptionPane.showMessageDialog(null, "La ruta especificada no contiene ningún archivo");
                }
                
            }
            
        }
        JOptionPane.showMessageDialog(null, "Dirección del archivo: " + dir);

        return dir;
    }

}
