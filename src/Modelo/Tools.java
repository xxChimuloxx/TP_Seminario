package Modelo;

public class Tools {
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
