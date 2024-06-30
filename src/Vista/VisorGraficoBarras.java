package Vista;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que representa una vista que permite mostrar en pantalla un grafico de barras.
 */
public class VisorGraficoBarras implements Mostrable {
    private boolean isHorizontal; // Flag to determine chart orientation
    private String format; // A o B
    private String[] columnNames;
    private List<String[]> data;
    private String titulo;
    private int tipo;

    /**
     * Constructor. inicializa los valores necesarios para que la clase funcione.
     * @param isHorizontal determina si el grafico sera de barras horizontales o verticales.
     * @param format "A" o "B". determina si el lote de trabajo sera tipo <dato><cantidad> o <dato><dato><cantidad>
     * @param columnNames titulos del lote de trabajo
     * @param data lote de trabajo
     * @param titulo titulo del reporte a mostrar
     * @param tipo indica que tipo de vista tiene que usar para mostrar los detalles
     */
    public VisorGraficoBarras(boolean isHorizontal,String format,String[] columnNames,List<String[]> data,String titulo, int tipo){
        this.isHorizontal = isHorizontal;

        this.format = format;
        this.columnNames = columnNames;
        this.data = data;
        this.titulo = titulo;
        this.tipo = tipo;
    }

    /**
     * Metodo que hereda de mostrable.
     * Es el que dispara el armado y compaginacion de la vista, mostrando el reporte en pantalla.
     */
    public void showDataAndChartFrame() {
        JFrame dataFrame = new JFrame(titulo);
        dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dataFrame.setSize(800, 600);
        dataFrame.setResizable(false); // No resizable
        dataFrame.setLocationRelativeTo(null); // Centrando en pantalla

        // Configurar el ícono del frame
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        dataFrame.setIconImage(image);

        // Crear el dataset para el gráfico de barras
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        Object[][] tableData;
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Todos");

        if ("A".equals(format)) {
            tableData = new Object[data.size()][2];

            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                barDataset.addValue(Integer.parseInt(row[1]), "Totales", row[0]);
                tableData[i][0] = row[0];
                tableData[i][1] = Integer.parseInt(row[1]);
            }
        } else {
            tableData = new Object[data.size()][3];

            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                barDataset.addValue(Integer.parseInt(row[2]), row[1], row[0]);
                tableData[i][0] = row[0];
                tableData[i][1] = row[1];
                tableData[i][2] = Integer.parseInt(row[2]);
            }

