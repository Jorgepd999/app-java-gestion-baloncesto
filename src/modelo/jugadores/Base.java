/**
 * @author JORGE PEREZ
 */
package modelo.jugadores;

 
public class Base extends Jugador {
  
    public Base(String nombre, int dorsal, double altura, int habilidad) {
        super(nombre, dorsal, altura, habilidad);
    }

    public void anotarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void hacerFalta() {
        this.falta++;
    }

   
    
}
