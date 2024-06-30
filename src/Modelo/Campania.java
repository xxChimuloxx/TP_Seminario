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
 * Clase que permite representar una campania publicitaria segun las necesidades del area de marketing.
 */
public class Campania {
    private int id;
    private String nombre;
    private String sinopsis;
    private String descripcion;
    private int vigente;
    private String fechaDesde;
    private String fechaHasta;
    private String link;

    /**
     * Constructor simplificado de la clase, recupera la informacion desde la base de datos.
     * @param id
     */
    public Campania(int id){
//        this.id = id;
        String consulta="SELECT * FROM mydb.campania WHERE ID="+id;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.id = resultado.getInt (1);
                this.nombre = resultado.getString (2);
                this.sinopsis = resultado.getString (3);
                this.descripcion = resultado.getString (4);
                this.vigente = resultado.getInt (5);
                this.fechaDesde = resultado.getString (6);
                this.fechaHasta = resultado.getString (7);
                this.link = resultado.getString (8);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
    }

    /**
     * verifica que existe la campaña en la base de datos.
     * @param id
     * @return
     */
    public boolean existeCampania(int id){
        boolean retorno = false;
        String consulta="SELECT * FROM mydb.campania WHERE ID="+id;

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
            JOptionPane.showMessageDialog(null, "Error al validar la existencia de la Campaña: " + e.getMessage());
        }
        return retorno;
    }

    /**
     * Metodo que lista todas las campañas de la base de datos y las devuelve en una JTable.
     * @param tblDatos
     */
    public static void listarCampanias(JTable tblDatos) {
        String[] columnNames = {"ID",
                "Nombre de la campañia",
                "Sinopsis",
                "Descripcion",
                "Vigente",
                "Fecha Desde",
                "Fecha Hasta",
                "Vinculo"};

        DefaultTableModel modelo = new DefaultTableModel(null, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
            }
        };
        tblDatos.setModel(modelo);

        Object[] nuevaLinea;
        String consulta="SELECT * FROM mydb.campania;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            while (resultado.next()){
                nuevaLinea= new Object[]{String.valueOf(resultado.getInt(1)),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        resultado.getString(8)};
                modelo.addRow(nuevaLinea);
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar elementos de la base de datos: " + e.getMessage());
        }
    }

    //Gets and Sets
    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public int getVigente() {
        return vigente;
    }

    public void setVigente(int vigente) {
        this.vigente = vigente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Metodo que elimina la campaña de la base de datos.
     */
    public void eliminar() {
        String consulta="DELETE FROM `mydb`.`Campania` WHERE `ID` = " + this.id + ";";
        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar los datos de la Persona: " + e.getMessage());
        }
    }

    /**
     * Registra la campaña en la base de datos con los registros de la clase.
     */
    public void insertar() {
        String consulta="INSERT INTO `mydb`.`Campania` (`Nombre`, `Sinopsis`, `Descripcion`, `Vigente`, `Fecha Inicio Vigencia`, `Fecha Fin Vigencia`, `LINK`)\n" +
                "VALUES ('"+this.nombre+"','"+this.sinopsis+"','"+this.descripcion+"',"+this.vigente+",'"+this.fechaDesde+"','"+this.fechaHasta+"','"+this.link+"'\n);";
        System.out.println(consulta);
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
   public void actualizar() {
        String consulta="UPDATE `mydb`.`Campania`\n" +
                "SET `Nombre` = '"+this.nombre+"',\n" +
                "    `Sinopsis` = '"+this.sinopsis+"',\n" +
                "    `Descripcion` = '"+this.descripcion+"',\n" +
                "    `Vigente` = "+this.vigente+",\n" +
                "    `Fecha Inicio Vigencia` = '"+this.fechaDesde+"',\n" +
                "    `Fecha Fin Vigencia` = '"+this.fechaHasta+"',\n" +
                "    `LINK` = '"+this.link+"'\n" +
                "WHERE `ID` = "+this.id+";";
        System.out.println(consulta);

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos de la Campaña: " + e.getMessage());
        }
    }

    //**********************************************************************************************************//
    //**********************************************************************************************************//

    public static int getReporteTotalRegistros(){
        int retorno = -1;

        String consulta="SELECT count(*) FROM mydb.campania;";

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

    public static int getReporteMediaMensual(){
        int retorno = -1;

        String consulta="SELECT\n" +
                "    AVG(Cantidad_Campañas) AS Media_Campañas_Mensuales\n" +
                "FROM (\n" +
                "    SELECT\n" +
                "        YEAR(`Fecha Inicio Vigencia`) AS Año,\n" +
                "        MONTH(`Fecha Inicio Vigencia`) AS Mes,\n" +
                "        COUNT(*) AS Cantidad_Campañas\n" +
                "    FROM\n" +
                "        `mydb`.`Campania`\n" +
                "    GROUP BY\n" +
                "        YEAR(`Fecha Inicio Vigencia`), MONTH(`Fecha Inicio Vigencia`)\n" +
                ") AS Subquery;";

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

    public static int getReporteMediaAnual(){
        int retorno = -1;

        String consulta="SELECT\n" +
                "    AVG(Total_Campañas) AS Media_Campañas_Anuales\n" +
                "FROM (\n" +
                "    SELECT\n" +
                "        YEAR(`Fecha Inicio Vigencia`) AS Año,\n" +
                "        COUNT(*) AS Total_Campañas\n" +
                "    FROM\n" +
                "        `mydb`.`Campania`\n" +
                "    GROUP BY\n" +
                "        YEAR(`Fecha Inicio Vigencia`)\n" +
                ") AS Subquery;";

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

    public static String[] getReporteCampaniaxVigenciaTitulos() {
        String[] columnNames = {"Campaña Vigentes", "Campaña No Vigentes"};
        return columnNames;
    }

    public static Object[][] getReporteCampaniaxVigencia() {
        Object[][] retorno = null;

        String consulta="SELECT\n" +
                "    SUM(CASE WHEN vigente = 1 THEN 1 ELSE 0 END) AS Usuarios_Activos,\n" +
                "    SUM(CASE WHEN vigente = 0 THEN 1 ELSE 0 END) AS Usuarios_Inactivos\n" +
                "FROM mydb.campania;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                Object[][] aux = {
                        {resultado.getInt (1), resultado.getInt (2)}
                };
                retorno = aux;
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    //**********************************************************************************************************//

    public static String[] getReporteCampaniaxMesTitulos() {
        String[] columnNames = {"Mes",
                "Año",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteCampaniaxMes() {
        List<String[]> retorno = null;

        String consulta="SELECT\n" +
                "    MONTH(`Fecha Inicio Vigencia`) AS Mes,\n" +
                "    YEAR(`Fecha Inicio Vigencia`) AS Año,\n" +
                "    COUNT(*) AS Total_Campañas\n" +
                "FROM\n" +
                "    `mydb`.`Campania`\n" +
                "GROUP BY\n" +
                "    YEAR(`Fecha Inicio Vigencia`), MONTH(`Fecha Inicio Vigencia`)\n" +
                "ORDER BY\n" +
                "    Año, Mes;";

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

    public static String[] getReporteCampaniaxAnioTitulos() {
        String[] columnNames = {"Año",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteCampaniaxAnio() {
        List<String[]> retorno = null;

        String consulta="-- Campañas por año\n" +
                "SELECT\n" +
                "    YEAR(`Fecha Inicio Vigencia`) AS Año,\n" +
                "    COUNT(*) AS Total_Campañas\n" +
                "FROM\n" +
                "    `mydb`.`Campania`\n" +
                "GROUP BY\n" +
                "    YEAR(`Fecha Inicio Vigencia`)\n" +
                "ORDER BY\n" +
                "    Año;";

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
    //**********************************************************************************************************//

}
