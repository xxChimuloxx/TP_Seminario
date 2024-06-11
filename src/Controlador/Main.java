package Controlador;

import Modelo.CConexionMySQL;
import Modelo.Usuario;
import Vista.VistaLogon;

import java.sql.*;


public class Main {

    public static void main(String[] args) {
        VistaLogon v = new VistaLogon();
    }

    //PRUEBAS RANDOM - DEPURAR
        //Usuario.probarIngreso("12345678","aloja");
        //Usuario.probarIngreso("122345678","aloja");



    public static void main11(String[] args) {

        VistaLogon vista;
        ControladorLogon controlador;

        vista = new VistaLogon();
    }




    public static void main1(String[] args) {
        String consulta="SELECT * FROM mydb.usuarios";
        consulta="INSERT INTO `mydb`.`Usuarios` (`UserID`, `Password`, `Descripcion`, `Tipo`, `Estado`)\n" +
                "VALUES\n" +
                "(02345678, 'password3', 'Descripción Modelo.Usuario 3', 1,1),\n" +
                "(07654321, 'password4', 'Descripción Modelo.Usuario 4', 2,0);\n";

        consulta="DELETE FROM `mydb`.`Usuarios`\n" +
                "WHERE UserID>0;\n";

        try {
            Statement sentencia= CConexionMySQL.obtener().createStatement();
            sentencia.executeUpdate(consulta);


            /*ResultSet resultado=sentencia.executeQuery(consulta);

            while (resultado.next())
            {
                System.out.println (resultado.getString (1) + " " + resultado.getString (2) + " " + resultado.getString (3) + " " + resultado.getInt(4) + " " + resultado.getInt(5));
            }
            */
            CConexionMySQL.cerrar();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }





    }
}