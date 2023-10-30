package servidor;

import pelicula.GestorPeliculas;
import pelicula.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class ClienteHandler implements Runnable {
	private Socket socketAlCliente;
	private GestorPeliculas gestorPeliculas;
	private Thread hilo;
	private static final Object lock = new Object();
	private static boolean agregandoPelicula = false;

	public ClienteHandler(Socket socketAlCliente, GestorPeliculas gestorPeliculas) {
		this.socketAlCliente = socketAlCliente;
		this.gestorPeliculas = gestorPeliculas;
		hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		System.out.println("SERVIDOR: Estableciendo comunicación con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		boolean continuar = true;

		try {
			salida = new PrintStream(socketAlCliente.getOutputStream());
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);

			String texto = "";

			while (continuar) {
				texto = entradaBuffer.readLine();

				switch (texto) {
				case "1":
					System.out.println("SERVIDOR: " + hilo.getName() + " Búsqueda por ID");
					String id = entradaBuffer.readLine();
					Pelicula peliculaPorID = gestorPeliculas.obtenerPeliculaPorID(id);
					salida.println(peliculaPorID != null ? peliculaPorID.toString() : "Película no encontrada");
					salida.println("FIN");
					break;

				case "2":
					System.out.println("SERVIDOR: " + hilo.getName() + " Búsqueda por Título");

					String titulo = entradaBuffer.readLine();
					Pelicula peliculaPorTitulo = gestorPeliculas.obtenerPeliculaPorTitulo(titulo);
					salida.println(peliculaPorTitulo != null ? peliculaPorTitulo.toString() : "Película no encontrada");
					salida.println("FIN");
					break;

				case "3":
					System.out.println("SERVIDOR: " + hilo.getName() + " Búsqueda por Director");
					String director = entradaBuffer.readLine();
					List<Pelicula> peliculasPorDirector = gestorPeliculas.obtenerPeliculasPorDirector(director);

					if (peliculasPorDirector.isEmpty()) {
						salida.println("Director no encontrado");
					} else {
						for (Pelicula pelicula : peliculasPorDirector) {
							salida.println(pelicula.toString());
						}
					}
					salida.println("FIN");
					break;

				case "4":
					System.out.println("SERVIDOR: " + hilo.getName() + " Añadir película");

					synchronized (lock) {
						String datosPelicula = entradaBuffer.readLine();
						String[] datos = datosPelicula.split(",");

						if (datos.length == 4) {
							String id1 = datos[0].trim();
							String titulo1 = datos[1].trim();
							String director1 = datos[2].trim();

							try {
								double precio1 = Double.parseDouble(datos[3].trim());

								while (agregandoPelicula) {
									try {
										lock.wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

								agregandoPelicula = true;

								gestorPeliculas.agregarPelicula(new Pelicula(id1, titulo1, director1, precio1));
								salida.println(
										"Película agregada con éxito: " + gestorPeliculas.obtenerPeliculaPorID(id1));
								System.out.println(this.gestorPeliculas.obtenerPeliculaPorID(id1) + " Añadida por "
										+ hilo.getName());

								agregandoPelicula = false;

								lock.notifyAll();
							} catch (NumberFormatException e) {
								salida.println(
										"Error en el formato del precio. Asegúrate de que el precio sea un número válido.");
							}
						} else {
							salida.println(
									"Formato de datos incorrecto. Debe ingresar id, titulo, director y precio separados por comas.");
						}
					}
					salida.println("FIN");
					break;

				case "5":
					System.out.println("SERVIDOR: Cerrada la comunicación con " + hilo.getName());
					salida.println("Saliendo de la aplicación...");
					continuar = false;
					break;

				default:
					System.out.println("SERVIDOR: Opción no válida." + hilo.getName());
					break;
				}
			}

			socketAlCliente.close();
		} catch (IOException e) {
			System.err.println("Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
	}
}
