package Modelo.ObjetosPersistentes;

import Modelo.ConexionMySQL;
import Modelo.Tools;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que se usa para definir un objeto generico que tiene persistencia contra la base de datos MySQL
 */
public abstract class ObjetoPersistente implements ConectableConMySQL{

    protected String queryBase = "sindefinir";
    protected String queryClave = "sindefinir";

    protected boolean claveNumerica;
    protected int claveIndex;

    protected String retornoClaveAlfa;
    protected int retornoClaveNumerica;


    /**
     * verifica que existe el elemento en la base de datos.
     * @param id
     * @return
     */
    public boolean existe(int id){
        this.retornoClaveAlfa = "";
        this.retornoClaveNumerica = -1;

        boolean retorno = false;
        String consulta="SELECT * FROM `mydb`.`"+this.queryBase+"` WHERE "+this.queryClave+"="+id;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                if(this.claveNumerica){
                    this.retornoClaveNumerica = resultado.getInt(this.claveIndex);
                }
                else{
                    this.retornoClaveAlfa = resultado.getString(this.claveIndex);
                }

                retorno = true;
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al validar la existencia del elemento: " + e.getMessage());
        }
        return retorno;
    }

    /**
     * Metodo que elimina la campaña de la base de datos.
     */
    public void eliminar() {
        String consulta="DELETE FROM `mydb`.`"+this.queryBase+"` WHERE `"+this.queryClave+"` = " + this.retornoClaveNumerica + ";";
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar los datos de la base: " + e.getMessage());
        }
    }

    /**
     * Inserta el elemento en la base de datos.
     */
    public void insertar(String consulta) {
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar los datos de la Campaña: " + e.getMessage());
        }
    }

    /**
     * Metodo que actualiza la campañan en la base de datos con los datos de la clase.
     */
    public void actualizar(String consulta) {
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos de la Campaña: " + e.getMessage());
        }
    }

}
