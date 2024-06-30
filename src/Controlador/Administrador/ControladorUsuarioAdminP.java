package Controlador.Administrador;

import Controlador.Raices.ControladorUsuarioInterno;
import Modelo.Persona;
import Modelo.Usuario;
import Vista.*;

import javax.swing.*;

/**
 * Clase que se utiliza para gestionar las acciones que realicen los usuarios administradores de personas.
 */
public class ControladorUsuarioAdminP implements ControladorInterfaz {

    private VistaAdministrador vista;
    private int CLAVE_POS = 1;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorUsuarioAdminP(VistaAdministrador vista) {
        this.vista = vista;

        this.setBotonEspecial1("");
        this.setBotonEspecial2("");
    }

    /**
     * Para ejecuciones de durante la fase de pruebas.
     * @param args
     */
    public static void main(String[] args) {
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_PERSONAS,true);
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
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,value, ControladorUsuarioInterno.MODO_EDICION);
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonConsultar
     * @param clave
     */
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            int value = Integer.parseInt(this.vista.getTableModel().getValueAt(clave,this.CLAVE_POS).toString());
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,value);
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
                Persona persona = new Persona(value);
                persona.eliminar();

                Usuario usuario = new Usuario(String.valueOf(value));
                usuario.eliminar();

                vista.refrescarTabla();
            }
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonEspecial1, no se usa.
     * @param clave
     */
    public void accionBotonEspecial1(int clave){}

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
        Persona.listarPersonas(tblDatos);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #1
     * @param texto
     */
    public void setBotonEspecial1(String texto){
        vista.setBotonEspecial1(texto);
    }

    /**
     * Determina si sera necesario utilizar el boton especial #1
     * @param texto
     */
    public void setBotonEspecial2(String texto){
        vista.setBotonEspecial2(texto);
    }       
}
