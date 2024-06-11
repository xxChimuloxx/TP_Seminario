package Controlador;

import Modelo.Persona;
import Modelo.Usuario;
import Vista.Dialogos;
import Vista.VistaLogon;
import Vista.VistaUsuarioAdminP;
import Vista.VistaUsuarioInterno;

import javax.swing.*;

public class ControladorUsuarioAdminP {

    private VistaUsuarioAdminP vista;

    public ControladorUsuarioAdminP(VistaUsuarioAdminP vista) {
        this.vista = vista;
    }

    public static void main(String[] args) {
        VistaUsuarioAdminP v = new VistaUsuarioAdminP();
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
        VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave, ControladorUsuarioInterno.MODO_ADD);
    }
    public void accionBotonModificar(int clave){
        if(clave!=-1) {
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave, ControladorUsuarioInterno.MODO_EDICION);
        }
    }
    public void accionBotonConsultar(int clave){
        if(clave!=-1) {
            VistaUsuarioInterno v = new VistaUsuarioInterno(vista,clave);
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
        Persona.listarPersonas(tblDatos);
    }
}
