package Vista;

import Controlador.ControladorSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaSelector extends JFrame implements ActionListener {
    private JButton btnLogon;
    private JButton btnUsuarioInterno;
    private JButton btnUsuarioAdminP;
    private JLabel lblmagen;
    private JPanel panel;
    private JButton btnSalir;
    private JPanel panel1;
    private JPanel panel2;
    private JButton btnSeguridad;

    private ControladorSelector controlador;

    public VistaSelector() {
        controlador = new ControladorSelector(this);
        initComponents();
    }

    /*
    initComponents
    Inicializa la vista
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
        this.btnLogon.setActionCommand("aBotonLogon");
        this.btnUsuarioInterno.setActionCommand("aBotonUsuarioInterno");
        this.btnUsuarioAdminP.setActionCommand("aBotonUsuarioAdminP");
        this.btnSeguridad.setActionCommand("aBotonSeguridad");
        this.btnSalir.addActionListener(this);
        this.btnLogon.addActionListener(this);
        this.btnUsuarioInterno.addActionListener(this);
        this.btnUsuarioAdminP.addActionListener(this);
        this.btnSeguridad.addActionListener(this);

        //definiciones generales
        this.setTitle("MODO PRUEBA: Seleccion de paneles.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if ("aBotonSalir".equals(e.getActionCommand())) {
            controlador.accionBotonSalir();
        }
        if ("aBotonLogon".equals(e.getActionCommand())) {
            controlador.accionBotonLogon();
        }
        if ("aBotonUsuarioInterno".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioInterno();
        }
        if ("aBotonSeguridad".equals(e.getActionCommand())) {
            controlador.accionBotonSeguridad();
        }
        if ("aBotonUsuarioAdminP".equals(e.getActionCommand())) {
            controlador.accionBotonUsuarioAdminP();
        }
    }

}


