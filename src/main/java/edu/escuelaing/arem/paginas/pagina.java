/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.paginas;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Declaracion de la clase pagina,
 * genera la salida del request solicitado por el cliente 
 * despues de haber generado la respectiva pagina correspondiente al tipo solicitada 
 * muestra la pagina web visualizada en el servidor
 * @author Luis
 */
public class pagina {
    /**
     * Dada la solicitud del cliente, este evaluara su tipo de archivo y con esta 
     * informacion solicitara la pagina correspondiente a este arhivo
     * @param archivo se debe de conocer el archivo solicitado por el cliente
     * @param clientSocket se debe conocer desde que socket se esta haciendo la solicitud
     * @throws IOException se estan leyendo archivos
     */
    public static void tipoArchivo(String archivo,Socket clientSocket,HashMap metod, ArrayList<String> variables) throws IOException{
        System.out.println("ADRESS POSTTYPE:" + archivo);
        String tipo=tipo(archivo);
        Boolean condicion=false;
        for (Object key : metod.keySet()) {
                if(archivo.equals(key)){
                    condicion=true;
                }
            }
        System.out.println(tipo(archivo));
        try{
            if(archivo.equals("/") ||tipo.equals("html")){
                solicitudHtml( archivo,clientSocket);
            }else if(tipo.equals("png")){
                solicitudPng(archivo,clientSocket);
            }else if(condicion){
                Method e=(Method) metod.get(archivo);
                if(e.getParameterCount()==variables.size()){
                    appWeb(clientSocket,(Method) metod.get(archivo),variables);
                }else{
                    solicitudHtml("/notfound.html",clientSocket);
                }
                
            }else{
                 solicitudHtml("/notfound.html",clientSocket);
            }
                 
            
        }catch(Exception e){
              
            solicitudHtml( "/notfound.html",clientSocket);
        }
        
        
        
    }
    /**
     * lee una pagina web .html ubicada en una direccion y parsea su contenido a un string
     * colocando encabezados y enviando la informacion a el cliente  
     * @param clientSocket se debe conocer desde que socket se esta haciendo la solicitud
     * @param archivo se debe de conocer el archivo solicitado por el cliente
     * @throws IOException se estan leyendo archivos
     */
    private static void solicitudHtml(String archivo, Socket clientSocket) throws IOException{
        String outputLine="";
        String sCadena="";
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                       
        try{
            BufferedReader bf=null;
            if(archivo.equals("/")){
                bf = new BufferedReader(new FileReader("resourses/notfound.html"));
                
            }else{
                bf = new BufferedReader(new FileReader("resourses"+archivo));
            }
             while ((sCadena = bf.readLine())!=null) {
                    outputLine+=sCadena;
                 }
        }catch(Exception e){
             BufferedReader bf = new BufferedReader(new FileReader("resourses/notfound.html"));
             while ((sCadena = bf.readLine())!=null) {
                    outputLine+=sCadena;
                 }
        }
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html" + "\r\n");
            out.println(outputLine);
            out.close();
    }
    /**
     * lee una pagina web .png ubicada en una direccion y parsea su contenido a un string
     * colocando encabezados y enviando la informacion a el cliente  
     * @param clientSocket se debe conocer desde que socket se esta haciendo la solicitud
     * @param archivo se debe de conocer el archivo solicitado por el cliente
     * @throws IOException se estan leyendo archivos
     */
    private static void solicitudPng(String archivo,Socket clientSocket) throws IOException{
        			
        DataOutputStream imageCode= new DataOutputStream(clientSocket.getOutputStream());  
        byte[] imageBytes = null;
        File image = new File("resourses"+archivo);
        FileInputStream inputImage = new FileInputStream(image);
        imageBytes = new byte[(int) image.length()];
        inputImage.read(imageBytes);
        imageCode.writeBytes("HTTP/1.1 200 OK \r\n");
        imageCode.writeBytes("Content-Type: image/png\r\n");
        imageCode.writeBytes("Content-Length: " + imageBytes.length);
        imageCode.writeBytes("\r\n\r\n"); 
        //hace visible la imagen en el servidor
        imageCode.write(imageBytes);            
        imageCode.close();
         imageCode.close();
    }
    /**
     * dado una  pagina solicitada este metodo retornara el tipo a la que corresponde dicha 
     * solicitud 
     * @param pagina string con el nombre de la solicitud de una pagina 
     * @throws IOException se estan leyendo archivos
     */
    private static String tipo(String pagina){
        try{
            String tipo="";
            Boolean condicion=false;
            for(int i=0; i<pagina.length();i++){
                if(condicion){
                    tipo+=pagina.charAt(i);
                }
                if(pagina.charAt(i)=='.'){
                    condicion=true;
                }
            }
            return tipo;
        }catch(Exception e){
            return "html";
        }
    }
    private static void appWeb(Socket clientSocket,Method metod, ArrayList<String> variables) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        //getParameterCount
        String Respuesta=Respuesta(metod,variables);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html" + "\r\n");
        out.println("<!DOCTYPE html>" + "\r\n");
        out.println("<html>" + "\r\n");
        out.println("<head>" + "\r\n");
        out.println("<meta charset=\"UTF-8\">" + "\r\n");
        out.println("<title>Title of the document</title>" + "\r\n");
        out.println("</head>" + "\r\n");
        out.println("<body>" + "\r\n");
        out.println(Respuesta+"\r\n");
        out.println("</body>" + "\r\n");
        out.println("</html>" + "\r\n");
        out.close();
	}
    private static String Respuesta(Method metod, ArrayList<String> variables)throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        switch (metod.getParameterCount()) {
            case 1:
                return (String) metod.invoke(null, variables.get(0));
            case 2:
                return (String) metod.invoke(null, variables.get(0),variables.get(1));
            default:
                break;
        }
        {
            return (String) metod.invoke(null, variables.get(0),variables.get(1),variables.get(2));
        }
        
       
    }
}
