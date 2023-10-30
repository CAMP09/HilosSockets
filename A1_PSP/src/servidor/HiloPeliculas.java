package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import cliente.SocketCliente;
import servidor.BibliotecaPeliculas;

//Creamos la clase HiloPeliculas que implentará el método Runnable.Los atributos de la clase son privados.
public class HiloPeliculas implements Runnable {
    private Thread hilo;
    private static int numCliente;
    private Socket socketAlCliente;
    private BibliotecaPeliculas biblioteca;
   
    
 /*
  * Constructor de la clase que tiene como parámetros el socket cliente que recibe y un objeto de la clase BibliotecaPeliculas.
  * Creamos un objeto de la clase y aarrancamos el hilo.
  *   
  */
    
    public HiloPeliculas(Socket socketAlCliente, BibliotecaPeliculas biblioteca) {
    	numCliente++;
        hilo = new Thread(this, "Cliente : " + numCliente);
        this.socketAlCliente = socketAlCliente;
        this.biblioteca = biblioteca;        
        hilo.start();
        
    }

    //Método run se ejecuta una vez arrancamos el hilo con el método start();  aquí se encontrará todo el código que se
    //ejecutará en el hilo, una vez finalice el método run se cierra el hilo. 
    
    @Override
    public void run() {
        System.out.println("Estableciendo comunicación con " + hilo.getName());

        try {
            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketAlCliente.getInputStream()));

            String opcion;
            boolean continuar = true;

            while (continuar) {
                opcion = entrada.readLine();

                switch (opcion) {
                    case "1":
                        int id = Integer.parseInt(entrada.readLine());
                        Pelicula peliculaPorId = biblioteca.buscarPeliculaPorId(id);

                        if (peliculaPorId != null) {
                            salida.println(peliculaPorId);
                        } else {
                            salida.println("Película no existe.");
                        }
                        break;

                    case "2":
                        String titulo = entrada.readLine();
                        List<Pelicula> peliculasPorTitulo = biblioteca.buscarPeliculasPorTitulo(titulo);

                        if (peliculasPorTitulo.isEmpty()) {
                            salida.println("No existen películas con ese título.");
                        } else {
                            for (Pelicula ele : peliculasPorTitulo) {
                                salida.println(ele);
                            }
                        }
                        break;

                    case "3":
                        salida.println("Adiós");
                        continuar = false;
                        break;
                    
                    case "4":
                        String director = entrada.readLine();                       
                       
                        List<Pelicula> peliculasPorDirector = biblioteca.buscarPeliculasPorDirector(director);                        
                           
                        if (peliculasPorDirector.isEmpty()) {
                            System.out.println("No se encontraron películas para el director: " + director); 
                            salida.println("No existen películas de ese director.");
                        } else {
                            for (Pelicula ele : peliculasPorDirector) {                                
                                salida.println(ele);
                            }
                        }
                        break;    
                    
                    case "5":                   	
                    	                    	
                        int id1 = Integer.parseInt(entrada.readLine());
                        String t1 = entrada.readLine();
                        String d1 = entrada.readLine();
                        double precio1= Double.parseDouble(entrada.readLine());
                      
                        
                        boolean añadida=biblioteca.añadir(id1, t1, d1, precio1);

                        if (añadida) {
                            salida.println("Película agregada con éxito.");
                            
                        } else {
                            salida.println("La película ya existe en la biblioteca.");
                        }                    
                                               	
                        break;           	 
                            	
                    	
                    default:
                        salida.println("Opción no válida.");
                        break;
                }
        }    

            socketAlCliente.close();
            
            System.out.println(hilo.getName() + " ha cerrado la comunicación");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
