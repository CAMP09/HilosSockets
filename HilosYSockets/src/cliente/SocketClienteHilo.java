package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * La clase SocketClienteHilo implementa un hilo para la comunicación con el servidor.
 * Proporciona un menú de opciones para consultar información sobre películas o añadir una nueva película.
 * Utiliza un socket para enviar y recibir datos al servidor.
 * @author Andrés
 */
public class SocketClienteHilo implements Runnable {

	private Thread thread;
	private Socket socketToServer;
	public static final String SEPARATOR = "----------------------------------------------";
	
	/**
     * Constructor de la clase SocketClienteHilo.
     * Inicia un nuevo hilo para la comunicación con el servidor.
     * @param socketToServer Socket para la comunicación con el servidor
     */
	public SocketClienteHilo(Socket socketToServer) {
		super();
		this.thread = new Thread(this);
		this.socketToServer = socketToServer;
		thread.start();
	}
	

	/**
	 * El método run() maneja la interacción entre el cliente y el servidor a través de la consola.
	 * Permite al usuario realizar consultas de películas por ID, título o director, así como agregar una nueva película.
	 * Además, proporciona la opción de cerrar la aplicación.
	 */
	@Override
	public void run() {
		PrintStream output;	
		InputStreamReader input;
		BufferedReader bf;
		
		try (Scanner read = new Scanner(System.in);){
			boolean close = false; // Variable de control para cerrar la aplicación

			do {
                // Menú de opciones para el usuario
				System.out.println("Menú (introduce el número de la opción deseada):\n"
						+ "1.Consultar película por ID\n"
						+ "2.Consultar película por título\n"
						+ "3.Consultar películas por director\n"
						+ "4.Añadir película\n"
						+ "5.Salir de la aplicación");

				String userOption = read.nextLine(); // Lee la opción del usuario
				String answer; // Almacena la respuesta del servidor

				switch (userOption) {
				case "1":
                    // Consulta por ID de la película
					System.out.println("Introduce el ID de la película (Número)");
					String id = read.nextLine();

					output = new PrintStream(socketToServer.getOutputStream());
					output.println(id);

					input = new InputStreamReader(socketToServer.getInputStream());
					bf = new BufferedReader(input);
					answer = bf.readLine();
					System.out.println(answer);

					System.out.println(SEPARATOR);
					break;
				case "2":
                    // Consulta por título de la película
					System.out.println("Introduce el título de la película");
					String title = read.nextLine();

					output = new PrintStream(socketToServer.getOutputStream());
					output.println(title);

					input = new InputStreamReader(socketToServer.getInputStream());
					bf = new BufferedReader(input);
					answer = bf.readLine();
					System.out.println(answer);

					System.out.println(SEPARATOR);
					break;
				case "3":
                    // Consulta por director de la película
					System.out.println("Introduce el director de la película");
					String director = read.nextLine();

					output = new PrintStream(socketToServer.getOutputStream());
					output.println(director);

					input = new InputStreamReader(socketToServer.getInputStream());
					bf = new BufferedReader(input);
					answer = bf.readLine();
					System.out.println(answer);

					System.out.println(SEPARATOR);
					break;
				case "4":
                    // Añadir nueva película
					System.out.println("Introduce el título de la película");
					String newTitle = read.nextLine();
					System.out.println("Introduce el director de la película");
					String newDirector = read.nextLine();
					System.out.println("Introduce el precio de la película");
					String newPrice = read.nextLine();

					output = new PrintStream(socketToServer.getOutputStream());
					output.println(newTitle + "/" + newDirector + "/" + newPrice +"$");
					
					input = new InputStreamReader(socketToServer.getInputStream());
					bf = new BufferedReader(input);
					answer = bf.readLine();
					System.out.println(answer);

					System.out.println(SEPARATOR);
					break;
				case "5":
                    // Opción para cerrar la aplicación
					output = new PrintStream(socketToServer.getOutputStream());
					output.println("5"); // Solicita cerrar la aplicación al servidor
					close = true; //Establece la variable de control para cerrar la aplicación
					break;
				}

			} while (close == false); // Repite el bucle hasta que se solicite cerrar la aplicación

			socketToServer.close(); // Cierra la conexión con el servidor
			
		} catch (IOException e) {
            // Excepción de error de entrada/salida
			System.err.println("Cliente: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
            // Otras excepciones generales
			System.err.println("Cliente: Error -> " + e);
			e.printStackTrace();
		}
        // Mensaje de fin del programa
		System.out.println("----- Fin de programa -----");
		
	}

}
