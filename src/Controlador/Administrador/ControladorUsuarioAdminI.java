package Controlador.Administrador;

import Controlador.Raices.ControladorCampania;
import Controlador.Raices.ControladorUsuarioInterno;
import Modelo.CodigoQR;
import Modelo.ObjetosPersistentes.Item;
import Vista.Dialogos;
import Vista.VistaAdministrador;
import Vista.VistaItem;

import javax.swing.*;

/**
 * Clase que se utiliza para controlar y gestionar las acciones del usuario administrador del area de gestion de activos.
 */
public class ControladorUsuarioAdminI implements ControladorInterfaz {

    private VistaAdministrador vista;
    private int CLAVE_POS = 0;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorUsuarioAdminI(VistaAdministrador vista) {
        this.vista = vista;

        this.setBotonEspecial1("Escanear Item");
        this.setBotonEspecial2("");
    }

    /**
     * Para ejecuciones de durante la fase de pruebas.
     * @param args
     */
    public static void main(String[] args) {
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_ITEMS,true);
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
     * Gestiona las acciones correspondientes al BotonAdd
     * @param clave
     */
    public void accionBotonAdd(int clave){
        VistaItem v = new VistaItem(vista,clave, ControladorUsuarioInterno.MODO_ADD);
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaItem v = new VistaItem(vista,value, ControladorCampania.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonConsultar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaItem v = new VistaItem(vista,value);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonBorrar
     * @param clave
     */
    public void accionBotonBorrar(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
                Item item = new Item(value);
                item.eliminar();

                vista.refrescarTabla();
            }
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonEspecial1, no se usa.
     * @param clave
     */
    public void accionBotonEspecial1(int clave){
        String s = CodigoQR.ScanQR(null);
        //Dialogos.advertencia("Informacion recuperada: \n"+s,this.vista);

        String[] lineas = s.split("\n");
        String primeraLinea = lineas[0];
        String numero = primeraLinea.replaceAll("[^0-9]", "");

        VistaItem i = new VistaItem(this.vista,Integer.parseInt(numero));
    }

    /**
     * Gestiona las acciones correspondientes al BotonEspecial2, no se usa.
     * @param clave
     */
    public void accionBotonEspecial2(int clave){}

    /**
     * Fuerza la carga de la tabla de datos con el objeto de refrescar la informacion de la misma.
     * @param tblDatos
     */
    public void cargarTabla(JTable tblDatos) {
        Item.listar(tblDatos);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #1
     * @param texto
     */
    public void setBotonEspecial1(String texto){
        vista.setBotonEspecial1(texto);
        vista.setValidaBotonEspecial1(false);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #2
     * @param texto
     */
    public void setBotonEspecial2(String texto){
        vista.setBotonEspecial2(texto);
    }

}

