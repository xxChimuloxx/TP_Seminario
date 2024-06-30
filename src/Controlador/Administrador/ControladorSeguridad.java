package Controlador.Administrador;

import Controlador.Raices.ControladorUsuarios;
import Modelo.ObjetosPersistentes.Persona;
import Modelo.ObjetosPersistentes.Usuario;
import Vista.*;

import javax.swing.*;

/**
 * Clase que se utiliza para gestionar las acciones y operaciones que realiza un administrador de la seguridad de la aplicacion.
 */
public class ControladorSeguridad implements ControladorInterfaz {
    private VistaAdministrador vista;
    private int CLAVE_POS = 0;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorSeguridad(VistaAdministrador vista) {
        this.vista = vista;

        this.setBotonEspecial1("Bloquear Usuario");
        this.setBotonEspecial2("Desbloquear Usuario");
    }

    /**
     * Para ejecuciones de prueba
     * @param args
     */
    public static void main(String[] args) {
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_SEGURIDAD,true);
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
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaUsuarios v = new VistaUsuarios(vista,value, ControladorUsuarios.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaUsuarios v = new VistaUsuarios(vista, value);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonDesbloquear
     * @param clave
     */
    public void accionBotonEspecial2(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
                Usuario.desbloquear(String.valueOf(value));
                vista.refrescarTabla();
            }
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonBloquear
     * @param clave
     */
    public void accionBotonEspecial1(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                int value = Integer.valueOf((String) this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS));
                Usuario.bloquear(String.valueOf(value));
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
                int value = Integer.valueOf((String) this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS));
                Persona persona = new Persona(value);
                persona.eliminar();

                Usuario usuario = new Usuario(String.valueOf(value));
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
        Usuario.listar(tblDatos);

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
    public void setBotonEspecial2(String texto){ vista.setBotonEspecial2(texto);}
}
