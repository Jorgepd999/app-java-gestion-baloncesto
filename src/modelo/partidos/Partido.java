/**
 * @author JORGE PEREZ
 */

package modelo.partidos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.equipos.Equipo;
import modelo.jugadores.Jugador;
import modelo.metodos.Metodos;
import modelo.metodos.MetodosFicheros;
import modelo.personas.Arbitro;
import vista.entradaSalida.ES;

public class Partido {
    private Map<Jugador, int[]> rendimientoPartido = new HashMap<>();
    private String equipoContrario;
    private boolean esOficial;
    private boolean esLocal;
    private int puntosEquipo;
    private int puntosContrario;
    private Arbitro arbitro;
    private Equipo miEquipo;

    public Partido(boolean esOficial, boolean esLocal, Arbitro arbitro, Equipo miEquipo) {
        // Verificamos si hay equipos disponibles antes de crear el partido
        if (!MetodosFicheros.hayEquiposDisponibles()) {
            System.out.println(ES.NO_EQUIPO_RIVALES);
            return; // No se crea el partido
        }

        this.esOficial = esOficial;
        this.esLocal = esLocal;
        this.arbitro = arbitro;
        this.miEquipo = miEquipo;

        // Si hay equipos disponibles, seleccionamos un rival
        this.equipoContrario = Metodos.obtenerRivalAleatorio(esOficial);
        if (equipoContrario == null) {
            System.out.println(ES.NO_EQUIPO_RIVALES);
            return; // No se puede obtener un rival, no se crea el partido
        }

        calcularResultado();
        repartirPuntosEntreJugadores();
        if (esOficial) {
            guardarJugadoresActualizados();
            guardarPartidoEnFicheroOficial();
        } else {
            guardarPartidoEnFicheroExhibicion();
        }

    }

    private void calcularResultado() {
        // Generamos un número aleatorio entre 0 y 1
        double aleatorio = Math.random();

        // Si es equipo local, le damos más probabilidad de ganar
        if (esLocal) {
            aleatorio = Math.pow(aleatorio, 0.5); // Aumento de la probabilidad de obtener valores altos
        }

        if (aleatorio < 0.2) {
            // Los puntos tienden a estar más cerca de 35 a 70
            puntosEquipo = (int) (Math.random() * 35 + 35);
        } else if (aleatorio < 0.8) {
            // Los puntos tienden a estar más cerca de 70 a 100 (rango más común)
            puntosEquipo = (int) (Math.random() * (100 - 70) + 70);
        } else {
            // Los puntos tienden a estar más cerca de 100 a 150
            puntosEquipo = (int) (Math.random() * (150 - 100) + 100);
        }

        // Para el equipo contrario, calculamos el puntaje de manera similar
        aleatorio = Math.random();
        if (aleatorio < 0.2) {
            puntosContrario = (int) (Math.random() * 35 + 35);
        } else if (aleatorio < 0.8) {
            puntosContrario = (int) (Math.random() * (100 - 70) + 70);
        } else {
            puntosContrario = (int) (Math.random() * (150 - 100) + 100);
        }
    }

    public void mostrarResumen() {
        System.out.println("Partido contra: " + equipoContrario);
        System.out.println("Tipo: " + (esOficial ? "Oficial" : "Exhibición"));
        System.out.println("Local: " + (esLocal ? "Sí" : "No"));
        System.out.println(ES.ARBITRO + arbitro.getNombre());
        System.out.println("Resultado: " + puntosEquipo + " - " + puntosContrario);

        for (Jugador jugador : miEquipo.getJugadores()) {
            int[] rendimiento = rendimientoPartido.get(jugador);
            if (rendimiento != null) {
                System.out.println("- " + jugador.getNombre()
                        + " | Puntos: " + rendimiento[0]
                        + " | Faltas: " + rendimiento[1]);
            } else {
                System.out.println("- " + jugador.getNombre() + " | Sin datos del partido");
            }
        }
    }

