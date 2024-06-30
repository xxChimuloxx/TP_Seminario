package Modelo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void esNumerica() {
        assertAll(
                () -> assertEquals(false,Tools.esNumerica("Hola Mundo")),
                () -> assertEquals(false,Tools.esNumerica("Hola1Mundo")),
                () -> assertEquals(false,Tools.esNumerica("12 Hola Mundo")),
                () -> assertEquals(false,Tools.esNumerica("12 1")),
                () -> assertEquals(true,Tools.esNumerica("100")),
                () -> assertEquals(false,Tools.esNumerica("1.0")),
                () -> assertEquals(false,Tools.esNumerica("1,0"))
        );
    }
}