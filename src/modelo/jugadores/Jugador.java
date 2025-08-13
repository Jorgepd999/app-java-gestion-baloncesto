/**
 * @author JORGE PEREZ
 */
package modelo.jugadores;

import modelo.personas.Persona;
import vista.entradaSalida.ES;

public abstract class Jugador extends Persona {

    // Atributos
    protected int dorsal;
    protected int puntos;
    protected int falta;
    protected double altura;
    protected int habilidad;
    protected String tipo;

    // Constructor
    public Jugador(String nombre, int dorsal, double altura, int habilidad) {
        super(nombre);
        this.dorsal = dorsal;
        this.puntos = 0;
        this.falta = 0;
        this.altura = altura;
        this.habilidad = habilidad;
        this.tipo = calcularPosicion();
    }

    public abstract void anotarPuntos(int puntos);

    public abstract void hacerFalta();

    private String calcularPosicion() {
        // Verificar si los valores de altura y habilidad están dentro del rango
        // esperado
        if (habilidad < 1 || habilidad > 5 || altura < 1.70 || altura > 2.20) {
            throw new IllegalArgumentException("Valores de altura o habilidad fuera de rango.");
        }
        

        // Normalizar la altura entre 1.70 y 2.20 metros
        double alturaNormalizada = (altura - 1.70) / (2.20 - 1.70);

        // Normalizar la habilidad entre 1 y 5 estrellas (usando 'double' para evitar
        // división entera)
        double habilidadNormalizada = (double) (habilidad - 1) / (5 - 1); // Aseguramos que la división sea de punto
                                                                          // flotante

        // Calcular el puntaje con más peso a la habilidad (70%) y menos a la altura
        // (30%)
        double puntaje = (0.7 * habilidadNormalizada) + (0.3 * (1 - alturaNormalizada));

        // Asignar la posición en base al puntaje
        if (puntaje >= 0.8) {
            return ES.BASE;
        } else if (puntaje >= 0.6) {
            return ES.ESCOLTA;
        } else if (puntaje >= 0.4) {
            return ES.ALERO;
        } else if (puntaje >= 0.2) {
            return ES.ALA_PIVOT;
        } else {
            return ES.PIVOT;
        }
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getFalta() {
        return falta;
    }

    public void setFalta(int falta) {
        this.falta = falta;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(int habilidad) {
        this.habilidad = habilidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.nombre + ", Dorsal: " + this.dorsal + ", Altura: " + this.altura + ", Habilidad: "
                + this.habilidad + ", Posicion: " + this.tipo + ", Faltas cometidas: " + this.falta
                + ", Puntos anotados: " + this.puntos;

    }

}