    private void guardarPartidoEnFicheroOficial() {

        File ficheroHistorico = new File(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_OFICIAL);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ficheroHistorico, true))) {
            writer.write("Partido contra: " + equipoContrario);
            writer.newLine();
            writer.write("Tipo: " + (esOficial ? "Oficial" : "Exhibición"));
            writer.newLine();
            writer.write("Local: " + (esLocal ? "Sí" : "No"));
            writer.newLine();
            writer.write("Árbitro: " + arbitro.getNombre());
            writer.newLine();
            writer.write("Resultado: " + puntosEquipo + " - " + puntosContrario);
            writer.newLine();
            writer.write("Resumen de jugadores:");
            writer.newLine();

            for (Jugador jugador : miEquipo.getJugadores()) {
                int[] rendimiento = rendimientoPartido.get(jugador);
                if (rendimiento != null) {
                    writer.write("- " + jugador.getNombre()
                            + " | Puntos: " + rendimiento[0]
                            + " | Faltas: " + rendimiento[1]);
                    writer.newLine(); // Asegura que cada jugador va en línea separada
                }
            }

            writer.write("---"); // Separador de partidos
            writer.newLine();
            writer.newLine(); // Línea extra para mejorar la legibilidad
        } catch (IOException e) {
            System.out.println("Error al guardar el partido en el fichero: " + e.getMessage());
        }
    }

    private void guardarPartidoEnFicheroExhibicion() {

        File ficheroHistorico = new File(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_EXHIBICION);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ficheroHistorico, true))) {
            writer.write("Partido contra: " + equipoContrario);
            writer.newLine();
            writer.write("Tipo: " + (esOficial ? "Oficial" : "Exhibición"));
            writer.newLine();
            writer.write("Local: " + (esLocal ? "Sí" : "No"));
            writer.newLine();
            writer.write("Árbitro: " + arbitro.getNombre());
            writer.newLine();
            writer.write("Resultado: " + puntosEquipo + " - " + puntosContrario);
            writer.newLine();
            writer.write("Resumen de jugadores:");
            writer.newLine();

            for (Jugador jugador : miEquipo.getJugadores()) {
                int[] rendimiento = rendimientoPartido.get(jugador);
                if (rendimiento != null) {
                    writer.write("- " + jugador.getNombre()
                            + " | Puntos: " + rendimiento[0]
                            + " | Faltas: " + rendimiento[1]);
                    writer.newLine(); // Asegura que cada jugador va en línea separada
                }
            }

            writer.write("---"); // Separador de partidos
            writer.newLine();
            writer.newLine(); // Línea extra para mejorar la legibilidad
        } catch (IOException e) {
            System.out.println(ES.ERROR_GUARDAR_JUGADORES_FICHERO + e.getMessage());
        }
    }

    private void repartirPuntosEntreJugadores() {
        List<Jugador> jugadores = miEquipo.getJugadores();
        int totalPuntos = puntosEquipo;
        rendimientoPartido.clear(); // Reiniciamos por si acaso

        if (jugadores.isEmpty()) {
            System.out.println(ES.NO_HAY_JUGADORES_PUNTOS);
            return;
        }

        int puntosRestantes = totalPuntos;
        int numJugadores = jugadores.size();

        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = jugadores.get(i);
            int puntos;

            if (i == numJugadores - 1) {
                puntos = puntosRestantes;
            } else {
                puntos = (int) (Math.random() * Math.max(1, puntosRestantes / 2.0));
            }

            puntosRestantes -= puntos;
            int faltas = (int) (Math.random() * 4); // 0-3 faltas

            // Acumular en el objeto jugador
            jugador.setPuntos(jugador.getPuntos() + puntos);
            jugador.setFalta(jugador.getFalta() + faltas);

            // Guardamos solo este partido
            rendimientoPartido.put(jugador, new int[] { puntos, faltas });
        }
    }

    private void guardarJugadoresActualizados() {
        for (Jugador jugador : miEquipo.getJugadores()) {
            File fichero = Equipo.obtenerFicheroPorTipo(jugador.getTipo());
            if (fichero != null) {
                actualizarJugadorEnFichero(jugador, fichero);
            }
        }
    }

    private void actualizarJugadorEnFichero(Jugador jugador, File fichero) {
        File tempFile = new File(fichero.getAbsolutePath() + ".tmp");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Jugador j = Equipo.parsearJugador(linea);
                if (j != null && j.getNombre().equalsIgnoreCase(jugador.getNombre())
                        && j.getDorsal() == jugador.getDorsal()) {
                    writer.write(jugador.toString()); 
                } else {
                    writer.write(linea);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_GUARDAR_JUGADORES_FICHERO+ e.getMessage());
        }

        // Reemplazamos el original
        if (fichero.delete()) {
            if (!tempFile.renameTo(fichero)) {
                System.out.println(ES.ERROR_RENOMBRAR_ARCHIVO);
            }
        } else {
            System.out.println(ES.ERROR_ELIMINAR_ARCHIVO);
        }

    }

    public String getEquipoContrario() {
        return equipoContrario;
    }

    public void setEquipoContrario(String equipoContrario) {
        this.equipoContrario = equipoContrario;
    }

    public boolean isEsOficial() {
        return esOficial;
    }

    public void setEsOficial(boolean esOficial) {
        this.esOficial = esOficial;
    }

    public boolean isEsLocal() {
        return esLocal;
    }

    public void setEsLocal(boolean esLocal) {
        this.esLocal = esLocal;
    }

    public int getPuntosEquipo() {
        return puntosEquipo;
    }

    public void setPuntosEquipo(int puntosEquipo) {
        this.puntosEquipo = puntosEquipo;
    }

    public int getPuntosContrario() {
        return puntosContrario;
    }

    public void setPuntosContrario(int puntosContrario) {
        this.puntosContrario = puntosContrario;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

}
