
package servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import resources.Film;

/**
 * La clase SocketServidor establece un servidor que acepta conexiones de clientes y gestiona la comunicación con ellos.
 * Almacena una lista de películas y se encarga de atender las peticiones de los clientes.
 * @author Andrés
 */
public class SocketServidor {
	
	/**
     * Constante estática para el puerto de acceso al servidor.
     */
	public static final int PORT = 2017;
	public static ArrayList<Film> films = new ArrayList<>();
	
	/**
     * Método principal que inicia el servidor y atiende las solicitudes de los clientes.
     */
	public static void main(String[] args) {
		/**
		 * Creamos las variables con los objetos necesarios para la conexión:
		 * A la dirección de de conexión le pasamos por parámetro  la IP del servidor y el puerto.
		 */
		InetSocketAddress serverAdress; // Dirección del servidor
		int client = 0;// Contador de clientes
		
        // Creación e inicialización de instancias de películas y adición al ArrayList de películas
		Film f1 = new Film("1","La lista de Schindler", "Steven Spielberg", "10$");
		Film f2 = new Film("2","El club de la lucha", "David Fincher", "20$");
		Film f3 = new Film("3","V de Vendetta", "James McTeigue", "16$");
		Film f4 = new Film("4","Salvador (Puig Antich)", "Manuel Huerga", "12$");
		Film f5 = new Film("5","La voz dormida", "Benito Zambrano", "30$");
		
		films.add(f1);
		films.add(f2);
		films.add(f3);
		films.add(f4);
		films.add(f5);
		
		
		try (ServerSocket serverSocket = new ServerSocket();) {
            // Configuración del servidor
			serverAdress = new InetSocketAddress(PORT);
			serverSocket.bind(serverAdress);
			
            // Bucle para aceptar conexiones entrantes de clientes
			while (true) {
				Socket socketToUser = serverSocket.accept();
				new HiloSocketServidor(socketToUser); // Inicia un hilo para atender al cliente
				System.out.println("SERVIDOR: cliente numero " + ++client);
			}
			
		} catch (IOException e) {
            // Excepción de error de entrada/salida
			System.err.println("Servidor: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
            // Otras excepciones generales
			System.err.println("Servidor: Error");
			e.printStackTrace();
		}
	}
}
