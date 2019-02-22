/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.framework;
import edu.escuelaing.arem.*;
/**
 *
 * @author 2137497
 */
public class pojo {
    @web("/cuadrado")
    public static String cuadraro(String n){
        int  respuesta=Integer.parseInt(n);
        respuesta=respuesta*respuesta;
        return Integer.toString(respuesta);
    }
    @html("/cuadra")
    public static String prueba(String n){
        int  respuesta=Integer.parseInt(n);
        respuesta=respuesta*respuesta;
        return Integer.toString(respuesta);
    }
    @web("/suma")
    public static String suma(String n,String n2){
        int  respuesta=Integer.parseInt(n);
        int  respuesta2=Integer.parseInt(n2);
        return Integer.toString(respuesta+respuesta2);
    }
    @web("/sumacuadrados")
    public static String sumacuadrados(String n,String n2){
        int  respuesta=Integer.parseInt(n);
        int  respuesta2=Integer.parseInt(n2);
        return Integer.toString(respuesta*respuesta+respuesta2*respuesta2);
    }
}
