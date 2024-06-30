package Vista;

/**
 * interfaz que define que una vista presenta un DataAndChart.
 */
public interface Mostrable {

    public static int VISOR_GRAFICO_SEGURIDAD = 1;
    public static int VISOR_GRAFICO_PERSONA = 2;
    public static int VISOR_GRAFICO_CAMPANIA = 3;
    public static int VISOR_GRAFICO_ITEM = 4;

    /**
     * funcion de una vista que muestra un grafico
     */
    public void showDataAndChartFrame();
}
