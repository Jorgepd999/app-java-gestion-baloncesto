package controlador;

/**
 * @author JORGE PEREZ
 */

import java.util.Scanner;

import modelo.equipos.Equipo;
import modelo.metodos.Metodos;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Metodos.cargarArbitros();
        Equipo miEquipo = Equipo.getInstancia("Enrique Tierno Garvan Baloncesto");
       

        Metodos.menuPrincipal(sc, miEquipo);

        sc.close();

    }
}
