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


/**
 *
 * @author 2137497
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
       ServerSocket serverSocket=SocketServidor.servidor();
	for (;;) {       
                        
                        Socket clientSocket = Socketcliente.servidor(serverSocket);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        DataOutputStream imageCode= new DataOutputStream(clientSocket.getOutputStream());            
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine;
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
                        try{
                            pagina.tipoArchivo(get[1],out,imageCode);
                        }catch(Exception e){
                            pagina.tipoArchivo("/index.html",out,imageCode);
                        }
                        
                        in.close();
                        out.close();
                        imageCode.close();
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
    
    
    
 }

