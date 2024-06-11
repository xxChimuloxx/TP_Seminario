package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;

public class Persona {

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
     * @apiNote  Interactua con la clase CConexionMySQL
     * @exception SQLException
     * @exception ClassNotFoundException
     *
     */
    public Persona(int dni) {
        cargoPersona(dni);
    }
    public void cargoPersona(int dni){
        String consulta="SELECT * FROM mydb.personas WHERE DNI="+dni;

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
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
            //CConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean existePersona(int dni){
        boolean retorno = false;
        String consulta="SELECT * FROM mydb.personas WHERE DNI="+dni;

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.dni = resultado.getInt(2);
                retorno = true;
            }
            //CConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    /*
    Set and Gets
     */
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
     * Registra la persona en la base de datos con los registros de la clase.
     */
    public void eliminar() {
        String consulta="DELETE FROM `mydb`.`Personas` WHERE `DNI` = " + this.dni + ";";
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra la persona en la base de datos con los registros de la clase.
      */
    public void insertar() {
        String consulta="INSERT INTO `mydb`.`Personas` (`DNI`, `Nombre`, `Apellido`, `Legajo`, `Correo Electronico`, `Telefono`, `Equipo de Trabajo`, `Area`, `Gerencia`)\n" +
                "VALUES ("+this.dni+",'"+this.nombre+"','"+this.apellido+"',"+this.legajo+",'"+this.correo+"','"+this.telefono+"','"+this.equipo+"','"+this.area+"','"+this.gerencia+"'\n);";
        System.out.println(consulta);
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de Persona en la base de datos.
     * No recibe parametros.
     * Toma los valores vigentes en el objeto para hacer la actualizacion.
     *
     * @apiNote  Interactua con la clase CConexionMySQL
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
        System.out.println(consulta);

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista usuarios de la tabla Persona
     * @param tblDatos
     */
    public static void listarPersonas(JTable tblDatos) {
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
            Statement sentencia= CConexionMySQL.obtener().createStatement();
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
            //CConexionMySQL.cerrar();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
