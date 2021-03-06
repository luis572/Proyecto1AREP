/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.httpServer;

import edu.escuelaing.arem.paginas.pagina;
import edu.escuelaing.arem.sockets.SocketServidor;
import edu.escuelaing.arem.sockets.Socketcliente;
import java.net.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Declaracion de la clase HttpServer,
 * main principal del proyecto, desde donde corre el servidor
 * @author Luis Pizza
 */
public class HttpServer {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    public static HashMap<String, Method> metod;
    /**
     * Creacion del main, que ejecutara todo el proyecto
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        clientSocket=null;
        serverSocket=SocketServidor.servidor();
	while(true==true) {
                        reconocerPojos();
                        clientSocket = Socketcliente.servidor(serverSocket);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine="";
                        String[] get = null;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received: " + inputLine);
				if (!in.ready()) {
					break;
				}
                                if (inputLine.contains("GET")) {
                                    get = inputLine.split(" ");
                                    System.out.println("Adress to show: "+ get[1]);
                            }
			}
                         ArrayList<String> parametrosMetodo=new ArrayList<>();
                         
                        try{
                            if(get[1].contains(":")){
                                String[] variables=get[1].split(":");
                                for(int i=1;i<variables.length;i++){
                                    parametrosMetodo.add(variables[i]);
                                }
                                get[1]=get[1].split(":")[0];
                            }
                            pagina.tipoArchivo(get[1],clientSocket,metod,parametrosMetodo);
                        }catch(Exception e){
                            pagina.tipoArchivo("/index.html",clientSocket,metod,parametrosMetodo);
                        }
                        
                        in.close();
                        clientSocket.close();
		}
	}
    
    	public static String getPageRequest(InputStream is) throws IOException {
		is.mark(0);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputLine = null;
		while ((inputLine = in.readLine()) != null) {
			if (!in.ready())
				break;
			if (inputLine.contains("GET")) {
				String[] get = inputLine.split(" ");
				return get[1];
			}
			break;
		}
		return "path";
	}
    
    /**
     * esta clase almacenara todos los pojos con anotacion @web de un directorio dentro de un hashmap la lleve sera la extencion de la
     * anotacion y el valor el metodo asociado a dicha anotacioa.
     * @param File se debera de enviar la carpeta donde estara todos los pojos
     * @param String para mayor facilidad se debe de conocer la ruta relativa de la carpeta en donde se van a extraer los metodos con anotacion web
     * @throws ClassNotFoundException 
     */
    public static void listarFicherosPorCarpeta(final File carpeta,String ruta) throws ClassNotFoundException {
        metod=new HashMap<String ,Method >();
        for (final File ficheroEntrada : carpeta.listFiles()) {
            String copiaRuta=ruta;
            String paquete="";
            copiaRuta=copiaRuta.replace("/",".");
            String namefichero=ficheroEntrada.getName().substring(0,ficheroEntrada.getName().length()-5);
            paquete=copiaRuta.substring(14,copiaRuta.length());
            Class c=Class.forName(paquete+namefichero);
            Method[] metodos=c.getDeclaredMethods();
            for(int i=0;i<metodos.length;i++){
                Annotation[] tipo=metodos[i].getDeclaredAnnotations();
                if(tipo.length>0){
                    String an=tipo[0].toString().substring(31,tipo[0].toString().length());
                    String llave=an.substring(10,an.length()-1);
                    if(an.contains("web") && !metod.containsKey(llave)){
                        metod.put(llave,metodos[i]);
                    } 
                }
            }  
        } 
    }
    /**
     * entra al paquete framework y examinara todas las clases que esten en dicho directorios para llamar
     * a listar ficheros por carpeta que evaluara archivo por archivo. 
     */
    public static void reconocerPojos(){
        File carpeta = new File("src/main/java/edu/escuelaing/arem/framework/");
           try {
               listarFicherosPorCarpeta(carpeta,"src/main/java/edu/escuelaing/arem/framework/");
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
 }

