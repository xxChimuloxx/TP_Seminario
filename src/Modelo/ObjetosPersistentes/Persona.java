package Modelo.ObjetosPersistentes;

import Modelo.ConexionMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase persona.
 * Representa a una persona fisica de la compañia segun el enfoque necesario por el area de personal
 */
public class Persona extends ObjetoPersistente{

    private int dni;
    private String nombre;
    private String apellido;
    private int legajo;
    private String correo;
    private String telefono;
    private String equipo;
    private String area;
    private String gerencia;

    /**
     * Constructor de Persona.
     *
     * @param dni Integer. Construye Persona en funcion de los datos que se recuperan para dni desde la base de datos.
     * @apiNote  Interactua con la clase ConexionMySQL
     * @exception SQLException
     * @exception ClassNotFoundException
     *
     */
    public Persona(int dni) {
        this.queryClave = "dni";
        this.queryBase = "personas";

        this.claveNumerica = true;
        this.claveIndex = 2;

        selectQuery(dni);
    }

    /**
     * Busca y Actualiza los datos del objeto con la informacion recuperada de la base de datos.
     * @param id
     */
    public void selectQuery(int id){
        String consulta="SELECT * FROM mydb.personas WHERE DNI="+id;

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                //System.out.println (resultado.getString (1) + " " + resultado.getString (2) + " " + resultado.getString (3) + " " + resultado.getInt(4) + " " + resultado.getInt(5));
                this.dni = resultado.getInt(2);
                this.nombre = resultado.getString(3);
                this.apellido = resultado.getString(4);
                this.legajo = resultado.getInt(5);
                this.correo = resultado.getString(6);
                this.telefono = resultado.getString(7);
                this.equipo = resultado.getString(8);
                this.area = resultado.getString(9);
                this.gerencia = resultado.getString(10);
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos de la Persona: " + e.getMessage());
        }
    }

    /**
     * verifica que existe el elemento en la base de datos.
     * @param id
     * @return
     */
    public boolean existe(int id){
        boolean retorno = super.existe(id);
        if(retorno){
            this.dni = this.retornoClaveNumerica;
        }
        return retorno;
    }

    //Gets and Sets
    public String getGerencia() {
        return gerencia;
    }

    public String getArea() {
        return area;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public int getLegajo() {
        return legajo;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    /**
     * Metodo que elimina la campaña de la base de datos.     *
     */
    public void eliminar(){
        this.retornoClaveNumerica=this.dni;
        super.eliminar();
    }

    /**
     * Inserta la persona en la base de datos con los registros de la clase.
      */
    public void insertar() {
        String consulta="INSERT INTO `mydb`.`Personas` (`DNI`, `Nombre`, `Apellido`, `Legajo`, `Correo Electronico`, `Telefono`, `Equipo de Trabajo`, `Area`, `Gerencia`)\n" +
                "VALUES ("+this.dni+",'"+this.nombre+"','"+this.apellido+"',"+this.legajo+",'"+this.correo+"','"+this.telefono+"','"+this.equipo+"','"+this.area+"','"+this.gerencia+"'\n);";
        super.insertar(consulta);
    }

    /**
     * Actualiza los datos de Persona en la base de datos.
     * No recibe parametros.
     * Toma los valores vigentes en el objeto para hacer la actualizacion.
     *
     * @apiNote  Interactua con la clase ConexionMySQL
     * @exception SQLException
     * @exception ClassNotFoundException
     *
     */
    public void actualizar() {
        String consulta="UPDATE `mydb`.`Personas`\n" +
                "SET `DNI` = "+this.dni+",\n" +
                "    `Nombre` = '"+this.nombre+"',\n" +
                "    `Apellido` = '"+this.apellido+"',\n" +
                "    `Legajo` = "+this.legajo+",\n" +
                "    `Correo Electronico` = '"+this.correo+"',\n" +
                "    `Telefono` = '"+this.telefono+"',\n" +
                "    `Equipo de Trabajo` = '"+this.equipo+"',\n" +
                "    `Area` = '"+this.area+"',\n" +
                "    `Gerencia` = '"+this.gerencia+"'\n" +
                "WHERE `DNI` = "+this.dni+";";
        super.actualizar(consulta);
    }

    /**
     * Lista usuarios de la tabla Persona
     * @param tblDatos
     */
    public static void listar(JTable tblDatos) {
        String[] columnNames = {"ID",
                "DNI",
                "Nombre",
                "Apellido",
                "Legajo",
                "Correo Electronico",
                "Telefono",
                "Equipo de Trabajo",
                "Area",
                "Gerencia"};

        DefaultTableModel modelo = new DefaultTableModel(null, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
            }
        };
        tblDatos.setModel(modelo);

        Object[] nuevaLinea;
        String consulta="SELECT * FROM mydb.personas;";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            while (resultado.next()){
                //System.out.println (resultado.getString (1) + " " + resultado.getString (2) + " " + resultado.getString (3) + " " + resultado.getInt(4) + " " + resultado.getInt(5));
                nuevaLinea= new Object[]{String.valueOf(resultado.getInt(1)),
                        resultado.getInt(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getInt(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        resultado.getString(8),
                        resultado.getString(9),
                        resultado.getString(10)};
                modelo.addRow(nuevaLinea);
            }
            //ConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar las personas de la base de datos: " + e.getMessage());
        }
    }

    //**********************************************************************************************************//
    //**********************************************************************************************************//

    public static int getReporteTotalRegistros(){
        int retorno = -1;

        String consulta="SELECT count(*) FROM mydb.personas;";

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

    public static int getReporteTotalEquipos(){
        int retorno = -1;

        String consulta="SELECT \n" +
                "    COUNT(DISTINCT `personas`.`Equipo de Trabajo`) AS distintas\n" +
                "FROM \n" +
                "    mydb.personas;";

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

    public static int getReporteTotalAreas(){
        int retorno = -1;

        String consulta="SELECT \n" +
                "    COUNT(DISTINCT `personas`.`Area`) AS distintas\n" +
                "FROM \n" +
                "    mydb.personas;";

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

    public static int getReporteTotalGerencias(){
        int retorno = -1;

        String consulta="SELECT \n" +
                "    COUNT(DISTINCT `personas`.`Gerencia`) AS distintas\n" +
                "FROM \n" +
                "    mydb.personas;";

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

    public static String[] getReportePersonaxEquipoTitulos() {
        String[] columnNames = {"Equipo de Trabajo",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReportePersonaxEquipo() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`personas`.`Equipo de Trabajo`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.personas\n" +
                "GROUP BY `personas`.`Equipo de Trabajo`;";

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

    public static String[] getReportePersonaxAreaTitulos() {
        String[] columnNames = {"Area",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReportePersonaxArea() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`personas`.`Area`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.personas\n" +
                "GROUP BY `personas`.`Area`;";

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

    public static String[] getReportePersonaxGerenciaTitulos() {
        String[] columnNames = {"Gerencia",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReportePersonaxGerencia() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`personas`.`Gerencia`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.personas\n" +
                "GROUP BY `personas`.`Gerencia`;";

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

    public static String[] getReporteEquiposxAreaTitulos() {
        String[] columnNames = {"Equipo de Trabajo",
                "Area",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteEquiposxArea() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`personas`.`Equipo de Trabajo`,\n" +
                "    `personas`.`Area`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.personas\n" +
                "GROUP BY `personas`.`Equipo de Trabajo`,`personas`.`Area`;";

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

    public static String[] getReporteAreaxGerenciaTitulos() {
        String[] columnNames = {"Area",
                "Gerencia",
                "Cantidad"};
        return columnNames;
    }

    public static List<String[]> getReporteAreaxGerencia() {
        List<String[]> retorno = null;

        String consulta="SELECT \n" +
                "\t`personas`.`Area`,\n" +
                "    `personas`.`Gerencia`,\n" +
                "    COUNT(*) AS total\n" +
                "FROM mydb.personas\n" +
                "GROUP BY `personas`.`Area`,`personas`.`Gerencia`;";

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
    //**********************************************************************************************************//
    //**********************************************************************************************************//
    //**********************************************************************************************************//

}
