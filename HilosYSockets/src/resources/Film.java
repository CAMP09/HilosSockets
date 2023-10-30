package resources;

import java.util.Objects;

/**
 * La clase Film representa una película con atributos como ID, título, director y precio.
 * Proporciona métodos para acceder y modificar estos atributos, además de sobrescribir métodos como equals, hashCode y toString.
 * @author Andrés
 */
public class Film {
	

	private String id;
	private String title;
	private String director;
	private String price;

    /**
     * Constructor de la clase Film.
     * @param id Identificador único de la película.
     * @param title Título de la película.
     * @param director Director de la película.
     * @param price Precio de la película.
     */
	public Film(String id, String title, String director, String price) {
		super();
		this.id = id;
		this.title = title;
		this.director = director;
		this.price = price;
	}

	
    // Getters y setters


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	//Equals y Hashcode
	
	@Override
	public int hashCode() {
		return Objects.hash(director, id, title);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(director, other.director) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title) && Objects.equals(price, other.price);
	}

	//toString
	
	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", title=" + title + ", director=" + director +", price=" + price + "]";
	}
	
}

