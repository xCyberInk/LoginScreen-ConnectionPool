/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ingsoftwareordenamiento.Ordenador;
import java.awt.Component;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author andres
 */
public class ViewPrincipalController implements Initializable {

    final FileChooser fc;
    public String dir;
    @FXML
    private ProgressBar progressBar;
    Task copyWorker;
    @FXML
    private Label progresslabel;
    Component aComponent;

    @FXML
    private ListView listview;
    @FXML
    private Label tiempo;
    @FXML
    private ListView outputField;

    public ViewPrincipalController() throws IOException {

        //this.dir = cargar();
        this.fc = new FileChooser();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(2000);
                    updateMessage("Tarea Compleatada : " + ((i * 10) + 10) + "%");
                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }

    public void progressbar() {
        progressBar.setProgress(0.0);
        copyWorker = createWorker();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(copyWorker.progressProperty());
        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                progresslabel.setText(newValue);
            }
        });
        new Thread(copyWorker).start();
    }

    public void ordenar() throws FileNotFoundException, IOException {
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
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(dir);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            try {
                fileSalida = new File(System.getProperty("user.home") + "/desktop/Sort.txt");

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

            // Lectura del fichero
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] demo = linea.split(" ");
                for (int i = 0; i < demo.length; i++) {
                    
                    numeros.add(demo[i]);
                    
                    listview.getItems().add(demo[i]);
                    
                            
                }

            }
            long inicio = System.nanoTime();
            //numeros.size()
            for (int i = 0; i < 10000; i++) {
                String x = (String) numeros.get(i);
                
                    for (int j = 0; j < x.length(); j++) {
                        int aux2 = Integer.parseInt(String.valueOf(x.charAt(j)));
                        toOrder[j] = aux2;
                    }
                    
                o.ordenarQuickSort(toOrder);

                String data = Arrays.toString(toOrder);
                fw = new FileWriter(fileSalida.getAbsoluteFile(), true);
                pw = new PrintWriter(fw);
                
                outputField.getItems().add(data);
               
                pw.write("\n" + data);

                pw.close();
                fw.close();
            }
            long fin = System.nanoTime();

            double diff = (double) (fin - inicio) * 1.0e-9;
            //System.out.println("El ordenamiento duró " + diff + " segundos");
            //JOptionPane.showMessageDialog(null, "El ordenamiento duró " + diff + " segundos");
            tiempo.setText("Tiempo: " + diff + " segundos");
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

    }

    @FXML
    private void btn_Shell(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

    public String cargar() throws FileNotFoundException, IOException {

        FileChooser fc = new FileChooser();
        //JFileChooser fc =  new JFileChooser();
        fc.setInitialDirectory(new File("C:\\"));
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
        //fc.setFileFilter(filtro);
        String dire = "";
        File seleccion = null;
        seleccion = fc.showOpenDialog(null);
        fc.setTitle("Archivo a ordenar");
        if (seleccion != null) {

            dire = String.valueOf((String) seleccion.getAbsolutePath());

        }

        return dire;
    }

    @FXML
    private void btn_QuickSort(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

    @FXML
    private void btn_Seleccion(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

    @FXML
    private void btn_BubbleSort(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

    @FXML
    private void btn_Insercion(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

    @FXML
    private void btn_Fusion(ActionEvent event) throws IOException {
        progressBar.setProgress(0.0);
        progressbar();
        dir = cargar();
        ordenar();
    }

}
