/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.sockets;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * Declaracion de la clase SocketServidor,
 * crea servidor, espera comunicacion del cliente,
 * recibe peticiones
 * envia respuestas
 * @author Luis
 */
public class SocketServidor {
    /**
     * inicia un servidor por medio de un socket,
     * para empezar a recibir solicitudes del cliente
     * @return serverSocket socket servidor desde el cual se van a recibir las solicitudes del cliente
     */
    public static ServerSocket servidor() {
        
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + getPort());
            System.exit(1);
        }
        return serverSocket;
    } 
    /**
     * obtiene el numero del puerto a cual se conecta el servidor
     * @return 4567 puerto por defecto donde corre la aplicacion
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
