package Vista;

import Modelo.ObjetosPersistentes.Campania;
import Modelo.ObjetosPersistentes.Item;
import Modelo.ObjetosPersistentes.Persona;
import Modelo.ObjetosPersistentes.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reportes selector se utiliza para mostrar el esquema de reportes, metricas y uso de la aplicacion.
 */
public class ReportesSelector extends JFrame {
    private JComboBox<String> reportesComboBox;
    private JButton seleccionarButton;
    private JLabel descripcionLabel;

    // Mapa para almacenar las acciones personalizadas
    private Map<String, ActionListener> accionesPersonalizadas;

    /**
     * Constructor personalizado. Crea el selector con las caracteristicas graficas determinadas.
     */
    public ReportesSelector() {
        setTitle("Reportes");
        setSize(400, 200);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Configurar el ícono del frame
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        setIconImage(image);

        descripcionLabel = new JLabel("Seleccione un reporte para su visualización:");
        String[] reportes = {
                "Reportes de Seguridad",
                "Reportes del Personal",
                "Reportes de Marketing",
                "Reportes de Activos Registrados"
                /*"Reportes de Codigos QR Generados"*/
        };
        reportesComboBox = new JComboBox<>(reportes);
        seleccionarButton = new JButton("Seleccionar");

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedReporte = (String) reportesComboBox.getSelectedItem();
                abrirFormularioDeReporte(selectedReporte);
            }
        });

        setLayout(new GridLayout(3, 1));
        add(descripcionLabel);
        add(reportesComboBox);
        add(seleccionarButton);

        // Inicializa el mapa de acciones personalizadas
        inicializarAccionesPersonalizadas();
    }

    /**
     * Inicializa las acciones de los bontones.
     */
    private void inicializarAccionesPersonalizadas() {
        accionesPersonalizadas = new HashMap<>();

        // Agrega acciones personalizadas
        //*******************  SEGURIDAD *******************//

        accionesPersonalizadas.put("Reporte de Usuarios x Estado", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteUsuarioxEstado();
            }
        });

        accionesPersonalizadas.put("Reporte de Usuarios x Tipo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteUsuarioxTipo();
            }
        });

        //*******************  PERSONAS *******************//

        accionesPersonalizadas.put("Reporte de Personas x Equipo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReportePersonaxEquipo();
            }
        });

        accionesPersonalizadas.put("Reporte de Personas x Area", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReportePersonaxArea();
            }
        });

        accionesPersonalizadas.put("Reporte de Personas x Gerencia", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReportePersonaxGerencia();
            }
        });

        accionesPersonalizadas.put("Reporte de Equipos x Area", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteEquiposxArea();
            }
        });

        accionesPersonalizadas.put("Reporte de Areas x Gerencia", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteAreasxGerencia();
            }
        });

        //*******************  ITEMS *******************//

        accionesPersonalizadas.put("Reporte de Item x Marca", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteItemxMarca();
            }
        });

        accionesPersonalizadas.put("Reporte de Item x Modelo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteItemxModelo();
            }
        });

        accionesPersonalizadas.put("Reporte de Modelo x Marca", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteModeloxMarca();
            }
        });

        //*******************  CAMPANIA *******************//

        accionesPersonalizadas.put("Reporte de Campañas x Vigencia", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteCampaniaxVigencia();
            }
        });

        accionesPersonalizadas.put("Reporte de Campañas x Mes", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteCampaniaxMes();
            }
        });

        accionesPersonalizadas.put("Reporte de Campañas x Año", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción personalizada para...
                accionPersonalizadaReporteCampaniaxAnio();
            }
        });

        //*******************  PERSONAS *******************//
        //********************************************************//
        // Agrega más acciones personalizadas según sea necesario
    }

    /**
     * abre e inicializar el jframe para mostrar el reporte.
     * contiene el arbol de opciones base que utilizara el selector para ser navegado.
     * @param reporte
     */
    private void abrirFormularioDeReporte(String reporte) {
        JFrame reporteFrame = new JFrame(reporte);
        reporteFrame.setSize(400, 300);
        reporteFrame.setLocationRelativeTo(null);
        reporteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reporteFrame.setResizable(false);

        // Configurar el ícono del frame
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        reporteFrame.setIconImage(image);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        JLabel label3 = new JLabel("Label 3");

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);

        switch (reporte) {
            case "Reportes de Seguridad":
                label1.setText("");
                label2.setText("   TOTAL DE USUARIOS DEFINIDOS: "+String.valueOf(Usuario.getReporteTotalRegistros()));
                label3.setText("");
                agregarOpciones(panel, new String[]{
                        "Reporte de Usuarios x Estado",
                        "Reporte de Usuarios x Tipo"
                });
                break;
            case "Reportes del Personal":
                label1.setText("   TOTAL DE USUARIOS DEFINIDOS: "+String.valueOf(Persona.getReporteTotalRegistros()));
                label2.setText("   Total de Equipos: "+String.valueOf(Persona.getReporteTotalEquipos())+"                     Total de Areas: "+String.valueOf(Persona.getReporteTotalAreas()));
                label3.setText("   Total de Gerencias: "+String.valueOf(Persona.getReporteTotalGerencias()));
                agregarOpciones(panel, new String[]{
                        "Reporte de Personas x Equipo",
                        "Reporte de Personas x Area",
                        "Reporte de Personas x Gerencia",
                        "Reporte de Equipos x Area",
                        "Reporte de Areas x Gerencia"
                });
                break;
            case "Reportes de Marketing":
                label1.setText("   TOTAL DE CAMPAÑAS DEFINIDAS: "+String.valueOf(Campania.getReporteTotalRegistros()));
                label2.setText("   Media mensual de Campañas: "+String.valueOf(Campania.getReporteMediaMensual()));
                label3.setText("   Media anual de Campañas: "+String.valueOf(Campania.getReporteMediaAnual()));
                agregarOpciones(panel, new String[]{
                        "Reporte de Campañas x Vigencia",
                        "Reporte de Campañas x Mes",
                        "Reporte de Campañas x Año"
                });
                break;
            case "Reportes de Activos Registrados":
                label1.setText("   TOTAL DE ITEMS DEFINIDOS: "+String.valueOf(Item.getReporteTotalRegistros()));
                label2.setText("   Total de Modelos: "+String.valueOf(Item.getReporteTotalModelos()));
                label3.setText("   Total de Marcas: "+String.valueOf(Item.getReporteTotalMarcas()));
                agregarOpciones(panel, new String[]{
                        "Reporte de Item x Marca",
                        "Reporte de Item x Modelo",
                        "Reporte de Modelo x Marca"
                });
                break;
            /*case "Reportes de Codigos QR Generados":
                agregarOpciones(panel, new String[]{
                        "Reporte de QR generados x Mes",
                        "Reporte de QR generados x Año"
                });
                break;*/
        }

        reporteFrame.add(panel);
        reporteFrame.setVisible(true);
    }

    /**
     * agrega las opciones (botones) que se mostraran en el panel de visualizacion al menu contextual.
     * asigna las acciones customizadas a cada boton del panel.
     * @param panel
     * @param opciones
     */
    private void agregarOpciones(JPanel panel, String[] opciones) {
        for (String opcion : opciones) {
            JButton button = new JButton(opcion);
            panel.add(button);

            // Agregar action listener para cada botón si hay una acción personalizada
            ActionListener accion = accionesPersonalizadas.get(opcion);
            if (accion != null) {
                button.addActionListener(accion);
            } else {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Acción por defecto para '" + opcion + "'");
                    }
                });
            }
        }
    }

    //*********************************************************************************************************//
    //*********************************************************************************************************//
    //*********************************************************************************************************//
    //*********************************************************************************************************//
    //*********************************************************************************************************//

    /**
     * accion personalizada.
     * genera el reporte Usuario x Estado
     */
    private void accionPersonalizadaReporteUsuarioxEstado(){
        String[] columnNames = Usuario.getReporteUsuarioxEstadoTitulos();
        Object[][] data = Usuario.getReporteUsuarioxEstado();
        Mostrable grafico = new VisorGraficoTorta(columnNames, data,"Reporte Usuario x Estado", Mostrable.VISOR_GRAFICO_SEGURIDAD);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Usuario x Tipo
     */
    private void accionPersonalizadaReporteUsuarioxTipo(){
        String[] columnNames = Usuario.getReporteUsuarioxTipoTitulos();
        Object[][] data = Usuario.getReporteUsuarioxTipo();
        Mostrable grafico = new VisorGraficoTorta(columnNames, data,"Reporte Usuario x Tipo", Mostrable.VISOR_GRAFICO_SEGURIDAD);
        grafico.showDataAndChartFrame();
    }

    //*********************************************************************************************************//

    /**
     * accion personalizada.
     * genera el reporte Persona x Equipo
     */
    private void accionPersonalizadaReportePersonaxEquipo(){
        String[] columnNames = Persona.getReportePersonaxEquipoTitulos();
        List<String[]> data = Persona.getReportePersonaxEquipo();
        Mostrable grafico = new VisorGraficoBarras(false, "A", columnNames, data,"Reporte Persona x Equipo", Mostrable.VISOR_GRAFICO_PERSONA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Persona x Area
     */
    private void accionPersonalizadaReportePersonaxArea(){
        String[] columnNames = Persona.getReportePersonaxAreaTitulos();
        List<String[]> data = Persona.getReportePersonaxArea();
        Mostrable grafico = new VisorGraficoBarras(false, "A", columnNames, data,"Reporte Persona x Area", Mostrable.VISOR_GRAFICO_PERSONA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Persona x Equipo
     */
    private void accionPersonalizadaReportePersonaxGerencia(){
        String[] columnNames = Persona.getReportePersonaxGerenciaTitulos();
        List<String[]> data = Persona.getReportePersonaxGerencia();
        Mostrable grafico = new VisorGraficoBarras(false, "A", columnNames, data,"Reporte Persona x Gerencia", Mostrable.VISOR_GRAFICO_PERSONA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Equipos x Area
     */
    private void accionPersonalizadaReporteEquiposxArea(){
        String[] columnNames = Persona.getReporteEquiposxAreaTitulos();
        List<String[]> data = Persona.getReporteEquiposxArea();
        Mostrable grafico = new VisorGraficoBarras(true, "B", columnNames, data,"Reporte Equipo x Area", Mostrable.VISOR_GRAFICO_PERSONA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Areas x Gerencia
     */
    private void accionPersonalizadaReporteAreasxGerencia(){
        String[] columnNames = Persona.getReporteAreaxGerenciaTitulos();
        List<String[]> data = Persona.getReporteAreaxGerencia();
        Mostrable grafico = new VisorGraficoBarras(true, "B", columnNames, data,"Reporte Area x Gerencia", Mostrable.VISOR_GRAFICO_PERSONA);
        grafico.showDataAndChartFrame();
    }

    //*********************************************************************************************************//

    /**
     * accion personalizada.
     * genera el reporte Item x Marca
     */
    private void accionPersonalizadaReporteItemxMarca(){
        String[] columnNames = Item.getReporteItemxMarcaTitulos();
        List<String[]> data = Item.getReporteItemxMarca();
        Mostrable grafico = new VisorGraficoBarras(true, "A", columnNames, data,"Reporte Item x Marca", Mostrable.VISOR_GRAFICO_ITEM);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Item x Modelo
     */
    private void accionPersonalizadaReporteItemxModelo(){
        String[] columnNames = Item.getReporteItemxModeloTitulos();
        List<String[]> data = Item.getReporteItemxModelo();
        Mostrable grafico = new VisorGraficoBarras(true, "A", columnNames, data,"Reporte Item x Modelo", Mostrable.VISOR_GRAFICO_ITEM);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Modelo x Marca
     */
    private void accionPersonalizadaReporteModeloxMarca(){
        String[] columnNames = Item.getReporteModeloxMarcaTitulos();
        List<String[]> data = Item.getReporteModeloxMarca();
        Mostrable grafico = new VisorGraficoBarras(true, "B", columnNames, data,"Reporte Modelo x Marca", Mostrable.VISOR_GRAFICO_ITEM);
        grafico.showDataAndChartFrame();
    }

    //*********************************************************************************************************//

    /**
     * accion personalizada.
     * genera el reporte Campania x Vigencia
     */
    private void accionPersonalizadaReporteCampaniaxVigencia(){
        String[] columnNames = Campania.getReporteCampaniaxVigenciaTitulos();
        Object[][] data = Campania.getReporteCampaniaxVigencia();
        Mostrable grafico = new VisorGraficoTorta(columnNames, data,"Reporte Campaña x Vigencia", Mostrable.VISOR_GRAFICO_CAMPANIA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Campania x Mes
     */
    private void accionPersonalizadaReporteCampaniaxMes(){
        String[] columnNames = Campania.getReporteCampaniaxMesTitulos();
        List<String[]> data = Campania.getReporteCampaniaxMes();
        Mostrable grafico = new VisorGraficoBarras(false, "B", columnNames, data,"Reporte Campañas x Mes", Mostrable.VISOR_GRAFICO_CAMPANIA);
        grafico.showDataAndChartFrame();
    }

    /**
     * accion personalizada.
     * genera el reporte Campania x Anio
     */
    private void accionPersonalizadaReporteCampaniaxAnio(){
        String[] columnNames = Campania.getReporteCampaniaxAnioTitulos();
        List<String[]> data = Campania.getReporteCampaniaxAnio();
        Mostrable grafico = new VisorGraficoBarras(true, "A", columnNames, data,"Reporte Campañas x Año", Mostrable.VISOR_GRAFICO_CAMPANIA);
        grafico.showDataAndChartFrame();
    }

    //*********************************************************************************************************//

    /**
     * para pruebas o ejecuciones aisladas
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportesSelector().setVisible(true);
            }
        });
    }
}

