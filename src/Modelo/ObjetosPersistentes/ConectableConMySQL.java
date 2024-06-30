package Modelo.ObjetosPersistentes;

import javax.swing.*;

/**
 * Interfaz que se usa para definir a un objeto que es conectable con MySQL
 */
public interface ConectableConMySQL {
    public boolean existe(int id);
    public void eliminar();
    public void insertar(String consulta);
    public void actualizar(String consulta);
    public void selectQuery(int id);

    public static void listar(JTable tblDatos){};
    public static int getReporteTotalRegistros(){return -1;};

}
