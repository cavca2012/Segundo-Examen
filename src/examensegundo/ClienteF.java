/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author cavca
 */
class ClienteF implements Runnable {

    int puerto;
    int miPuerto;

//    @Override
    public ClienteF(int myPuerto,int puerto) {
    miPuerto = myPuerto;
        this.puerto = puerto;
    }

    public void run() {
        System.out.println("ClienteF");

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = new Socket("localhost", puerto);

            in = socket.getInputStream();
            
            DataOutputStream out2 = new DataOutputStream(socket.getOutputStream());
            DataInputStream in2 = new DataInputStream(socket.getInputStream());
            
            out2.writeUTF("Empieza");
            String nombre = in2.readUTF();

            System.out.println("\n.\\src\\CarpetasDeServidores\\"+miPuerto+"\\"+nombre);
            out = new FileOutputStream(".\\src\\CarpetasDeServidores\\"+miPuerto+"\\"+nombre);
            byte[] bytes = new byte[16 * 1024];

            int count;
            while ((count = in2.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            out2.close();
            in2.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
