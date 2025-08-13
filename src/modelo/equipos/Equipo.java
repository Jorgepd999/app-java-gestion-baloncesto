/**
 * @author JORGE PEREZ
 */

package modelo.equipos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import modelo.jugadores.Jugador;
import modelo.jugadores.JugadorFactory;
import vista.entradaSalida.ES;

public class Equipo {
    // Atributos
    private String nombre;
    private ArrayList<Jugador> jugadores;
    // Instancia Singleton
    private static Equipo instancia;

    private static final int MAX_JUGADORES_POR_TIPO = 3;
    private static final int MAX_JUGADORES = 15;

    // Constructor
    
    // Método Singleton
    public static Equipo getInstancia(String nombre) {
        if (instancia == null) {
            instancia = new Equipo(nombre);
        }
        return instancia;
    }

    private Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
        cargarTodosLosJugadoresFichero();
    }

    // Metodos

    private int contarJugadoresPorTipo(String tipo) {
        int contador = 0;
        for (Jugador j : jugadores) {
            if (j.getTipo().equalsIgnoreCase(tipo)) {
                contador++;
            }
        }
        return contador;
    }

    // Método para verificar si el dorsal ya está en uso en el equipo
    private boolean existeDorsalEnEquipo(int dorsal) {
        for (Jugador jugador : jugadores) {
            if (jugador.getDorsal() == dorsal) {
                return true; // Dorsal ya en uso
            }
        }
        return false; // Dorsal no está en uso
    }

    private void cargarTodosLosJugadoresFichero() {
        String[] tipos = { ES.BASE, ES.ESCOLTA, ES.ALERO, ES.ALA_PIVOT, ES.PIVOT };
        for (String tipo : tipos) {
            File fichero = obtenerFicheroPorTipo(tipo);
            cargarJugadoresDesdeFichero(fichero);
        }
    }

    public boolean agregarJugadorEquipo(Jugador jugador) {

        // Verificar si el dorsal ya está en uso en el equipo
        if (existeDorsalEnEquipo(jugador.getDorsal())) {
            System.out.println("Error: El dorsal " + jugador.getDorsal() + " ya está en uso.");
            return false;
        }
        // Verificar si ya hay 3 jugadores del mismo tipo
        if (contarJugadoresPorTipo(jugador.getTipo()) >= MAX_JUGADORES_POR_TIPO) {
            System.out.println("Error: Ya hay 3 jugadores del tipo " + jugador.getTipo() + ".");
            return false;
        }

        File fichero = obtenerFicheroPorTipo(jugador.getTipo());

        if (!guardarJugadorEnFichero(jugador, fichero)) {
            System.out.println(ES.ERROR_LEER_FICHERO);
            return false;
        }

        if (jugadores.size() < MAX_JUGADORES) {
            jugadores.add(jugador);
            System.out.println(ES.JUGADOR_AGREGADO_EQUIPO + "\n" + "Nombre: " + jugador.getNombre() + "\n"
                    + "Posicion: " + jugador.getTipo() + "\n" + "Dorsal: " + jugador.getDorsal());
            return true;
        }

        System.out.println(ES.NO_AGREGAR_JUGADOR + ES.PLANTILLA_COMPLETA);
        return false;
    }

    public static File obtenerFicheroPorTipo(String tipo) {
        File directorio = new File(ES.RUTA_PRINCIPAL);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        return switch (tipo) {
            case ES.BASE -> new File(ES.RUTA_PRINCIPAL + ES.BASE_FICHERO_RUTA);
            case ES.ESCOLTA -> new File(ES.RUTA_PRINCIPAL + ES.ESCOLTAS_FICHERO_RUTA);
            case ES.ALERO -> new File(ES.RUTA_PRINCIPAL + ES.ALEROS_FICHERO_RUTA);
            case ES.ALA_PIVOT -> new File(ES.RUTA_PRINCIPAL + ES.ALA_PIVOT_FICHERO_RUTA);
            case ES.PIVOT -> new File(ES.RUTA_PRINCIPAL + ES.PIVOTS_FICHERO_RUTA);
            default -> null;
        };
    }

    private static boolean guardarJugadorEnFichero(Jugador jugador, File fichero) {
        if (fichero == null) {
            System.out.println(ES.NO_EXISTE_FICHERO);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichero, true))) {
            writer.write(jugador.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println(ES.ERROR_GUARDAR_JUGADORES_FICHERO + e.getMessage());
            return false;
        }
    }

    public void mostrarJugadores() {
        if (jugadores.isEmpty()) {
            System.out.println(ES.NO_JUGADORES_EQUIPO);
        } else {
            System.out.println(ES.JUGADORES_EQUIPO + nombre + ":");

            // Usamos un for normal con índice para recorrer la lista de jugadores
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador jugador = jugadores.get(i); // Obtener el jugador por índice
                System.out.println(jugador.getNombre() + " Dorsal: " + jugador.getDorsal());
                // jugador
            }
        }
    }

    public Jugador buscarJugadorDorsal(int dorsal) {
        for (Jugador j : jugadores) {
            if (j.getDorsal() == dorsal) {
                return j;
            }
        }
        return null;
    }

    private boolean eliminarJugadorDelFichero(Jugador jugador) {
        File fichero = obtenerFicheroPorTipo(jugador.getTipo());
        if (fichero == null || !fichero.exists()) {
            System.out.println(ES.NO_EXISTE_FICHERO);
            return false;
        }

        File ficheroTemp = new File(fichero.getAbsolutePath() + ".tmp");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                BufferedWriter writer = new BufferedWriter(new FileWriter(ficheroTemp))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.contains(", Dorsal: " + jugador.getDorsal())) {
                    writer.write(linea);
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
            return false;
        }

        if (fichero.delete()) {
            if (!ficheroTemp.renameTo(fichero)) {
                System.out.println(ES.ERROR_RENOMBRAR_ARCHIVO);
                return false;
            }
        } else {
            System.out.println(ES.ERROR_ELIMINAR_ARCHIVO);
            return false;
        }

        return true;
    }

    public boolean eliminarJugadorEquipo(int dorsal) {
        Jugador jugadorABorrar = buscarJugadorDorsal(dorsal);

        if (jugadorABorrar == null) {
            System.out.println(ES.JUGADOR_NO_ENCONTRADO);
            return false;
        }

        // Eliminar de la lista en memoria
        jugadores.remove(jugadorABorrar);

        // Eliminar del fichero
        if (eliminarJugadorDelFichero(jugadorABorrar)) {
            System.out.println(ES.JUGADOR_ELIMINADO + " " + jugadorABorrar.getNombre() + " Dorsal: " + dorsal);
            return true;
        } else {
            System.out.println(ES.ERROR_ELIMINAR_JUGADOR);
            return false;
        }
    }

    public static Jugador parsearJugador(String linea) {
        try {
            String[] partes = linea.split(", ");

            String nombre = partes[0].trim();
            int dorsal = Integer.parseInt(partes[1].split(":")[1].trim());
            double altura = Double.parseDouble(partes[2].split(":")[1].replace(" metros", "").trim());
            int habilidad = Integer.parseInt(partes[3].split(":")[1].trim());
            int faltas = Integer.parseInt(partes[5].split(":")[1].trim());
            int puntos = Integer.parseInt(partes[6].split(":")[1].trim());

            // Creamos el jugador con tipo usando el factory
            Jugador jugador = JugadorFactory.crearJugador(nombre, dorsal, altura, habilidad);
            jugador.setFalta(faltas);
            jugador.setPuntos(puntos);

            return jugador;

        } catch (Exception e) {
            System.out.println("Error al parsear jugador: " + e.getMessage());
            return null;
        }
    }

    public void cargarJugadoresDesdeFichero(File fichero) {
        if (fichero == null || !fichero.exists()) {
            System.out.println("Fichero no encontrado: " + (fichero != null ? fichero.getAbsolutePath() : "null"));
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Jugador jugador = parsearJugador(linea);
                if (jugador != null) {
                    jugadores.add(jugador);
                }
            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
        }
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public static int getMaxJugadores() {
        return MAX_JUGADORES;
    }

    @Override
    public String toString() {
        return nombre;
    }

}

