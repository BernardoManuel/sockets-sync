package model;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket_Servidor {

    private ServerSocket serverSocket;

    private Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private File file;

    public Socket_Servidor(int puerto) throws IOException {
        serverSocket = new ServerSocket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(puerto);

        serverSocket.bind(inetSocketAddress);
    }

    public void start() throws IOException {
        System.out.println("Esperando conexiones...");

        while (true) {
            //Aceptamos la conexion del cliente.
            socket = serverSocket.accept();

            //Inicializar Datainput y outputstreams
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //Creamos el archivo
            file = new File("src/main/resources/file.txt");

            //Enviamos un mensaje de bienvenida al cliente
            String nombreCliente = dataInputStream.readUTF();
            System.out.println("Se ha conectado: " + nombreCliente);
            dataOutputStream.writeUTF("Bienvenido " + nombreCliente + ", ¿qué desea realizar?\n");

            //Creamos el menu
            String titulo = "---MENU---\n";
            String op1 = "1. Leer Archivo.\n";
            String op2 = "2. Escribir Archivo.\n";
            String op3 = "3. Cerrar el servidor.\n";
            String menu = titulo + op1 + op2 + op3;

            //Enviamos el menu de opciones
            dataOutputStream.writeUTF(menu);

            //Recogemos la opcion seleccionada
            int opSelected = dataInputStream.readInt();


            //Manejamos el menu de opciones.
            if (opSelected == 1) {
                //Leer archivo
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder contenido = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    contenido.append(line);
                    contenido.append(System.lineSeparator());
                }
                br.close();
                //Enviar el contenido del archivo al cliente
                dataOutputStream.writeUTF("Contenido del archivo:\n" + contenido.toString());
            } else if (opSelected == 2) {
                //Escribir archivo
                //Pedimos al cliente el contenido que desea escribir en el archivo
                dataOutputStream.writeUTF("Escriba el contenido que desea agregar al archivo:");
                String contenido = dataInputStream.readUTF();

                //Escribimos el contenido en el archivo
                FileWriter fileWriter = new FileWriter(file, true); //true para agregar al final del archivo
                fileWriter.write(contenido+"\n"); 
                fileWriter.close();

                //Confirmamos al cliente que se ha agregado el contenido al archivo
                dataOutputStream.writeUTF("Contenido agregado exitosamente al archivo.");

            } else if (opSelected == 3) {
                //Desconectar cliente
                break;
            }
        }
    }

    public void stop() throws IOException {
        System.out.println("Servidor: Cerrando conexiones...");

        serverSocket.close();

        //Cerrar Input y Output Streams
        dataInputStream.close();
        dataOutputStream.close();

        System.out.println("Servidor: Conexiones cerradas.");
    }


    public static void main(String[] args) {

        int puerto = 3333;
        String nombreCliente;

        try {
            Socket_Servidor socketServidor = new Socket_Servidor(puerto);

            socketServidor.start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
