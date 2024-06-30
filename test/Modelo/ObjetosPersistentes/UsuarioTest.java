package Modelo.ObjetosPersistentes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void existe() {
        Usuario m = new Usuario("12345602");

        assertAll(
                () -> assertEquals(true, m.existe(12345602)),
                () -> assertEquals(false, m.existe(12349999))
        );
    }

    @Test
    void getReporteTotalRegistros() {
        assertAll(
                () -> assertEquals(43, Usuario.getReporteTotalRegistros())
        );
    }

    @Test
    void actualizar() {
        Usuario m = new Usuario("5");

        m.setUserID("12345678");
        m.setPassword("12345678");
        m.setDescripcion("Esta es una gran prueba");
        m.setTipo(10000);
        m.setIntentos(1);
        m.setEstado(1);

        m.insertar();

        assertAll(
                () -> assertEquals("12345678", m.getUserID()),
                () -> assertEquals("12345678", m.getPassword()),
                () -> assertEquals("Esta es una gran prueba", m.getDescripcion()),
                () -> assertEquals(10000, m.getTipo()),
                () -> assertEquals(1, m.getIntentos()),
                () -> assertEquals(1, m.getEstado())
        );


        m.setPassword("11111111");
        m.setDescripcion("Esta es una gran prueba2");
        m.setTipo(10010);
        m.setIntentos(2);
        m.setEstado(0);

        assertAll(
                () -> assertEquals("12345678", m.getUserID()),
                () -> assertEquals("11111111", m.getPassword()),
                () -> assertEquals("Esta es una gran prueba2", m.getDescripcion()),
                () -> assertEquals(10010, m.getTipo()),
                () -> assertEquals(2, m.getIntentos()),
                () -> assertEquals(0, m.getEstado())
        );

        m.eliminar();

    }

    @Test
    void eliminar() {

        assertAll(
                () -> assertEquals(43, Usuario.getReporteTotalRegistros())
        );

        Usuario m = new Usuario("5");

        m.setUserID("12345678");
        m.setPassword("12345678");
        m.setDescripcion("Esta es una gran prueba");
        m.setTipo(10000);
        m.setIntentos(1);
        m.setEstado(1);

        m.insertar();

        assertAll(
                () -> assertEquals(44, Usuario.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(43, Usuario.getReporteTotalRegistros())
        );
    }

    @Test
    void selectQuery() {
        Usuario m = new Usuario("12345601");
        assertAll(
                () -> assertEquals("12345601", m.getUserID()),
                () -> assertEquals("12345601", m.getPassword()),
                () -> assertEquals("Juan Perez", m.getDescripcion()),
                () -> assertEquals(11000, m.getTipo()),
                () -> assertEquals(0, m.getIntentos()),
                () -> assertEquals(1, m.getEstado())
        );
    }

    @Test
    void insertar() {

        assertAll(
                () -> assertEquals(43, Usuario.getReporteTotalRegistros())
        );

        Usuario m = new Usuario("5");

        m.setUserID("12345678");
        m.setPassword("12345678");
        m.setDescripcion("Esta es una gran prueba");
        m.setTipo(10000);
        m.setIntentos(1);
        m.setEstado(1);

        m.insertar();

        assertAll(
                () -> assertEquals(44, Usuario.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(43, Usuario.getReporteTotalRegistros())
        );
    }

}