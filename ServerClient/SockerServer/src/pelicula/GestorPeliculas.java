package pelicula;

import java.util.ArrayList;
import java.util.List;

public class GestorPeliculas {

	private static List<Pelicula> listaPeliculas;

	public GestorPeliculas() {
		listaPeliculas = new ArrayList<>();

		listaPeliculas.add(new Pelicula("1", "El Padrino", "Francis Ford Coppola", 15.5));
		listaPeliculas.add(new Pelicula("2", "Titanic", "James Cameron", 30.0));
		listaPeliculas.add(new Pelicula("3", "Terminator", "James Cameron", 12.75));
		listaPeliculas.add(new Pelicula("4", "Star Wars", "George Lucas", 5.75));
		listaPeliculas.add(new Pelicula("5", "Avatar", "James Cameron", 3.99));

	}

	public void agregarPelicula(Pelicula pelicula) {
		listaPeliculas.add(pelicula);
	}

	public Pelicula obtenerPeliculaPorID(String id) {
		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.getId().equalsIgnoreCase(id)) {
				return pelicula;
			}
		}
		return null;
	}

	public Pelicula obtenerPeliculaPorTitulo(String titulo) {

		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
				return pelicula;
			}
		}
		return null;
	}

	public List<Pelicula> obtenerPeliculasPorDirector(String director) {
		List<Pelicula> peliculasDirector = new ArrayList<>();

		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.getDirector().equalsIgnoreCase(director)) {
				peliculasDirector.add(pelicula);
			}
		}

		if (peliculasDirector.isEmpty()) {
			return new ArrayList<>();
		} else {
			return peliculasDirector;
		}
	}
}
