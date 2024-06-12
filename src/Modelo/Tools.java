package Modelo;

/**
 * Clase que tiene herramientas basicas de la aplicacion.
 * PAra su uso en forma centralizada.
 */
public class Tools {

    /**
     * Valida si la cadena que se recibe como parametro es numerica.
     * @param cadena
     * @return
     */
    public static boolean esNumerica(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return false;
        }
        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
