package Vista;

import javax.swing.*;
import java.awt.event.ActionListener;

import Controlador.Raices.ControladorCampania;
import Controlador.Raices.ControladorItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Vista que se utiliza para mostrar las caracteristicas de un item de gestion de activos.
 */
public class VistaItem extends JFrame implements ActionListener {
    private JPanel panel1;
    private JLabel lblImagen;
    private JTextField txtMarca;
    private JButton btnGenerarBanner;
    private JButton btnModificar;
    private JTextField txtDescripcion;
    private JTextField txtModelo;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JButton btnCancelar;

    private ControladorItem controlador;
    private JFrame vistaPadre;
    private boolean closeOnExit = true;

    /**
     * para pruebas unitarias o verificacion funcional aislada.
     * @param args
     */
    public static void main(String[] args) {
        VistaCampania v = new VistaCampania();
    }

    /**
     * Constructor basico, para pruebas de entorno.
     * @deprecated
     */
    public VistaItem() {
        initComponents();
        controlador = new ControladorItem(this);
    }

    /**
     * Constructor de acceso directo. Es el que se utiliza cuando accede un usuario basico.
     * @param clave
     */
    public VistaItem(int clave) {
        initComponents();
        controlador = new ControladorItem(this,clave);
    }

    /**
     * Constructor de acceso indirecto. Es el que se utiliza cuando lo invoco desde otra vista.
     * Mantiene la referencia a vistaPadre para poder actualizarla si corresponde.
     * @param vistaPadre
     * @param clave
     */
    public VistaItem(JFrame vistaPadre,int clave) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorItem(this,clave);
        this.vistaPadre = vistaPadre;
    }

    /**
     * Constructor de acceso indirecto. Es el que se utiliza cuando lo invoco desde otra vista.
     * Mantiene la referencia a vistaPadre para poder actualizarla si corresponde.
     * Incluye el modo de acceso, que determina particularidades en el comportamiento del elemento.
     * @param vistaPadre
     * @param clave
     * @param modo puede ser ControladorUsuarioInterno.MODO_ADD o ControladorUsuarioInterno.MODO_EDICION
     * @see ControladorCampania
     */
    public VistaItem(JFrame vistaPadre,int clave, int modo) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorItem(this,clave,modo);
        this.vistaPadre = vistaPadre;
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
        JLabel background = new JLabel(icon);
        lblImagen.setText("");
        lblImagen.setIcon(icon);

        this.setContentPane(panel1);
        panel1.setBackground(new Color(255,255,255));


        //definiciones de componentes
        this.btnSalir.setActionCommand("aBotonSalir");
        this.btnGenerarBanner.setActionCommand("aBotonGenerarBanner");
        this.btnModificar.setActionCommand("aBotonModificar");
        this.btnGuardar.setActionCommand("aBotonGuardar");
        this.btnCancelar.setActionCommand("aBotonCancelar");

        this.btnSalir.addActionListener(this);
        this.btnGenerarBanner.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnGuardar.addActionListener(this);
        this.btnCancelar.addActionListener(this);

        //definiciones de elementos
        bloquearEdicion();

        //definiciones generales
        this.setTitle("Sistema de Gestion de QR. Vista Gestion de Activos");
        if (this.closeOnExit){this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
        else{this.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));}
        this.pack();
        //this.setSize(600,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Permite bloquear la edicion de los campos de la vista.
     */
    public void bloquearEdicion() {
        this.txtDescripcion.setEnabled(false);
        this.txtMarca.setEnabled(false);
        this.txtModelo.setEnabled(false);

        this.txtDescripcion.setDisabledTextColor(new Color(25, 112, 74));
        this.txtMarca.setDisabledTextColor(new Color(25, 112, 74));
        this.txtModelo.setDisabledTextColor(new Color(25, 112, 74));

        btnModificar.setEnabled(true);
        btnModificar.setVisible(true);
        btnSalir.setEnabled(true);
        btnSalir.setVisible(true);
        btnGenerarBanner.setEnabled(true);
        btnGenerarBanner.setVisible(true);
        btnGuardar.setEnabled(false);
        btnGuardar.setVisible(false);
        btnCancelar.setEnabled(false);
        btnCancelar.setVisible(false);
    }

    /**
     * Permite habilitar la edicion de los campos de la vista.
     */
    public void habilitarEdicion() {
        //this.txtDNI.setEnabled(true);
        this.txtDescripcion.setEnabled(true);
        this.txtMarca.setEnabled(true);
        this.txtModelo.setEnabled(true);

        this.txtDescripcion.requestFocus();

        btnModificar.setEnabled(false);
        btnModificar.setVisible(false);
        btnSalir.setEnabled(false);
        btnSalir.setVisible(false);
        btnGenerarBanner.setEnabled(false);
        btnGenerarBanner.setVisible(false);
        btnGuardar.setEnabled(true);
        btnGuardar.setVisible(true);
        btnCancelar.setEnabled(true);
        btnCancelar.setVisible(true);

    }

    /**
     * Implementa lo requerido por ActionPerformed.
     * En este caso en funcion del evento E gestiona la accion del Boton.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if ("aBotonGuardar".equals(e.getActionCommand())) {
            if (formularioCompleto()) {
                controlador.accionBotonGuardar();
                if (this.vistaPadre != null) {((VistaAdministrador) this.vistaPadre).refrescarTabla();}
            }
            else{
                Dialogos.advertencia("Por favor complete correctamente el formulario.",this);
            }
        }
        if ("aBotonModificar".equals(e.getActionCommand())) {
            controlador.accionBotonModificar();
            if (this.vistaPadre!=null){((VistaAdministrador)this.vistaPadre).refrescarTabla();}
        }
        if ("aBotonGenerarBanner".equals(e.getActionCommand())) {
            controlador.accionGenerarBanner();
        }
        if ("aBotonSalir".equals(e.getActionCommand())) {
            controlador.accionBotonSalir();
            if (this.vistaPadre!=null){((VistaAdministrador)this.vistaPadre).refrescarTabla();}
        }
        if ("aBotonCancelar".equals(e.getActionCommand())) {
            controlador.accionBotonCancelar();
            if (this.vistaPadre!=null){((VistaAdministrador)this.vistaPadre).refrescarTabla();}
        }
    }

    /**
     * Valida que el formulario se encuentre completo y con la validacion de datos definida.
     * @return
     */
    private boolean formularioCompleto(){
        boolean retorno = true;

        if(Objects.equals(this.txtDescripcion.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtMarca.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtModelo.getText(), "")){retorno = false;}

        return retorno;
    }

    //Sets and Gets
    public String getTxtMarca() {
        return txtMarca.getText();
    }

    public void setTxtMarca(String txtMarca) {
        this.txtMarca.setText(txtMarca);
    }

    public String getTxtModelo() {
        return txtModelo.getText();
    }

    public void setTxtModelo(String txtModelo) {
        this.txtModelo.setText(txtModelo);
    }

    public String getTxtDescripcion() {
        return txtDescripcion.getText();
    }

    public void setTxtDescripcion(String txtDescripcion) {
        this.txtDescripcion.setText(txtDescripcion);
    }

    /**
     * Limpia los campos del formulario y los deja en blanco.
     * Habilita la edicion del formulario.
     * Deja el focus en el primer elemento del formulario.
     */
    public void vaciarCampos(){
        txtDescripcion.setText("");
        txtMarca.setText("");
        txtModelo.setText("");

        this.habilitarEdicion();
        this.txtDescripcion.setEnabled(true);
        this.txtDescripcion.requestFocus();
    }


}