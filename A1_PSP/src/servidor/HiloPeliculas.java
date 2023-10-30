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

//Creamos la clase HiloPeliculas que implentar� el m�todo Runnable.Los atributos de la clase son privados.
public class HiloPeliculas implements Runnable {
    private Thread hilo;
    private static int numCliente;
    private Socket socketAlCliente;
    private BibliotecaPeliculas biblioteca;
   
    
 /*
  * Constructor de la clase que tiene como par�metros el socket cliente que recibe y un objeto de la clase BibliotecaPeliculas.
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

    //M�todo run se ejecuta una vez arrancamos el hilo con el m�todo start();  aqu� se encontrar� todo el c�digo que se
    //ejecutar� en el hilo, una vez finalice el m�todo run se cierra el hilo. 
    
    @Override
    public void run() {
        System.out.println("Estableciendo comunicaci�n con " + hilo.getName());

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
                            salida.println("Pel�cula no existe.");
                        }
                        break;

                    case "2":
                        String titulo = entrada.readLine();
                        List<Pelicula> peliculasPorTitulo = biblioteca.buscarPeliculasPorTitulo(titulo);

                        if (peliculasPorTitulo.isEmpty()) {
                            salida.println("No existen pel�culas con ese t�tulo.");
                        } else {
                            for (Pelicula ele : peliculasPorTitulo) {
                                salida.println(ele);
                            }
                        }
                        break;

                    case "3":
                        salida.println("Adi�s");
                        continuar = false;
                        break;
                    
                    case "4":
                        String director = entrada.readLine();                       
                       
                        List<Pelicula> peliculasPorDirector = biblioteca.buscarPeliculasPorDirector(director);                        
                           
                        if (peliculasPorDirector.isEmpty()) {
                            System.out.println("No se encontraron pel�culas para el director: " + director); 
                            salida.println("No existen pel�culas de ese director.");
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
                      
                        
                        boolean a�adida=biblioteca.a�adir(id1, t1, d1, precio1);

                        if (a�adida) {
                            salida.println("Pel�cula agregada con �xito.");
                            
                        } else {
                            salida.println("La pel�cula ya existe en la biblioteca.");
                        }                    
                                               	
                        break;           	 
                            	
                    	
                    default:
                        salida.println("Opci�n no v�lida.");
                        break;
                }
        }    

            socketAlCliente.close();
            
            System.out.println(hilo.getName() + " ha cerrado la comunicaci�n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
