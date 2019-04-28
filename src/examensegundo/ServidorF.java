/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cavca
 */
class ServidorF {

    int puerto;
    File archivo;

    ServidorF(int i, File enviar) {
        puerto = i;
        archivo = enviar;
    }

//    @Override
    public void run() {
        System.out.println("ServidorF");
        System.out.println("Archivo: " + archivo.getName());

        try {
            ServerSocket socket = new ServerSocket(puerto);
            Socket socket_cli = socket.accept();
            DataInputStream din = new DataInputStream(socket_cli.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket_cli.getOutputStream());

            String mensaje = "";
            mensaje = din.readUTF();
            System.out.println(mensaje);
            dout.writeUTF(archivo.getName());

            long length = archivo.length();
            byte[] b = new byte[1024];
            DataInputStream dis = new DataInputStream(new FileInputStream(archivo));

            //Seccion para el envio del archivo
            long enviados = 0;
            int porcentaje, n;

            while (enviados < length) {
                n = dis.read(b);
                dout.write(b, 0, n);
                dout.flush();
                enviados = enviados + n;
                porcentaje = (int) (enviados * 100 / length);
                System.out.print("Enviado: " + porcentaje + "%\r");
            }
            System.out.println("\n\nArchivo Enviado");
            dis.close();
            dout.close();
            din.close();
            socket_cli.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
