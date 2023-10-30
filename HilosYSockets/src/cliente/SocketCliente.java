/**
 * 
 */
package cliente;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * La clase SocketCliente establece una conexión con un servidor mediante un socket.
 * Utiliza un hilo de ejecución para manejar la comunicación con el servidor.
 * @author Andrés
 *
 */
public class SocketCliente {
	
	/**
	 * Constantes estáticas para el puerto de acceso y la dirección IP del servidor.
	 */
	public static final int PORT = 2017;
	public static final String IP_SERVER = "localhost";
	public static final String SEPARATOR = "----------------------------------------------";

	/**
    * Método principal que establece la conexión con el servidor.
    */
	public static void main(String[] args) {
		
		 // Dirección del servidor
		InetSocketAddress serverAdress = new InetSocketAddress(IP_SERVER, PORT);
		
		/**
		 * Creamos un bloque Try-with-resources, para capturar los posibles errores. 
		 * dentro del try declaramos e inicializamos el socket del cliente, para asegurarnos de que este se cierra 
		 * cuando finalice el bloque sin necesidad de implementar el método .close().
		 */
		
		try {
            // Se crea un socket y se establece la conexión con el servidor
			Socket socketToServer = new Socket();
			socketToServer.connect(serverAdress);
            // Se inicia un hilo para manejar la comunicación con el servidor
			new SocketClienteHilo(socketToServer);
			
			
		} catch (UnknownHostException e) {
            // Excepción en caso de no encontrar la dirección IP del servidor
			System.err.println("Cliente: El servidor no se encuentra en la dirección " + IP_SERVER);
			e.printStackTrace();
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
