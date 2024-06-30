package Modelo.ObjetosPersistentes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampaniaTest {

    @Test
    void existe() {
        Campania m = new Campania(5);

        assertAll(
                () -> assertEquals(true, m.existe(5)),
                () -> assertEquals(false, m.existe(100))
        );
    }

    @Test
    void getReporteTotalRegistros() {
        assertAll(
                () -> assertEquals(49, Campania.getReporteTotalRegistros())
        );
    }

    @Test
    void actualizar() {
        Campania m = new Campania(5);

        m.setId(50);
        m.setNombre("NombreCampaña");
        m.setSinopsis("CampañaDePrueba");
        m.setDescripcion("Esta es una gran campaña de prueba");
        m.setVigente(1);
        m.setFechaDesde("2025-12-01");
        m.setFechaHasta("2025-12-02");
        m.setLink("www.google.com");

        m.insertar();

        assertAll(
                () -> assertEquals(50, m.getId()),
                () -> assertEquals("NombreCampaña", m.getNombre()),
                () -> assertEquals("CampañaDePrueba", m.getSinopsis()),
                () -> assertEquals("Esta es una gran campaña de prueba", m.getDescripcion()),
                () -> assertEquals(1, m.getVigente()),
                () -> assertEquals("2025-12-01", m.getFechaDesde()),
                () -> assertEquals("2025-12-02", m.getFechaHasta()),
                () -> assertEquals("www.google.com", m.getLink())
        );

        m.setNombre("NombreCampañaNuevo");
        m.setSinopsis("CampañaDePruebaNueva");
        m.setDescripcion("Esta es una gran campaña de prueba, mas grande");
        m.setVigente(0);
        m.setFechaDesde("2025-12-02");
        m.setFechaHasta("2025-12-03");
        m.setLink("www.google.com.ar");

        assertAll(
                () -> assertEquals(50, m.getId()),
                () -> assertEquals("NombreCampañaNuevo", m.getNombre()),
                () -> assertEquals("CampañaDePruebaNueva", m.getSinopsis()),
                () -> assertEquals("Esta es una gran campaña de prueba, mas grande", m.getDescripcion()),
                () -> assertEquals(0, m.getVigente()),
                () -> assertEquals("2025-12-02", m.getFechaDesde()),
                () -> assertEquals("2025-12-03", m.getFechaHasta()),
                () -> assertEquals("www.google.com.ar", m.getLink())
        );

        m.eliminar();

    }

    @Test
    void eliminar() {

        assertAll(
                () -> assertEquals(49, Campania.getReporteTotalRegistros())
        );

        Campania m = new Campania(5);

        m.setId(51);
        m.setNombre("NombreCampañaNuevo");
        m.setSinopsis("CampañaDePruebaNueva");
        m.setDescripcion("Esta es una gran campaña de prueba, mas grande");
        m.setVigente(0);
        m.setFechaDesde("2025-12-02");
        m.setFechaDesde("2025-12-03");
        m.setLink("www.google.com.ar");

        m.insertar();

        assertAll(
                () -> assertEquals(50, Campania.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(49, Campania.getReporteTotalRegistros())
        );
    }

    @Test
    void selectQuery() {
        Campania m = new Campania(5);
        assertAll(
                () -> assertEquals(5, m.getId()),
                () -> assertEquals("Campaña Liquidación Invierno 2023", m.getNombre()),
                () -> assertEquals("Liquidación de Invierno 2023", m.getSinopsis()),
                () -> assertEquals("Descripción de la Campaña Liquidación Invierno 2023", m.getDescripcion()),
                () -> assertEquals(1, m.getVigente()),
                () -> assertEquals("2023-01-20", m.getFechaDesde()),
                () -> assertEquals("2023-01-31", m.getFechaHasta()),
                () -> assertEquals("http://www.example1.com", m.getLink())
        );
    }

    @Test
    void insertar() {

        assertAll(
                () -> assertEquals(49, Campania.getReporteTotalRegistros())
        );

        Campania m = new Campania(5);

        m.setId(52);
        m.setNombre("NombreCampañaNuevo");
        m.setSinopsis("CampañaDePruebaNueva");
        m.setDescripcion("Esta es una gran campaña de prueba, mas grande");
        m.setVigente(0);
        m.setFechaDesde("2025-12-02");
        m.setFechaDesde("2025-12-03");
        m.setLink("www.google.com.ar");

        m.insertar();

        assertAll(
                () -> assertEquals(50, Campania.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(49, Campania.getReporteTotalRegistros())
        );
    }

}