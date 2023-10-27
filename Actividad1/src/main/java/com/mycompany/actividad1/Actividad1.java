/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.actividad1;

/**
 *
 * @author Alumno
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;

public class Actividad1 extends JFrame {

    public Actividad1() {
        setTitle("File Manager");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Introduzca la opción deseada del 1 al 9");
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        String[] opciones = new String[9];
        opciones[0] = "mostar";
        opciones[1] = "copiar";
        opciones[2] = "crear ";
        opciones[3] = "borrar";
        opciones[4] = "Extensiones de ficheros";
        opciones[5] = "Leer ficheros";
        opciones[6] = "Escribir en ficheros";
        opciones[7] = "Ruta del fichero";
        opciones[8] = "Listado del directorio";
        


        ImageIcon[] imagenes = new ImageIcon[9];
        imagenes[0] = new ImageIcon("ver (1).png");
        imagenes[1] = new ImageIcon("copia (1).png");
        imagenes[2] = new ImageIcon("agregar (1).png");
        imagenes[3] = new ImageIcon("borrar (1).png");
        imagenes[4] = new ImageIcon("extensiones (1).png");
        imagenes[5] = new ImageIcon("estudiante (1).png");
        imagenes[6] = new ImageIcon("contrato (1).png");
        imagenes[7] = new ImageIcon("mapa-vial (2).png");
        imagenes[8] = new ImageIcon("lista-de-la-compra (1).png");
        
        for (int i = 0; i <= 8; i++) {
            JButton button = new JButton( opciones[i], imagenes[i]);
            button.addActionListener(new ButtonListener(i));
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonListener implements ActionListener {

        private final int option;

        public ButtonListener(int option) {
            this.option = option;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (option) {
                case 0:
                    showFilePermissions();
                    break;

                case 1:
                    // Opción 2: Copiar ficheros
                    performFileCopy();
                    break;
                case 2:
                    // Opción 3: Crear ficheros
                    createNewFile();
                    break;
                case 3:
                    // Opción 4: Borrar ficheros
                    performFileDelete();
                    break;
                case 4:
                    // Opción 5: Extensiones de ficheros
                    filterFilesByExtension();
                    break;
                case 5:
                    // Opción 6: Leer ficheros
                    readTextFile();
                    break;
                case 6:
                    // Opción 7: Escribir en ficheros
                    writeToFile();
                    break;
                case 7:
                    // Opción 8: Ruta del fichero
                    showFilePath();
                    break;
                case 8:
                    // Opción 9: Listado del directorio
                    listDirectoryContents();
                    break;
                default:
                    break;
            }
        }
    }

    private void showFilePermissions() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar un archivo para ver los permisos");
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder permissions = new StringBuilder();

            if (selectedFile.canRead()) {
                permissions.append("Puede ser leído\n");
            } else {
                permissions.append("No puede ser leído\n");
            }

            if (selectedFile.canWrite()) {
                permissions.append("Puede ser modificado\n");
            } else {
                permissions.append("No puede ser modificado\n");
            }

            if (selectedFile.canExecute()) {
                permissions.append("Puede ser ejecutado\n");
            } else {
                permissions.append("No puede ser ejecutado\n");
            }

            JOptionPane.showMessageDialog(this, "Permisos del archivo:\n" + permissions.toString(), "Permisos del archivo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void performFileCopy() {
        JFileChooser sourceFileChooser = new JFileChooser();
        sourceFileChooser.setDialogTitle("Seleccionar archivo de origen");
        int sourceReturnValue = sourceFileChooser.showOpenDialog(null);

        if (sourceReturnValue == JFileChooser.APPROVE_OPTION) {
            File sourceFile = sourceFileChooser.getSelectedFile();

            JFileChooser destinationFileChooser = new JFileChooser();
            destinationFileChooser.setDialogTitle("Seleccionar ubicación de destino");
            int destReturnValue = destinationFileChooser.showSaveDialog(null);

            if (destReturnValue == JFileChooser.APPROVE_OPTION) {
                File destFile = destinationFileChooser.getSelectedFile();

                try {
                    // Copiar el archivo
                    Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "Archivo copiado con éxito.", "Copia de archivo", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al copiar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void createNewFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar ubicación para crear el archivo");
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File newFile = fileChooser.getSelectedFile();

            try {
                if (newFile.createNewFile()) {
                    JOptionPane.showMessageDialog(null, "Archivo creado con éxito.", "Creación de archivo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo ya existe.", "Creación de archivo", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void performFileDelete() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar el archivo a borrar");
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File fileToDelete = fileChooser.getSelectedFile();

            if (fileToDelete.delete()) {
                JOptionPane.showMessageDialog(null, "Archivo borrado con éxito.", "Borrar archivo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filterFilesByExtension() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivos por extensión");

        // Agregar filtros para las extensiones deseadas (ejemplo: txt, jpg, png)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt) y de imagen (.jpg, .png)", "txt", "jpg", "png");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Archivo seleccionado: " + selectedFile.getName(), "Archivo Seleccionado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void readTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de texto para leer");

        // Agregar un filtro para archivos de texto
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder fileContents = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContents.append(line).append("\n");
                }

                JTextArea textArea = new JTextArea(20, 50);
                textArea.setText(fileContents.toString());
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(this, scrollPane, "Contenido del archivo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void writeToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo para escribir");

        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                String contentToWrite = "Este es un ejemplo de contenido para escribir en el archivo.";

                // Usar FileWriter para escribir el contenido en el archivo
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(contentToWrite);
                writer.close();

                JOptionPane.showMessageDialog(this, "Contenido escrito con éxito en el archivo.", "Escritura en archivo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al escribir en el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar un archivo para mostrar su ruta");
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            JOptionPane.showMessageDialog(this, "Ruta del archivo: " + filePath, "Ruta del archivo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void listDirectoryContents() {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setDialogTitle("Seleccionar directorio para listar");
        int returnValue = directoryChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File directory = directoryChooser.getSelectedFile();
            File[] files = directory.listFiles();

            if (files != null) {
                ArrayList<String> fileList = new ArrayList<>();
                for (File file : files) {
                    fileList.add(file.getName());
                }
                String fileNames = String.join(", ", fileList);

                JOptionPane.showMessageDialog(null, "Archivos en el directorio: " + fileNames, "Listado de directorio", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El directorio está vacío.", "Listado de directorio", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Actividad1 app = new Actividad1();
            app.setVisible(true);
        });
    }
}
