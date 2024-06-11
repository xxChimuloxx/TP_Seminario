package Controlador;

import Vista.*;

public class ControladorSelector {

    private VistaSelector vista;

    public ControladorSelector(VistaSelector vista) {
        this.vista = vista;
    }

    public static void main(String[] args) {
        VistaSelector v = new VistaSelector();
    }
    /*
    accion Botones
     */
    public void accionBotonSalir(){
        System.exit(0);
    }
    public void accionBotonLogon(){
        VistaLogon v = new VistaLogon();
    }
    public void accionBotonUsuarioInterno(){
        VistaUsuarioInterno v = new VistaUsuarioInterno();
    }
    public void accionBotonSeguridad(){
        VistaSeguridad v = new VistaSeguridad();
    }
    public void accionBotonUsuarioAdminP(){
        VistaUsuarioAdminP v = new VistaUsuarioAdminP();
    }
}
