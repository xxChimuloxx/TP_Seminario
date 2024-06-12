package Controlador;

import Modelo.Persona;
import Modelo.Usuario;
import Vista.*;

import javax.swing.*;

/**
 * Clase que se utiliza para gestionar las acciones y operaciones que realiza un administrador de la seguridad de la aplicacion.
 */
public class ControladorSeguridad {
    private VistaSeguridad vista;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorSeguridad(VistaSeguridad vista) {
        this.vista = vista;
    }

    /**
     * Para ejecuciones de prueba
     * @param args
     */
    public static void main(String[] args) {
        VistaSeguridad v = new VistaSeguridad();
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
        VistaUsuarios v = new VistaUsuarios(vista,clave, ControladorUsuarios.MODO_ADD);
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            VistaUsuarios v = new VistaUsuarios(vista,clave, ControladorUsuarios.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            VistaUsuarios v = new VistaUsuarios(vista, clave);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonDesbloquear
     * @param clave
     */
    public void accionBotonDesbloquear(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                Usuario.desbloquear(String.valueOf(clave));
                vista.refrescarTabla();
            }
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonBloquear
     * @param clave
     */
    public void accionBotonBloquear(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                Usuario.bloquear(String.valueOf(clave));
                vista.refrescarTabla();
            }
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
     * Realiza la carga de datos de la tabla que se recibe como parametro en funcion de los datos del modelo.
     * @param tblDatos
     */
    public void cargarTabla(JTable tblDatos) {
        Usuario.listarPersonas(tblDatos);

    }
}
