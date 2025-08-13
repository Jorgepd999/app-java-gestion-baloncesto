/**
 * @author JORGE PEREZ
 */
package modelo.personas;

public class Arbitro extends Persona {
    private static final double PROBABILIDAD_ENFERMO = 0.2;
    
    private boolean enfermo;

    public Arbitro(String nombre) {
        super(nombre);
        determinarEnfermedad();
    }
   
    public void determinarEnfermedad() {
        this.enfermo = Math.random() < PROBABILIDAD_ENFERMO; // 20% probabilidad de estar enfermo.
    }

    public boolean estaEnfermo() {
        return enfermo;
    }
}
