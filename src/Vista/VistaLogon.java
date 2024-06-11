package Vista;

import Controlador.ControladorLogon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaLogon extends JFrame implements ActionListener{
    private JPanel panel1;
    private JButton btnIngresar;
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JLabel lblError;

    private ControladorLogon controlador;

    public VistaLogon() {
        controlador = new ControladorLogon(this);
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
        JLabel background = new JLabel(icon);
        //background = new JLabel(new ImageIcon(ImageIO.read(new File("E:\\10.GDevelop\\Background.png"))));

        //set layout
        this.setContentPane(background);
        this.setLayout(new GridBagLayout());
        this.add(panel1);

        //definiciones de componentes
        this.lblError.setText("");
        this.lblError.setForeground(new Color(200,0,0));

        this.btnIngresar.setActionCommand("aBotonIngresar");
        this.btnIngresar.addActionListener(this);

        //definiciones generales
        this.setTitle("Acceso al Sistema");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setSize(600,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /*
    Implementa lo requerido por ActionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("aBotonIngresar".equals(e.getActionCommand())) {
            controlador.accionBotonIngresar(this.txtUsuario.getText(), this.txtClave.getText());
        }
    }
    /*
    rutinas para set de acceso correcto o incorreto
     */
    public void accesoIncorrecto(){
        this.lblError.setText("Acceso Incorrecto.");
        this.txtClave.setText("");
        this.txtUsuario.setText("");
        this.txtUsuario.requestFocus();
    }
    public void accesoCorrecto(){
        lblError.setForeground(new Color(0,0,200));
        lblError.setText("Accediendo...");
    }
    public void bloqueoUsuario(){
        lblError.setForeground(new Color(200,0,0));
        lblError.setText("Usuario Bloqueado");
    }


}