package servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import servidor.BibliotecaPeliculas;
public class SocketServidor {
	

    public static final int PUERTO = 2017;
    private static BibliotecaPeliculas bibliotecaPeliculas;
    
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("      APLICACIÓN DE SERVIDOR      ");
        System.out.println("----------------------------------");
        int peticion = 0;

        bibliotecaPeliculas = new BibliotecaPeliculas();

        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress direccion = new InetSocketAddress(PUERTO);
            serverSocket.bind(direccion);
            
            System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);          
            
            while (true) {
                Socket socketAlCliente = serverSocket.accept();
                System.out.println("Esperando petición: " + ++peticion + " recibida");
                
              //Creamos un hilo para la solicitud que nos llega del cliente dejando liberado el hilo main principal
                new HiloPeliculas(socketAlCliente, bibliotecaPeliculas);
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
