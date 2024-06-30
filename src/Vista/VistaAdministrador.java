package Vista;

import Controlador.Administrador.ControladorUsuarioAdminC;
import Controlador.Administrador.ControladorUsuarioAdminI;
import Controlador.Administrador.ControladorInterfaz;
import Controlador.Administrador.ControladorSeguridad;
import Controlador.Administrador.ControladorUsuarioAdminP;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Clase que se utiliza para mostrar las acciones y operaciones que realiza un usuario administrador de la seguridad del sistema.
 */
public class VistaAdministrador extends JFrame implements ActionListener{
    private JPanel panel;
    private JPanel panel0;
    private JPanel panel1;
    private JButton btnSalir;
    private JButton btnModificar;
    private JButton btnEspecial1;
    private JButton btnEspecial2;
    private JButton btnConsultar;
    private JButton btnAdd;
    private JButton btnBorrar;
    private JPanel panel2;
    private JLabel lblImagen;
    private JLabel lblTitulo;
    private JTable tblDatos;
    private ControladorInterfaz controlador;

    public static int VISTA_SEGURIDAD = 1;
    public static int VISTA_PERSONAS  = 2;
    public static int VISTA_CAMPANIAS = 3;
    public static int VISTA_ITEMS     = 4;

    private boolean validaBotonEspecial1=true;
    private boolean validaBotonEspecial2=true;

    private boolean closeOnExit = true;

    /**
     * Constructor de la clase generico, segun parametros.
     */
    public VistaAdministrador(int modo, boolean closeOnExit) {
        controlador = null;
        this.closeOnExit = closeOnExit;
        if(modo==VistaAdministrador.VISTA_PERSONAS){
            controlador = new ControladorUsuarioAdminP(this);
            this.setTitle("Administracion de Personal");
            this.lblTitulo.setText("Listado de Personas");
            this.btnEspecial1.setEnabled(false);
            this.btnEspecial2.setEnabled(false);
            this.btnEspecial1.setVisible(false);
            this.btnEspecial2.setVisible(false);
        }
        if(modo==VistaAdministrador.VISTA_CAMPANIAS){
            controlador = new ControladorUsuarioAdminC(this);
            this.setTitle("Administracion de Campañias de la Aplicacion  ");
            this.lblTitulo.setText("Listado de Campañias");
        }
        if(modo==VistaAdministrador.VISTA_ITEMS){
            controlador = new ControladorUsuarioAdminI(this);
            this.setTitle("Administracion de Items de la Aplicacion  ");
            this.lblTitulo.setText("Listado de Items");
        }
        if(modo==VistaAdministrador.VISTA_SEGURIDAD){
            controlador = new ControladorSeguridad(this);
            this.setTitle("Administracion de Usuarios de la Aplicacion  ");
            this.lblTitulo.setText("Listado de Usuarios");
        }

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
        this.btnEspecial1.setActionCommand("aBotonEspecial1");
        this.btnEspecial2.setActionCommand("aBotonEspecial2");
        this.btnSalir.addActionListener(this);
        this.btnAdd.addActionListener(this);
        this.btnConsultar.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnBorrar.addActionListener(this);
        this.btnEspecial1.addActionListener(this);
        this.btnEspecial2.addActionListener(this);

        //definiciones generales
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (this.closeOnExit){this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
        else{this.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));}

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
        if ("aBotonEspecial1".equals(e.getActionCommand())) {
            if(this.validaBotonEspecial1){
            controlador.accionBotonEspecial1(evaluarSeleccion());
            }
            else{
                controlador.accionBotonEspecial1(-1);
            }
        }
        if ("aBotonEspecial2".equals(e.getActionCommand())) {
            if(this.validaBotonEspecial2){
                controlador.accionBotonEspecial2(evaluarSeleccion());
            }
            else{
                controlador.accionBotonEspecial2(-1);
            }

        }
    }

    /**
     * Se obtiene el registro seleccionado de la tabla de datos de la vista.
     * @return se retorna la clave del objeto seleccionado.
     */
    private int evaluarSeleccion(){
        int retorno = -1;
        int selectedRow = tblDatos.getSelectedRow();

        if((selectedRow !=-1)&(tblDatos.getSelectedRowCount()==1)) {
            int modelRow = tblDatos.convertRowIndexToModel(selectedRow);
            //int value = Integer.valueOf((String) tblDatos.getModel().getValueAt(modelRow, 0));
            retorno = modelRow;
        }
        else{
            Dialogos.advertencia("Ha seleccionado muchas o ninguna fila. Seleccione SOLO una fila.",this);
        }

        return retorno;
    }

    /**
     * obtiene el modelo de la JTable
     * @return
     */
    public TableModel getTableModel(){
        return this.tblDatos.getModel();
    }

    /**
     * Vuelva a cargar la informacion de la tabla de datos para refrescar su contenido.
     */
    public void refrescarTabla() {
        controlador.cargarTabla(this.tblDatos);
    }

    /**
     * Determina o no el uso del boton adicional de la vista. #1
     * @param texto
     */
    public void setBotonEspecial1(String texto) {
        if (Objects.equals(texto, "")){
            this.btnEspecial1.setEnabled(false);
            this.btnEspecial1.setVisible(false);
        }
        else{
            this.btnEspecial1.setText(texto);
            this.btnEspecial1.setEnabled(true);
            this.btnEspecial1.setVisible(true);
        }
    }

    /**
     * determina si se validara que se haya seleccionado o no algo al utilizar el boton especial #1.
     * por defecto siempre se valida. se utiliza para desactivar el feature al momento de la creacion.
     * @param v
     */
    public void setValidaBotonEspecial1(boolean v){
        this.validaBotonEspecial1 = v;
    }

    /**
     * determina si se validara que se haya seleccionado o no algo al utilizar el boton especial #2.
     * por defecto siempre se valida. se utiliza para desactivar el feature al momento de la creacion.
     * @param v
     */
    public void setValidaBotonEspecial2(boolean v){
        this.validaBotonEspecial2 = v;
    }

    /**
     * Determina o no el uso del boton adicional de la vista. #2
     * @param texto
     */
    public void setBotonEspecial2(String texto) {
        if (Objects.equals(texto, "")){
            this.btnEspecial2.setEnabled(false);
            this.btnEspecial2.setVisible(false);
        }
        else{
            this.btnEspecial2.setText(texto);
            this.btnEspecial2.setEnabled(true);
            this.btnEspecial2.setVisible(true);
        }
    }
}

