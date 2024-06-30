package Controlador.Raices;

import Modelo.ObjetosPersistentes.Campania;
import Modelo.CodigoQR;
import Vista.Dialogos;
import Vista.VistaCampania;
import com.google.zxing.WriterException;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Clase que se utilizar para controlar y gestionar las acciones sobre las campañas de marketing.
 */
public class ControladorCampania {
    private VistaCampania vista;

    private int ID = 1;
    private Campania campania;

    public static int MODO_EDICION = 1;
    public static int MODO_ADD = 2;

    private int modo = -1;

    /**
     * Constructor basico, para pruebas de entorno.
     * Presenta por defecto la informacion del usuario: "12345678".
     * @param vista
     * @deprecated
     */
    public ControladorCampania(VistaCampania vista) {
        this.vista = vista;
        this.campania = new Campania(ID);
        actualizarVistaDatosCampania();
    }

    /**
     * Constructor de acceso directo. Es el que se utiliza cuando accede un usuario basico.
     * @param vista
     * @param clave
     */
    public ControladorCampania(VistaCampania vista,int clave) {
        this.vista = vista;
        this.ID = clave;
        this.campania = new Campania(ID);
        actualizarVistaDatosCampania();
    }

    /**
     * Constructor de acceso indirecto. Es el que se utiliza cuando se accede a este vista desde otra vista.
     * @param vista
     * @param clave
     * @param modo
     */
    public ControladorCampania(VistaCampania vista,int clave,int modo) {
        this.vista = vista;
        this.ID = clave;
        this.campania = new Campania(ID);
        actualizarVistaDatosCampania();

        //System.out.println(modo);
        if (modo == this.MODO_EDICION){
            vista.habilitarEdicion();
        }
        if (modo == this.MODO_ADD){
            vista.vaciarCampos();
        }

        this.modo = modo;
    }

    /**
     * Permite actualizar la vista con los datos de la persona actual.
     */
    private void actualizarVistaDatosCampania() {
        this.vista.setTxtNombre(this.campania.getNombre());
        this.vista.setTxtSinopsis(this.campania.getSinopsis());
        this.vista.setTxtDescripcion(this.campania.getDescripcion());
        this.vista.setTxtFechaDesde(this.campania.getFechaDesde());
        this.vista.setTxtFechaHasta(this.campania.getFechaHasta());
        this.vista.setTxtLink(this.campania.getLink());
        if (this.campania.getVigente()==1){this.vista.setChkVigente(true);}
        else {this.vista.setChkVigente(false);}
    }

    /**
     * Para pruebas o ejecuciones directas.
     * @param args
     */
    public static void main(String[] args) {
        VistaCampania v = new VistaCampania();
    }

    /**
     * Gestiona las acciones correspondientes al BotonSalir
     */
    public void accionBotonSalir(){
        if (vista.getDefaultCloseOperation()== JFrame.EXIT_ON_CLOSE) {
            System.exit(0);
        }
        else{
            vista.dispose();
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     */
    public void accionBotonModificar(){
        vista.habilitarEdicion();
    }

    /**
     * Gestiona las acciones correspondientes al BotonCancelar
     */
    public void accionBotonCancelar(){
        vista.bloquearEdicion();
        actualizarVistaDatosCampania();
        if (this.modo != -1){
            vista.dispose();
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonGuardar
     */
    public void accionBotonGuardar(){
        if (Dialogos.confirmacionAccion()){
            vista.bloquearEdicion();

            //this.persona.setDni((int)Integer.parseInt(this.vista.getTxtDNI()));
            this.campania.setNombre(this.vista.getTxtNombre());
            this.campania.setSinopsis(this.vista.getTxtSinopsis());
            this.campania.setDescripcion(this.vista.getTxtDescripcion());
            this.campania.setFechaDesde(this.vista.getTxtFechaDesde());
            this.campania.setFechaHasta(this.vista.getTxtFechaHasta());
            this.campania.setLink(this.vista.getTxtLink());
            if(this.vista.getChkVigente()){this.campania.setVigente(1);}
            else{this.campania.setVigente(0);}

            this.campania.setId(this.ID);

            if(this.campania.existe(this.campania.getId())){
                this.campania.actualizar();
            }
            else{
                this.campania.insertar();
                //cruce contra la tabla campania, verifico.
                Campania campaniaAuxiliar = new Campania(this.ID);
                if(!campaniaAuxiliar.existe(this.ID)){
                    campaniaAuxiliar.setNombre(this.vista.getTxtNombre());
                    campaniaAuxiliar.setSinopsis(this.vista.getTxtSinopsis());
                    campaniaAuxiliar.setDescripcion(this.vista.getTxtDescripcion());
                    campaniaAuxiliar.setFechaDesde(this.vista.getTxtFechaDesde());
                    campaniaAuxiliar.setFechaHasta(this.vista.getTxtFechaHasta());
                    campaniaAuxiliar.setLink(this.vista.getTxtLink());
                    if(this.vista.getChkVigente()){campaniaAuxiliar.setVigente(1);}
                    else{campaniaAuxiliar.setVigente(0);}
                }
            }

            actualizarVistaDatosCampania();
        }
        if (this.modo != -1){
            vista.dispose();
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonGenerarBanner
     */
    public void accionGenerarBanner() {
        accionGenerarBannerQR();
        CodigoQR.registrarQR(this.campania.getId(),CodigoQR.CONSTANTE_TIPO_CAMPANIA);
    }

    /**
     * Genera un codigoQR con los datos de la persona actual, y lo muestra en pantalla.
     */
    public void accionGenerarBannerQR() {
        String link = campania.getLink();

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            link = "http://" + link;
        }

        try {
            BufferedImage qrImage = CodigoQR.generarQRCodeImage(link, 350, 350);
            BufferedImage qrImageIcon = CodigoQR.agregarIconoQRCode(qrImage,vista);
            CodigoQR.mostrarQRCodePopup(qrImageIcon);
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar el código QR: " + ex.getMessage());
        }
    }

}

