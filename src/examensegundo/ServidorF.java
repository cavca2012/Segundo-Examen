/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.DataInputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cavca
 */
class ServidorF{
    
    int puerto;
    File archivo;

    ServidorF(int i, File enviar) {
        puerto = i;
        archivo = enviar;
    }

//    @Override
    public void run() {
        System.out.println("ServidorF");

        try {
            ServerSocket socket = new ServerSocket(puerto);
            Socket socket_cli = socket.accept();
            DataInputStream in = new DataInputStream(socket_cli.getInputStream());

            String mensaje = "";
            mensaje = in.readUTF();
            System.out.println(mensaje);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
