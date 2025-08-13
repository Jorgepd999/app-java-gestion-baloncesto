/**
 * @author JORGE PEREZ
 */
package vista.entradaSalida;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import modelo.equipos.Equipo;
import modelo.jugadores.Jugador;

public class ES {
    public final static String BIENVENIDA = "BIENVENIDO AL GESTOR DEL EQUIPO ";
    public final static String DESPEDIDA = "¡Adios, vuelva pronto!";
    public final static String MENU_SELECCION = """
            1.Añadir nuevo jugador.
            2.Eliminar jugador.
            3.Jugar un partido.
            4.Mostrar resumen del último partido oficial.
            5.Mostrar histórico de la temporada.
            6.Mostrar resumen de jugadores en partidos oficiales.
            7.Mostrar jugadores del equipo.
            0.Salir
            Opcion: """;

    // Opciones
    public final static int OPCION_0 = 0;
    public final static int OPCION_1 = 1;
    public final static int OPCION_2 = 2;
    public final static int OPCION_3 = 3;
    public final static int OPCION_4 = 4;
    public final static int OPCION_5 = 5;
    public final static int OPCION_6 = 6;
    public final static int OPCION_7 = 7;

    public final static String BASE = "Base";
    public final static String ALERO = "Alero";
    public final static String PIVOT = "Pivot";
    public final static String ALA_PIVOT = "Ala-Pivot";
    public final static String ESCOLTA = "Escolta";

    // Rutas ficheros
    public final static String RUTA_PRINCIPAL = System.getProperty("user.dir") + "/archivos/";
    public final static String RUTA_FICHEROS = "./archivos/";
    public final static String EQUIPOS_FICHERO = "equipos.txt";
    public final static String HISTORICO_EQUIPO_FICHERO_OFICIAL = "historicoEquipoOficial.txt";
    public final static String HISTORICO_EQUIPO_FICHERO_EXHIBICION = "historicoEquipoExhibicion.txt";
    public final static String BASE_FICHERO_RUTA = "bases.txt";
    public final static String ALA_PIVOT_FICHERO_RUTA = "alaPivots.txt";
    public final static String ALEROS_FICHERO_RUTA = "aleros.txt";
    public final static String ESCOLTAS_FICHERO_RUTA = "escoltas.txt";
    public final static String PIVOTS_FICHERO_RUTA = "pivots.txt";
    public final static String ARBITRO_FICERO_RUTA = "arbitros.txt";
    public final static String ERROR_LEER_FICHERO = "Error al leer el archivo ";
    public final static String NO_EXISTE_FICHERO = "No existe el fichero.";
    public final static String NO_JUGADORES_EQUIPO = "No hay jugadores en el equipo.";
    public final static String ERROR_RENOMBRAR_ARCHIVO = "No se pudo renombrar el archivo";
    public final static String ERROR_ELIMINAR_ARCHIVO = "No se pudo eliminar el archivo original.";
    public final static String ERROR_GUARDAR_JUGADORES_FICHERO = "Error al guardar el jugador en el fichero: ";

    public final static String JUGADOR_NO_ENCONTRADO = "Jugador no encontrado.";
    public final static String JUGADOR_ELIMINADO = "Jugador eliminado correctamente.";
    public final static String ERROR_ELIMINAR_JUGADOR = "Error al eliminar el jugador del fichero.";
    public final static String JUGADORES_EQUIPO = "Jugadores en el equipo ";
    public final static String JUGADOR = "jugador: ";

    public final static String EQUIPO = "Equipo: ";
    public final static String ARBITRO = "Árbitro: ";
    public final static String NOMBRE = "Escriba el nombre del ";
    public final static String EDAD = "Escriba la edad del ";
    public final static String DORSAL = "Escriba el dorsal del ";
    public final static String ALTURA = "Escriba la altura en metros del ";
    public final static String HABILIDAD = "Escriba la habilidad del jugador (de 1 a 5 estrellas): ";
    public final static String NO_ESCRIBIR_LETRAS = "No escriba letras.";
    public final static String REGISTRADO = " ha sido registrado.";
    public final static String ERROR_ALTURA = "La altura debe estar entre 1.70 y 2.20 metros.";
    public final static String ERROR_HABILIDAD = "La habilidad debe estar entre 1 y 5.";
    public final static String PARTIDO_CONTRA = "Partido contra: ";
    public final static String HISTORICO = "Historico ";
    public final static String JUGADOR_AGREGADO_EQUIPO = "Jugador agregado al equipo.";
    public final static String NO_AGREGAR_JUGADOR = "No se pudo añadir el jugador." ;
    public final static String NO_ELIMINAR_JUGADOR="No se eliminó ningún jugador.";
    public final static String CONFIRMAR_ELIMINAR_JUGADOR="¿Seguro que quiere eliminar el jugador? 1.Si 2.No ";
    public final static String NOMBRE_INVALIDO="Nombre inválido. Debe contener al menos 3 letras y no incluir números.";
    public final static String ERROR_DORSAL="Error: El dorsal debe estar entre 1 y 99. Por favor, introduce otro.";
    public final static String DORSAL_EN_USO="Ese dorsal ya está en uso. Por favor, introduce otro.";

    public final static String NO_EQUIPO_RIVALES = "No hay equipos rivales disponibles.";
    public final static String NO_HAY_JUGADORES = "Hay menos de 5 jugadores.";
    public final static String NO_HAY_ARBITROS = "No hay árbitros disponibles.";
    public final static String NO_JUGAR_PARTIDO = "No se puede jugar el partido.";
    public final static String NINGUN_RIVAL = "Ningún rival";
    public final static String NO_PARTIDOS_GUARDADOS = "No se encontraron partidos guardados.";
    public final static String OPCION_ERRONEA = "Opcion incorrecta.";

