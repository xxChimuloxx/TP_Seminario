package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un item o componente del area de gestion de activos.
 */
public class Item {
    private int id;
    private String descripcion;
    private String marca;
    private String modelo;

    /**
     * Constructor simplificado de la clase, recupera la informacion desde la base de datos.
     * @param id
     */
    public Item(int id){
//        this.id = id;
        String consulta="SELECT * FROM mydb.item WHERE ID="+id;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.id = resultado.getInt (1);
                this.descripcion = resultado.getString (2);
                this.marca = resultado.getString (3);
                this.modelo = resultado.getString (4);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite verificar si existe el Item en la base de datos.
     * @param id
     * @return
     */
    public boolean existeItem(int id){
        boolean retorno = false;
        String consulta="SELECT * FROM mydb.item WHERE ID="+id;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.id = resultado.getInt(1);
                retorno = true;
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al validar la existencia del Item: " + e.getMessage());
        }
        return retorno;
    }

    /**
     * Lista los elementos de la base de datos y devuelve una JTable.
     * @param tblDatos
     */
    public static void listarItems(JTable tblDatos) {
        String[] columnNames = {"ID",
                "Descripcion",
                "Marca",
                "Modelo"};

        DefaultTableModel modelo = new DefaultTableModel(null, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
            }
        };
        tblDatos.setModel(modelo);

        Object[] nuevaLinea;
        String consulta="SELECT * FROM mydb.item;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            while (resultado.next()){
                nuevaLinea= new Object[]{String.valueOf(resultado.getInt(1)),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4)};
                modelo.addRow(nuevaLinea);
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar elementos de la base de datos: " + e.getMessage());
        }
    }

    //Gets and Sets
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que elimina el componente de la base de datos.
     */
    public void eliminar() {
        String consulta="DELETE FROM `mydb`.`Item` WHERE `ID` = " + this.id + ";";
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar los datos del Item: " + e.getMessage());
        }
    }

    /**
     * Registra el item en la base de datos con los registros de la clase.
     */
    public void insertar() {
        String consulta="INSERT INTO `mydb`.`Item` (`Descripcion`, `Marca`, `Modelo`)\n" +
                "VALUES ('"+this.descripcion+"','"+this.marca+"','"+this.modelo+"'\n);";
        System.out.println(consulta);
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar los datos del Item: " + e.getMessage());
        }
    }

    /**
     * Metodo que actualiza el componente en la base de datos.
     */
    public void actualizar() {
        String consulta="UPDATE `mydb`.`Item`\n" +
                "SET `Descripcion` = '"+this.descripcion+"',\n" +
                "    `Marca` = '"+this.marca+"',\n" +
                "    `Modelo` = '"+this.modelo+"'\n" +
                "WHERE `ID` = "+this.id+";";
        System.out.println(consulta);

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos del Item: " + e.getMessage());
        }
    }

    //**********************************************************************************************************//
    //**********************************************************************************************************//

    public static int getReporteTotalRegistros(){
        int retorno = -1;

        String consulta="SELECT count(*) FROM mydb.item;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                retorno = resultado.getInt (1);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    public static int getReporteTotalModelos(){
        int retorno = -1;

        String consulta="SELECT \n" +
                "    COUNT(DISTINCT `item`.`Modelo`) AS distintas\n" +
                "FROM \n" +
                "    mydb.item;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                retorno = resultado.getInt (1);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    public static int getReporteTotalMarcas(){
        int retorno = -1;

        String consulta="SELECT \n" +
                "    COUNT(DISTINCT `item`.`Marca`) AS distintas\n" +
                "FROM \n" +
                "    mydb.item;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                retorno = resultado.getInt (1);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    //**********************************************************************************************************//

    public static String[] getReporteItemxMarcaTitulos() {
        String[] columnNames = {"Marca",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteItemxMarca() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`item`.`Marca`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.item\n" +
                "GROUP BY `item`.`Marca`;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            List<String[]> aux = new ArrayList<>();
            while (resultado.next()){
                String[] elemento = {resultado.getString (1),String.valueOf(resultado.getInt (2))};
                aux.add(elemento);
            };
            retorno = aux;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    //**********************************************************************************************************//

    public static String[] getReporteItemxModeloTitulos() {
        String[] columnNames = {"Modelo",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteItemxModelo() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`item`.`Modelo`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.item\n" +
                "GROUP BY `item`.`Modelo`;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            List<String[]> aux = new ArrayList<>();
            while (resultado.next()){
                String[] elemento = {resultado.getString (1),String.valueOf(resultado.getInt (2))};
                aux.add(elemento);
            };
            retorno = aux;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    //**********************************************************************************************************//

    public static String[] getReporteModeloxMarcaTitulos() {
        String[] columnNames = {"Modelo",
                "Marca",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteModeloxMarca() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "    `item`.`Modelo`,\n" +
                "    `item`.`Marca`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.item\n" +
                "GROUP BY `item`.`Modelo`,`item`.`Marca`;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            List<String[]> aux = new ArrayList<>();
            while (resultado.next()){
                String[] elemento = {resultado.getString (1),resultado.getString (2),String.valueOf(resultado.getInt (3))};
                aux.add(elemento);
            };
            retorno = aux;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    //**********************************************************************************************************//
    //**********************************************************************************************************//

}
