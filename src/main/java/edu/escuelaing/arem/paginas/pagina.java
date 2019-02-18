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
import java.net.Socket;
/**
 *
 * @author Luis
 */
public class pagina {
    public static void tipoArchivo(String archivo,Socket clientSocket) throws IOException{
        System.out.println("ADRESS POSTTYPE:" + archivo);
        String tipo=tipo(archivo);
        System.out.println(tipo(archivo));
        try{
            if(archivo.equals("/") ||tipo.equals("html")){
                solicitudHtml( archivo,clientSocket);
            }else if(tipo.equals("png")){
                solicitudPng(archivo,clientSocket);
            }else{
                 solicitudHtml("/notfound.html",clientSocket);
            }
            
        }catch(Exception e){
              
            solicitudHtml( "/notfound.html",clientSocket);
        }
        
        
        
    }
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
}
