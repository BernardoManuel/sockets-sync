package model;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int puerto = 3306;
        String host = "localhost";

        try {

            //Creamos he iniciamos los clientes
            Socket_Cliente cliente1 = new Socket_Cliente(host,puerto);
            cliente1.start();
            Socket_Cliente cliente2 = new Socket_Cliente(host,puerto);
            cliente2.start();
            Socket_Cliente cliente3 = new Socket_Cliente(host,puerto);
            cliente3.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}