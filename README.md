# Taller de Clientes y Servicios en Java

## Empezando
se debe de clonar el proyecto, para esto utilizaremos el comando git clone. ubíquese la carpeta a guardar el proyecto y escriba el siguiente comando en la terminal:
 
 ### git clone https://github.com/luis572/Arep.git
   
## Prerrequisitos
Se debe tener instalados los siguientes programas en nuestro sistema operativo: 
- Maven 
- Git
- Java

## Ejercicios 
 ###  Trabajando con URLs 
 #### 3.1.  Leyendo los valores de un objeto URL 
El programador puede usar varios metodos para leer la informaci´on de un objeto URL: getProtocol, getAuthority, getHost, getPort, getPath, getQuery, getFile, getRef.
  Escriba un programa en el cual usted cree un objeto URL e imprima en pantalla cada uno de los datos que retornan los 8 m´etodos de la secci´on anterior.
 ```
		public class URLReader {
	    public static void main(String[] args) throws Exception {
        URL google = new URL("http://www.google.com/");
        System.out.println("URL: "+google);
        System.out.println("Authority: "+getAuthority(google));
        System.out.println("Protocol: "+getProtocol(google));
        System.out.println("Path: "+getPath(google));
        System.out.println("Host: "+getHost(google));
        System.out.println("Port: "+getPort(google));
        System.out.println("Query: "+getQuery(google));
        System.out.println("File: "+getFile(google));
        System.out.println("Ref: "+getRef(google));
    }
 ```
 #### 3.2. Leyendo paginas de internet
 Escriba una aplicaci´on browser que pregunte una direcci´on URL al usuario y que lea datos de esa direccion y que los almacene en un archivo con el nombre resultado.html. Luego intente ver este archivo en el navegador.
 ``` 
 public class LeyendoPaginas {
    public static void main(String[] args) throws Exception {
        String html = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese porfavor el URL:http://");
        String pagina=in.readLine();
        URL google = new URL("http://"+pagina);
        try (BufferedReader reader
            = new BufferedReader(new InputStreamReader(google.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                 html += inputLine + "\n";
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        File archivo = new File("resultado.html");
        if(archivo.exists()){
            archivo.delete();
        }
        archivo.createNewFile();
        FileWriter escribir=new FileWriter(archivo.getAbsoluteFile(), true);
        BufferedWriter b=new BufferedWriter(escribir);
        b.write(html);
        b.close();
        escribir.close();
        
    }
```
### 4. Sockets (enchufes)
#### 4.3.1
Escriba un servidor que reciba un numero y responda el cuadrado de este numero.
```
public class EchoServer {
    public static void main(String[] args) throws IOException {
       ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8182);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        
        while ((inputLine = in.readLine()) != null) {
            
            System.out.println("Mensaje: " + inputLine);
            int numero=Integer.parseInt(inputLine);
            outputLine = "Respuesta: " + numero*numero ;
            out.println(outputLine);
            if (outputLine.equals("Respuestas: Bye."))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
```
##### cliente: 
```
public class EchoClient {
    public static void main(String[] args) throws IOException {
	
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			echoSocket = new Socket("127.0.0.1", 8182);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Donâ€™t know about host!.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldnâ€™t get I/O for " + "the connection to: localhost.");
			System.exit(1);
		}
		System.out.println("Escriba el numero: ");
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		while ((userInput = stdIn.readLine()) != null) {
                       
			out.println(userInput);
			System.out.println("echo: " + in.readLine());
                        System.out.println("Escriba el numero: ");
		}
		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}
```
#### 4.3.2
Escriba un servidor que pueda recibir un numero y responda con un operaci´on sobre este numero. Este servidor puede recibir un mensaje que empiece por “fun:”, si recibe este mensaje cambia la operacion a las especificada. El servidor debe responder las funciones seno, coseno y tangente. Por defecto debe empezar calculando el coseno. Por ejemplo, si el primer numero que recibe es 0, debe responder 1, si despues recibe π/2 debe responder 0, si luego recibe “fun:sin” debe cambiar la operacion actual a seno, es decir a a partir de ese momento debe calcular senos. Si enseguida recibe 0 debe responder 0.
```
public class ServidorOperaciones {
    public static void main(String[] args) throws IOException {
       ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8182);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        String operacion="cos";
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje: " + inputLine);
            try{
                 outputLine = "Respuesta: ";
                 if(inputLine.contains("sen")){
                     operacion="sen";
                }
                 else if(inputLine.contains("cos")){
                     operacion="cos";
                 }
                 else if(inputLine.contains("tan")){
                     operacion="tan";
                 }
                 else{
                    double  numero=Double.parseDouble(inputLine);
                    numero= Math.toRadians(numero);
                     switch (operacion) {
                         case "sen":
                             outputLine = outputLine +  Math.sin(numero);
                             break;
                         case "cos":
                             outputLine = outputLine +  Math.cos(numero) ;
                             break;
                         case "tan":
                             outputLine = outputLine +  Math.tan(numero) ;
                             break;
                         default:
                             break;
                     }
                   
                 }
                 System.out.println("Dijite Datos V");
                 out.println(outputLine);
                 if (outputLine.equals("Respuestas: Bye."))
                        break;
            }catch(NumberFormatException e){
                System.out.println("Dijite Datos Validos");
            }
           
            
            
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
```
#### 4.4 Servidor web
 El codigo 4 presenta un servidor web que atiende una solicitud. Implemente el servidor e intente conectarse desde el browser.
 ```
 public class HttpServer {
    public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(38000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}
		Socket clientSocket = null;
		for (;;) {
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received: " + inputLine);
				if (!in.ready()) {
					break;
				}
			}
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html" + "\r\n");
			out.println("<!DOCTYPE html>" + "\r\n");
			out.println("<html>" + "\r\n");
			out.println("<head>" + "\r\n");
			out.println("<meta charset=\"UTF-8\">" + "\r\n");
			out.println("<title>Title of the document</title>" + "\r\n");
			out.println("</head>" + "\r\n");
			out.println("<body>" + "\r\n");
			out.println("<h1>My Web Site</h1>" + "\r\n");
			out.println("</body>" + "\r\n");
			out.println("</html>" + "\r\n");
			out.flush();
		}
	}
 }
```



## Autores 
- Luis Fernando Pizza Gamba https://github.com/luis572
## Bibliografia: 
Conversacion  y Explicacion de Andres florez sobre el punto 4.4 Servidor web 
## Licencia
La licencia de este proyecto es MIT License para mas informacion consultar el archivo [LICENSE](https://github.com/luis572/Arep/blob/master/LICENSE "LICENSE").