            Map<String, List<String[]>> groupedData = data.stream()
                    .collect(Collectors.groupingBy(row -> row[1]));
            groupedData.keySet().forEach(comboBox::addItem);
        }

        // Crear el gráfico basado en la variable isHorizontal
        JFreeChart barChart = ChartFactory.createBarChart(
                "Gráfico de Totales",
                "Categoría",
                "Valor",
                barDataset,
                isHorizontal ? PlotOrientation.HORIZONTAL : PlotOrientation.VERTICAL,
                true, true, false
        );

        // Añadir etiquetas de valores
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelPaint(Color.BLACK);
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                //isHorizontal ? TextAnchor.CENTER_RIGHT : TextAnchor.TOP_CENTER,
                //isHorizontal ? TextAnchor.CENTER_RIGHT : TextAnchor.TOP_CENTER
        ));

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        // Crear la tabla de datos
        JTable table = new JTable(tableData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 100));

        // Crear panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnDetails = new JButton("Ver detalles");
        btnDetails.addActionListener(e -> abrirDetalles(tipo));
        buttonPanel.add(btnDetails);

        JButton btnPrint = new JButton("Imprimir");
        btnPrint.addActionListener(e -> printChartAndTable(chartPanel, table, dataFrame));
        buttonPanel.add(btnPrint);

        JButton btnExportCSV = new JButton("Exportar a CSV");
        btnExportCSV.addActionListener(e -> exportTableToCSV(table, dataFrame));
        buttonPanel.add(btnExportCSV);

        JButton btnExportExcel = new JButton("Exportar a Excel");
        btnExportExcel.addActionListener(e -> exportTableToExcel(table, dataFrame));
        buttonPanel.add(btnExportExcel);

        JButton btnBack = new JButton("Volver");
        btnBack.addActionListener(e -> dataFrame.dispose());
        buttonPanel.add(btnBack);

        // Agregar componentes al frame
        if ("B".equals(format)) {
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.add(comboBox, BorderLayout.NORTH);
            topPanel.add(chartPanel, BorderLayout.CENTER);
            dataFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
        } else {
            dataFrame.getContentPane().add(chartPanel, BorderLayout.NORTH);
        }
        dataFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        dataFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        dataFrame.pack();
        dataFrame.setVisible(true);

        // Añadir ActionListener al comboBox para actualizar el gráfico y la tabla
        comboBox.addActionListener(e -> updateChartAndTable(barDataset, data, table, comboBox.getSelectedItem().toString()));
    }

    /**
     * actualiza la vista en pantalla en funcion de la informacion o filtros aplicados.
     * @param dataset lote de datos
     * @param data informacion
     * @param table jtable de trabajo
     * @param filter filtro a aplicar
     */
    private void updateChartAndTable(DefaultCategoryDataset dataset, List<String[]> data, JTable table, String filter) {
        dataset.clear();
        if (filter.equals("Todos")) {
            for (String[] row : data) {
                dataset.addValue(Integer.parseInt(row[2]), row[1], row[0]);
            }
            table.setModel(new javax.swing.table.DefaultTableModel(
                    data.stream().map(r -> new Object[]{r[0], r[1], Integer.parseInt(r[2])}).toArray(Object[][]::new),
                    columnNames
            ));
        } else {
            List<String[]> filteredData = data.stream().filter(row -> row[1].equals(filter)).collect(Collectors.toList());
            for (String[] row : filteredData) {
                dataset.addValue(Integer.parseInt(row[2]), row[1], row[0]);
            }
            table.setModel(new javax.swing.table.DefaultTableModel(
                    filteredData.stream().map(r -> new Object[]{r[0], r[1], Integer.parseInt(r[2])}).toArray(Object[][]::new),
                    columnNames
            ));
        }
    }

    /**
     * arma y compagina el grafico y la tabla del reporte.
     * @param chartPanel panel donde esta el grafico
     * @param table tabla de datos
     * @param parent vista padre que los contiene
     */
    private void printChartAndTable(Component chartPanel, JTable table, Component parent) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Imprimir Gráfico y Tabla");
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Imprimir gráfico
            double chartScaleFactor = Math.min(
                    pageFormat.getImageableWidth() / chartPanel.getWidth(),
                    pageFormat.getImageableHeight() / chartPanel.getHeight() / 2
            );
            g2d.scale(chartScaleFactor, chartScaleFactor);
            chartPanel.printAll(g2d);

            // Imprimir tabla debajo del gráfico
            g2d.translate(30, chartPanel.getHeight() * chartScaleFactor + 200);
            double tableScaleFactor = Math.min(
                    pageFormat.getImageableWidth() / table.getWidth(),
                    pageFormat.getImageableHeight() / table.getHeight() / 2
            );
            g2d.scale(tableScaleFactor, tableScaleFactor);
            table.printAll(g2d);

            return Printable.PAGE_EXISTS;
        });
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(parent, "Error al imprimir: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * funcion que representa la accion del boton exportar a CSV.
     * @param table tabla de datos
     * @param parent vista que lo contiene
     */
    private void exportTableToCSV(JTable table, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como CSV");
        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter csvWriter = new FileWriter(fileToSave + ".csv")) {
                for (int i = 0; i < table.getColumnCount(); i++) {
                    csvWriter.write(table.getColumnName(i) + ",");
                }
                csvWriter.write("\n");

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        csvWriter.write(table.getValueAt(i, j).toString() + ",");
                    }
                    csvWriter.write("\n");
                }
                JOptionPane.showMessageDialog(parent, "Datos exportados a CSV exitosamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error al exportar a CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * metodo que representa la accion de exportar la tabla a Excel
     * @param table tabla de datos
     * @param parent vista que la contiene
     */
    private void exportTableToExcel(JTable table, Component parent) {
        // Implementar la exportación a Excel si es necesario
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
            //JOptionPane.showMessageDialog(parent, "Datos exportados a CSV exitosamente");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Error al exportar a CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Intentar abrir el archivo CSV en Excel
        try {
            Desktop.getDesktop().open(new java.io.File(filePath));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error al abrir el archivo CSV en Excel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra una ventana con los detalles del tipo de vista que corresponde.
     * @param tipo indica que tipo de vista tiene que usar para mostrar los detalles.
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
                Dialogos.advertencia("Opcion no disponible.",null);
                break;
        }
    }
}

