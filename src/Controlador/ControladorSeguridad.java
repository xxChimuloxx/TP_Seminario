package Controlador;

import Modelo.Persona;
import Modelo.Usuario;
import Vista.*;

import javax.swing.*;

public class ControladorSeguridad {
    private VistaSeguridad vista;

    public ControladorSeguridad(VistaSeguridad vista) {
        this.vista = vista;
    }

    public static void main(String[] args) {
        VistaSeguridad v = new VistaSeguridad();
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
    public void accionBotonAdd(int clave){
        VistaUsuarios v = new VistaUsuarios(vista,clave, ControladorUsuarios.MODO_ADD);
    }
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            VistaUsuarios v = new VistaUsuarios(vista,clave, ControladorUsuarios.MODO_EDICION);
        }
    }
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            VistaUsuarios v = new VistaUsuarios(vista, clave);
        }
    }

    public void accionBotonDesbloquear(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                Usuario.desbloquear(String.valueOf(clave));
                vista.refrescarTabla();
            }
        }
    }

    public void accionBotonBloquear(int clave){
        if(clave!=-1) {
            if(Dialogos.confirmacionAccion()) {
                Usuario.bloquear(String.valueOf(clave));
                vista.refrescarTabla();
            }
        }
    }

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

    public void cargarTabla(JTable tblDatos) {
        Usuario.listarPersonas(tblDatos);

    }
}
