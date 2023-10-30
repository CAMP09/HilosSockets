package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import resources.Film;

/**
 * Clase HiloSocketServidor: Implementa un hilo para manejar las solicitudes de los clientes en el servidor.
 * Se encarga de procesar las peticiones de los clientes y responderles de acuerdo a la solicitud.
 * @author Andrés
 */
public class HiloSocketServidor implements Runnable {
	private Thread thread;
	private Socket socketToUser;

	/**
	 * En el constructor del hilo que va a generar el servidor, inicializamos una
	 * instancia de hilo pasando por parametro la propia clase que implementa
	 * Runnable. E inicializamos el hilo.
	 * 
	 * @param socketToUser viene del servidor para proporcionarnos la conexión
	 * autónoma con el cliente por parte del hilo.
	 */
	public HiloSocketServidor(Socket socketToUser) {
		super();
		thread = new Thread(this);
		this.socketToUser = socketToUser;
		thread.start();
	}

	
	/**
	 * Método que maneja la ejecución del hilo en el servidor para atender las solicitudes de los clientes.
	 * Lee las peticiones de los clientes, busca la información correspondiente y responde según la solicitud.
	 * También gestiona la adición de nuevas películas y cierra la conexión cuando se recibe la solicitud de cierre.
	 */
	
	@Override
	public void run() {
		PrintStream output;	
		InputStreamReader input;
		BufferedReader bf;
		boolean open = true; // Variable de control para mantener abierto el bucle del servidor
		
		try  {
			// Bucle principal del servidor, escucha y responde a las peticiones de los clientes
			while (open) {

				input = new InputStreamReader(socketToUser.getInputStream()); 
				bf = new BufferedReader(input); 
				String userRequest = bf.readLine();
				
				boolean found = false; // Control que indica si se encontró la película solicitada
				ArrayList<Film> films = SocketServidor.films; // Obtiene la lista de películas del servidor

	            // Busca la película solicitada por ID, título o director en la lista de películas
				for (Film f : films) {
					if (userRequest.equalsIgnoreCase(f.getId()) || 
				        userRequest.equalsIgnoreCase(f.getTitle()) || 
				        userRequest.equalsIgnoreCase(f.getDirector())) {
				     
				            output = new PrintStream(socketToUser.getOutputStream());
				            output.println(f.toString());
				            found = true; // Se encontró la película, se establece a verdadero
				            break; 
					}  
				}
				
	            // Si no se encontró la película, se procesa la solicitud para añadir una nueva película
				if (!found) {
					String[] data = userRequest.split("/"); // Divide la solicitud en partes (título/director/precio)
					int lastId = films.size()+1; // Obtiene el próximo ID para la nueva película
					newFilm(films, data, lastId);// Agrega una nueva película a la lista con un método sincronizado
					//films.add(new Film(Integer.toString(lastId), data[0], data[1], data[2]));
					output = new PrintStream(socketToUser.getOutputStream());
					output.println(films.toString());
				}
				
	            // Si se recibe la solicitud de cierre desde el cliente, cierra la conexión y finaliza el servidor
				if (userRequest.equalsIgnoreCase("5")) {
					System.out.println("Cerrando servidor");
					socketToUser.close();
					open = false;
				}
			}
			
		} catch (IOException e) {
			// Manejo de error de E/S
			System.err.println("HiloContadorLetras: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			// Manejo de otros errores generales
			System.err.println("HiloContadorLetras: Error");
			e.printStackTrace();
		}	
	
	}
	
    /**
     * Método sincronizado para agregar una nueva película a la lista de películas.
     * @param films Lista de películas.
     * @param data Información de la nueva película.
     * @param lastId Último ID disponible para la nueva película.
     */
	public synchronized void newFilm(ArrayList<Film> films, String[] data, int lastId) {
		films.add(new Film(Integer.toString(lastId), data[0], data[1], data[2]));
		
		System.out.println("Esperando al servidor");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("El servidor responde");
	}
}
