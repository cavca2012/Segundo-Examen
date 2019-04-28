/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author cavca
 */
class ClienteD {

    byte[] mensaje;
    int puertoServidor = 9000;
    int miPuerto;
//    int miPuerto = 9000;
            
    ClienteD(int puerto) {
        miPuerto = puerto;
    }

    public int getPuertoServidor() {
        return puertoServidor;
    }

    public void setPuertoServidor(int puertoServidor) {
        this.puertoServidor = puertoServidor;
    }

    public byte[] getMensaje() {
        return mensaje;
    }

    public void setMensaje(byte[] mensaje) {
        this.mensaje = mensaje;
        run();
    }
    
    public void run() {
        System.out.println("ClienteD");
        try {
            DatagramSocket socketUDP = new DatagramSocket();
            //byte[] mensaje = "5401R022409165J1TWQC.pdf".getBytes();
            InetAddress hostServidor = InetAddress.getByName("localhost");

            // Construimos un datagrama para enviar el mensaje al servidor
            DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puertoServidor);

            // Enviamos el datagrama
            System.out.println("Enviar a "+puertoServidor);
            socketUDP.send(peticion);

            // Construimos el DatagramPacket que contendrá la respuesta
            byte[] bufer = new byte[4];
            DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
            System.out.println("Esperando Archivo");
            socketUDP.receive(respuesta);
            String puerto = new String(respuesta.getData());
            // Enviamos la respuesta del servidor a la salida estandar
            System.out.println("Respuesta: " + puerto);
            
            ClienteF cf = new ClienteF(miPuerto,Integer.parseInt(puerto));
            cf.run();
            // Cerramos el socket
            socketUDP.close();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

}
