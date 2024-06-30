package Controlador.Raices;

import Modelo.ObjetosPersistentes.Persona;
import Modelo.ObjetosPersistentes.Usuario;
import Vista.*;

/**
 * Clase que permite gestionar las acciones y operaciones de los usuarios basicos.
 */
public class ControladorUsuarios {

    private VistaUsuarios vista;
    private Usuario usuario;

    public static int USUARIO_BASICO = 1;
    public static int USUARIO_ADMIN_PERSONAS = 2;
    public static int USUARIO_ADMIN_ITEMS = 3;
    public static int USUARIO_ADMIN_CAMPANIA = 4;
    public static int USUARIO_ADMIN_SEGURIDAD = 5;

    public static int MODO_EDICION = 1;
    public static int MODO_ADD = 2;

    private int modo = -1;

    /**
     * Constructor de la clase. Acceso indirecto a traves de otra vista.
     * @param vista
     * @param clave
     */
    public ControladorUsuarios(VistaUsuarios vista,int clave) {
        this.vista = vista;

        this.usuario = new Usuario(String.valueOf(clave));
        actualizarVistaDatosUsuario();
    }

    /**
     * Constructor de la clase. Acceso indirecto a traves de otra vista.
     * Modo determina funciones acotadas al funcionamiento normal.
     * @param vista
     * @param clave
     * @param modo ControladorUsuarios.MODO_EDICION o ControladorUsuarios.MODO_ADD
     */
    public ControladorUsuarios(VistaUsuarios vista,int clave,int modo) {
        this.vista = vista;
        this.usuario = new Usuario(String.valueOf(clave));
        if(clave!=-1){actualizarVistaDatosUsuario();}

        //System.out.println(modo);
        if (modo == this.MODO_EDICION){
            vista.habilitarEdicion();
        }
        if (modo == this.MODO_ADD){
            vista.vaciarCampos();
        }

        this.modo = modo;
    }

    /**
     * Para ejecuciones y pruebas de funcionamiento.
     * @param args
     */
    public static void main(String[] args) {
        VistaUsuarios v = new VistaUsuarios(null, 12345003);
    }

    /**
     * Gestiona las acciones correspondientes al BotonSalir
     */
    public void accionBotonSalir(){
        this.vista.dispose();
    }

    /**
     * Gestiona las acciones correspondientes al BotonGuardar
     */
    public void accionBotonGuardar(){
        if (Dialogos.confirmacionAccion()){
            this.vista.inhabilitarEdicion();

            this.usuario.setUserID(this.vista.getTxtUserID());
            this.usuario.setPassword(this.vista.getTxtPassword());
            this.usuario.setDescripcion(this.vista.getTxtDescripcion());
            this.usuario.setTipo(this.vista.getTipo());
            this.usuario.setIntentos(Integer.valueOf(this.vista.getTxtIntentos()));
            this.usuario.setEstado(this.vista.getEstado());

            if(this.usuario.existe()){
                this.usuario.actualizar();
            }
            else{
                this.usuario.insertar();
                //cruce contra la tabla usuario, verifico.
                Persona personaAuxiliar = new Persona(Integer.valueOf(this.usuario.getUserID()));
                if(!personaAuxiliar.existe(Integer.valueOf(this.usuario.getUserID()))){
                    personaAuxiliar.setDni(Integer.valueOf(this.usuario.getUserID()));
                    personaAuxiliar.setNombre("a completar");
                    personaAuxiliar.setApellido("a completar");
                    personaAuxiliar.setLegajo(-1);
                    personaAuxiliar.setCorreo("a completar");
                    personaAuxiliar.setTelefono("a completar");
                    personaAuxiliar.setEquipo("");
                    personaAuxiliar.setArea("");
                    personaAuxiliar.setGerencia("");
                    personaAuxiliar.insertar();
                }
            }
            actualizarVistaDatosUsuario();
        }
    }

    /**
     * Gestiona las acciones correspondientes al BotonModificar
     */
    public void accionBotonModificar(){
        this.vista.habilitarEdicion();
    }

    /**
     * Gestiona las acciones correspondientes al BotonCancelar
     */
    public void accionBotonCancelar(){
        this.vista.dispose();
    }

    /**
     * Fuerza la actualizacion de los datos de la vista, con los datos actuales del usuario.
     */
    private void actualizarVistaDatosUsuario() {
        this.vista.setTxtUserID(this.usuario.getUserID());
        this.vista.setTxtPassword(this.usuario.getPassword());
        this.vista.setTxtDescripcion(this.usuario.getDescripcion());
        this.vista.setTxtIntentos(String.valueOf(this.usuario.getIntentos()));

        this.vista.setEstado(this.usuario.getEstado());
        this.vista.setTipo(this.usuario.getTipo());

    }
}

