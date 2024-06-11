package Controlador;

import Modelo.Persona;
import Modelo.Usuario;
import Vista.*;

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

    public ControladorUsuarios(VistaUsuarios vista,int clave) {
        this.vista = vista;

        this.usuario = new Usuario(String.valueOf(clave));
        actualizarVistaDatosUsuario();
    }

    public ControladorUsuarios(VistaUsuarios vista,int clave,int modo) {
        this.vista = vista;
        this.usuario = new Usuario(String.valueOf(clave));
        actualizarVistaDatosUsuario();

        System.out.println(modo);
        if (modo == this.MODO_EDICION){
            vista.habilitarEdicion();
        }
        if (modo == this.MODO_ADD){
            vista.vaciarCampos();
        }

        this.modo = modo;
    }

    public static void main(String[] args) {
        //VistaUsuarios v = new VistaUsuarios(12345003);
    }

    public void accionBotonSalir(){
        this.vista.dispose();
    }
    public void accionBotonGuardar(){
        if (Dialogos.confirmacionAccion()){
            this.vista.inhabilitarEdicion();

            this.usuario.setUserID(this.vista.getTxtUserID());
            this.usuario.setPassword(this.vista.getTxtPassword());
            this.usuario.setDescripcion(this.vista.getTxtDescripcion());
            this.usuario.setTipo(Integer.valueOf(this.vista.getTxtTipo()));
            this.usuario.setIntentos(Integer.valueOf(this.vista.getTxtIntentos()));
            this.usuario.setEstado(Integer.valueOf(this.vista.getTxtEstado()));


            if(this.usuario.existeUsuario()){
                this.usuario.actualizar();
            }
            else{
                this.usuario.insertar();
                System.out.println("PASE!");
                //cruce contra la tabla usuario, verifico.
                Persona personaAuxiliar = new Persona(Integer.valueOf(this.usuario.getUserID()));
                if(!personaAuxiliar.existePersona(Integer.valueOf(this.usuario.getUserID()))){
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
    public void accionBotonModificar(){
        this.vista.habilitarEdicion();
    }
    public void accionBotonCancelar(){
        this.vista.dispose();
    }

    private void actualizarVistaDatosUsuario() {
        this.vista.setTxtUserID(this.usuario.getUserID());
        this.vista.setTxtPassword(this.usuario.getPassword());
        this.vista.setTxtDescripcion(this.usuario.getDescripcion());
        this.vista.setTxtTipo(String.valueOf(this.usuario.getTipo()));
        this.vista.setTxtIntentos(String.valueOf(this.usuario.getIntentos()));
        this.vista.setTxtEstado(String.valueOf(this.usuario.getEstado()));
    }
}

