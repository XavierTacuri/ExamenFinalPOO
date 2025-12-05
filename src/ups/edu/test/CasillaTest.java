package ups.edu.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ups.edu.modelo.Casilla;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para Casilla - Happy Path y Excepciones")
class CasillaTest {

    private Casilla casilla;

    @BeforeEach
    void setUp() {
        casilla = new Casilla();
    }

    @Test
    @DisplayName("Casilla se inicializa correctamente")
    void testInicializacionCasilla() {
        assertFalse(casilla.esMina());
        assertFalse(casilla.estaDescubierta());
        assertFalse(casilla.estaMarcada());
        assertEquals(0, casilla.getMinasVecinas());
    }

    @Test
    @DisplayName("Colocar mina funciona correctamente")
    void testPonerMina() {
        casilla.ponerMina();
        assertTrue(casilla.esMina());
    }

    @Test
    @DisplayName("Descubrir casilla funciona correctamente")
    void testDescubrir() {
        casilla.descubrir();
        assertTrue(casilla.estaDescubierta());
    }

    @Test
    @DisplayName("Alternar marca funciona correctamente")
    void testAlternarMarcada() {
        assertFalse(casilla.estaMarcada());
        casilla.alternarMarcada();
        assertTrue(casilla.estaMarcada());
        casilla.alternarMarcada();
        assertFalse(casilla.estaMarcada());
    }

    @Test
    @DisplayName("Incrementar minas vecinas funciona correctamente")
    void testIncrementarMinasVecinas() {
        assertEquals(0, casilla.getMinasVecinas());
        casilla.incrementarMinasVecinas();
        assertEquals(1, casilla.getMinasVecinas());
        casilla.incrementarMinasVecinas();
        assertEquals(2, casilla.getMinasVecinas());
    }

    @Test
    @DisplayName("Una casilla puede tener mina y ser descubierta")
    void testCasillaConMinaDescubierta() {
        casilla.ponerMina();
        casilla.descubrir();
        assertTrue(casilla.esMina());
        assertTrue(casilla.estaDescubierta());
    }

    @Test
    @DisplayName("Una casilla puede tener mina y ser marcada")
    void testCasillaConMinaMarcada() {
        casilla.ponerMina();
        casilla.alternarMarcada();
        assertTrue(casilla.esMina());
        assertTrue(casilla.estaMarcada());
    }

    @Test
    @DisplayName("Incrementar múltiples veces las minas vecinas")
    void testIncrementarVariasVecesMinasVecinas() {
        for (int i = 1; i <= 8; i++) {
            casilla.incrementarMinasVecinas();
            assertEquals(i, casilla.getMinasVecinas());
        }
    }
// --- NUEVAS PRUEBAS DE CASOS LÍMITE / EXCEPCIONES LÓGICAS ---

    @Test
    @DisplayName("Caso Límite: No se debe descubrir una casilla si está marcada")
    void testNoDescubrirSiEstaMarcada() {
        // 1. Marcamos la casilla
        casilla.alternarMarcada();
        assertTrue(casilla.estaMarcada(), "Precondición: La casilla debe estar marcada");

        // 2. Intentamos descubrirla
        casilla.descubrir();

        // 3. Verificamos que NO se haya descubierto (protección contra clic accidental)
        assertFalse(casilla.estaDescubierta(),
                "La casilla NO debería descubrirse si tiene una marca puesta");
    }

    @Test
    @DisplayName("Caso Límite: No se debe marcar una casilla si ya está descubierta")
    void testNoMarcarSiEstaDescubierta() {
        // 1. Descubrimos la casilla
        casilla.descubrir();
        assertTrue(casilla.estaDescubierta(), "Precondición: La casilla debe estar descubierta");

        // 2. Intentamos marcarla
        casilla.alternarMarcada();

        // 3. Verificamos que NO se haya marcado (estado inválido lógico)
        assertFalse(casilla.estaMarcada(),
                "La casilla NO debería poder marcarse si ya ha sido descubierta");
    }
}
