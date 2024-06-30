package Controlador;

import Vista.*;

import javax.swing.*;

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
        VistaSelector v = new VistaSelector(1,1,0,1,1,"Jose",-1);
    }

    //Controlador de acciones de los botones
    public void accionBotonSalir(){
        System.exit(0);
    }
    public void accionBotonUsuarioInterno(int usuario){
        VistaUsuarioInterno v = new VistaUsuarioInterno(usuario);
    }
    public void accionBotonSeguridad(){
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_SEGURIDAD,false);
    }
    public void accionBotonUsuarioAdminP(){
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_PERSONAS,false);
    }
    public void accionBotonUsuarioAdminI(){
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_ITEMS,false);
    }
    public void accionBotonUsuarioAdminC(){
        VistaAdministrador v = new VistaAdministrador(VistaAdministrador.VISTA_CAMPANIAS,false);
    }
    public void accionaBotonReportes(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportesSelector().setVisible(true);
            }
        });
    }
}
