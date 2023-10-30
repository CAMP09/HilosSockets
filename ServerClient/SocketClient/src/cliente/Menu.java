package cliente;

import java.io.PrintStream;

public class Menu {

	public static void mostrarMenu(PrintStream salida) {
		salida.println("MENU:");
		salida.println("1. Consultar película por ID");
		salida.println("2. Consultar película por título");
		salida.println("3. Consultar película por Director");
		salida.println("4. Añadir película");
		salida.println("5. Salir de la aplicación");
		salida.println("Elija una opción:");
	}

	public static void menuOpcion(String opcion, PrintStream salida) {
		switch (opcion) {
		case "1":
			salida.println("Introduce el ID");
			break;
		case "2":
			salida.println("Introduce el Título");
			break;
		case "3":
			salida.println("Introduce el Director");
			break;
		case "4":
			salida.println("Introduce el ID, Título, Director y precio");
			break;
		case "5":
			salida.println("Saliendo de la aplicación...");
			break;
		default:
			salida.println("Opción no válida.");
		}
	}
}
