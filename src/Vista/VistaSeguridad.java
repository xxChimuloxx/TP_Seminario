package Vista;

import Controlador.ControladorSeguridad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaSeguridad extends JFrame implements ActionListener {
    private JPanel panel;
    private JPanel panel0;
    private JPanel panel1;
    private JButton btnSalir;
    private JButton btnAdd;
    private JButton btnConsultar;
    private JButton btnModificar;
    private JButton btnBorrar;
    private JPanel panel2;
    private JLabel lblImagen;
    private JLabel lblTitulo;
    private JTable tblDatos;
    private JButton btnDesbloquear;
    private JButton btnBloquear;
    private ControladorSeguridad controlador;

    public VistaSeguridad() {
        controlador = new ControladorSeguridad(this);
        initComponents();

        controlador.cargarTabla(this.tblDatos);
        tblDatos.setAutoscrolls(true);
        tblDatos.setAutoCreateRowSorter(true);
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
        Image imageI = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoBackground.png");
        ImageIcon icon = new ImageIcon(imageI);
        lblImagen.setIcon(icon);

        //set layout
        this.setContentPane(panel);

        //definiciones de componentes
        lblImagen.setForeground(new Color(255, 255, 255));
        panel.setForeground(new Color(255, 255, 255));
        panel0.setForeground(new Color(255, 255, 255));
        panel1.setForeground(new Color(255, 255, 255));
        panel2.setForeground(new Color(255, 255, 255));
        lblImagen.setText("");

        this.btnSalir.setActionCommand("aBotonSalir");
        this.btnAdd.setActionCommand("aBotonAdd");
        this.btnConsultar.setActionCommand("aBotonConsultar");
        this.btnModificar.setActionCommand("aBotonModificar");
        this.btnBorrar.setActionCommand("aBotonBorrar");
        this.btnBloquear.setActionCommand("aBotonBloquear");
        this.btnDesbloquear.setActionCommand("aBotonDesbloquear");
        this.btnSalir.addActionListener(this);
        this.btnAdd.addActionListener(this);
        this.btnConsultar.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnBorrar.addActionListener(this);
        this.btnBloquear.addActionListener(this);
        this.btnDesbloquear.addActionListener(this);

        //definiciones generales
        this.setTitle("Administrador de Personas ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if ("aBotonSalir".equals(e.getActionCommand())) {
            controlador.accionBotonSalir();
        }
        if ("aBotonAdd".equals(e.getActionCommand())) {
            controlador.accionBotonAdd(-1);
        }
        if ("aBotonConsultar".equals(e.getActionCommand())) {
            controlador.accionBotonConsultar(evaluarSeleccion());
        }
        if ("aBotonModificar".equals(e.getActionCommand())) {
            controlador.accionBotonModificar(evaluarSeleccion());
        }
        if ("aBotonBorrar".equals(e.getActionCommand())) {
            controlador.accionBotonBorrar(evaluarSeleccion());
        }
        if ("aBotonDesbloquear".equals(e.getActionCommand())) {
            controlador.accionBotonDesbloquear(evaluarSeleccion());
        }
        if ("aBotonBloquear".equals(e.getActionCommand())) {
            controlador.accionBotonBloquear(evaluarSeleccion());
        }
    }

    private int evaluarSeleccion(){
        int retorno = -1;
        int selectedRow = tblDatos.getSelectedRow();

        if((selectedRow !=-1)&(tblDatos.getSelectedRowCount()==1)) {
            int modelRow = tblDatos.convertRowIndexToModel(selectedRow);
            int value = Integer.valueOf((String) tblDatos.getModel().getValueAt(modelRow, 0));

            retorno = value;
        }
        else{
            Dialogos.advertencia("Ha seleccionado muchas o ninguna fila. Seleccione SOLO una fila.",this);
        }

        return retorno;
    }

    public void refrescarTabla() {
        controlador.cargarTabla(this.tblDatos);
    }
}

