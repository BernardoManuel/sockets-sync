package model;

import java.net.Socket;

public class Manejador_Cliente extends Thread{
    private Socket socketCliente;
    private Manejador_Cliente(Socket socketCliente){
        this.socketCliente=socketCliente;

    }
}
