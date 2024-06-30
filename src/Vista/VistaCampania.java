package Vista;

import Controlador.Raices.ControladorCampania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Clase que se usa para mostrar las caracteristicas de una campaña publicitaria.
 */
public class VistaCampania extends JFrame implements ActionListener {
    private JPanel panel1;
    private JTextField txtNombre;
    private JTextField txtSinopsis;
    private JTextArea  txtDescripcion;
    private JTextField txtLink;
    private JLabel lblImagen;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnGenerarBanner;
    private JButton btnSalir;
    private JButton btnModificar;
    private JCheckBox chkVigente;
    private JButton btnEditarFechaDesde;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnEditarFechaHasta;

    private ControladorCampania controlador;
    private JFrame vistaPadre;
    private boolean closeOnExit = true;

    /**
     * Para pruebas aisladas.
     * @param args
     */
    public static void main(String[] args) {
        VistaCampania v = new VistaCampania();
    }

    /**
     * Constructor basico, para pruebas de entorno.
     * @deprecated
     */
    public VistaCampania() {
        initComponents();
        controlador = new ControladorCampania(this);
    }

    /**
     * Constructor de acceso directo. Es el que se utiliza cuando accede un usuario basico.
     * @param clave
     */
    public VistaCampania(int clave) {
        initComponents();
        controlador = new ControladorCampania(this,clave);
    }

    /**
     * Constructor de acceso indirecto. Es el que se utiliza cuando lo invoco desde otra vista.
     * Mantiene la referencia a vistaPadre para poder actualizarla si corresponde.
     * @param vistaPadre
     * @param clave
     */
    public VistaCampania(JFrame vistaPadre,int clave) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorCampania(this,clave);
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
    public VistaCampania(JFrame vistaPadre,int clave, int modo) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorCampania(this,clave,modo);
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
        this.btnEditarFechaHasta.setActionCommand("aBotonEditarFechaHasta");
        this.btnEditarFechaDesde.setActionCommand("aBotonEditarFechaDesde");

        this.btnSalir.addActionListener(this);
        this.btnGenerarBanner.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnGuardar.addActionListener(this);
        this.btnCancelar.addActionListener(this);
        this.btnEditarFechaDesde.addActionListener(this);
        this.btnEditarFechaHasta.addActionListener(this);

        //definiciones de elementos
        bloquearEdicion();

        //definiciones generales
        this.setTitle("Sistema de Gestion de QR. Vista Campaña Publicitaria");
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
        this.txtNombre.setEnabled(false);
        this.txtSinopsis.setEnabled(false);
        this.txtDescripcion.setEnabled(false);
        this.chkVigente.setEnabled(false);
        this.txtLink.setEnabled(false);
        this.txtFechaDesde.setEnabled(false);
        this.txtFechaHasta.setEnabled(false);

        this.txtNombre.setDisabledTextColor(new Color(25, 112, 74));
        this.txtSinopsis.setDisabledTextColor(new Color(25, 112, 74));
        this.txtDescripcion.setDisabledTextColor(new Color(25, 112, 74));
        this.txtLink.setDisabledTextColor(new Color(25, 112, 74));
        this.txtFechaDesde.setDisabledTextColor(new Color(25, 112, 74));
        this.txtFechaHasta.setDisabledTextColor(new Color(25, 112, 74));

        btnEditarFechaDesde.setEnabled(false);
        btnEditarFechaHasta.setEnabled(false);
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
        this.txtNombre.setEnabled(true);
        this.txtSinopsis.setEnabled(true);
        this.txtDescripcion.setEnabled(true);
        this.chkVigente.setEnabled(true);
        this.txtLink.setEnabled(true);
        this.txtFechaDesde.setEnabled(false);
        this.txtFechaHasta.setEnabled(false);

        this.txtNombre.requestFocus();

        btnEditarFechaDesde.setEnabled(true);
        btnEditarFechaHasta.setEnabled(true);
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
        if ("aBotonEditarFechaDesde".equals(e.getActionCommand())) {
            String selectedDate = SelectorFechas.showDialog(this);
            if(selectedDate!=null) {
                this.txtFechaDesde.setText(selectedDate);
            }
            else{
                Dialogos.advertencia("No se selecciono ninguna fecha",this);
            }
        }
        if ("aBotonEditarFechaHasta".equals(e.getActionCommand())) {
            String selectedDate = SelectorFechas.showDialog(this);
            if(selectedDate!=null) {
                this.txtFechaHasta.setText(selectedDate);
            }
            else{
                Dialogos.advertencia("No se selecciono ninguna fecha",this);
            }
        }
    }

    /**
     * Valida que el formulario se encuentre completo y con la validacion de datos definida.
     * @return
     */
    private boolean formularioCompleto(){
        boolean retorno = true;

        if(Objects.equals(this.txtNombre.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtSinopsis.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtDescripcion.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtLink.getText(), "")){retorno = false;}
        //if(!chkVigente.isSelected()){retorno = false; }
        if(Objects.equals(this.txtFechaDesde.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtFechaHasta.getText(), "")){retorno = false;}

        return retorno;
    }

    //Sets and Gets
    public String getTxtFechaHasta() {
        return txtFechaHasta.getText();
    }

    public void setTxtFechaHasta(String txtFechaHasta) {
        this.txtFechaHasta.setText(txtFechaHasta);
    }

    public String getTxtFechaDesde() {
        return txtFechaDesde.getText();
    }

    public void setTxtFechaDesde(String txtFechaDesde) {
        this.txtFechaDesde.setText(txtFechaDesde);
    }

    public String getTxtNombre() {
        return txtNombre.getText();
    }

    public void setTxtNombre(String txtNombre) {
        this.txtNombre.setText(txtNombre);
    }

    public String getTxtSinopsis() {
        return txtSinopsis.getText();
    }

    public void setTxtSinopsis(String txtSinopsis) {
        this.txtSinopsis.setText(txtSinopsis);
    }

    public String getTxtDescripcion() {
        return txtDescripcion.getText();
    }

    public void setTxtDescripcion(String txtDescripcion) {
        this.txtDescripcion.setText(txtDescripcion);
    }

    public String getTxtLink() {
        return txtLink.getText();
    }

    public void setTxtLink(String txtLink) {
        this.txtLink.setText(txtLink);
    }

    public boolean getChkVigente() {
        return chkVigente.isSelected();
    }

    public void setChkVigente(boolean chkVigente) {
        this.chkVigente.setSelected(chkVigente);
    }

    /**
     * Limpia los campos del formulario y los deja en blanco.
     * Habilita la edicion del formulario.
     * Deja el focus en el primer elemento del formulario.
     */
    public void vaciarCampos(){
        txtNombre.setText("");
        txtSinopsis.setText("");
        txtDescripcion.setText("");
        txtFechaDesde.setText("");
        txtFechaHasta.setText("");
        txtLink.setText("");

        this.habilitarEdicion();
        this.txtNombre.setEnabled(true);
        this.txtNombre.requestFocus();
    }


}