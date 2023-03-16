package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Socket_Cliente {
    private String nombreCliente;
    private Socket socket;
    private String Serverhost;
    private int Serverpuerto;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public Socket_Cliente(String host, int puerto) throws IOException {
        this.Serverhost=host;
        this.Serverpuerto=puerto;
    }

    public void start() throws IOException {
        System.out.println("Cliente: Estableciendo conexion...");

        socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(Serverhost,Serverpuerto);
        socket.connect(address);

        //Inicializar Datainput y outputstreams
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        System.out.println("Cliente: Conexion establecida...");

        System.out.println("Introduzca su nombre: ");
        Scanner sc = new Scanner(System.in);
        nombreCliente=sc.next();

        dataOutputStream.writeUTF(nombreCliente);

        System.out.println(dataInputStream.readUTF());
        System.out.println(dataInputStream.readUTF());

        int opcion = sc.nextInt();
        dataOutputStream.writeInt(opcion);

        if(opcion==1){
            System.out.println(dataInputStream.readUTF());
        } else if (opcion==2) {
            System.out.println(dataInputStream.readUTF());
            String texto = sc.next();
            dataOutputStream.writeUTF(texto);
        } else if (opcion==3) {
            stop();
        }


    }

    public void stop() throws IOException {
        System.out.println("Cliente: Cerrando conexion...");

        socket.close();

        dataInputStream.close();
        dataOutputStream.close();

        System.out.println("Cliente: Conexion cerrada.");
    }

    public static void main(String[] args) {
        int puerto = 3333;
        String host = "localhost";
        String nombreCliente;

        try {

            Socket_Cliente socketCliente = new Socket_Cliente(host,puerto);

            socketCliente.start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
