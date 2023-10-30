package servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import pelicula.GestorPeliculas;

public class SocketServidorHilo {

	public static final int PUERTO = 2018;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACIÓN DE SERVIDOR CON HILOS     ");
		System.out.println("-------------------------------------------");
		GestorPeliculas gestorPeliculas = new GestorPeliculas();

		try (ServerSocket servidor = new ServerSocket()) {
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);

			while (true) {
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: Peticion recibida");
				new ClienteHandler(socketAlCliente, gestorPeliculas);
			}

		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}
}