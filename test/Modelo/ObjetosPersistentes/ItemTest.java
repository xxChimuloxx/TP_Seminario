package Modelo.ObjetosPersistentes;

import Modelo.Tools;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void existe() {
        Item m = new Item(5);

        assertAll(
                () -> assertEquals(true, m.existe(5)),
                () -> assertEquals(false, m.existe(100))
        );
    }

    @Test
    void getReporteTotalRegistros() {
        assertAll(
                () -> assertEquals(46, Item.getReporteTotalRegistros())
        );
    }

    @Test
    void actualizar() {
        Item m = new Item(5);

        m.setId(47);
        m.setDescripcion("elemento 47");
        m.setMarca("marca 47");
        m.setModelo("modelo 47");
        m.insertar();

        assertAll(
                () -> assertEquals(47, m.getId()),
                () -> assertEquals("elemento 47", m.getDescripcion()),
                () -> assertEquals("marca 47", m.getMarca()),
                () -> assertEquals("modelo 47", m.getModelo())
        );

        m.setDescripcion("elemento 48");
        m.setMarca("marca 48");
        m.setModelo("modelo 48");

        assertAll(
                () -> assertEquals(47, m.getId()),
                () -> assertEquals("elemento 48", m.getDescripcion()),
                () -> assertEquals("marca 48", m.getMarca()),
                () -> assertEquals("modelo 48", m.getModelo())
        );

        m.eliminar();

    }

    @Test
    void eliminar() {

        assertAll(
                () -> assertEquals(46, Item.getReporteTotalRegistros())
        );

        Item m = new Item(5);

        m.setId(48);
        m.setDescripcion("elemento 48");
        m.setMarca("marca 48");
        m.setModelo("modelo 48");
        m.insertar();

        assertAll(
                () -> assertEquals(47, Item.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(46, Item.getReporteTotalRegistros())
        );
    }

    @Test
    void selectQuery() {
        Item m = new Item(5);
        assertAll(
                () -> assertEquals(5, m.getId()),
                () -> assertEquals("Impresora de Gran Formato", m.getDescripcion()),
                () -> assertEquals("Marca Y", m.getMarca()),
                () -> assertEquals("Modelo A1", m.getModelo())
        );
    }

    @Test
    void insertar() {

        assertAll(
                () -> assertEquals(46, Item.getReporteTotalRegistros())
        );

        Item m = new Item(5);

        m.setId(49);
        m.setDescripcion("elemento 49");
        m.setMarca("marca 49");
        m.setModelo("modelo 49 ");
        m.insertar();

        assertAll(
                () -> assertEquals(47, Item.getReporteTotalRegistros())
        );

        m.eliminar();

        assertAll(
                () -> assertEquals(46, Item.getReporteTotalRegistros())
        );
    }

}