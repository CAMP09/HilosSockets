package pelicula;

public class MainPruebaMetodos {

	public static void main(String[] args) {

		GestorPeliculas gestor = new GestorPeliculas();

		System.out.println(gestor.obtenerPeliculasPorDirector("JAmes CamEron"));
		System.out.println(gestor.obtenerPeliculaPorID("1"));
		System.out.println(gestor.obtenerPeliculasPorDirector("George Lucas"));
		System.out.println(gestor.obtenerPeliculasPorDirector("JAmes CAmeron"));
		System.out.println(gestor.obtenerPeliculaPorTitulo("TerminatoR"));
		gestor.agregarPelicula(new Pelicula("6", "Hola", "Adios", 20.0));
		System.out.println(gestor.obtenerPeliculaPorID("6"));

	}

}
