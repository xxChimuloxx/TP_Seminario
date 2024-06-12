package Controlador;

import Vista.*;

/**
 * Clase de prueba para concentrar los accesos a las distintas vistas/controladores de la aplicacion.
 */
public class ControladorSelector {

    private VistaSelector vista;

    /**
     * Constructo de la clase
     * @param vista
     */
    public ControladorSelector(VistaSelector vista) {
        this.vista = vista;
    }

    /**
     * Para ejecuciones de prueba
     * @param args
     */
    public static void main(String[] args) {
        VistaSelector v = new VistaSelector();
    }

    //Controlador de acciones de los botones
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
