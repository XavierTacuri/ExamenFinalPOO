package ups.edu.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ups.edu.modelo.Casilla;
import ups.edu.modelo.CasillaDescubierta;
import ups.edu.modelo.JuegoException;
import ups.edu.modelo.Tablero;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

@DisplayName("Pruebas unitarias para Tablero - Happy Path y Excepciones")
class TableroTest {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
    }

    @Test
    @DisplayName("El tablero se inicializa con el tamaño correcto")
    void testInicializacionTablero() {
        assertEquals(10, tablero.getTamano());
    }

    @Test
    @DisplayName("Se pueden obtener casillas del tablero")
    void testObtenerCasilla() {
        Casilla casilla = tablero.getCasilla(0, 0);
        assertNotNull(casilla);
    }

    @Test
    @DisplayName("Todas las casillas del tablero están inicializadas")
    void testTodasCasillasInicializadas() {
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                assertNotNull(tablero.getCasilla(i, j));
            }
        }
    }

    @Test
    @DisplayName("El tablero tiene exactamente 10 minas")
    void testCantidadMinas() {
        int contadorMinas = 0;
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    contadorMinas++;
                }
            }
        }
        assertEquals(10, contadorMinas);
    }

    @Test
    @DisplayName("Descubrir casilla sin mina funciona correctamente")
    void testDescubrirCasillaSinMina() throws CasillaDescubierta, JuegoException {
        // Buscar una casilla sin mina
        boolean encontrada = false;
        for (int i = 0; i < tablero.getTamano() && !encontrada; i++) {
            for (int j = 0; j < tablero.getTamano() && !encontrada; j++) {
                if (!tablero.getCasilla(i, j).esMina()) {
                    boolean pisoMina = tablero.descubrirCasilla(i, j);
                    assertFalse(pisoMina);
                    assertTrue(tablero.getCasilla(i, j).estaDescubierta());
                    encontrada = true;
                }
            }
        }
        assertTrue(encontrada, "Debe haber al menos una casilla sin mina");
    }

    @Test
    @DisplayName("Descubrir casilla con mina retorna true")
    void testDescubrirCasillaConMina() throws CasillaDescubierta, JuegoException {
        // Buscar una casilla con mina
        boolean encontrada = false;
        for (int i = 0; i < tablero.getTamano() && !encontrada; i++) {
            for (int j = 0; j < tablero.getTamano() && !encontrada; j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    boolean pisoMina = tablero.descubrirCasilla(i, j);
                    assertTrue(pisoMina);
                    assertTrue(tablero.getCasilla(i, j).estaDescubierta());
                    encontrada = true;
                }
            }
        }
        assertTrue(encontrada, "Debe haber al menos una mina");
    }

    @Test
    @DisplayName("Marcar casilla funciona correctamente")
    void testMarcarCasilla() throws JuegoException {
        tablero.marcarCasilla(0, 0);
        assertTrue(tablero.getCasilla(0, 0).estaMarcada());
    }

    @Test
    @DisplayName("Alternar marca funciona correctamente")
    void testAlternarMarca() throws JuegoException {
        tablero.marcarCasilla(0, 0);
        assertTrue(tablero.getCasilla(0, 0).estaMarcada());
        tablero.marcarCasilla(0, 0);
        assertFalse(tablero.getCasilla(0, 0).estaMarcada());
    }

    @Test
    @DisplayName("Descubrir casilla con 0 minas vecinas activa cascada")
    void testDescubrirCascada() throws CasillaDescubierta, JuegoException {
        // Encuentra una casilla con 0 minas vecinas
        boolean encontrada = false;
        for (int i = 0; i < tablero.getTamano() && !encontrada; i++) {
            for (int j = 0; j < tablero.getTamano() && !encontrada; j++) {
                Casilla c = tablero.getCasilla(i, j);
                if (!c.esMina() && c.getMinasVecinas() == 0) {
                    tablero.descubrirCasilla(i, j);
                    // Verificar que la casilla se descubrió
                    assertTrue(tablero.getCasilla(i, j).estaDescubierta());
                    encontrada = true;
                }
            }
        }
    }

    @Test
    @DisplayName("Las casillas calculan correctamente las minas vecinas")
    void testCalculoMinasVecinas() {
        // Verificar que ninguna casilla no-mina tenga más de 8 minas vecinas
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                Casilla c = tablero.getCasilla(i, j);
                if (!c.esMina()) {
                    assertTrue(c.getMinasVecinas() >= 0 && c.getMinasVecinas() <= 8,
                            "Minas vecinas debe estar entre 0 y 8");
                }
            }
        }
    }

    @Test
    @DisplayName("Detectar victoria cuando todas las casillas sin mina están descubiertas")
    void testDetectarVictoria() throws CasillaDescubierta, JuegoException {
        // Descubrir todas las casillas que no son minas
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                if (!tablero.getCasilla(i, j).esMina()) {
                    if (!tablero.getCasilla(i, j).estaDescubierta()) {
                        tablero.descubrirCasilla(i, j);
                    }
                }
            }
        }
        assertTrue(tablero.esVictoria());
    }

    @Test
    @DisplayName("El juego no está ganado al inicio")
    void testNoVictoriaAlInicio() {
        assertFalse(tablero.esVictoria());
    }

    @Test
    @DisplayName("El tablero es serializable")
    void testTableroSerializable() throws IOException, ClassNotFoundException {
        // Guardar
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("test_tablero.dat"))) {
            out.writeObject(tablero);
        }

        // Cargar
        Tablero tableroRecuperado;
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("test_tablero.dat"))) {
            tableroRecuperado = (Tablero) in.readObject();
        }

        assertNotNull(tableroRecuperado);
        assertEquals(tablero.getTamano(), tableroRecuperado.getTamano());

        // Limpiar archivo de prueba
        new File("test_tablero.dat").delete();
    }

    @Test
    @DisplayName("Marcar múltiples casillas funciona correctamente")
    void testMarcarVariasCasillas() throws JuegoException {
        tablero.marcarCasilla(0, 0);
        tablero.marcarCasilla(1, 1);
        tablero.marcarCasilla(2, 2);

        assertTrue(tablero.getCasilla(0, 0).estaMarcada());
        assertTrue(tablero.getCasilla(1, 1).estaMarcada());
        assertTrue(tablero.getCasilla(2, 2).estaMarcada());
    }

}
