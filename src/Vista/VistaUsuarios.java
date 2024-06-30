package Vista;

import Controlador.Raices.ControladorUsuarios;
import Modelo.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Clase que permite mostrar las acciones y operaciones de los usuarios basicos.
 */
public class VistaUsuarios extends JFrame implements ActionListener {
    private JPanel panel1;
    private JLabel lblImagen;
    private JLabel lblTitulo;
    private JTextField txtUserID;
    private JTextField txtDescripcion;
    private JTextField txtPassword;
    private JTextField txtIntentos;
    private JButton btnSalir;
    private JButton btnModificar;
    private JButton btnCancelar;
    private JButton btnGuardar;
    private JPanel panel;
    private JPanel panel2;
    private JCheckBox ckNum1;
    private JCheckBox ckNum2;
    private JCheckBox ckNum3;
    private JCheckBox ckNum4;
    private JCheckBox ckNum5;
    private JCheckBox ckActivo;

    private ControladorUsuarios controlador;
    private JFrame vistaPadre;

    /**
     * Constructor de la clase. Para acceso indirecto desde otra vista.     *
     * @param vistaPadre
     * @param clave
     */
    public VistaUsuarios(JFrame vistaPadre,int clave) {
        initComponents();
        controlador = new ControladorUsuarios(this,clave);
        this.vistaPadre = vistaPadre;
        this.lblTitulo.setBackground(new Color(255, 255, 255));
    }

    /**
     * Constructor de la clase. Para acceso indirecto desde otra vista.     *
     * modo permite determinar y habilitar solo ciertas funcionalidades de la vista.
     * @param vistaPadre
     * @param clave
     * @param modo ControladorUsuarios.MODO_EDICION o ControladorUsuarios.MODO_ADD
     * @see ControladorUsuarios
     */
    public VistaUsuarios(JFrame vistaPadre,int clave, int modo) {
        initComponents();
        controlador = new ControladorUsuarios(this,clave,modo);
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
        lblImagen.setIcon(icon);

        //set layout
        this.setContentPane(panel);

        //definiciones de componentes
        lblImagen.setForeground(new Color(255, 255, 255));
        panel.setForeground(new Color(255, 255, 255));
        panel1.setForeground(new Color(255, 255, 255));
        panel2.setForeground(new Color(255, 255, 255));
        lblImagen.setText("");

        this.btnSalir.setActionCommand("aBotonSalir");
        this.btnModificar.setActionCommand("aBotonModificar");
        this.btnCancelar.setActionCommand("aBotonCancelar");
        this.btnGuardar.setActionCommand("aBotonGuardar");
        this.btnSalir.addActionListener(this);
        this.btnModificar.addActionListener(this);
        this.btnCancelar.addActionListener(this);
        this.btnGuardar.addActionListener(this);

        inhabilitarEdicion();
        //definiciones generales
        this.setTitle("Administrador de Personas ");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Permite inhabilitar la edicion de los componentes de la vista.
     */
    public void inhabilitarEdicion(){
        this.txtUserID.setDisabledTextColor(new Color(45, 131, 82));
        this.txtPassword.setDisabledTextColor(new Color(45, 131, 82));
        this.txtDescripcion.setDisabledTextColor(new Color(45, 131, 82));
        this.txtIntentos.setDisabledTextColor(new Color(45, 131, 82));

        this.txtUserID.setEnabled(false);
        this.txtPassword.setEnabled(false);
        this.txtDescripcion.setEnabled(false);
        this.txtIntentos.setEnabled(false);

        this.ckActivo.setEnabled(false);
        this.ckNum1.setEnabled(false);
        this.ckNum2.setEnabled(false);
        this.ckNum3.setEnabled(false);
        this.ckNum4.setEnabled(false);
        this.ckNum5.setEnabled(false);

        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        btnModificar.setVisible(true);
    }

    /**
     * Permite habilitar la edicion de los componentes de la vista.
     */
    public void habilitarEdicion(){
        this.txtUserID.setDisabledTextColor(new Color(136, 24, 54));
        this.txtPassword.setDisabledTextColor(new Color(136, 24, 54));
        this.txtDescripcion.setDisabledTextColor(new Color(136, 24, 54));
        this.txtIntentos.setDisabledTextColor(new Color(136, 24, 54));

        //this.txtUserID.setEnabled(true);
        this.txtPassword.setEnabled(true);
        this.txtDescripcion.setEnabled(true);
        this.txtIntentos.setEnabled(true);

        this.ckActivo.setEnabled(true);
        this.ckNum1.setEnabled(true);
        this.ckNum2.setEnabled(true);
        this.ckNum3.setEnabled(true);
        this.ckNum4.setEnabled(true);
        this.ckNum5.setEnabled(true);

        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
        btnModificar.setVisible(false);

        txtPassword.requestFocus();
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
        if ("aBotonCancelar".equals(e.getActionCommand())) {
            controlador.accionBotonCancelar();
        }
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
        }
    }

