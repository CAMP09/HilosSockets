package servidor;

import java.util.LinkedList;
import java.util.Queue;

import javabean.Pelicula;
/**
 * Clase que representa una cola, en forma de estructura de datos que almacena objetos Pelicula
 * con una capacidad maxima de 1 elemento y sigue el principio FIFO (First In First Out).
 * En esta clase se creara una cola, en la cual solo puede haber un objeto pelicula.
 * Los demas hilos tendran que esperar (wait) a que el objeto pelicula salga para introducirse en la
 * lista de peliculas y lo notifique (notify)al resto de hilos para que puedan agregar su pelicula a
 * la cola. De esta forma solo pueden añadir una pelicula cada hilo a la vez.
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version 2.1
 */
public class ColaBiblioteca {
		//Numero maximo objetos Pelicula que tiene la bolsa
		public final static int MAX_ELEMENTOS = 1;
		
		//Una cola es ideal para implementar este ejemplo
		//FIFO -> First In First Out 
		private Queue<Pelicula> bolsa = new LinkedList<>();
		
		/**
	     * Este metodo obtiene una pelicula de la bolsa. Si la bolsa esta vacia, el hilo se bloquea
	     * y espera a que otro hilo agregue una pelicula antes de continuar.
	     *
	     * @return la pelicula obtenida de la bolsa.
	     */		
		public synchronized Pelicula obtenerPeliculaBolsa(){
			//Si la bolsa está vacia no debemos intentar sacar ningun elemento mas
			//por lo que esperamos a que otro hilo ponga un elemento pelicula
			while(bolsa.size() == 0){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Sacamos un elemento de la cola
			Pelicula peliculaBolsa = bolsa.poll();
			//Despertamos a un hilo que esté en estado 'wait'
			notify();
			return peliculaBolsa;
		}	
		/**
	     * Este metodo agrega una pelicula a la bolsa. Si la bolsa esta llena, el hilo se bloquea
	     * y espera a que otro hilo saque una pelicula antes de continuar.
	     *
	     * @param pelicula representa pelicula la pelicula que se va a agregar a la bolsa.
	     */
		public synchronized void addPeliculaBolsa(Pelicula pelicula){
			//Si la bolsa esta llena no debemos introducir ninguna pelicula mas
			//por lo que esperamos a que otro hilo libere espacio
			while(bolsa.size() == MAX_ELEMENTOS){//1
				try {
					wait();			
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Añadimos un elemento a la cola
			bolsa.offer(pelicula);
			//Despertamos a un hilo que este en estado 'wait'
			notify();
		}
}
