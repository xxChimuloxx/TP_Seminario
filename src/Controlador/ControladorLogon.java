package Controlador;

import Modelo.Usuario;
import Vista.VistaLogon;
import Vista.VistaSeguridad;
import Vista.VistaUsuarioAdminP;
import Vista.VistaUsuarioInterno;

public class ControladorLogon {

    private VistaLogon vista;

    public static int USUARIO_BASICO = 1;
    public static int USUARIO_ADMIN_PERSONAS = 2;
    public static int USUARIO_ADMIN_ITEMS = 3;
    public static int USUARIO_ADMIN_CAMPANIA = 4;
    public static int USUARIO_ADMIN_SEGURIDAD = 5;

    public ControladorLogon(VistaLogon vista) {
        this.vista = vista;
    }

    public static void main(String[] args) {
        VistaLogon v = new VistaLogon();
    }

    /*
    accion BotonIngresar
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
    /*
    validarAcceso
    verifica que las credenciales ingresadas en la vista sean correctas.
     */
    private boolean validarAcceso(String usuario,String clave){
        return Usuario.probarIngreso(usuario,clave);
    }
    private void penalizarAcceso(String usuario){
        if (Usuario.penalizar(usuario)>=3){
            Usuario.bloquear(usuario);
            vista.bloqueoUsuario();
        }
    }
}
