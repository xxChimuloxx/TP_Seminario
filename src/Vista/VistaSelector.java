package Vista;

import Controlador.ControladorSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * Clase utilizada para mostrar el selector de paneles y vista de la aplicacion.
 * Para pruebas y accesos directos.
 */
public class VistaSelector extends JFrame implements ActionListener {
    private JLabel lblmagen;
    private JPanel panel;
    private JButton btnSalir;
    private JPanel panel1;
    private JPanel panel2;
    private JButton btnSeguridad;
    private JButton btnUsuarioAdminC;
    private JButton btnUsuarioAdminI;
    private JButton btnUsuarioInterno;
    private JButton btnUsuarioAdminP;
    private JLabel lblSaludo;
    private JButton btnReportes;

    private ControladorSelector controlador;
    private int usuario;

    /**
     * Constructor de la clase
     */
    public VistaSelector(int op1,int op2,int op3,int op4,int op5,String descripcion,int usuario) {
        controlador = new ControladorSelector(this);
        this.usuario = usuario;
        initComponents();
        this.lblSaludo.setText("Hola "+descripcion+"!!!");

        if(op1!=1){this.btnUsuarioInterno.setVisible(true);this.btnUsuarioInterno.setEnabled(false);}
        if(op2!=1){this.btnUsuarioAdminP.setVisible(true);this.btnUsuarioAdminP.setEnabled(false);}
        if(op3!=1){this.btnUsuarioAdminI.setVisible(true);this.btnUsuarioAdminI.setEnabled(false);}
        if(op4!=1){this.btnUsuarioAdminC.setVisible(true);this.btnUsuarioAdminC.setEnabled(false);}
        if(op5!=1){this.btnSeguridad.setVisible(true);this.btnSeguridad.setEnabled(false);}
    }

    /**
     * Inicializa componentes de la vista.
     */
    private void initComponents() {
        //load del icono de la herramienta
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        this.setIconImage(image);

        //load de la imagen de background
        Image imageI = Toolkit.getDefaultToolkit().getImage("src/Recursos/Background.png");
        ImageIcon icon = new ImageIcon(imageI);
        lblmagen.setIcon(icon);

        //set layout
        this.setContentPane(panel);

        //definiciones de componentes
        panel.setForeground(new Color(255,255,255));
        panel1.setForeground(new Color(255,255,255));
        panel2.setForeground(new Color(255,255,255));
        lblmagen.setText("");

        this.btnSalir.setActionCommand("aBotonSalir");
        this.btnUsuarioInterno.setActionCommand("aBotonUsuarioInterno");
        this.btnUsuarioAdminP.setActionCommand("aBotonUsuarioAdminP");
        this.btnUsuarioAdminI.setActionCommand("aBotonUsuarioAdminI");
        this.btnUsuarioAdminC.setActionCommand("aBotonUsuarioAdminC");
        this.btnSeguridad.setActionCommand("aBotonSeguridad");
        this.btnReportes.setActionCommand("aBotonReportes");
        this.btnSalir.addActionListener(this);
        this.btnUsuarioInterno.addActionListener(this);
        this.btnUsuarioAdminP.addActionListener(this);
        this.btnUsuarioAdminI.addActionListener(this);
        this.btnUsuarioAdminC.addActionListener(this);
        this.btnSeguridad.addActionListener(this);
        this.btnReportes.addActionListener(this);

        crearMenu();

        //definiciones generales
        this.setTitle("Gestor de Codigos QR. Seleccion de paneles.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * crea el menu y los menu item para mostrar en la vista.
     */
    private void crearMenu(){
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear el menú "DOCUMENTACION"
        JMenu menuDocumentacion = new JMenu("Documentacion");
        menuDocumentacion.setMnemonic('D');

        // Crear y agregar elementos del menú "DOCUMENTACION"
        String[] entregables = {"JavaDoc","TP 1", "TP 2", "TP 3", "TP 4","Video Presentacion"};
        String[] links = {
                "http://www.example1.com",
                "http://www.example2.com",
                "http://www.example3.com",
                "http://www.example3.com",
                "http://www.example3.com",
                "http://www.example4.com"
        };

        for (int i = 0; i < entregables.length; i++) {
            String entregable = entregables[i];
            String link = links[i];
            JMenuItem menuItem = new JMenuItem(entregable);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(link));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            menuDocumentacion.add(menuItem);
        }

        // Crear el menú "ACERCA DE..."
        JMenu menuAcercaDe = new JMenu("Acerca de...");

        // Crear y agregar el elemento del menú "ACERCA DE..."
        JMenuItem menuItemAcercaDe = new JMenuItem("Acerca de...");
        menuItemAcercaDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(VistaSelector.this,
                        "(2024) © Gestor de Codigos QR\nVersión 1.0\nDesarrollado por (VINF09044) Sebastian Fuentes \n" +
                                "INF275 Seminario de Practica de Informatica \n" +
                                "TE: Ana Carolina Ferreyra \n" +
                                "PTD: Pablo Alejandro Virgolini",
                        "Acerca de",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuAcercaDe.add(menuItemAcercaDe);

        // Agregar los menús a la barra de menú
        menuBar.add(menuDocumentacion);
        menuBar.add(menuAcercaDe);

        // Establecer la barra de menú en el JFrame
        this.setJMenuBar(menuBar);
    }

    /**
     * Implementa lo requerido por ActionPerformed.
     * En este caso en funcion del evento E gestiona la accion del Boton.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if ("aBotonSalir".equals(e.getActionCommand())) {
            controlador.accionBotonSalir();
        }
        if ("aBotonUsuarioInterno".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioInterno(this.usuario);
        }
        if ("aBotonSeguridad".equals(e.getActionCommand())) {
            controlador.accionBotonSeguridad();
        }
        if ("aBotonUsuarioAdminP".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioAdminP();
        }
        if ("aBotonUsuarioAdminI".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioAdminI();
        }
        if ("aBotonUsuarioAdminC".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioAdminC();
        }
        if ("aBotonReportes".equals(e.getActionCommand())) {
            controlador.accionaBotonReportes();
        }
    }

}


