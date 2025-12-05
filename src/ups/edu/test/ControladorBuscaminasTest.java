package ups.edu.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ups.edu.controlador.ControladorBuscaminas;

import java.lang.reflect.Method;

@DisplayName("Pruebas unitarias para ControladorBuscaminas - Happy Path y Casos Límite")
class ControladorBuscaminasTest {

    private ControladorBuscaminas controlador;

    @BeforeEach
    void setUp() {
        controlador = new ControladorBuscaminas();
    }

    @Test
    @DisplayName("El controlador se inicializa correctamente")
    void testInicializacionControlador() {
        assertNotNull(controlador);
    }

    @Test
    @DisplayName("Parsear coordenadas A1 funciona correctamente")
    void testParsearCoordenadasA1() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "A1");
        assertArrayEquals(new int[]{0, 0}, resultado);
    }

    @Test
    @DisplayName("Parsear coordenadas B5 funciona correctamente")
    void testParsearCoordenadasB5() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "B5");
        assertArrayEquals(new int[]{1, 4}, resultado);
    }

    @Test
    @DisplayName("Parsear coordenadas J10 funciona correctamente")
    void testParsearCoordenadasJ10() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "J10");
        assertArrayEquals(new int[]{9, 9}, resultado);
    }

    @Test
    @DisplayName("Parsear coordenadas C3 funciona correctamente")
    void testParsearCoordenadasC3() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "C3");
        assertArrayEquals(new int[]{2, 2}, resultado);
    }

    @Test
    @DisplayName("Parsear coordenadas E7 funciona correctamente")
    void testParsearCoordenadasE7() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "E7");
        assertArrayEquals(new int[]{4, 6}, resultado);
    }

    @Test
    @DisplayName("Parsear coordenadas con espacios funciona correctamente")
    void testParsearCoordenadasConEspacios() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        int[] resultado = (int[]) metodo.invoke(controlador, "A 1");
        assertArrayEquals(new int[]{0, 0}, resultado);
    }

// --- NUEVOS CASOS LÍMITE AÑADIDOS ---

    @Test
    @DisplayName("Caso Límite: Coordenadas en minúsculas (a1) deben ser válidas")
    void testParsearCoordenadasMinusculas() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        // Prueba que 'a1' se interprete igual que 'A1' (0,0)
        int[] resultado = (int[]) metodo.invoke(controlador, "a1");
        assertArrayEquals(new int[]{0, 0}, resultado);
    }

    @Test
    @DisplayName("Caso Límite: Coordenadas con espacios al inicio y final")
    void testParsearCoordenadasConTrim() throws Exception {
        Method metodo = ControladorBuscaminas.class.getDeclaredMethod(
                "parsearCoordenadas", String.class);
        metodo.setAccessible(true);

        // Prueba que ' B5 ' se limpie y se interprete como 'B5' (1,4)
        int[] resultado = (int[]) metodo.invoke(controlador, " B5 ");
        assertArrayEquals(new int[]{1, 4}, resultado);
    }
}

