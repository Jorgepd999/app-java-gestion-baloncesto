/**
 * @author JORGE PEREZ
 */

package modelo.metodos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.equipos.Equipo;
import modelo.jugadores.*;
import modelo.partidos.Partido;
import modelo.personas.Arbitro;
import vista.entradaSalida.ES;

public class Metodos {
    private static List<String> equiposRivales;
    private static List<String> equiposYaJugados;
    private static List<Arbitro> arbitrosDisponibles = new ArrayList<>();

    static {
        // Cargar los equipos rivales y los equipos ya jugados al iniciar la clase
        cargarEquiposRivales();
        cargarEquiposYaJugados();
    }

    public static String obtenerRivalAleatorio(boolean esOficial) {
        List<String> rivalesDisponibles = new ArrayList<>(equiposRivales);

        if (esOficial) {
            // Filtrar los equipos rivales que ya han sido jugados
            rivalesDisponibles.removeAll(equiposYaJugados);
        }

        if (rivalesDisponibles.isEmpty()) {
            System.out.println();
            return ES.NINGUN_RIVAL;
        }

        // Elegir un equipo aleatorio
        Collections.shuffle(rivalesDisponibles);
        return rivalesDisponibles.get(0);
    }

    public static void cargarEquiposRivales() {
        equiposRivales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ES.RUTA_PRINCIPAL + ES.EQUIPOS_FICHERO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty() && !equiposRivales.contains(linea.trim())) {
                    equiposRivales.add(linea.trim());
                }
            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
        }
    }

    // Cargar equipos ya jugados desde el fichero historicoEquipo.txt
    public static void cargarEquiposYaJugados() {
        equiposYaJugados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_OFICIAL))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith(ES.PARTIDO_CONTRA)) {
                    String equipo = linea.replace(ES.PARTIDO_CONTRA, "").trim();
                    equiposYaJugados.add(equipo);
                }
            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
        }
    }

    public static void marcarEquipoComoJugado(String equipo) {
        // Solo marcar como jugado si no está ya en la lista
        if (!equiposYaJugados.contains(equipo)) {
            equiposYaJugados.add(equipo);
        }
    }

    public static void menuPrincipal(Scanner sc, Equipo miEquipo) {

        int seleccion = -1;
        do {
            try {
                System.out.print(ES.MENU_SELECCION);
                seleccion = sc.nextInt();
            } catch (InputMismatchException e) {
            } finally {
                sc.nextLine();
            }

            menuSwitch(sc, seleccion, miEquipo);

        } while (seleccion != 0);
    }

    public static void menuSwitch(Scanner sc, int seleccion, Equipo miEquipo) {
        switch (seleccion) {
            case ES.OPCION_1:
                agregarJugadorEquipoConsola(sc, miEquipo);
                break;
            case ES.OPCION_2:
                miEquipo.mostrarJugadores();
                eliminarJugadorPorConsola(sc, miEquipo);
                break;
            case ES.OPCION_3:
                jugarPartido(sc, miEquipo);
                break;
            case ES.OPCION_4:
                MetodosFicheros.cargarUltimoPartidoDesdeFichero();
                break;
            case ES.OPCION_5:
                verHistoricoPartidosOficialExhibicion(sc);
                break;
            case ES.OPCION_6:
                mostrarHistoricoJugadores();
                break;
            case ES.OPCION_7:
                miEquipo.mostrarJugadores();
                break;
            case ES.OPCION_0:
                System.out.println(ES.DESPEDIDA);
                break;
            default:
                System.out.println(ES.OPCION_ERRONEA);
                break;
        }
    }

    public static int seleccionHistoricoOficialExhibicion(Scanner sc) {
        int seleccion;
        do {
            try {
                do {
                    System.out.print(ES.ELECCION_HISTORICO);
                    seleccion = sc.nextInt();
                } while (seleccion != 1 && seleccion != 2);
                break;
            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine();
            }
        } while (true);
        return seleccion;
    }

    public static void verHistoricoPartidosOficialExhibicion(Scanner sc) {
        int num = seleccionHistoricoOficialExhibicion(sc);

        if (num == ES.OPCION_1) {
            MetodosFicheros.verHistoricoPartidosOficialesEquipo();
        } else if (num == ES.OPCION_2) {
            MetodosFicheros.verHistoricoPartidosExhibicionEquipo();
        }

    }

    // }
    public static void jugarPartido(Scanner sc, Equipo miEquipo) {
        if (!hayJugadoresSuficientes(miEquipo))
            return;

        int tipoPartido = oficialExhibicion(sc);
        boolean esOficial = tipoPartido == ES.OPCION_1;

        String rival = obtenerRivalAleatorio(esOficial);
        if (rival.equalsIgnoreCase(ES.NINGUN_RIVAL)) {
            System.out.println(ES.NO_EQUIPO_RIVALES);
            return;
        }

        boolean esLocal = ES.seleccionLocalVisitante(sc);

        Arbitro arbitro = obtenerArbitroDisponible();
        if (!arbitroDisponible(arbitro))
            return;

        Partido partido = crearPartido(esOficial, esLocal, arbitro, miEquipo);
        mostrarResumenYMarcarJugado(partido);
    }

    private static boolean hayJugadoresSuficientes(Equipo equipo) {
        if (equipo.getJugadores().size() <= ES.OPCION_4) {
            System.out.println(ES.NO_HAY_JUGADORES + ES.NO_JUGAR_PARTIDO);
            return false;
        }
        return true;
    }

    private static boolean arbitroDisponible(Arbitro arbitro) {
        if (arbitro == null) {
            System.out.println(ES.NO_JUGAR_PARTIDO);
            return false;
        }
        return true;
    }

    private static Partido crearPartido(boolean esOficial, boolean esLocal, Arbitro arbitro, Equipo miEquipo) {
        return new Partido(esOficial, esLocal, arbitro, miEquipo);
    }

    private static void mostrarResumenYMarcarJugado(Partido partido) {
        partido.mostrarResumen();
        Metodos.marcarEquipoComoJugado(partido.getEquipoContrario());
        System.out.println(ES.PARTIDO_CONTRA + partido.getEquipoContrario() + ES.REGISTRADO);
    }

    public static int oficialExhibicion(Scanner sc) {
        int eleccion = -1;
        do {
            try {
                System.out.print(ES.ELECCION_OFICIAL_EXHIBICION);
                eleccion = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine();
            }

        } while (eleccion != 1 && eleccion != 2);
        return eleccion;
    }

    // Creacion jugador
    public static Jugador creacionJugador(Scanner sc, Equipo miEquipo) {
        String nombre = ES.nombreJugador(sc);
        int dorsal = ES.dorsalJugador(sc, miEquipo);
        double altura = ES.alturaJugador(sc);
        int habilidad = ES.habilidadJugador(sc);
        Jugador jugador = JugadorFactory.crearJugador(nombre, dorsal, altura, habilidad);

        return jugador;
    }

    public static int confirmarCreacionJugador(Scanner sc, String nombre, int dorsal) {
        int eleccion = 0;
        do {
            try {
                System.out.print(
                        "¿Estás seguro de añadir al jugador " + nombre + " con el dorsal " + dorsal + " ? 1.Si 2.No: ");
                eleccion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine();
            }
        } while (eleccion != 1 && eleccion != 2);
        return eleccion;
    }

    public static void agregarJugadorEquipoConsola(Scanner sc, Equipo miEquipo) {
        Jugador jugador = creacionJugador(sc, miEquipo);
        int eleccion = confirmarCreacionJugador(sc, jugador.getNombre(), jugador.getDorsal());
        if (eleccion == 1) {
            miEquipo.agregarJugadorEquipo(jugador);
        } else {
            System.out.println(ES.NO_AGREGAR_JUGADOR);
            return;
        }

    }

    public static void eliminarJugadorPorConsola(Scanner sc, Equipo miEquipo) {
        int dorsal = ES.eliminarJugador(sc);
        if (confirmarBorrarJugador(sc) == 1) {
            miEquipo.eliminarJugadorEquipo(dorsal);
        } else {
            System.out.println(ES.NO_ELIMINAR_JUGADOR);
            return;
        }

    }

    public static int confirmarBorrarJugador(Scanner sc) {
        int eleccion = 0;
        do {
            try {
                System.out.print(ES.CONFIRMAR_ELIMINAR_JUGADOR);
                eleccion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {

                sc.nextLine();

            }

        } while (eleccion != 1 && eleccion != 2);
        return eleccion;
    }

    public static Arbitro creacionArbitro(Scanner sc) {
        String nombre = ES.nombreArbitro(sc);
        Arbitro arbitro = new Arbitro(nombre);
        return arbitro;
    }

    public static void mostrarHistoricoJugadores() {
        System.out.println(ES.HISTORICO + ES.ALA_PIVOT);
        mostrarEstadisticasJugadores(ES.RUTA_PRINCIPAL + ES.ALA_PIVOT_FICHERO_RUTA);
        System.out.println(ES.HISTORICO + ES.ALERO);
        mostrarEstadisticasJugadores(ES.RUTA_PRINCIPAL + ES.ALEROS_FICHERO_RUTA);
        System.out.println(ES.HISTORICO + ES.BASE);
        mostrarEstadisticasJugadores(ES.RUTA_PRINCIPAL + ES.BASE_FICHERO_RUTA);
        System.out.println(ES.HISTORICO + ES.ESCOLTA);
        mostrarEstadisticasJugadores(ES.RUTA_PRINCIPAL + ES.ESCOLTAS_FICHERO_RUTA);
        System.out.println(ES.HISTORICO + ES.PIVOT);
        mostrarEstadisticasJugadores(ES.RUTA_PRINCIPAL + ES.PIVOTS_FICHERO_RUTA);
    }

    public static void mostrarEstadisticasJugadores(String rutaFichero) {
        Map<String, String[]> estadisticas = MetodosFicheros.leerEstadisticasDesdeArchivo(rutaFichero);
        mostrarEstadisticas(estadisticas);
    }

    public static boolean esLineaValidaJugador(String linea) {
        return linea.contains("Dorsal:") &&
                linea.contains("Faltas cometidas:") &&
                linea.contains("Puntos anotados:");
    }

    public static void procesarLineaJugador(String linea, Map<String, String[]> estadisticas) {
        String[] partes = linea.split(", ");

        if (partes.length >= 7) {
            try {
                String nombre = partes[0].trim();
                String dorsal = partes[1].split(":")[1].trim();
                String tipo = partes[4].split(":")[1].trim();
                int faltas = Integer.parseInt(partes[5].split(":")[1].trim());
                int puntos = Integer.parseInt(partes[6].split(":")[1].trim());

                estadisticas.put(nombre,
                        new String[] {
                                "Dorsal: " + dorsal,
                                "Tipo: " + tipo,
                                "Puntos: " + puntos,
                                "Faltas: " + faltas
                        });

            } catch (Exception e) {
                System.out.println("Error procesando la línea: " + linea);
            }
        } else {
            System.out.println("Línea mal formada: " + linea);
        }
    }

    private static void mostrarEstadisticas(Map<String, String[]> estadisticas) {
        for (Map.Entry<String, String[]> entry : estadisticas.entrySet()) {
            String nombre = entry.getKey();
            String[] datos = entry.getValue();
            System.out.printf("-%s | %s | %s | %s | %s%n",
                    nombre,
                    datos[0], // Dorsal
                    datos[1], // Tipo
                    datos[2], // Puntos Totales
                    datos[3] // Faltas Totales
            );
        }
    }

    // Seleccionar un árbitro disponible (que no esté enfermo)
    public static Arbitro obtenerArbitroDisponible() {
        List<Arbitro> arbitrosSanos = new ArrayList<>();

        for (Arbitro arbitro : arbitrosDisponibles) {
            arbitro.determinarEnfermedad(); // Actualizamos estado
            if (!arbitro.estaEnfermo()) {
                arbitrosSanos.add(arbitro);
            }
        }

        if (arbitrosSanos.isEmpty()) {
            System.out.println(ES.NO_HAY_ARBITROS);
            return null;
        }

        // Elegir uno aleatorio de los sanos
        int indice = (int) (Math.random() * arbitrosSanos.size());
        return arbitrosSanos.get(indice);
    }

    // Cargar árbitros desde el fichero arbitros.txt
    public static void cargarArbitros() {
        File archivoArbitros = new File(ES.RUTA_PRINCIPAL + ES.ARBITRO_FICERO_RUTA);
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoArbitros))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String nombre = linea.trim();
                if (!nombre.isEmpty()) {
                    Arbitro arbitro = new Arbitro(nombre);
                    // Aquí no determinamos si el árbitro está enfermo todavía
                    arbitrosDisponibles.add(arbitro);
                }
            }
        } catch (IOException e) {
            System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
        }
    }

}
