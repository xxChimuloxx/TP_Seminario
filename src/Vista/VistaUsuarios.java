package Vista;

import Controlador.ControladorUsuarioInterno;
import Controlador.ControladorUsuarios;
import Modelo.Tools;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
    private JComboBox cboTipo;
    private JComboBox cboEstado;
    private JPanel panel;
    private JPanel panel2;
    private JTextField txtTipo;
    private JTextField txtEstado;
    private JPanel panel3;

    private ControladorUsuarios controlador;
    private JFrame vistaPadre;

    public VistaUsuarios(JFrame vistaPadre,int clave) {
        initComponents();
        controlador = new ControladorUsuarios(this,clave);
        this.vistaPadre = vistaPadre;
    }

    public VistaUsuarios(JFrame vistaPadre,int clave, int modo) {
        initComponents();
        controlador = new ControladorUsuarios(this,clave,modo);
        this.vistaPadre = vistaPadre;
    }

    private void initComponents() {
        //load del icono de la herramienta
        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        this.setIconImage(image);

        //load de la imagen de background
        Image imageI = Toolkit.getDefaultToolkit().getImage("src/Recursos/Background.png");
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

    public void inhabilitarEdicion(){
        this.txtUserID.setDisabledTextColor(new Color(45, 131, 82));
        this.txtPassword.setDisabledTextColor(new Color(45, 131, 82));
        this.txtDescripcion.setDisabledTextColor(new Color(45, 131, 82));
        this.txtIntentos.setDisabledTextColor(new Color(45, 131, 82));
        this.txtTipo.setDisabledTextColor(new Color(45, 131, 82));
        this.txtEstado.setDisabledTextColor(new Color(45, 131, 82));

        this.txtUserID.setEnabled(false);
        this.txtPassword.setEnabled(false);
        this.txtDescripcion.setEnabled(false);
        this.txtIntentos.setEnabled(false);
        this.txtTipo.setEnabled(false);
        this.txtEstado.setEnabled(false);

        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        btnModificar.setVisible(true);
    }

    public void habilitarEdicion(){
        this.txtUserID.setDisabledTextColor(new Color(136, 24, 54));
        this.txtPassword.setDisabledTextColor(new Color(136, 24, 54));
        this.txtDescripcion.setDisabledTextColor(new Color(136, 24, 54));
        this.txtIntentos.setDisabledTextColor(new Color(136, 24, 54));
        this.txtTipo.setDisabledTextColor(new Color(136, 24, 54));
        this.txtEstado.setDisabledTextColor(new Color(136, 24, 54));

        //this.txtUserID.setEnabled(true);
        this.txtPassword.setEnabled(true);
        this.txtDescripcion.setEnabled(true);
        this.txtIntentos.setEnabled(true);
        this.txtTipo.setEnabled(true);
        this.txtEstado.setEnabled(true);

        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
        btnModificar.setVisible(false);

        txtPassword.requestFocus();
    }

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
                if (this.vistaPadre != null) {((VistaSeguridad) this.vistaPadre).refrescarTabla();}
            }
            else{
                Dialogos.advertencia("Por favor complete correctamente el formulario.",this);
            }
        }
        if ("aBotonModificar".equals(e.getActionCommand())) {
            controlador.accionBotonModificar();
        }
    }

    private boolean formularioCompleto(){
        boolean retorno = true;

        if(Objects.equals(this.txtUserID.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtPassword.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtDescripcion.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtTipo.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtIntentos.getText(), "")){retorno = false;}
        if(Objects.equals(this.txtEstado.getText(), "")){retorno = false;}

        if(!Tools.esNumerica(this.txtTipo.getText())){retorno = false;}
        if(!Tools.esNumerica(this.txtIntentos.getText())){retorno = false;}
        if(!Tools.esNumerica(this.txtEstado.getText())){retorno = false;}

        int valor;
        valor = Integer.valueOf(this.txtEstado.getText());
        if((valor!=1)&&(valor!=0)){retorno = false;}

        valor = Integer.valueOf(this.txtTipo.getText());
        if((valor<=0)||(valor>=6)){retorno = false;}

        valor = Integer.valueOf(this.txtIntentos.getText());
        if((valor<=0)||(valor>=4)){retorno = false;}

        return retorno;
    }

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

    public String getTxtEstado() {
        return txtEstado.getText();
    }

    public void setTxtEstado(String txtEstado) {
        this.txtEstado.setText(txtEstado);
    }

    public String getTxtTipo() {
        return txtTipo.getText();
    }

    public void setTxtTipo(String txtTipo) {
        this.txtTipo.setText(txtTipo);
    }

    public void vaciarCampos(){
        this.txtUserID.setText("");
        this.txtPassword.setText("");
        this.txtDescripcion.setText("");
        this.txtTipo.setText("");
        this.txtIntentos.setText("");
        this.txtEstado.setText("");

        this.habilitarEdicion();
        this.txtUserID.setEnabled(true);
        this.txtUserID.requestFocus();
    }
}
