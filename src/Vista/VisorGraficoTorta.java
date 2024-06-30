package Vista;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

/**
 * Clase que representa la vista de un grafico tipo torta.
 */
public class VisorGraficoTorta implements Mostrable {

    private String titulo;
    private int tipo;
    private String[] columnNames;
    private Object[][] data;

    /**
     * Constructor de la clase.
     * @param columnNames titulos del grafico
     * @param data datos del grafico en formato <dato><dato><dato>.. <dato>
     * @param titulo titulo del grafico
     * @param tipo tipo de datos que mostrara al navegar los detalles
     */
    public VisorGraficoTorta(String[] columnNames, Object[][] data,String titulo,int tipo) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.columnNames = columnNames;
        this.data = data;
    }

    /**
     * metodo que se hereda de la interfaz mostrable.
     * permite armar y mostrar el jframe que contendra el informe con los datos pasados como parametro.
     */
    public void showDataAndChartFrame() {
        JFrame dataFrame = new JFrame(titulo);
        dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dataFrame.setSize(800, 600);
        dataFrame.setResizable(false);
        dataFrame.setLocationRelativeTo(null);

        // Crear la tabla con los datos
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Configurar el ícono del frame
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        dataFrame.setIconImage(image);

        // Crear el gráfico de torta con los datos de la tabla
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 0; i < columnNames.length; i++) {
            int total = 0;
            for (int j = 0; j < data.length; j++) {
                total += (int) data[j][i];
            }
            pieDataset.setValue(columnNames[i], total);
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Gráfico de Totales",
                pieDataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}"));

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        // Crear el panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        JButton btnDetails = new JButton("Ver detalles");
        JButton btnPrint = new JButton("Imprimir");
        JButton btnExportCSV = new JButton("Exportar a CSV");
        JButton btnExportExcel = new JButton("Exportar a EXCEL");
        JButton btnBack = new JButton("Volver");

        buttonPanel.add(btnDetails);
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnExportCSV);
        buttonPanel.add(btnExportExcel);
        buttonPanel.add(btnBack);

        // Gestionar las acciones de los botones
        btnDetails.addActionListener(e -> {
            //JOptionPane.showMessageDialog(dataFrame, "Se presionó el botón 'Ver detalles'");
            abrirDetalles(tipo);
        });

        btnPrint.addActionListener(e -> {
            try {
                printComponent(chartPanel, table);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(dataFrame, "Error al imprimir: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExportCSV.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como CSV");
            int userSelection = fileChooser.showSaveDialog(dataFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try (FileWriter csvWriter = new FileWriter(fileChooser.getSelectedFile() + ".csv")) {
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        csvWriter.write(table.getColumnName(i) + ";");
                    }
                    csvWriter.write("\n");

                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            csvWriter.write(table.getValueAt(i, j).toString() + ";");
                        }
                        csvWriter.write("\n");
                    }
                    JOptionPane.showMessageDialog(dataFrame, "Datos exportados a CSV exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dataFrame, "Error al exportar a CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExportExcel.addActionListener(e -> {
            // Ruta donde se guardará el archivo CSV
            String filePath = "C:\\temp\\tabla.csv";

            // Guardar la tabla en el archivo CSV
            try (FileWriter csvWriter = new FileWriter(filePath)) {
                for (int i = 0; i < table.getColumnCount(); i++) {
                    csvWriter.write(table.getColumnName(i) + ";");
                }
                csvWriter.write("\n");

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        csvWriter.write(table.getValueAt(i, j).toString() + ";");
                    }
                    csvWriter.write("\n");
                }
                //JOptionPane.showMessageDialog(dataFrame, "Datos exportados a CSV exitosamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dataFrame, "Error al exportar a CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Intentar abrir el archivo CSV en Excel
            try {
                Desktop.getDesktop().open(new java.io.File(filePath));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dataFrame, "Error al abrir el archivo CSV en Excel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> dataFrame.dispose());

        // Usar JSplitPane para dividir el gráfico y la tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, tableScrollPane);
        splitPane.setDividerLocation(400); // Ajusta esta altura para el gráfico

        dataFrame.add(splitPane, BorderLayout.CENTER);
        dataFrame.add(buttonPanel, BorderLayout.SOUTH);

        dataFrame.setVisible(true);
    }

    /**
     * muestra una ventana con los detalles dependiendo del tipo que se le pasa como parametro.
     * @param tipo
     */
    private void abrirDetalles(int tipo) {
        VistaAdministrador v;
        switch (tipo) {
            case 1:
                v = new VistaAdministrador(VistaAdministrador.VISTA_SEGURIDAD,false);
                break;
            case 2:
                v = new VistaAdministrador(VistaAdministrador.VISTA_PERSONAS,false);
                break;
            case 3:
                v = new VistaAdministrador(VistaAdministrador.VISTA_CAMPANIAS,false);
                break;
            case 4:
                v = new VistaAdministrador(VistaAdministrador.VISTA_ITEMS,false);
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    /**
     * Metodo que gestiona la accion de imprimir la grafica y la tabla que se muestran por pantalla.
     * @param chartPanel
     * @param table
     * @throws PrinterException
     */
    private void printComponent(ChartPanel chartPanel, JTable table) throws PrinterException {
        // Crear un BufferedImage del ChartPanel
        BufferedImage chartImage = new BufferedImage(
                chartPanel.getWidth(), chartPanel.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = chartImage.createGraphics();
        chartPanel.paint(g2d);
        g2d.dispose();

        // Crear el documento para imprimir
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Ajustar tamaño del gráfico para que quepa en la página
            double scaleX = pageFormat.getImageableWidth() / chartPanel.getWidth();
            double scaleY = pageFormat.getImageableHeight() / (chartPanel.getHeight() + table.getHeight());
            double scale = Math.min(scaleX, scaleY);
            g2.scale(scale, scale);

            // Imprimir el gráfico
            g2.drawImage(chartImage, 0, 0, chartPanel);

            // Imprimir la tabla
            g2.translate(0, chartPanel.getHeight());
            table.print(g2);

            return PAGE_EXISTS;
        });

        if (printerJob.printDialog()) {
            printerJob.print();
        }
    }
}