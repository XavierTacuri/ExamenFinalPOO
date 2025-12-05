package ups.edu.controlador;

import java.io.*;
import java.util.Scanner;

import ups.edu.modelo.CasillaDescubierta;
import ups.edu.modelo.JuegoException;
import ups.edu.modelo.Tablero;
import ups.edu.vista.VistaJuego;

public class ControladorBuscaminas {

    private Tablero tablero;
    private final VistaJuego vista;
    private final Scanner scanner;

    public ControladorBuscaminas() {
        this.vista = new VistaJuego();
        this.scanner = new Scanner(System.in);
    }

    public void ejecutarMenu() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    nuevaPartida();
                    break;
                case "2":
                    cargarPartida();
                    break;
                case "3":
                    vista.mostrarAyuda();
                    esperarEnter();
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida. Intenta nuevamente.");
                    break;
            }
        }
        vista.mostrarMensaje("Gracias por jugar. ¡Hasta luego!");
    }

    private void nuevaPartida() {
        tablero = new Tablero();
        jugar();
    }

    private void jugar() {
        boolean juegoAcabado = false;
        boolean perdio = false;

        /**
         * Ciclo principal de control del juego.
         * Este bucle mantiene la partida activa permitiendo la interacción continua
         * con el usuario hasta que se alcance una condición de victoria o derrota.
         */
        while (!juegoAcabado) {
            // Se solicita a la vista que renderice el estado actual del tablero.
            // El parámetro 'false' indica que las minas deben permanecer ocultas.
            vista.mostrarTablero(tablero, false);

            // Interfaz de texto para guiar al usuario en la entrada de datos.
            System.out.println("\nIngresa una jugada:");
            System.out.println("- Ejemplo descubrir: A5");
            System.out.println("- Ejemplo marcar: M A5");
            System.out.println("- G para guardar y salir");
            System.out.print("> ");

            // Captura de la entrada, normalizando el texto (mayúsculas y sin espacios extra)
            // para simplificar el procesamiento posterior.
            String entrada = scanner.nextLine().trim().toUpperCase();

            // Opción de persistencia: Permite al usuario pausar el estado actual del objeto.
            if (entrada.equals("G")) {
                guardarPartida();
                vista.mostrarMensaje("Partida guardada. Volviendo al menú...");
                return; // Interrumpe el flujo del método para volver al menú principal.
            }

            // Bloque try-catch para garantizar la robustez del programa.
            // Capturamos cualquier error de lógica o formato sin que el programa se cierre abruptamente.
            try {
                if (entrada.startsWith("M ")) {
                    // Lógica para 'Marcar' (Bandera): Extraemos la coordenada ignorando el prefijo "M ".
                    String coord = entrada.substring(2).trim();
                    int[] fc = parsearCoordenadas(coord);
                    
                    // Delegamos la acción al modelo (Tablero).
                    tablero.marcarCasilla(fc[0], fc[1]);
                } else {
                    // Lógica para 'Descubrir': Procesamos la coordenada estándar.
                    int[] fc = parsearCoordenadas(entrada);
                    boolean pisoMina = tablero.descubrirCasilla(fc[0], fc[1]);

                    // Evaluación inmediata de las condiciones de fin de juego.
                    if (pisoMina) {
                        perdio = true;
                        juegoAcabado = true;
                    } else if (tablero.esVictoria()) {
                        juegoAcabado = true;
                    }
                }
            } catch (CasillaDescubierta e) {
                // Manejo específico: Informamos si el usuario intenta una acción redundante.
                vista.mostrarMensaje(e.getMessage());
            } catch (JuegoException e) {
                // Manejo de errores controlados (coordenadas fuera de rango, sintaxis incorrecta).
                vista.mostrarMensaje("Error: " + e.getMessage());
            } catch (Exception e) {
                // Captura genérica para cualquier error no previsto.
                vista.mostrarMensaje("Entrada no válida. Usa formato A5 o M A5.");
            }
        }

        // Fase final: Se muestra el tablero revelando todas las minas (true).
        vista.mostrarTablero(tablero, true);
        
        // Retroalimentación final al usuario basada en el estado de la bandera 'perdio'.
        if (perdio) {
            vista.mostrarMensaje("¡BOOM! Has pisado una mina. Has perdido.");
        } else {
            vista.mostrarMensaje("¡Felicidades! Has ganado el juego.");
        }
        esperarEnter();
    }

    /**
     * Método auxiliar encargado de traducir las coordenadas alfanuméricas (ej. "A5")
     * a índices matriciales [fila, columna] procesables por el sistema.
     */
    private int[] parsearCoordenadas(String entrada) throws JuegoException {
        // Validación de longitud mínima para evitar errores de índice.
        if (entrada.length() < 2) {
            throw new JuegoException("Formato de coordenada incorrecto.");
        }

        // Conversión de carácter a índice numérico utilizando aritmética de caracteres (ASCII).
        // 'A' - 'A' = 0; 'B' - 'A' = 1, etc.
        char letraFila = entrada.charAt(0);
        if (letraFila < 'A' || letraFila > 'Z') {
            throw new JuegoException("La fila debe ser una letra (A-J).");
        }
        int fila = letraFila - 'A';

        // Procesamiento de la parte numérica.
        String numColStr = entrada.substring(1).trim();
        int col;
        try {
            // Ajuste de índice: El usuario ve 1-10, pero la matriz usa 0-9.
            col = Integer.parseInt(numColStr) - 1;
        } catch (NumberFormatException e) {
            throw new JuegoException("La columna debe ser un número.");
        }

        return new int[]{fila, col};
    }

    /**
     * Implementación de persistencia de datos mediante Serialización.
     * Guarda el estado completo del objeto 'tablero' en un archivo binario.
     */
    private void guardarPartida() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("buscaminas.dat"))) {
            out.writeObject(tablero); // Serialización del objeto.
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo guardar la partida.");
        }
    }

    /**
     * Recupera el estado del juego deserializando el objeto desde el archivo binario.
     * Permite reanudar una sesión previa.
     */
    private void cargarPartida() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("buscaminas.dat"))) {

            this.tablero = (Tablero) in.readObject(); // Casting explícito del objeto recuperado.
            vista.mostrarMensaje("Partida cargada. ¡A jugar!");
            esperarEnter();
            jugar(); // Llamada recursiva para reiniciar el bucle de juego con los datos cargados.

        } catch (Exception e) {
            vista.mostrarMensaje("No existe ninguna partida guardada.");
            esperarEnter();
        }
    }

    private void esperarEnter() {
        System.out.println("Presiona ENTER para continuar...");
        scanner.nextLine();
    }
}
