package Controlador;

import Modelo.Usuario;
import Vista.*;

import java.nio.channels.Selector;

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

            int tipo = lusuario.getTipo();
            int user = Integer.parseInt(lusuario.getUserID());
            String descripcion = lusuario.getDescripcion();

            String numeroComoCadena = String.valueOf(tipo);

            // Asegurarse de que el número tiene exactamente 5 dígitos
            int num1 = Character.getNumericValue(numeroComoCadena.charAt(0));
            int num2 = Character.getNumericValue(numeroComoCadena.charAt(1));
            int num3 = Character.getNumericValue(numeroComoCadena.charAt(2));
            int num4 = Character.getNumericValue(numeroComoCadena.charAt(3));
            int num5 = Character.getNumericValue(numeroComoCadena.charAt(4));

            String nombre = lusuario.getDescripcion();
            this.vista.setVisible(false);
            VistaSelector v = new VistaSelector(num1,num2,num3,num4,num5,descripcion,user);
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
