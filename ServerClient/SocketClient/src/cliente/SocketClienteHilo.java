package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClienteHilo {

	public static final int PUERTO = 2018;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {

		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");

			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);

			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + " por el puerto " + PUERTO);

			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);

			boolean continuar = true;

			do {
				String respuesta = "";
				Menu.mostrarMenu(System.out);
				String opcion = sc.nextLine();

				PrintStream salidaServidor1 = new PrintStream(socketAlServidor.getOutputStream());
				salidaServidor1.println(opcion);

				switch (opcion) {
				case "1":
					System.out.println("Introduce el ID");
					respuesta = sc.nextLine();
					break;

				case "2":
					System.out.println("Introduce el Título");
					respuesta = sc.nextLine();
					break;

				case "3":
					System.out.println("Introduce el Director");
					respuesta = sc.nextLine();
					break;

				case "4":
					System.out.println("Añadir película");
					System.out
							.println("Introduce los datos de la película en el formato id, titulo, director, precio:");
					respuesta = sc.nextLine();
					break;

				case "5":
					System.out.println("Saliendo de la aplicación...");
					continuar = false;
					break;

				default:
					System.out.println("Opción no válida.");
					continue;
				}

				if (!opcion.equals("5")) {
					PrintStream salidaServidor2 = new PrintStream(socketAlServidor.getOutputStream());
					salidaServidor2.println(respuesta);

					System.out.println("CLIENTE: Esperando al resultado del servidor...");
					String resultado = entradaBuffer.readLine();

					System.out.println("CLIENTE: El resultado de la búsqueda es:");

					while (!resultado.equals("FIN")) {
						System.out.println(resultado);
						resultado = entradaBuffer.readLine();
					}
				}

			} while (continuar);

			socketAlServidor.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección " + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}

		System.out.println("CLIENTE: Fin del programa");
	}
}
