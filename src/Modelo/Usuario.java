package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Clase que se usa para representar un usuario segun el enfoque del area de seguridad.
 * Un usuario registrado, debe tambien existir en la tabla de personas.
 */
public class Usuario {

    private String userID;
    private String password;
    private String descripcion;
    private Integer tipo;
    private Integer intentos;
    private Integer estado;


    /**
     * Constructor general de la clase.
     * @param userID
     * @param password
     * @param descripcion
     * @param tipo
     */
    public Usuario(String userID, String password, String descripcion, Integer tipo){
        this.userID = userID;
        this.password = password;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.intentos = 0;
        this.estado = 1;
    }

    /**
     * Constructor simplificado de la clase, recupera la informacion desde la base de datos.
     * @param userID
     */
    public Usuario(String userID){
        this.userID = userID;
        String consulta="SELECT * FROM mydb.usuarios WHERE UserID="+userID;

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                //this.userID = resultado.getString (1);
                this.password = resultado.getString (2);
                this.descripcion = resultado.getString (3);
                this.tipo = resultado.getInt (4);
                this.intentos = resultado.getInt (5);
                this.estado = resultado.getInt (6);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
    }

    /**
     * actualiza el usuario con los datos cargados
     */
    public void actualizar() {
        String consulta="UPDATE `mydb`.`Usuarios`\n" +
                "SET `UserID` = "+this.userID+",\n" +
                "    `Password` = "+this.password+",\n" +
                "    `Descripcion` = '"+this.descripcion+"',\n" +
                "    `Tipo` = "+this.tipo+",\n" +
                "    `Intentos` = "+this.intentos+",\n" +
                "    `Estado` = "+this.estado+"\n" +
                "WHERE `UserID` = "+this.userID+";";
        System.out.println(consulta);

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar informacion en la base de datos: " + e.getMessage());
        }
    }

    /**
     * lista las personas de la base de datos. todas las definiciones.
     * @param tblDatos
     */
    public static void listarPersonas(JTable tblDatos) {
            String[] columnNames = {"UserID",
                    "Password",
                    "Descripcion",
                    "Tipo",
                    "Intentos",
                    "Estado"};

            DefaultTableModel modelo = new DefaultTableModel(null, columnNames){
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Hacer que todas las celdas no sean editables
                    return false;
                }
            };
            tblDatos.setModel(modelo);

            Object[] nuevaLinea;
            String consulta="SELECT * FROM mydb.Usuarios;";

            try {
                Statement sentencia= CConexionMySQL.obtener().createStatement();
                ResultSet resultado=sentencia.executeQuery(consulta);

                while (resultado.next()){
                    nuevaLinea= new Object[]{String.valueOf(resultado.getInt(1)),
                            resultado.getString(2),
                            resultado.getString(3),
                            resultado.getInt(4),
                            resultado.getInt(5),
                            resultado.getInt(6)};
                    modelo.addRow(nuevaLinea);
                }
                //CConexionMySQL.cerrar();
            } catch (SQLException | ClassNotFoundException | IOException e) {
                //e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al listar elementos de la base de datos: " + e.getMessage());
            }

    }

    /**
     * elimina los datos definidos en la clase en la base de datos de usuarios
     */
    public void eliminar() {
        String consulta="DELETE FROM `mydb`.`Usuarios` WHERE `UserID` = " + this.userID + ";";
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar elemento de la base de datos: " + e.getMessage());
        }
    }

    /**
     * inserta los datos definidos en la clase en la base de datos de usuarios
     */
    public void insertar() {
        String consulta="INSERT INTO `mydb`.`Usuarios` (`UserID`, `Password`, `Descripcion`, `Tipo`, `Intentos`, `Estado`)\n" +
                "VALUES ("+this.userID+",'"+this.password+"','"+this.descripcion+"',"+this.tipo+","+this.intentos+","+this.estado+"\n);";
        //System.out.println(consulta);
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar elemento en la base de datos: " + e.getMessage());
        }
    }

    /**
     * valida si existe el usuario this.userID
     * @return verdadero o falso segun corresponda
     */
    public boolean existeUsuario() {
        boolean retorno = false;

        String consulta="SELECT * FROM mydb.usuarios WHERE UserID="+this.userID;

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.userID = resultado.getString (1);
                retorno = true;
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar elemento de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    /**
     * Valida que el usuario y la contraseña sean correctas.
     * @param userID
     * @param password
     * @return
     */
    public static Boolean probarIngreso(String userID, String password){
        Boolean retorno = false;
        String consulta="SELECT * FROM mydb.usuarios WHERE UserID="+userID;

        System.out.println(consulta);
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                //System.out.println (resultado.getString (1) + " " + resultado.getString (2) + " " + resultado.getString (3) + " " + resultado.getInt(4) + " " + resultado.getInt(5));
                String rUsuario = resultado.getString (1);
                String rPassword = resultado.getString (2);
                int rEstado = resultado.getInt (6);

                //valido contraseña
                if (Objects.equals(password, rPassword)){
                    //valido estado
                    if (rEstado == 1) {
                        retorno = true;
                    }
                }
            }
            //CConexionMySQL.cerrar();

        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar informacion de la base de datos: " + e.getMessage());
        }
        return retorno;
    }

    /**
     * Penaliza los accesos incorrectos. 3 intentos.
     * @param userID
     * @return
     */
    public static int penalizar(String userID) {
        int rIntentos = -1;
        String consulta="SELECT * FROM mydb.Usuarios WHERE UserID="+userID;
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                String rUsuario = resultado.getString (1);
                rIntentos = resultado.getInt (5);
                rIntentos+=1;
                if (rIntentos < 4){
                    consulta="UPDATE mydb.Usuarios\n" +
                            "SET `Intentos` = "+rIntentos+"\n" +
                            "WHERE `UserID` = "+userID+";";
                    sentencia.executeUpdate(consulta);
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar elemento de la base de datos: " + e.getMessage());
        }
        return rIntentos;
    }

    /**
     * Bloquea el usuario en la base de datos. No sera posible utilizarlo en el sistema.
     * @param userID
     */
    public static void bloquear(String userID){
        String consulta="UPDATE mydb.Usuarios\n" +
                "SET `Estado` = 0\n" +
                "WHERE `UserID` = "+userID;
        System.out.println(consulta);
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar elemento de la base de datos: " + e.getMessage());
        }
    }

    /**
     * Desbloquea el usuario en la base de datos. Sera posible utilizarlo en el sistema.
     * @param userID
     */
    public static void desbloquear(String userID){
        String consulta="UPDATE mydb.Usuarios\n" +
                "SET `Estado` = 1,\n" +
                "    `Intentos`= 0\n" +
                "WHERE `UserID` = "+userID;
        System.out.println(consulta);
        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar elemento de la base de datos: " + e.getMessage());
        }
    }


    //Gets and Sets
    public Integer getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUserID() {
        return userID;
    }

    public Integer getEstado() {
        return estado;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public String getPassword() {
        return password;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
