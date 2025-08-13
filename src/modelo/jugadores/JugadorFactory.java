/**
 * @author JORGE PEREZ
 */
package modelo.jugadores;
//JugadorFactory: Es una clase de fábrica que usa la lógica de cálculo del tipo
// (calculado a partir de la altura y habilidad) para decidir qué tipo de objeto crear (por ejemplo, Base, Escolta, etc.).
// Así, se crea un jugador del tipo correcto sin tener que decidirlo manualmente.
public class JugadorFactory {

    public static Jugador crearJugador(String nombre,int dorsal, double altura, int habilidad) {
       
            // Primero calculamos el tipo
            double alturaNormalizada = (altura - 1.70) / (2.20 - 1.70);
            double habilidadNormalizada = (double)(habilidad - 1) / (5 - 1); 
            double puntaje = (0.7 * habilidadNormalizada) + (0.3 * (1 - alturaNormalizada));

            // Según el puntaje, decidimos el tipo y creamos la clase correspondiente
            if (puntaje >= 0.8) {
                return new Base(nombre,dorsal, altura, habilidad);  // Si el puntaje es alto, crea un Base
            } else if (puntaje >= 0.6) {
                return new Escolta(nombre,dorsal, altura, habilidad);  // Si el puntaje es medio-alto, crea un Escolta
            } else if (puntaje >= 0.4) {
                return new Alero(nombre,dorsal, altura, habilidad);  // Crea un Alero
            } else if (puntaje >= 0.2) {
                return new AlaPivot(nombre,dorsal, altura, habilidad);  // Crea un Ala-pívot
            } else {
                return new Pivot(nombre,dorsal, altura, habilidad);  // Crea un Pívot si el puntaje es bajo
            }
    }
}
