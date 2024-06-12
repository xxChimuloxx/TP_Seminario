package Controlador;

import Modelo.Usuario;
import Vista.VistaLogon;
import Vista.VistaSeguridad;
import Vista.VistaUsuarioAdminP;
import Vista.VistaUsuarioInterno;

/**
 * Clase utilizada para controlar las operaciones y acciones del proceso de logon al aplicativo.
 */
public class ControladorLogon {

    private VistaLogon vista;

    public static int USUARIO_BASICO = 1;
    public static int USUARIO_ADMIN_PERSONAS = 2;
    public static int USUARIO_ADMIN_ITEMS = 3;
    public static int USUARIO_ADMIN_CAMPANIA = 4;
    public static int USUARIO_ADMIN_SEGURIDAD = 5;

    /**
     * Constructor de la clase
     * @param vista
     */
    public ControladorLogon(VistaLogon vista) {
        this.vista = vista;
    }

    /**
     * Para ejecuciones de prueba
     * @param args
     */
    public static void main(String[] args) {
        VistaLogon v = new VistaLogon();
    }

    /**
     * Gestiona las acciones correspondientes al BotonIngresar
     * @param usuario
     * @param clave
     */
    public void accionBotonIngresar(String usuario, String clave){
        if (!validarAcceso(usuario,clave)){
            vista.accesoIncorrecto();
            this.penalizarAcceso(usuario);
        }
        else{
            vista.accesoCorrecto();
            //VALIDAR ACCESO
            Usuario lusuario = new Usuario(clave);
            if(lusuario.getTipo()==ControladorLogon.USUARIO_BASICO){
                VistaUsuarioInterno v = new VistaUsuarioInterno(Integer.parseInt(clave));
            }
            if(lusuario.getTipo()==ControladorLogon.USUARIO_ADMIN_PERSONAS){
                VistaUsuarioAdminP v = new VistaUsuarioAdminP();
            }
            if(lusuario.getTipo()==ControladorLogon.USUARIO_ADMIN_SEGURIDAD){
                VistaSeguridad v = new VistaSeguridad();
            }

        }
    }

    /**
     * Verifica que las credenciales ingresadas en la vista sean correctas.
     * @param usuario
     * @param clave
     * @return
     */
    private boolean validarAcceso(String usuario,String clave){
        return Usuario.probarIngreso(usuario,clave);
    }

    /**
     * Penaliza el acceso para el usuario que se recibe como parametro.
     * Fallas maximas toleradas 3.
     * @param usuario
     */
    private void penalizarAcceso(String usuario){
        if (Usuario.penalizar(usuario)>=3){
            Usuario.bloquear(usuario);
            vista.bloqueoUsuario();
        }
    }
}
