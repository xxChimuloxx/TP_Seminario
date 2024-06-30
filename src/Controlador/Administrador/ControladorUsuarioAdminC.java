package Controlador.Administrador;

import Controlador.Raices.ControladorCampania;
import Controlador.Raices.ControladorUsuarioInterno;
import Modelo.ObjetosPersistentes.Campania;
import Vista.Dialogos;
import Vista.VistaAdministrador;
import Vista.VistaCampania;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Clase que se utiliza para gestionar y controlar las acciones de los usuarios administradores del area de Marketing.
 */
public class ControladorUsuarioAdminC implements ControladorInterfaz {

    private VistaAdministrador vista;
    private int CLAVE_POS = 0;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorUsuarioAdminC(VistaAdministrador vista) {
        this.vista = vista;

        this.setBotonEspecial1("Probar Link");
        this.setBotonEspecial2("");
    }

    /**
     * Para ejecuciones de durante la fase de pruebas.
     * @param args
     */
    public static void main(String[] args) {
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_CAMPANIAS,true);
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
        VistaCampania v = new VistaCampania(vista,clave, ControladorUsuarioInterno.MODO_ADD);
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaCampania v = new VistaCampania(vista,value, ControladorCampania.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonConsultar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaCampania v = new VistaCampania(vista,value);
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
                Campania campania = new Campania(value);
                campania.eliminar();

                vista.refrescarTabla();
            }
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonEspecial1, no se usa.
     * @param clave
     */
    public void accionBotonEspecial1(int clave){
        if(clave!=-1) {
            //nivelo clave (posicion en la lista) con ID.
            clave+=1;
            Campania campania = new Campania(clave);

            //System.out.println(campania.getLink());
            try {
                Desktop.getDesktop().browse(new java.net.URI(campania.getLink()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                Dialogos.advertencia("No se puede abrir el link solicitado.", vista);
                //throw new RuntimeException(e);
            }
        }
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
        Campania.listar(tblDatos);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #1
     * @param texto
     */
    public void setBotonEspecial1(String texto){
        vista.setBotonEspecial1(texto);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #2
     * @param texto
     */
    public void setBotonEspecial2(String texto){
        vista.setBotonEspecial2(texto);
    }
}
