package Controlador;

import Modelo.Usuario;
import Vista.Dialogos;
import Vista.VistaUsuarioInterno;

import Modelo.Persona;
import Modelo.CodigoQR;
import com.google.zxing.WriterException;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControladorUsuarioInterno {
    private VistaUsuarioInterno vista;

    private int userID = 12345678;
    private Persona persona;

    public static int MODO_EDICION = 1;
    public static int MODO_ADD = 2;

    private int modo = -1;

    public ControladorUsuarioInterno(VistaUsuarioInterno vista) {
        this.vista = vista;
        this.persona = new Persona(userID);
        actualizarVistaDatosPersona();
    }
    public ControladorUsuarioInterno(VistaUsuarioInterno vista,int clave) {
        this.vista = vista;
        this.userID = clave;
        this.persona = new Persona(userID);
        actualizarVistaDatosPersona();
    }
    public ControladorUsuarioInterno(VistaUsuarioInterno vista,int clave,int modo) {
        this.vista = vista;
        this.userID = clave;
        this.persona = new Persona(userID);
        actualizarVistaDatosPersona();

        System.out.println(modo);
        if (modo == this.MODO_EDICION){
            vista.habilitarEdicion();
        }
        if (modo == this.MODO_ADD){
            vista.vaciarCampos();
        }

        this.modo = modo;
    }

    private void actualizarVistaDatosPersona() {
        this.vista.setTxtDNI(String.valueOf(this.persona.getDni()));
        this.vista.setTxtNombre(this.persona.getNombre());
        this.vista.setTxtApellido(this.persona.getApellido());
        this.vista.setTxtLegajo(String.valueOf(this.persona.getLegajo()));
        this.vista.setTxtCorreo(this.persona.getCorreo());
        this.vista.setTxtTelefono(this.persona.getTelefono());
        this.vista.setTxtEquipo(this.persona.getEquipo());
        this.vista.setTxtArea(this.persona.getArea());
        this.vista.setTxtGerencia(this.persona.getGerencia());
    }

    public static void main(String[] args) {
        VistaUsuarioInterno v = new VistaUsuarioInterno();
    }
    /*
    accion Botones
     */
    public void accionBotonSalir(){
        if (vista.getDefaultCloseOperation()==JFrame.EXIT_ON_CLOSE) {
            System.exit(0);
        }
        else{
            vista.dispose();
        }
    }
    public void accionBotonModificar(){
        vista.habilitarEdicion();
    }
    public void accionBotonCancelar(){
        vista.bloquearEdicion();
        actualizarVistaDatosPersona();
        if (this.modo != -1){
            vista.dispose();
        }
    }
    public void accionBotonGuardar(){
        if (Dialogos.confirmacionAccion()){
            vista.bloquearEdicion();

            //this.persona.setDni((int)Integer.parseInt(this.vista.getTxtDNI()));
            this.persona.setNombre(this.vista.getTxtNombre());
            this.persona.setApellido(this.vista.getTxtApellido());
            this.persona.setLegajo((int)Integer.parseInt(this.vista.getTxtLegajo()));
            this.persona.setCorreo(this.vista.getTxtCorreo());
            this.persona.setTelefono(this.vista.getTxtTelefono());
            this.persona.setEquipo(this.vista.getTxtEquipo());
            this.persona.setArea(this.vista.getTxtArea());
            this.persona.setGerencia(this.vista.getTxtGerencia());

            this.persona.setDni((int)Integer.parseInt(this.vista.getTxtDNI()));
            if(this.persona.existePersona(this.persona.getDni())){
                this.persona.actualizar();
            }
            else{
                this.persona.insertar();
                //cruce contra la tabla usuario, verifico.
                Usuario usuarioAuxiliar = new Usuario(String.valueOf(this.persona.getDni()));
                if(!usuarioAuxiliar.existeUsuario()){
                    usuarioAuxiliar.setUserID(String.valueOf(this.persona.getDni()));
                    usuarioAuxiliar.setPassword(String.valueOf(this.persona.getDni()));
                    usuarioAuxiliar.setDescripcion(this.persona.getNombre()+" "+this.persona.getApellido());
                    usuarioAuxiliar.setTipo(ControladorLogon.USUARIO_BASICO);
                    usuarioAuxiliar.setIntentos(0);
                    usuarioAuxiliar.setEstado(1);
                    usuarioAuxiliar.insertar();
                }
            }

            actualizarVistaDatosPersona();
        }
        if (this.modo != -1){
            vista.dispose();
        }
    }

    public void accionGenerarBanner() {

        String[] options = {"Generar Banner", "Generar Codigo QR"};
        int choice = JOptionPane.showOptionDialog(null, "Elige una opción:", "Diálogo de Opciones",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
        case 0:
            option1Selected();
            break;
        case 1:
            option2Selected();
            break;
        default:
            // Si el usuario cierra el diálogo sin seleccionar ninguna opción
            System.out.println("Ninguna opción seleccionada.");
        }
        CodigoQR.registrarQR(this.persona.getDni(),CodigoQR.CONSTANTE_TIPO_PERSONA);
    }

    private void option1Selected() {
        //System.out.println("Opción 2 seleccionada.");
        // Llama a la función correspondiente para la opción 2
        accionGenerarBannerCB();
    }

    private void option2Selected() {
        //System.out.println("Opción 1 seleccionada.");
        // Llama a la función correspondiente para la opción 1
        accionGenerarBannerQR();
    }

    /**
     * muestra QR
     */
    public void accionGenerarBannerQR() {
        String text = "DNI: " + persona.getDni() + "\n" +
                "Nombre: " + persona.getNombre() + "\n" +
                "Apellido: " + persona.getApellido() + "\n" +
                "Legajo: " + persona.getLegajo() + "\n" +
                "Correo Electrónico: " + persona.getCorreo() + "\n" +
                "Teléfono: " + persona.getTelefono() + "\n" +
                "Equipo de Trabajo: " + persona.getEquipo() + "\n" +
                "Área: " + persona.getArea() + "\n" +
                "Gerencia: " + persona.getGerencia();
        try {
            BufferedImage qrImage = CodigoQR.generarCodeImage(text, 350, 350);
            CodigoQR.mostrarQRCodePopup(qrImage);
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar el código QR: " + ex.getMessage());
        }
    }

    /**
     * muestra banner con datos, logo y qr
     */
    public void accionGenerarBannerCB() {
        //String text = "Hola Mundo";
        String qrContent = "DNI: " + persona.getDni() + "\n" +
                "Nombre: " + persona.getNombre() + "\n" +
                "Apellido: " + persona.getApellido() + "\n" +
                "Legajo: " + persona.getLegajo() + "\n" +
                "Correo Electrónico: " + persona.getCorreo() + "\n" +
                "Teléfono: " + persona.getTelefono() + "\n" +
                "Equipo de Trabajo: " + persona.getEquipo() + "\n" +
                "Área: " + persona.getArea() + "\n" +
                "Gerencia: " + persona.getGerencia();
        try {
            BufferedImage qrImage = CodigoQR.generarCodeImage(qrContent, 350, 150);
            BufferedImage combinedImage = CodigoQR.combineImages(CodigoQR.crearTablaDatos(String.valueOf(persona.getDni()), persona.getNombre(), persona.getApellido(), String.valueOf(persona.getLegajo()), persona.getCorreo(), persona.getTelefono(), persona.getEquipo(), persona.getArea(), persona.getGerencia()), qrImage);
            CodigoQR.mostrarQRCodePopup(combinedImage);
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar la imagen: " + ex.getMessage());
        }
    }

}
