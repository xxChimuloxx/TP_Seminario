package Vista;

import Controlador.ControladorUsuarioAdminP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que se utiliza para mostrar las acciones que realicen los usuarios administradores de personas.
 */
public class VistaUsuarioAdminP extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel lblImagen;
    private JTable tblDatos;
    private JButton btnConsultar;
    private JButton btnSalir;
    private JButton btnAdd;
    private JButton btnModificar;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel0;
    private JLabel lblTitulo;
    private JButton btnBorrar;

    private ControladorUsuarioAdminP controlador;

    public VistaUsuarioAdminP() {
        controlador = new ControladorUsuarioAdminP(this);
        initComponents();


        controlador.cargarTabla(this.tblDatos);
        tblDatos.setAutoscrolls(true);
        tblDatos.setAutoCreateRowSorter(true);

    }

    /**
     * Inicializa los componentes de la vista.
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
        this.btnSalir.addActionListener(this);
        this.btnAdd.addActionListener(this);
        this.btnConsultar.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnBorrar.addActionListener(this);

        //definiciones generales
        this.setTitle("Administrador de Personas ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

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
    }

    /**
     * Recupera el valor de la clave correspondiente al registro seleccionado en la tabla de datos.
     * @return retorna el valor de la clave del registro seleccionado.
     */
    private int evaluarSeleccion(){
        int retorno = -1;
        int selectedRow = tblDatos.getSelectedRow();

        if((selectedRow !=-1)&(tblDatos.getSelectedRowCount()==1)) {
            int modelRow = tblDatos.convertRowIndexToModel(selectedRow);
            int value = (int) tblDatos.getModel().getValueAt(modelRow, 1);

            retorno = value;
        }
        else{
            Dialogos.advertencia("Ha seleccionado muchas o ninguna fila. Seleccione SOLO una fila.",this);
        }

        return retorno;
    }

    /**
     * Permite refrescar la vista de la tabla de datos para reflejar actualizaciones.
     */
    public void refrescarTabla() {
        controlador.cargarTabla(this.tblDatos);
    }
}
