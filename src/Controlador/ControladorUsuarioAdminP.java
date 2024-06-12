package Controlador;

import Modelo.Persona;
import Modelo.Usuario;
import Vista.Dialogos;
import Vista.VistaLogon;
import Vista.VistaUsuarioAdminP;
import Vista.VistaUsuarioInterno;

import javax.swing.*;

/**
 * Clase que se utiliza para gestionar las acciones que realicen los usuarios administradores de personas.
 */
public class ControladorUsuarioAdminP {

    private VistaUsuarioAdminP vista;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorUsuarioAdminP(VistaUsuarioAdminP vista) {
        this.vista = vista;
    }

    /**
     * Para ejecuciones de durante la fase de pruebas.
     * @param args
     */
    public static void main(String[] args) {
        VistaUsuarioAdminP v = new VistaUsuarioAdminP();
    }

    /**
     * Gestiona las acciones correspondientes al BotonSalir
     */
    public void accionBotonSalir(){
        if (vista.getDefaultCloseOperation()==JFrame.EXIT_ON_CLOSE) {
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
        VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave, ControladorUsuarioInterno.MODO_ADD);
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave, ControladorUsuarioInterno.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonConsultar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonBorrar
     * @param clave
     */
    public void accionBotonBorrar(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                Persona persona = new Persona(clave);
                persona.eliminar();

                Usuario usuario = new Usuario(String.valueOf(clave));
                usuario.eliminar();

                vista.refrescarTabla();
            }
        }
    }

    /**
     * Fuerza la carga de la tabla de datos con el objeto de refrescar la informacion de la misma.
     * @param tblDatos
     */
    public void cargarTabla(JTable tblDatos) {
        Persona.listarPersonas(tblDatos);
    }
}
