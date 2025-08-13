
# **Documentación del Proyecto**

Este proyecto es un simulador de un equipo de baloncesto, donde interactúan diversas entidades como **Jugadores**, **Equipos**, **Arbitros** y **Partidos**. A continuación se presenta una descripción general de las clases y sus funcionalidades.

---

## **Clases Principales**

### **1. Persona**
- **Descripción**: Representa una persona genérica con un nombre.
- **Atributos**:
  - 'nombre' (String): Nombre de la persona.
- **Métodos**:
  - 'getNombre()': Retorna el nombre de la persona.
  - 'setNombre(String nombre)': Establece un nuevo nombre para la persona.

### **2. Jugador (Clase Abstracta)**
- **Descripción**: Clase base para representar a un jugador de baloncesto. Los jugadores pueden tener diferentes roles como Base, Pivot, etc.
- **Atributos**:
  - 'nombre' (String): Nombre del jugador.
  - 'dorsal' (int): Número de camiseta del jugador.
  - 'puntos' (int): Puntos acumulados del jugador.
  - 'falta' (int): Número de faltas cometidas por el jugador.
- **Métodos**:
  - 'realizarAccion()': Método abstracto que debe ser implementado por las clases derivadas.
  - 'entrenar()': Método abstracto que debe ser implementado por las clases derivadas.

### **3. Arbitro (Hereda de Persona)**
- **Descripción**: Representa un árbitro que tiene una probabilidad de estar enfermo, lo cual puede afectar su disponibilidad para arbitrar un partido.
- **Atributos**:
  - 'enfermo' (boolean): Indica si el árbitro está enfermo o no.
- **Métodos**:
  - 'determinarEnfermedad()': Establece si el árbitro está enfermo con un 20% de probabilidad.
  - 'estaEnfermo()': Retorna si el árbitro está enfermo.

### **4. Equipo**
- **Descripción**: Representa un equipo de baloncesto, compuesto por una lista de jugadores.
- **Atributos**:
  - 'jugadores' (List<Jugador>): Lista de jugadores que componen el equipo.
  - 'nombre' (String): Nombre del equipo.
- **Métodos**:
  - 'obtenerFicheroPorTipo(String tipo)': Retorna el archivo correspondiente al tipo de jugador (base, ala, pivot, etc.).
  - 'parsearJugador(String linea)': Convierte una línea de texto en un objeto 'Jugador'.
  - 'agregarJugador(Jugador jugador)': Agrega un jugador al equipo.

### **5. Partido**
- **Descripción**: Representa un partido de baloncesto entre dos equipos.
- **Atributos**:
  - 'rendimientoPartido' (Map<Jugador, int[]>): Mapa que asocia a cada jugador con su rendimiento (puntos y faltas).
  - 'equipoContrario' (String): Nombre del equipo contrario.
  - 'esOficial' (boolean): Indica si el partido es oficial o no.
  - 'esLocal' (boolean): Indica si el partido se juega en casa.
  - 'puntosEquipo' (int): Puntos del equipo.
  - 'puntosContrario' (int): Puntos del equipo contrario.
  - 'arbitro' (Arbitro): Árbitro encargado de dirigir el partido.
  - 'miEquipo' (Equipo): El equipo que participa en el partido.
- **Métodos**:
  - 'calcularResultado()': Calcula el resultado del partido basándose en probabilidades.
  - 'mostrarResumen()': Muestra un resumen del partido.
  - 'guardarPartidoEnFicheroOficial()': Guarda los detalles del partido en un archivo.
  - 'guardarPartidoEnFicheroExhibicion()': Guarda los detalles del partido en un archivo de exhibición.
  - 'repartirPuntosEntreJugadores()': Asigna puntos y faltas a los jugadores del equipo.
  - 'guardarJugadoresActualizados()': Guarda la información actualizada de los jugadores después del partido.

### **6. MetodosFicheros**
- **Descripción**: Clase con métodos para manejar archivos de texto relacionados con los equipos y partidos.
- **Métodos**:
  - 'escribirEquiposFichero(File fichero, Scanner sc, ArrayList<Equipo> equipos)': Escribe un nuevo equipo en el archivo.
  - 'mostrarContenidoFicheroEquipos(File fichero)': Muestra el contenido del archivo de equipos.
  - 'hayEquiposDisponibles()': Verifica si existen equipos disponibles.
  - 'verHistoricoPartidosOficialesEquipo()': Muestra el historial de partidos oficiales de un equipo.
  - 'verHistoricoPartidosExhibicionEquipo()': Muestra el historial de partidos de exhibición de un equipo.
  - 'cargarUltimoPartidoDesdeFichero()': Carga el último partido jugado desde el archivo.
  - 'leerEstadisticasDesdeArchivo(String rutaFichero)': Lee las estadísticas de los jugadores desde un archivo.

---

## **Clases de Jugadores Específicos**

Las siguientes clases heredan de 'Jugador' y representan las distintas posiciones en un equipo de baloncesto:

### **7. AlaPivot**
- **Descripción**: Representa un jugador que juega en la posición de Ala-Pivot.
- **Métodos**:
  - 'realizarAccion()': Realiza una acción específica para un Ala-Pivot.
  - 'entrenar()': Entrena en la posición de Ala-Pivot.

### **8. Base**
- **Descripción**: Representa un jugador que juega en la posición de Base.
- **Métodos**:
  - 'realizarAccion()': Realiza una acción específica para un Base.
  - 'entrenar()': Entrena en la posición de Base.

### **9. Escolta**
- **Descripción**: Representa un jugador que juega en la posición de Escolta.
- **Métodos**:
  - 'realizarAccion()': Realiza una acción específica para un Escolta.
  - 'entrenar()': Entrena en la posición de Escolta.

### **10. Pivot**
- **Descripción**: Representa un jugador que juega en la posición de Pivot.
- **Métodos**:
  - 'realizarAccion()': Realiza una acción específica para un Pivot.
  - 'entrenar()': Entrena en la posición de Pivot.

### **11. Ala**
- **Descripción**: Representa un jugador que juega en la posición de Ala.
- **Métodos**:
  - 'realizarAccion()': Realiza una acción específica para un Ala.
  - 'entrenar()': Entrena en la posición de Ala.

---

## **Estructura de Archivos**
El proyecto hace uso de varios archivos para almacenar la información sobre los equipos, jugadores y partidos. Los archivos se encuentran en las siguientes rutas:
- **Históricos de Partidos Oficiales**: 'ES.HISTORICO_EQUIPO_FICHERO_OFICIAL'
- **Históricos de Partidos de Exhibición**: 'ES.HISTORICO_EQUIPO_FICHERO_EXHIBICION'
- **Equipos**: 'ES.EQUIPOS_FICHERO'

---

## **Instrucciones para Ejecutar el Proyecto**

1. Clona el repositorio en tu máquina local.
2. Compila los archivos Java en tu IDE o usando la línea de comandos.
3. Ejecuta la clase principal (si está disponible) para simular partidos, jugadores y equipos.
4. Revisa los archivos generados en las rutas especificadas para ver los resultados de los partidos y la información de los jugadores.

---

## **Conclusión**

Este proyecto permite simular un equipo de baloncesto, creando jugadores con diversas posiciones, partidos de diferentes tipos (oficiales o de exhibición) y almacenando los resultados en archivos. Utiliza estructuras de datos como listas y mapas para gestionar la información de manera eficiente.

---
