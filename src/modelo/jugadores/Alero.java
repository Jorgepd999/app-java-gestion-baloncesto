/**
 * @author JORGE PEREZ
 */
package modelo.jugadores;

public class Alero extends Jugador {
    public Alero(String nombre,int dorsal,  double altura, int habilidad) {
        super(nombre,dorsal, altura, habilidad);
    }

    public void anotarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void hacerFalta() {
        this.falta++;
    }

}
