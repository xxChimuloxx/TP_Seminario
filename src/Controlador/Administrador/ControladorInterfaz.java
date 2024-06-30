package Controlador.Administrador;

import javax.swing.*;

/**
 * Interfaz que agrupa las funcionalidades de un controlador de administracion y los metodos minimos que tienen que implementar los mismos.
 */
public interface ControladorInterfaz {
    public void accionBotonAdd(int clave);
    public void accionBotonConsultar(int clave);
    public void accionBotonModificar(int clave);
    public void accionBotonBorrar(int clave);
    public void accionBotonSalir();

    public void accionBotonEspecial1(int clave);
    public void accionBotonEspecial2(int clave);

    public void setBotonEspecial1(String texto);
    public void setBotonEspecial2(String texto);

    public void cargarTabla(JTable tblDatos);
}
