package ups.edu.modelo;

public interface ITableroJugable extends ITableroConsultable
{
    boolean descubrirCasilla(int fila, int columna)
        throws CasillaDescubierta, JuegoException;

    void marcarCasilla(int fila, int columna)
        throws JuegoException;

    boolean esVictoria();
}
