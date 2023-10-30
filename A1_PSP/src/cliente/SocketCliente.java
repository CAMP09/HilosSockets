package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import servidor.Pelicula;

public class SocketCliente {
	//Establecemos la IP y el puerto para la conexión.
    public static final int PUERTO = 2017;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        System.out.println("      APLICACION CLIENTE      ");
        System.out.println("------------------------------");

        //Encapsulamos la IP y el puerto
        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

        try (Scanner leer = new Scanner(System.in)) {
        	//Creamos el objeto socket del cliente al servidor, se creará un objeto Socket por cada petición que llegue de un cliente distinto
            Socket socketAlServidor = new Socket();

            System.out.println("Esperando a que el servidor acepte la conexión");
            //Establecemos la conexión una vez acepte el servidor la petición.
            socketAlServidor.connect(direccionServidor);
            System.out.println("Conexión establecida con el servidor");
            
          /* Creamos un objeto de tipo InputStreamReader que me va a permitir recibir los datos que emite el socker servidor.
           * Creamos el objeto BufferedReader, que me permitirá leer lo que llega del servidor en bloque y no linea a linea.
           * Creamos el objeto PrintStream para dar salida al texto o mensaje que se envía desde la aplicación cliente al servidor. 
           * 
           */
            InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
            BufferedReader bf = new BufferedReader(entrada);
            PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
            
            //Defino las variables que voy a necesitar para elaborar el programa que estará en la aplicación cliente.
            String opcion;
            boolean continuar = true;
            String resultado;
            String titulo;
            
            /* Creamos un bucle do/while en donde el cliente verá un menú de tres opciones que posteriormente se ampliará a 5, 
             * según la opción escogida podrá buscar las películas por identificador o por título, el servidor devolverá la 
             * información completa sobre la película escogida.El bucle seguirá activo mientras no le de a la opción 3 que es "salir".
             */
            do {
                System.out.println("Seleccione una opción para buscar su pelicula:");
                System.out.println("1. Buscar películas por ID");
                System.out.println("2. Buscar películas por título");                               
                System.out.println("3. Salir de la aplicación");
                System.out.println("4. Buscar películas por director"); 
                System.out.println("5. Añadir una película");
                opcion = leer.nextLine();
                salida.println(opcion);

                switch (opcion) {
                    case "1":
                        System.out.println("Introduce el ID de la película:");
                        int id = Integer.parseInt(leer.nextLine());
                        salida.println(id);
                        resultado = bf.readLine();
                        System.out.println("La pelicula con ID :  " + id + " es : " + resultado);
                        break;

                    case "2":
                        System.out.println("Introduzca el título de la película que desea comprar");
                        titulo = leer.nextLine();
                        salida.println(titulo);
                        resultado = bf.readLine();                        
                        System.out.println("Su película es: " + resultado);
                        break;

                    case "3":
                        System.out.println("Salir de la aplicación");
                        continuar = false;
                        break;

             //Requerimiento 2: añadir al menú de la aplicación del cliente la búsqueda de películas por director.
                        
                    case "4":
                    	String director;
                    	System.out.println("Introduzca el nombre del Director");
                        director = leer.nextLine();                       
                        salida.println(director);  
                        resultado = bf.readLine();  
                        System.out.println("Listado de peliculas de " + director + ":\n");
                       
                        while (!resultado.equals("Fin de la lista")) {
                        	System.out.println(resultado);
                        	resultado = bf.readLine();                      
                            
                        }                      
                     
                        break;
                        
              //Requerimiento 3:Introdudir los datos para añadir una nueva pelicula a la lista.
                    case "5":
                        System.out.println("Introduce los datos de la película:");
                        System.out.print("ID: ");
                        int id1 = Integer.parseInt(leer.nextLine());
                        System.out.print("Título: ");
                        String t1 = leer.nextLine();
                        System.out.print("Director: ");
                        String d1 = leer.nextLine();
                        System.out.print("Precio: ");
                        double precio1 = Double.parseDouble(leer.nextLine());

                        salida.println(id1);
                        salida.println(t1);
                        salida.println(d1);
                        salida.println(precio1);
                        resultado = bf.readLine();
                        System.out.println(resultado);
                      
                        break;
                    
                    default:
                        System.out.println("Opción errónea");
                }

            } while (continuar);
            
            //En cuanto el cliente seleccione la opción 3 se cierra la conexión del socketAlServidor.
            socketAlServidor.close();

        } catch (IOException e) {
            System.err.println("CLIENTE: Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("CLIENTE: Error -> " + e);
            e.printStackTrace();
        }
    }
}