    /**
     * Controla que el formulario este completo, y que se hayan cargado los datos conforme las validaciones definidas.
     * @return
     */
    private boolean formularioCompleto(){
        boolean retorno = true;

        if(Objects.equals(this.txtUserID.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtPassword.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtDescripcion.getText(), "")){retorno = false;}
        if(!this.ckNum1.isSelected()){retorno = false;}
        if(Objects.equals(this.txtIntentos.getText(), "")){retorno = false;}

        if(!Tools.esNumerica(this.txtIntentos.getText())){retorno = false;}

        int valor;
        valor = Integer.valueOf(this.txtIntentos.getText());
        if((valor<=-1)||(valor>=4)){retorno = false;}

        return retorno;
    }

    //Get and Sets
    public String getTxtUserID() {
        return txtUserID.getText();
    }

    public void setTxtUserID(String txtUserID) {
        this.txtUserID.setText(txtUserID);
    }

    public String getTxtIntentos() {
        return txtIntentos.getText();
    }

    public void setTxtIntentos(String txtIntentos) {
        this.txtIntentos.setText(txtIntentos);
    }

    public String getTxtPassword() {
        return txtPassword.getText();
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword.setText(txtPassword);
    }

    public String getTxtDescripcion() {
        return txtDescripcion.getText();
    }

    public void setTxtDescripcion(String txtDescripcion) {
        this.txtDescripcion.setText(txtDescripcion);
    }

    public void setTipo(int numero){
        String numeroComoCadena = String.valueOf(numero);

        // Asegurarse de que el número tiene exactamente 5 dígitos
        int num1 = Character.getNumericValue(numeroComoCadena.charAt(0));
        int num2 = Character.getNumericValue(numeroComoCadena.charAt(1));
        int num3 = Character.getNumericValue(numeroComoCadena.charAt(2));
        int num4 = Character.getNumericValue(numeroComoCadena.charAt(3));
        int num5 = Character.getNumericValue(numeroComoCadena.charAt(4));

        if(num1==1){this.ckNum1.setSelected(true);}
        else{this.ckNum1.setSelected(false);}
        if(num2==1){this.ckNum2.setSelected(true);}
        else{this.ckNum2.setSelected(false);}
        if(num3==1){this.ckNum3.setSelected(true);}
        else{this.ckNum3.setSelected(false);}
        if(num4==1){this.ckNum4.setSelected(true);}
        else{this.ckNum4.setSelected(false);}
        if(num5==1){this.ckNum5.setSelected(true);}
        else{this.ckNum5.setSelected(false);}
    }

    public void setEstado(int numero){
        if(numero==1){this.ckActivo.setSelected(true);}
        else{this.ckActivo.setSelected(false);}
    }

    public int getTipo(){
        int num1;
        int num2;
        int num3;
        int num4;
        int num5;

        if(this.ckNum1.isSelected()){num1=1;}
        else{num1=0;}
        if(this.ckNum2.isSelected()){num2=1;}
        else{num2=0;}
        if(this.ckNum3.isSelected()){num3=1;}
        else{num3=0;}
        if(this.ckNum4.isSelected()){num4=1;}
        else{num4=0;}
        if(this.ckNum5.isSelected()){num5=1;}
        else{num5=0;}

        String numeroComoCadena = String.valueOf(num1)+String.valueOf(num2)+String.valueOf(num3)+String.valueOf(num4)+String.valueOf(num5);
        return Integer.valueOf(numeroComoCadena);
    }

    public int getEstado(){
        if(this.ckActivo.isSelected()){return 1;}
        else{return 0;}
    }

    /**
     * Limpia los campos del formulario y los deja en blanco.
     * Habilita el modo edicion.
     * Hace focus en el primer campo del formualario.
     */
    public void vaciarCampos(){
        this.txtUserID.setText("");
        this.txtPassword.setText("");
        this.txtDescripcion.setText("");
        this.txtIntentos.setText("");

        this.ckNum1.setSelected(false);
        this.ckNum2.setSelected(false);
        this.ckNum3.setSelected(false);
        this.ckNum4.setSelected(false);
        this.ckNum5.setSelected(false);
        this.ckActivo.setSelected(false);

        this.habilitarEdicion();
        this.txtUserID.setEnabled(true);
        this.txtUserID.requestFocus();
    }
}
