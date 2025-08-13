/**
 * @author JORGE PEREZ
 */

package modelo.metodos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.*;

import vista.entradaSalida.ES;


public class MetodosFicheros {

   public static void mostarContenidoFicheroJugadores(File fichero) {
      try (Scanner lectorFichero = new Scanner(fichero)) {
         while (lectorFichero.hasNextLine()) {
            System.out.println(lectorFichero.nextLine());
         }
      } catch (FileNotFoundException e) {
         System.out.println(e.getMessage());
      }
   }

   public static void mostrarContenidoFicheroEquipos(File fichero) {
      try (Scanner lectorFichero = new Scanner(fichero)) {
         while (lectorFichero.hasNextLine()) {
            System.out.println(lectorFichero.nextLine());
         }
      } catch (FileNotFoundException e) {
         System.out.println(e.getMessage());
      }
   }

   public static boolean hayEquiposDisponibles() {
      File archivoEquipos = new File(ES.RUTA_FICHEROS + ES.EQUIPOS_FICHERO); // Ruta del archivo de equipos
      if (!archivoEquipos.exists() || archivoEquipos.length() == 0) {
         return false; // Si el archivo no existe o está vacío, no hay equipos disponibles
      }

      // Verificamos si el archivo tiene alguna línea con información de equipo
      try (BufferedReader reader = new BufferedReader(new FileReader(archivoEquipos))) {
         String linea;
         while ((linea = reader.readLine()) != null) {
            if (!linea.trim().isEmpty()) { // Si hay al menos una línea válida, hay equipos disponibles
               return true;
            }
         }
      } catch (IOException e) {
         System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
      }
      return false; // Si no hay líneas válidas, retornamos false
   }

   public static void verHistoricoPartidosOficialesEquipo() {
      File archivoHistoricoEquipo = new File(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_OFICIAL);
      if (!archivoHistoricoEquipo.exists()) {
         System.out.println(ES.NO_EXISTE_FICHERO);
         return;
      }
      try (BufferedReader reader = new BufferedReader(new FileReader(archivoHistoricoEquipo))) {
         String linea;
         boolean hayContenido = false;
         boolean primeraVez = true;

         while ((linea = reader.readLine()) != null) {
            if (primeraVez) {
               System.out.println(ES.RESUMEN_PARTIDO);
               primeraVez = false;
               hayContenido = true;
            }
            System.out.println(linea);
         }
         if (!hayContenido) {
            System.out.println(ES.NO_PARTIDOS_JUGADOS);
         } else {
            System.out.println(ES.FIN_RESUMEN);
         }

      } catch (IOException e) {
         System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
      }

   }

   public static void verHistoricoPartidosExhibicionEquipo() {
      File archivoHistoricoEquipo = new File(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_EXHIBICION);

      if (!archivoHistoricoEquipo.exists()) {
         System.out.println(ES.NO_EXISTE_FICHERO);
         return;
      }

      try (BufferedReader reader = new BufferedReader(new FileReader(archivoHistoricoEquipo))) {
         String linea;
         boolean hayContenido = false;
         boolean primeraVez = true;

         while ((linea = reader.readLine()) != null) {
            if (primeraVez) {
               System.out.println(ES.RESUMEN_PARTIDO);
               primeraVez = false;
               hayContenido = true;
            }
            System.out.println(linea);
         }

         if (!hayContenido) {
            System.out.println(ES.NO_PARTIDOS_JUGADOS);
         } else {
            System.out.println(ES.FIN_RESUMEN);
         }

      } catch (IOException e) {
         System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
      }
   }

   public static void cargarUltimoPartidoDesdeFichero() {
      File ficheroHistorico = new File(ES.RUTA_PRINCIPAL + ES.HISTORICO_EQUIPO_FICHERO_OFICIAL);
      if (!ficheroHistorico.exists()) {
         System.out.println(ES.NO_EXISTE_FICHERO);
         return;
      }

      List<String> lineasUltimoPartido = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(ficheroHistorico))) {
         List<String> buffer = new ArrayList<>();
         String linea;
         while ((linea = reader.readLine()) != null) {
            if (linea.equals("---")) {
               lineasUltimoPartido = new ArrayList<>(buffer); // actualiza con el último partido leído
               buffer.clear();
            } else {
               buffer.add(linea);
            }
         }
      } catch (IOException e) {
         System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
         return;
      }

      if (lineasUltimoPartido.isEmpty()) {
         System.out.println(ES.NO_PARTIDOS_GUARDADOS);
         return;
      }

      System.out.println("===== Último Partido =====");
      for (String linea : lineasUltimoPartido) {
         System.out.println(linea);
      }
      System.out.println("===================================");
   }

   public static Map<String, String[]> leerEstadisticasDesdeArchivo(String rutaFichero) {
      Map<String, String[]> estadisticas = new HashMap<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(rutaFichero))) {
         String linea;

         while ((linea = reader.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty())
               continue;

            if (Metodos.esLineaValidaJugador(linea)) {
               Metodos.procesarLineaJugador(linea, estadisticas);
            }
         }

      } catch (IOException e) {
         System.out.println(ES.ERROR_LEER_FICHERO + e.getMessage());
      }

      return estadisticas;
   }

}
