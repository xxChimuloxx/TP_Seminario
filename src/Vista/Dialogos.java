package Vista;

import javax.swing.*;

public class Dialogos {

    /**
     * solicitud de confirmacion de una accion. look centralizado.
     * @return falso o verdadero segun sea la respuesta.
     */
    public static boolean confirmacionAccion(){
        boolean retorno = false;
        String mensaje = "¿Estás seguro de que quieres continuar? \n(Si continua la solicitud impactara \n contra la base de datos)";

        // Título del cuadro de diálogo
        String titulo = "Confirmación";

        // Mostrar el cuadro de diálogo de confirmación
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);

        // Procesar la respuesta del usuario
        if (respuesta == JOptionPane.YES_OPTION) {
            retorno = true;
        } else if (respuesta == JOptionPane.NO_OPTION) {
            retorno = false;
        } else if (respuesta == JOptionPane.CLOSED_OPTION) {
            retorno = false;
        }

        return retorno;
    }

    /**
     * genera un mensaje de advertencia en la aplicacion. look centralizado.
     * @param cadena
     */
    public static void advertencia(String cadena,JFrame vista){
        JOptionPane.showMessageDialog(vista,
                cadena,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

}
