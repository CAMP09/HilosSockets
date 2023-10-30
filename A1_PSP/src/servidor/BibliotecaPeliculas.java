package servidor;
import java.util.ArrayList;
import java.util.List;


public class BibliotecaPeliculas{
	
	//Defino el único atributo de la clase
    private List<Pelicula> biblioteca;
    
    //Constructor que cargará el listado de peliculas en la biblioteca
    public BibliotecaPeliculas() {
        biblioteca = new ArrayList<>();
        cargarBiblioteca();
        
        
    }

    public List<Pelicula> getBiblioteca() {
        return biblioteca;
    }

    //Método que realiza la carga del objeto pelicula en un ArrayList
    private void cargarBiblioteca() {
        biblioteca.add(new Pelicula(1, "Indiana Jones y el Templo Maldito", "Steven Spielberg", 6.00));
        biblioteca.add(new Pelicula(2, "Indiana Jones en Busca del Arca Perdida", "Steven Spielberg", 8.00));
        biblioteca.add(new Pelicula(3, "Indiana Jones y la Última Cruzada", "Steven Spielberg", 10.00));
        biblioteca.add(new Pelicula(4, "Indiana Jones y el Reino de la Calavera de Cristal", "Steven Spielberg", 12.00));
        biblioteca.add(new Pelicula(5, "Indiana Jones y el Dial del Destino", "James Manglod", 15.00));
    }

    //Método que devuelve un objeto Pelicula si el cliente introduce el identificador de la misma.

	  public Pelicula buscarPeliculaPorId(int id) {
	        for (Pelicula ele : biblioteca) {
	            if (ele.getId()== id) {
	                return ele;
	            }
	        }
	        return null;
	    }
	  
	 //Método que devuelve un objeto Pelicula si el cliente introduce el titulo de la misma.

	 public List<Pelicula> buscarPeliculasPorTitulo(String titulo) {
	    List<Pelicula> aux = new ArrayList<>();
	    for (Pelicula ele : biblioteca) {
	        if (ele.getTitulo().equalsIgnoreCase(titulo)) {
	            aux.add(ele);
	        }
	    }
	    return aux;
	}
	 
	 //Requerimiento 2
	 //Método que proporciona un listado de películas pasando por parámetro el atributo "director" de tipo String.
	 
	 public List<Pelicula> buscarPeliculasPorDirector(String director){
		 List<Pelicula> aux =new ArrayList<>();
		 
		 for (Pelicula ele:biblioteca) {
			 if(ele.getDirector().equalsIgnoreCase(director)) 
				 aux.add(ele);
			 
		 }
		 return aux;		 
		 		 
	 }
	 /*Requerimiento 3: con la palabra 'synchronized' conseguimos que únicamente esta opción la puede ejecutar un hilo a la vez.
	  * El método 'añadir' servirá para que el cliente introduzca los atributos de la clase película para añadirla a la 
	  * lista biblioteca,verificará antes a través del método 'existe' si ya se encuentra en la biblioteca.
	  *  
	  */	
	 public synchronized boolean añadir(int id, String titulo, String director, double precio) {
	        if (!existe(id)) {
	            Pelicula p1 = new Pelicula(id, titulo, director, precio);
	            biblioteca.add(p1);
	            return true;
	        }
	        return false;
	    }

	   private boolean existe(int id) {
	        for (Pelicula pelicula : biblioteca) {
	            if (pelicula.getId() == id) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
}
