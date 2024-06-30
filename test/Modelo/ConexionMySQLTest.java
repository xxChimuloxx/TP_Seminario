package Modelo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ConexionMySQLTest {

    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private int legajo;
    private String correo;
    private String telefono;
    private String equipo;
    private String area;
    private String gerencia;

    private Object o;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void obtener() {
        try {
            assertAll(
                    () -> assertEquals(true,o==null)
            );
            o = ConexionMySQL.obtener();
            assertAll(
                    () -> assertEquals(true,o!=null)
            );
        } catch (SQLException | ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos de la Persona: " + e.getMessage());
        }

        String consulta="SELECT * FROM mydb.personas WHERE ID=5";

        try {
            Statement sentencia= ConexionMySQL.obtener().createStatement();
            ResultSet resultado=sentencia.executeQuery(consulta);

            if (resultado.next()){
                this.id = resultado.getInt(1);
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
            assertAll(
                    () -> assertEquals(true,id==5),
                    () -> assertEquals(true,id!=4),
                    () -> assertEquals(false,id==4),
                    () -> assertEquals(false,dni==1234),
                    () -> assertEquals(true,dni==12345605),
                    () -> assertEquals(false,legajo==1234),
                    () -> assertEquals(true,legajo==1005),
                    () -> assertEquals(true, nombre.equals("Luis")),
                    () -> assertEquals(false, nombre.equals("Paco")),
                    () -> assertEquals(true, apellido.equals("Fernandez")),
                    () -> assertEquals(false, apellido.equals("Rodriguez")),
                    () -> assertEquals(true, correo.equals("luis.fernandez1@example.com")),
                    () -> assertEquals(false, correo.equals("luis.fernandez1@example.com.ar")),
                    () -> assertEquals(true, telefono.equals("1234567894")),
                    () -> assertEquals(false, telefono.equals("1234235464")),
                    () -> assertEquals(true, equipo.equals("Equipo C")),
                    () -> assertEquals(false, equipo.equals("Equipo D")),
                    () -> assertEquals(true, area.equals("Área 1")),
                    () -> assertEquals(false, area.equals("Área 2")),
                    () -> assertEquals(true, gerencia.equals("Gerencia 1")),
                    () -> assertEquals(false, gerencia.equals("Gerencia 1 "))

            );


        } catch (SQLException | ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos de la Persona: " + e.getMessage());
        }
    }

    @Test
    void cerrar() {
    }
}