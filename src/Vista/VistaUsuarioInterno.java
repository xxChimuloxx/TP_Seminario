package Vista;

import Controlador.ControladorUsuarioInterno;
import Modelo.Tools;
import Vista.Dialogos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VistaUsuarioInterno extends JFrame implements ActionListener {
    private JButton btnSalir;
    private JButton btnModificar;
    private JButton btnGenerarBanner;
    private JTextField txtDNI;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtLegajo;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtEquipo;
    private JTextField txtArea;
    private JTextField txtGerencia;
    private JButton btnGuardar;
    private JPanel panel1;
    private JLabel lblImagen;
    private JButton btnCancelar;

    private ControladorUsuarioInterno controlador;
    private JFrame vistaPadre;
    private boolean closeOnExit = true;

    public VistaUsuarioInterno() {
        initComponents();
        controlador = new ControladorUsuarioInterno(this);
    }

    public VistaUsuarioInterno(int clave) {
        initComponents();
        controlador = new ControladorUsuarioInterno(this,clave);
    }

    public VistaUsuarioInterno(JFrame vistaPadre,int clave) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorUsuarioInterno(this,clave);
        this.vistaPadre = vistaPadre;
    }

    public VistaUsuarioInterno(JFrame vistaPadre,int clave, int modo) {
        this.closeOnExit=false;
        initComponents();
        controlador = new ControladorUsuarioInterno(this,clave,modo);
        this.vistaPadre = vistaPadre;
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
        JLabel background = new JLabel(icon);
        lblImagen.setText("");
        lblImagen.setIcon(icon);
        //background = new JLabel(new ImageIcon(ImageIO.read(new File("E:\\10.GDevelop\\Background.png"))));

        //set layout
        //this.setContentPane(background);
        this.setContentPane(panel1);
        panel1.setBackground(new Color(255,255,255));
        //this.setLayout(new GridLayout());
        //this.add(panel1);

        //definiciones de elementos
        bloquearEdicion();

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

        //definiciones generales
        this.setTitle("Sistema de Gestion de QR. Vista Usuario Interno");
        if (this.closeOnExit){this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
        else{this.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));}
        this.pack();
        //this.setSize(600,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void bloquearEdicion() {
        this.txtDNI.setEnabled(false);
        this.txtNombre.setEnabled(false);
        this.txtApellido.setEnabled(false);
        this.txtLegajo.setEnabled(false);
        this.txtCorreo.setEnabled(false);
        this.txtTelefono.setEnabled(false);
        this.txtEquipo.setEnabled(false);
        this.txtArea.setEnabled(false);
        this.txtGerencia.setEnabled(false);

        this.txtDNI.setDisabledTextColor(new Color(25, 112, 74));
        this.txtNombre.setDisabledTextColor(new Color(25, 112, 74));
        this.txtApellido.setDisabledTextColor(new Color(25, 112, 74));
        this.txtLegajo.setDisabledTextColor(new Color(25, 112, 74));
        this.txtCorreo.setDisabledTextColor(new Color(25, 112, 74));
        this.txtTelefono.setDisabledTextColor(new Color(25, 112, 74));
        this.txtEquipo.setDisabledTextColor(new Color(25, 112, 74));
        this.txtArea.setDisabledTextColor(new Color(25, 112, 74));
        this.txtGerencia.setDisabledTextColor(new Color(25, 112, 74));

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
    public void habilitarEdicion() {
        //this.txtDNI.setEnabled(true);
        this.txtNombre.setEnabled(true);
        this.txtApellido.setEnabled(true);
        this.txtLegajo.setEnabled(true);
        this.txtCorreo.setEnabled(true);
        this.txtTelefono.setEnabled(true);
        this.txtEquipo.setEnabled(true);
        this.txtArea.setEnabled(true);
        this.txtGerencia.setEnabled(true);

        this.txtNombre.requestFocus();

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

    public void actionPerformed(ActionEvent e) {
        if ("aBotonGuardar".equals(e.getActionCommand())) {
            if (formularioCompleto()) {
                controlador.accionBotonGuardar();
                if (this.vistaPadre != null) {((VistaUsuarioAdminP) this.vistaPadre).refrescarTabla();}
            }
            else{
                Dialogos.advertencia("Por favor complete correctamente el formulario.",this);
            }
        }
        if ("aBotonModificar".equals(e.getActionCommand())) {
            controlador.accionBotonModificar();
            if (this.vistaPadre!=null){((VistaUsuarioAdminP)this.vistaPadre).refrescarTabla();}
        }
        if ("aBotonGenerarBanner".equals(e.getActionCommand())) {
            controlador.accionGenerarBanner();
        }
        if ("aBotonSalir".equals(e.getActionCommand())) {
            controlador.accionBotonSalir();
            if (this.vistaPadre!=null){((VistaUsuarioAdminP)this.vistaPadre).refrescarTabla();}
        }
        if ("aBotonCancelar".equals(e.getActionCommand())) {
            controlador.accionBotonCancelar();
            if (this.vistaPadre!=null){((VistaUsuarioAdminP)this.vistaPadre).refrescarTabla();}
        }
    }

    private boolean formularioCompleto(){
        boolean retorno = true;

        if(Objects.equals(this.txtDNI.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtNombre.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtApellido.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtLegajo.getText(), "")){retorno = false;}

        if(!Tools.esNumerica(this.txtDNI.getText())){retorno = false;}
        if(!Tools.esNumerica(this.txtLegajo.getText())){retorno = false;}

        return retorno;
    }

    /*
    Set and Gets
     */

    public String getTxtDNI() {
        return txtDNI.getText();
    }

    public void setTxtDNI(String txtDNI) {
        this.txtDNI.setText(txtDNI);
    }

    public String getTxtNombre() {
        return txtNombre.getText();
    }

    public void setTxtNombre(String txtNombre) {
        this.txtNombre.setText(txtNombre);
    }

    public String getTxtApellido() {
        return txtApellido.getText();
    }

    public void setTxtApellido(String txtApellido) {
        this.txtApellido.setText(txtApellido);
    }

    public String getTxtLegajo() {
        return txtLegajo.getText();
    }

    public void setTxtLegajo(String txtLegajo) {
        this.txtLegajo.setText(txtLegajo);
    }

    public String getTxtCorreo() {
        return txtCorreo.getText();
    }

    public void setTxtCorreo(String txtCorreo) {
        this.txtCorreo.setText(txtCorreo);
    }

    public String getTxtTelefono() {
        return txtTelefono.getText();
    }

    public void setTxtTelefono(String txtTelefono) {
        this.txtTelefono.setText(txtTelefono);
    }

    public String getTxtEquipo() {
        return txtEquipo.getText();
    }

    public void setTxtEquipo(String txtEquipo) {
        this.txtEquipo.setText(txtEquipo);
    }

    public String getTxtArea() {
        return txtArea.getText();
    }

    public void setTxtArea(String txtArea) {
        this.txtArea.setText(txtArea);
    }

    public String getTxtGerencia() {
        return txtGerencia.getText();
    }

    public void setTxtGerencia(String txtGerencia) {
        this.txtGerencia.setText(txtGerencia);
    }

    public void vaciarCampos(){
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtLegajo.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtEquipo.setText("");
        txtArea.setText("");
        txtGerencia.setText("");

        this.habilitarEdicion();
        this.txtDNI.setEnabled(true);
        this.txtDNI.requestFocus();
    }
}