    public final static String ELECCION_HISTORICO = "1. Histórico partidos oficiales 2. Histórico partidos exhibición:  ";
    public final static String ELECCION_LOCAL_VISITANTE = "¿Jugarás como local? (1. Sí, 2. No): ";
    public final static String ELECCION_OFICIAL_EXHIBICION = "1. Oficial 2. Exhibicion: ";
    public final static String RESUMEN_PARTIDO = "----- Resúmenes de Partidos -----";
    public final static String FIN_RESUMEN = "----- Fin de Resúmenes -----";
    public final static String NO_PARTIDOS_JUGADOS = "No hay partidos jugados.";
    public final static String PLANTILLA_COMPLETA=" Plantilla completa.";
    public final static String NO_HAY_JUGADORES_PUNTOS="No hay jugadores para repartir puntos.";

    public static String nombreArbitro(Scanner sc) {
        System.out.print(NOMBRE + ARBITRO);
        String nombreArbitro = sc.nextLine();
        return nombreArbitro;
    }

    public static String nombreEquipo(Scanner sc) {
        System.out.print(NOMBRE + EQUIPO);
        String nombreEquipo = sc.nextLine();
        return nombreEquipo;
    }

    public static String nombreJugador(Scanner sc) {
        String nombreJugador;
        do {
            System.out.print(NOMBRE + JUGADOR);
            nombreJugador = sc.nextLine().trim();

            // Validación: mínimo 3 letras y solo letras (puede incluir espacios si quieres)
            if (!nombreJugador.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{3,}")) {
                System.out.println(NOMBRE_INVALIDO);
            } else {
                break;
            }
        } while (true);

        return nombreJugador;
    }

    public static int edadJugador(Scanner sc) {
        int edadJugador = -1;
        do {
            try {
                System.out.print(EDAD + JUGADOR);
                edadJugador = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print(ES.NO_ESCRIBIR_LETRAS);

            } finally {
                sc.nextLine();
            }
        } while (true);
        return edadJugador;
    }

    public static int dorsalJugador(Scanner sc, Equipo equipo) {
        while (true) {
            System.out.print(DORSAL + JUGADOR); // Pide al usuario que ingrese el dorsal
            try {
                int dorsal = Integer.parseInt(sc.nextLine()); // Lee el dorsal como un número entero

                // Verifica si el dorsal está en el rango permitido (1 a 99)
                if (dorsal < 1 || dorsal > 99) {
                    System.out.println(ERROR_DORSAL);
                    continue; // Continúa pidiendo un dorsal si no está en el rango correcto
                }

                boolean repetido = false;
                // Verifica si el dorsal ya está en uso
                for (Jugador jugador : equipo.getJugadores()) {
                    if (jugador.getDorsal() == dorsal) {
                        repetido = true;
                        break; // Si se encuentra un dorsal repetido, se sale del ciclo
                    }
                }

                // Si el dorsal ya está en uso, se avisa al usuario
                if (repetido) {
                    System.out.println(DORSAL_EN_USO);
                } else {
                    return dorsal; // Si el dorsal es válido y no está repetido, se retorna
                }

            } catch (NumberFormatException e) {
                // Si no se puede convertir la entrada a número, se avisa al usuario
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            }
        }
    }

    public static double alturaJugador(Scanner sc) {
        double alturaJugador = -1;
        do {
            try {
                // Usamos el locale adecuado para asegurar el punto como separador decimal
                sc.useLocale(Locale.US);
                System.out.print(ALTURA + JUGADOR);
                alturaJugador = sc.nextDouble();

                // Validación: Si la altura es menor a 1.75 o mayor a 2.20, vuelve a pedir la
                // altura
                if (alturaJugador < 1.70 || alturaJugador > 2.20) {
                    System.out.println(ERROR_ALTURA);
                } else {
                    // Si la altura es válida, salimos del ciclo
                    break;
                }
            } catch (InputMismatchException e) {
                // Si el usuario ingresa un valor no numérico
                System.out.println(ES.NO_ESCRIBIR_LETRAS);

            } finally {
                sc.nextLine(); // Limpiamos el buffer de entrada
            }
        } while (true);

        return alturaJugador;
    }

    public static int habilidadJugador(Scanner sc) {
        int habilidadJugador = -1;
        do {
            try {
                System.out.print(HABILIDAD);
                habilidadJugador = sc.nextInt();
                if (habilidadJugador <= 0 || habilidadJugador > 5) {
                    System.out.println(ES.ERROR_HABILIDAD);
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.print(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine();
            }
        } while (true);
        return habilidadJugador;
    }

    public static int eliminarJugador(Scanner sc) {
        int dorsal = -1;
        do {
            try {
                System.out.print(ES.DORSAL + ES.JUGADOR);
                dorsal = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine();
            }
        } while (true);
        return dorsal;
    }

    public static boolean seleccionLocalVisitante(Scanner sc) {
        boolean esLocal = true;
        int num;
        do {
            try {
                System.out.print(ES.ELECCION_LOCAL_VISITANTE);
                num = sc.nextInt();
                if (num == ES.OPCION_1) {
                    esLocal = true;
                    break;
                } else if(num== ES.OPCION_2) {
                    esLocal = false;
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ES.NO_ESCRIBIR_LETRAS);
            } finally {
                sc.nextLine(); // Limpiar el buffer
            }

        } while (true);
        return esLocal;
    }
}
