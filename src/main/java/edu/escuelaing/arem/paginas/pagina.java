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
/**
 *
 * @author Luis
 */
public class pagina {
    public static void tipoArchivo(String archivo,PrintWriter out,DataOutputStream imageCode) throws IOException{
        System.out.println("ADRESS POSTTYPE:" + archivo);
        String tipo=tipo(archivo);
        System.out.println(tipo(archivo));
        try{
            if(archivo.equals("/") ||tipo.equals("html")){
                System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            solicitudHtml( out, archivo);
            }else if(tipo.equals("png")){
                solicitudPng(archivo,imageCode);
            }else{
                 solicitudHtml( out, "/notfound.html");
            }
            
        }catch(Exception e){
              
            solicitudHtml( out, "/notfound.html");
        }
        
        
    }
    private static void solicitudHtml(PrintWriter out,String archivo) throws IOException{
        String outputLine="";
        String sCadena="";
        try{
            BufferedReader bf=null;
            if(archivo.equals("/")){
                bf = new BufferedReader(new FileReader("resourses/notfound.html"));
                System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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
    }
    private static void solicitudPng(String archivo, DataOutputStream imageCode) throws IOException{
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
