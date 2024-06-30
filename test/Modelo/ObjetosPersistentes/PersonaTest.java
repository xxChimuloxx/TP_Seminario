package Modelo.ObjetosPersistentes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    @Test
    void existe() {
        Persona m = new Persona(5);

        assertAll(
                () -> assertEquals(true, m.existe(12345603)),
                () -> assertEquals(false, m.existe(100))
        );
    }

    @Test
    void getReporteTotalRegistros() {
        assertAll(
                () -> assertEquals(43, Persona.getReporteTotalRegistros())
        );
    }

    @Test
    void actualizar() {
        Persona m = new Persona(5);

        m.setDni(12345678);
        m.setNombre("Nombre A");
        m.setApellido("Apellido A");
        m.setLegajo(12345678);
        m.setCorreo("Correo A");
        m.setTelefono("Telefono A");
        m.setEquipo("Equipo A");
        m.setArea("Area A");
        m.setGerencia("Gerencia A");

        m.insertar();

        assertAll(
                () -> assertEquals(12345678, m.getDni()),
                () -> assertEquals("Nombre A", m.getNombre()),
                () -> assertEquals("Apellido A", m.getApellido()),
                () -> assertEquals(12345678, m.getLegajo()),
                () -> assertEquals("Correo A", m.getCorreo()),
                () -> assertEquals("Telefono A", m.getTelefono()),
                () -> assertEquals("Equipo A", m.getEquipo()),
                () -> assertEquals("Area A", m.getArea()),
                () -> assertEquals("Gerencia A", m.getGerencia())
        );

        m.setNombre("Nombre B");
        m.setApellido("Apellido B");
        m.setLegajo(11111111);
        m.setCorreo("Correo B");
        m.setTelefono("Telefono B");
        m.setEquipo("Equipo B");
        m.setArea("Area B");
        m.setGerencia("Gerencia B");

        assertAll(
                () -> assertEquals(12345678, m.getDni()),
                () -> assertEquals("Nombre B", m.getNombre()),
                () -> assertEquals("Apellido B", m.getApellido()),
                () -> assertEquals(11111111, m.getLegajo()),
                () -> assertEquals("Correo B", m.getCorreo()),
                () -> assertEquals("Telefono B", m.getTelefono()),
                () -> assertEquals("Equipo B", m.getEquipo()),
                () -> assertEquals("Area B", m.getArea()),
                () -> assertEquals("Gerencia B", m.getGerencia())
        );

        m.eliminar();

    }

    @Test
    void eliminar() {

        assertAll(
                () -> assertEquals(43, Persona.getReporteTotalRegistros())
        );

        Persona m = new Persona(5);

        m.setDni(12345678);
        m.setNombre("Nombre A");
        m.setApellido("Apellido A");
        m.setLegajo(12345678);
        m.setCorreo("Correo A");
        m.setTelefono("Telefono A");
        m.setEquipo("Equipo A");
        m.setArea("Area A");
        m.setGerencia("Gerencia A");

        m.insertar();

        assertAll(
                () -> assertEquals(44, Persona.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(43, Persona.getReporteTotalRegistros())
        );
    }

    @Test
    void selectQuery() {
        Persona m = new Persona(12345602);
        assertAll(
                () -> assertEquals(12345602, m.getDni()),
                () -> assertEquals("Ana", m.getNombre()),
                () -> assertEquals("Ramirez", m.getApellido()),
                () -> assertEquals(1002, m.getLegajo()),
                () -> assertEquals("ana.ramirez1@example.com", m.getCorreo()),
                () -> assertEquals("1234567891", m.getTelefono()),
                () -> assertEquals("Equipo A", m.getEquipo()),
                () -> assertEquals("Área 1", m.getArea()),
                () -> assertEquals("Gerencia 1", m.getGerencia())
        );
    }

    @Test
    void insertar() {

        assertAll(
                () -> assertEquals(43, Persona.getReporteTotalRegistros())
        );

        Persona m = new Persona(5);

        m.setDni(12345678);
        m.setNombre("Nombre A");
        m.setApellido("Apellido A");
        m.setLegajo(12345678);
        m.setCorreo("Correo A");
        m.setTelefono("Telefono A");
        m.setEquipo("Equipo A");
        m.setArea("Area A");
        m.setGerencia("Gerencia A");

        m.insertar();

        assertAll(
                () -> assertEquals(44, Persona.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(43, Persona.getReporteTotalRegistros())
        );
    }
}