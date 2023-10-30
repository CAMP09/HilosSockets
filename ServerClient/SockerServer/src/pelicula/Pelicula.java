package pelicula;

public class Pelicula {

	private String id;
	private String titulo;
	private String director;
	private Double precio;

	public Pelicula(String id, String titulo, String director, Double precio) {
		this.id = id;
		this.titulo = titulo;
		this.director = director;
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Pelicula = ID: " + id + ", Titulo: " + titulo + ", Director: " + director + ", Precio: " + precio + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}
