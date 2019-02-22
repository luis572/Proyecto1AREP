/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.framework;

import edu.escuelaing.arem.framework.web;

/**
 *
 * @author 2137587
 */
public class pojo2 {
    @web("/cudrados")
    public static String cuadraro(String n){
        int  respuesta=Integer.parseInt(n);
        respuesta=respuesta*respuesta;
        return Integer.toString(respuesta);
    }
    @web("/concatenar")
    public static String concatenar(String n, String n2){
        return n+n2;
    }
    @web("/longitud")
    public static String longitud(String n){
        return Integer.toString(n.length());
    }
 }
