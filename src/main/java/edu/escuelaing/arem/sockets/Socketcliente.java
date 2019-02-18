/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.sockets;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Declaracion de la clase ClientSckt,
 * socket, se comunica con el servidor,
 * le envia peticiones
 * recibe respuestas
 * @author Luis
 */
public class Socketcliente {
    public static Socket servidor(ServerSocket serverSocket) {
        Socket clientSocket= null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        return clientSocket;
    } 
}
