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
	//Establecemos la IP y el puerto para la conexi�n.
    public static final int PUERTO = 2017;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        System.out.println("      APLICACION CLIENTE      ");
        System.out.println("------------------------------");

        //Encapsulamos la IP y el puerto
        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

        try (Scanner leer = new Scanner(System.in)) {
        	//Creamos el objeto socket del cliente al servidor, se crear� un objeto Socket por cada petici�n que llegue de un cliente distinto
            Socket socketAlServidor = new Socket();

            System.out.println("Esperando a que el servidor acepte la conexi�n");
            //Establecemos la conexi�n una vez acepte el servidor la petici�n.
            socketAlServidor.connect(direccionServidor);
            System.out.println("Conexi�n establecida con el servidor");
            
          /* Creamos un objeto de tipo InputStreamReader que me va a permitir recibir los datos que emite el socker servidor.
           * Creamos el objeto BufferedReader, que me permitir� leer lo que llega del servidor en bloque y no linea a linea.
           * Creamos el objeto PrintStream para dar salida al texto o mensaje que se env�a desde la aplicaci�n cliente al servidor. 
           * 
           */
            InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
            BufferedReader bf = new BufferedReader(entrada);
            PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
            
            //Defino las variables que voy a necesitar para elaborar el programa que estar� en la aplicaci�n cliente.
            String opcion;
            boolean continuar = true;
            String resultado;
            String titulo;
            
            /* Creamos un bucle do/while en donde el cliente ver� un men� de tres opciones que posteriormente se ampliar� a 5, 
             * seg�n la opci�n escogida podr� buscar las pel�culas por identificador o por t�tulo, el servidor devolver� la 
             * informaci�n completa sobre la pel�cula escogida.El bucle seguir� activo mientras no le de a la opci�n 3 que es "salir".
             */
            do {
                System.out.println("Seleccione una opci�n para buscar su pelicula:");
                System.out.println("1. Buscar pel�culas por ID");
                System.out.println("2. Buscar pel�culas por t�tulo");                               
                System.out.println("3. Salir de la aplicaci�n");
                System.out.println("4. Buscar pel�culas por director"); 
                System.out.println("5. A�adir una pel�cula");
                opcion = leer.nextLine();
                salida.println(opcion);

                switch (opcion) {
                    case "1":
                        System.out.println("Introduce el ID de la pel�cula:");
                        int id = Integer.parseInt(leer.nextLine());
                        salida.println(id);
                        resultado = bf.readLine();
                        System.out.println("La pelicula con ID :  " + id + " es : " + resultado);
                        break;

                    case "2":
                        System.out.println("Introduzca el t�tulo de la pel�cula que desea comprar");
                        titulo = leer.nextLine();
                        salida.println(titulo);
                        resultado = bf.readLine();                        
                        System.out.println("Su pel�cula es: " + resultado);
                        break;

                    case "3":
                        System.out.println("Salir de la aplicaci�n");
                        continuar = false;
                        break;

             //Requerimiento 2: a�adir al men� de la aplicaci�n del cliente la b�squeda de pel�culas por director.
                        
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
                        
              //Requerimiento 3:Introdudir los datos para a�adir una nueva pelicula a la lista.
                    case "5":
                        System.out.println("Introduce los datos de la pel�cula:");
                        System.out.print("ID: ");
                        int id1 = Integer.parseInt(leer.nextLine());
                        System.out.print("T�tulo: ");
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
                        System.out.println("Opci�n err�nea");
                }

            } while (continuar);
            
            //En cuanto el cliente seleccione la opci�n 3 se cierra la conexi�n del socketAlServidor.
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
