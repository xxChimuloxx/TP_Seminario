package Controlador.Raices;

import Modelo.CodigoQR;
import Modelo.ObjetosPersistentes.Item;
import Vista.Dialogos;
import Vista.VistaItem;
import com.google.zxing.WriterException;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControladorItem {
    private VistaItem vista;

    private int ID = 1;
    private Item item;

    public static int MODO_EDICION = 1;
    public static int MODO_ADD = 2;

    private int modo = -1;

    /**
     * Constructor basico, para pruebas de entorno.
     * Presenta por defecto la informacion del usuario: "12345678".
     * @param vista
     * @deprecated
     */
    public ControladorItem(VistaItem vista) {
        this.vista = vista;
        this.item = new Item(ID);
        actualizarVistaDatosItem();
    }

    /**
     * Constructor de acceso directo. Es el que se utiliza cuando accede un usuario basico.
     * @param vista
     * @param clave
     */
    public ControladorItem(VistaItem vista,int clave) {
        this.vista = vista;
        this.ID = clave;
        this.item = new Item(ID);
        actualizarVistaDatosItem();
    }

    /**
     * Constructor de acceso indirecto. Es el que se utiliza cuando se accede a este vista desde otra vista.
     * @param vista
     * @param clave
     * @param modo
     */
    public ControladorItem(VistaItem vista,int clave,int modo) {
        this.vista = vista;
        this.ID = clave;
        this.item = new Item(ID);
        actualizarVistaDatosItem();

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
    private void actualizarVistaDatosItem() {
        this.vista.setTxtDescripcion(this.item.getDescripcion());
        this.vista.setTxtMarca(this.item.getMarca());
        this.vista.setTxtModelo(this.item.getModelo());
    }

    /**
     * Para pruebas o ejecuciones directas.
     * @param args
     */
    public static void main(String[] args) {
        VistaItem v = new VistaItem();
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
        actualizarVistaDatosItem();
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
            this.item.setDescripcion(this.vista.getTxtDescripcion());
            this.item.setMarca(this.vista.getTxtMarca());
            this.item.setModelo(this.vista.getTxtModelo());

            this.item.setId(this.ID);

            if(this.item.existe(this.item.getId())){
                this.item.actualizar();
            }
            else{
                this.item.insertar();
                //cruce contra la tabla campania, verifico.
                Item itemAuxiliar = new Item(this.ID);
                if(!itemAuxiliar.existe(this.ID)){
                    itemAuxiliar.setDescripcion(this.vista.getTxtDescripcion());
                    itemAuxiliar.setMarca(this.vista.getTxtMarca());
                    itemAuxiliar.setModelo(this.vista.getTxtModelo());
                }
            }

            actualizarVistaDatosItem();
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
        CodigoQR.registrarQR(this.item.getId(),CodigoQR.CONSTANTE_TIPO_ITEM);
    }

    /**
     * Genera un codigoQR con los datos de la persona actual, y lo muestra en pantalla.
     */
    public void accionGenerarBannerQR() {
        String text = "ID" + this.item.getId() + "\n" +
                "Descripcion: " + this.item.getDescripcion() + "\n" +
                "Marca: " + this.item.getMarca() + "\n" +
                "Modelo: " + this.item.getModelo();

        try {
            BufferedImage qrImage = CodigoQR.generarQRCodeImage(text, 350, 350);
            BufferedImage qrImageIcon = CodigoQR.agregarIconoQRCode(qrImage,vista);
            CodigoQR.mostrarQRCodePopup(qrImageIcon);
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar el código QR: " + ex.getMessage());
        }
    }

}
