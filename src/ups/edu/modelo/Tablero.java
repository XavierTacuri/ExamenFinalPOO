package ups.edu.modelo;
import java.io.*;
import java.util.Random;

public class Tablero implements ITableroJugable, Serializable {

    /*
    Aplicación del principio SRP (Single Responsibility Principle).
    La clase Tablero se encarga exclusivamente de gestionar el estado del tablero de juego,
    delegando las tareas específicas de validar victoria, gestionar el descubrimiento
    generar el tablero, colocar las minas en clases especializadas.
     */
    private static final long serialVersionUID = 1L;
    private static final int TAMANO = 10;
    private static final int NUM_MINAS = 10;
    private Casilla[][] casillas;

    private transient GeneradorTablero generador;
    private transient ColocadorMinas colocadorMinas;
    private transient CalculadorMinasVecinas calculador;
    private transient GestorDescubrimiento gestorDescubrimiento;
    private transient ValidadorVictoria validadorVictoria;


    public Tablero() {
        casillas = new Casilla[TAMANO][TAMANO];
        inicializarDelegados();
        inicializar();
    }

    private void inicializarDelegados() {
        this.generador = new GeneradorTablero();
        this.colocadorMinas = new ColocadorMinas();
        this.calculador = new CalculadorMinasVecinas();
        this.gestorDescubrimiento = new GestorDescubrimiento();
        this.validadorVictoria = new ValidadorVictoria();
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();  // Lee casillas[][]
        inicializarDelegados();   // Recrea los delegados
    }

    private void inicializar() {
        this.casillas = generador.inicializarTablero(TAMANO);
        colocadorMinas.colocarMinas(casillas, TAMANO, NUM_MINAS);
        calculador.calcularMinasVecinas(casillas, TAMANO);
    }

    @Override
    public boolean descubrirCasilla(int fila, int col)
            throws CasillaDescubierta, JuegoException {
        validarCoordenadas(fila, col);
        return gestorDescubrimiento.descubrirCasilla(casillas, fila, col, TAMANO);
    }

    @Override
    public boolean esVictoria() {
        return validadorVictoria.esVictoria(casillas, TAMANO);
    }

    @Override
    public int getTamano()
    {
        return TAMANO;
    }

    @Override
    public Casilla getCasilla(int fila, int col)
    {
        return casillas[fila][col];
    }

    private void validarCoordenadas(int fila, int col) throws JuegoException {
        if (fila < 0 || fila >= TAMANO || col < 0 || col >= TAMANO) {
            throw new JuegoException("Coordenadas fuera de rango.");
        }
    }

    @Override
    public void marcarCasilla(int fila, int col) throws JuegoException
    {
        validarCoordenadas(fila, col);
        Casilla c = casillas[fila][col];
        if (c.estaDescubierta()) {
            throw new JuegoException("No puedes marcar una casilla descubierta.");
        }
        c.alternarMarcada();
    }

}