package Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que permite gestionar las conexiones SQL contra el motor de base de datos
 */
public class ConexionMySQL {

    private static Connection cnx = null;

    /**
     * Permite obtener una conexion contra la base de datos.
     * Implementa Singleton de forma tal que solo posibilita la existencia de una conexion a la vez por ejecucion.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection obtener() throws SQLException, ClassNotFoundException, IOException {
        if (cnx == null) {
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream("src/config.properties")) {
                properties.load(input);
            } catch (IOException e) {
                //e.printStackTrace();
                throw new IOException(e);
            }

            String jdbcUrl = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                //cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Passw0rd");
                cnx = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return cnx;
    }

    /**
     * Cierra la conexion SQL contra el motor de base de datos.
     * @throws SQLException
     */
    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
        cnx = null;
    }
}