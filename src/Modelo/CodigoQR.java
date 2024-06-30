package Modelo;

import Vista.Dialogos;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class CodigoQR {

    public static int CONSTANTE_TIPO_PERSONA = 1;
    public static int CONSTANTE_TIPO_CAMPANIA = 2;
    public static int CONSTANTE_TIPO_ITEM = 3;

    /**
     * Registra un codigo QR con una clave y un tipo asignado.
     * @param clave
     * @param tipo
     */
    public static void registrarQR(int clave, int tipo){
        String consulta="SELECT * FROM mydb.codigosqr WHERE `Tipo`="+tipo+" and `ID Objeto`="+clave;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (!resultado.next()){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                consulta="INSERT INTO `mydb`.`CodigosQR` (`hash`, `Descripcion`, `Tipo`, `ID Objeto`)\n" +
                                "VALUES ('"+timestamp+"', 'Codigo QR: tipo:"+tipo+" - clave: "+clave+"', "+tipo+", "+clave+");";

                sentencia= ConexionMySQL.obtener().createStatement();
                    sentencia.executeUpdate(consulta);
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el código QR: " + e.getMessage());
        }

    }

    /**
     * genera codigo QR con el texto que se le pasa como parametro, del tamaño width x height recibido, y lo guarda en el filepatch solicitado.
     * @param text
     * @param width
     * @param height
     * @param filePath
     * @throws WriterException
     * @throws IOException
     */
    public static void generarQRCodeImageAndDownload(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * genera codigo QR con el texto que se le pasa como parametro del tamaño NxM informado
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage generarQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * Ejemplo de ejecucion. Para depuracion y control
     * @param args
     */
    public static void main(String[] args) {
        try {
            generarQRCodeImageAndDownload("https://www.example1.com", 350, 350, "qrCode.png");
            System.out.println("Código QR generado con éxito.");
        } catch (WriterException e) {
            System.err.println("Error al generar el código QR: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de imagen: " + e.getMessage());
        }
    }

    /**
     * muestra codigoQR en un pop-up.
     * @param qrImage
     * @deprecated
     */
    public static void mostrarQRCodePopupOld(BufferedImage qrImage) {
        ImageIcon icon = new ImageIcon(qrImage);
        JLabel label = new JLabel(icon);
        JOptionPane.showMessageDialog(null, label, "Código QR Generado", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * muestra codigoQR en un pop-up. da la opcion de guardar la imagen.
     * @param qrImage
     */
    public static void mostrarQRCodePopup(BufferedImage qrImage) {
        ImageIcon icon = new ImageIcon(qrImage);
        JLabel label = new JLabel(icon);

        JButton saveButton = new JButton("Guardar QR");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar como");

                // Set the default directory to the user's downloads directory
                fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "/Downloads/qrCode.png"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(qrImage, "png", fileToSave);
                        JOptionPane.showMessageDialog(null, "QR guardado en " + fileToSave.getAbsolutePath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + ex.getMessage());
                    }
                }
            }
        });

        JButton printButton = new JButton("Imprimir QR");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opciones = {"Imprimir Normal", "Imprimir en Mosaico"};
                int tipoImpresion = Dialogos.seleccion(opciones,null);

                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex != 0) {
                            return NO_SUCH_PAGE;
                        }

                        Graphics2D g2d = (Graphics2D) graphics;
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                        double pageWidth = pageFormat.getImageableWidth();
                        double pageHeight = pageFormat.getImageableHeight();
                        double imageWidth = qrImage.getWidth();
                        double imageHeight = qrImage.getHeight();

                        double scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

                        int scaledWidth = (int) (imageWidth * scale);
                        int scaledHeight = (int) (imageHeight * scale);

                        //impresion normal
                        if (tipoImpresion==0){g2d.drawImage(qrImage, 0, 0, scaledWidth, scaledHeight, null);}

                        //impresion mosaico
                        if (tipoImpresion==1){
                            int columns = (int) Math.ceil(pageWidth / imageWidth)+1;
                            int rows = (int) Math.ceil(pageHeight / imageHeight)+1;
                            scale = 0.5;
                            scaledWidth = (int) (imageWidth * scale);
                            scaledHeight = (int) (imageHeight * scale);
                            for (int row = 0; row < rows; row++) {
                                for (int col = 0; col < columns; col++) {
                                    int x = (int)(col * scaledWidth);
                                    int y = (int) (row * scaledHeight);
                                    g2d.drawImage(qrImage, x, y, scaledWidth, scaledHeight,null);
                                }
                            }
                        };

                        return PAGE_EXISTS;
                    }
                });

                boolean doPrint = printerJob.printDialog();
                if (doPrint) {
                    try {
                        printerJob.print();
                    } catch (PrinterException ex) {
                        JOptionPane.showMessageDialog(null, "Error al imprimir la imagen: " + ex.getMessage());
                    }
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        //panel.add(saveButton, BorderLayout.SOUTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(printButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);


        //JOptionPane.showMessageDialog(null, panel, "Código QR Generado", JOptionPane.PLAIN_MESSAGE);
        Image imageA = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        ImageIcon iconA = new ImageIcon(imageA);

        // Agregar icono y detalles a la ventana emergente
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optionPane.createDialog("Código QR Generado");
        dialog.setIconImage(imageA);
        dialog.setVisible(true);
    }

    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */
    /* ***************************************************************************************************** */

    /**
     * crea una imagen pre-formateada (banner) con los datos que se pasan como parametro, para la publicacion del codigo QR.
     * @param dni
     * @param nombre
     * @param apellido
     * @param legajo
     * @param correo
     * @param telefono
     * @param equipo
     * @param area
     * @param gerencia
     * @return imagen con los datos pre formateada
     */
    public static BufferedImage crearTablaDatos(String dni, String nombre, String apellido, String legajo, String correo, String telefono, String equipo, String area, String gerencia) {
        int tableWidth = 350;
        int tableHeight = 120;
        int cellHeight = 20;

        BufferedImage tableImage = new BufferedImage(tableWidth, tableHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tableImage.createGraphics();

        // Set up fonts and colors
        Font titleFont = new Font("Arial", Font.BOLD, 12);
        Font dataFont = new Font("Arial", Font.PLAIN, 12);
        Color titleBackgroundColor = new Color(173, 216, 230); // Light blue color
        Color dataBackgroundColor = Color.WHITE;
        Color textColor = Color.BLACK;

        // Draw the table with alternating row colors
        String[] titles = {"DNI", "Nombre", "Apellido", "Legajo", "Correo Electrónico", "Teléfono", "Equipo de Trabajo", "Área", "Gerencia"};
        String[] data = {dni, nombre, apellido, legajo, correo, telefono, equipo, area, gerencia};
/*
        for (int i = 0; i < titles.length; i++) {
            // Draw title cells
            g2d.setColor(titleBackgroundColor);
            g2d.fillRect(0, i * cellHeight, tableWidth, cellHeight);
            g2d.setColor(textColor);
            g2d.setFont(titleFont);
            g2d.drawString(titles[i], 10, (i + 1) * cellHeight - 5);

            // Draw data cells
            g2d.setColor(dataBackgroundColor);
            g2d.fillRect(150, i * cellHeight, tableWidth - 150, cellHeight);
            g2d.setColor(textColor);
            g2d.setFont(dataFont);
            g2d.drawString(data[i], 160, (i + 1) * cellHeight - 5);
        }
*/
        g2d.setColor(dataBackgroundColor);
        g2d.fillRect(0, 1 * cellHeight, tableWidth, cellHeight);
        g2d.fillRect(0, 2 * cellHeight, tableWidth, cellHeight);
        g2d.fillRect(0, 3 * cellHeight, tableWidth, cellHeight);
        g2d.fillRect(0, 4 * cellHeight, tableWidth, cellHeight);
        g2d.fillRect(0, 5 * cellHeight, tableWidth, cellHeight);
        g2d.setColor(textColor);
        g2d.setFont(dataFont);
        g2d.drawString("______________________________", 80, (1 + 1) * cellHeight - 5);
        g2d.drawString(data[3]+" - "+data[1]+" "+data[2], 100, (2 + 1) * cellHeight - 5);
        g2d.drawString("eMail: "+data[4], 100, (3 + 1) * cellHeight - 5);
        g2d.drawString("Telefono: "+data[5], 100, (4 + 1) * cellHeight - 5);
        g2d.drawString(data[6]+" - "+data[7]+" - "+data[8], 100, (5 + 1) * cellHeight - 5);

        g2d.dispose();
        return tableImage;
    }

    /**
     * combina las imagenes en una sola.
     * @param image1
     * @param image2
     * @return
     */
    public static BufferedImage combineImages(BufferedImage image1, BufferedImage image2) {
        Image imageB = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoBackground.png");

        int width = Math.max(image1.getWidth(), image2.getWidth());
        int height = image1.getHeight() + image2.getHeight() + imageB.getHeight(null);
        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(image1, 0, 0, null);
        g2d.drawImage(imageB, 80, image1.getHeight(), null);
        g2d.drawImage(image2, 0, image1.getHeight() + imageB.getHeight(null), null);
        g2d.dispose();

        return combined;
    }

    /**
     * guarda la imagen unificada. se creo para la prueba. deprecated.
     * @param combinedImage
     * @throws IOException
     * @deprecated
     */
    public static void saveCombinedImage(BufferedImage combinedImage) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como");
        fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "/Downloads/combinedImage.png"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            ImageIO.write(combinedImage, "png", fileToSave);
        }
    }

    /**
     * Genera un vcard tomando como base la informacion proporcionada como parametro.
     * @param dni
     * @param nombre
     * @param apellido
     * @param legajo
     * @param correo
     * @param telefono
     * @param equipo
     * @param area
     * @param gerencia
     * @return un string que contiene la vcard.
     */
    public static String createVCard(String dni, String nombre, String apellido, String legajo, String correo, String telefono, String equipo, String area, String gerencia) {
        return "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:" + nombre + " " + apellido + "\n" +
                "N:" + apellido + ";" + nombre + ";;;\n" +
                "ORG:" + equipo + " - " + area + " - " + gerencia + "\n" +
                "TEL;TYPE=CELL:" + telefono + "\n" +
                "EMAIL:" + correo + "\n" +
                "NOTE:DNI: " + dni + " - Legajo: " + legajo + "\n" +
                "END:VCARD";
    }

    /**
     * Genera el codigo QR con el icono embebido.
     * @param qrImage
     * @return
     */
    public static BufferedImage agregarIconoQRCode(BufferedImage qrImage,JFrame vista){
        String iconPath = "src/Recursos/IconoCentral.png"; // Ruta del ícono

        try {
            BufferedImage iconImage = ImageIO.read(new File(iconPath));
            return overlayIcon(qrImage, iconImage);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar la imagen: " + ex.getMessage());
        }
        return qrImage;
    }

    /**
     * superpone dos imagenes, el codigo QR y el icono.
     * @param qrImage
     * @param iconImage
     * @return imagen superpuesta.
     */
    private static BufferedImage overlayIcon(BufferedImage qrImage, BufferedImage iconImage) {
        int qrWidth = qrImage.getWidth();
        int qrHeight = qrImage.getHeight();
        int iconWidth = iconImage.getWidth();
        int iconHeight = iconImage.getHeight();

        // Calculate the position for the icon
        int x = (qrWidth - iconWidth) / 2;
        int y = (qrHeight - iconHeight) / 2;

        // Combine the QR code and the icon
        BufferedImage combined = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        g.drawImage(qrImage, 0, 0, null);
        g.drawImage(iconImage, x, y, null);
        g.dispose();

        return combined;
    }

    /**
     * Metodo que permite la decodificacion de una codigo QR.
     * Para el alcance del presente y ante la falta de un lector de codigos QR. La funcion se realiza a traves de
     * la carga de una imagen y su analisis posterior.
     * @param panel
     * @return
     */
    public static String ScanQR(JPanel panel) {
        JFileChooser fileChooser = new JFileChooser();
        String decodedText = "";
        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                decodedText = decodeQRCode(bufferedImage);
                if (decodedText == null) {
                    JOptionPane.showMessageDialog(panel, "No se encontró un código QR en la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //else {
                    //JOptionPane.showMessageDialog(panel, "Datos del Código QR:\n" + decodedText, "QR Code Data", JOptionPane.INFORMATION_MESSAGE);
                    // Process the decoded text
                    //processDecodedText(decodedText, panel);
                //}
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "No se pudo leer el archivo de imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return decodedText;
    }

    /**
     * Metodo que permite la decodificacion de una codigo QR. Este metodo es puntualmente el que decodifica.
     * @param qrImage
     * @return
     */
    private static String decodeQRCode(BufferedImage qrImage) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(qrImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new QRCodeReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al decodificar el QR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}