package ups.edu.modelo;

public interface ITableroConsultable
{
    /*
    Interface que define los métodos para consultar el estado del tablero de juego.
    Permite obtener el tamaño del tablero y acceder a las casillas individuales.
    Esta separación de responsabilidades facilita la mantenibilidad y escalabilidad del código.
    Aplicando el principio ISP (Interface Segregation Principle) de SOLID.
     */
    int getTamano();

    Casilla getCasilla(int fila, int columna);
}
