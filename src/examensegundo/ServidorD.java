/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cavca
 */
class ServidorD implements Runnable {

    int puerto;
    int puerto2;

    public ServidorD(int puert) {
        puerto = puert;
    }

    public int getPuerto2() {
        return puerto2;
    }

    public void setPuerto2(int puerto2) {
        this.puerto2 = puerto2;
    }

    
    @Override
    public void run() {
        System.out.println("ServidorD");
        try {
            while (true) {
                byte[] bufer = new byte[1000];

                DatagramSocket socketUDP = new DatagramSocket(puerto);
                // Construimos el DatagramPacket para recibir peticiones
                DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

                // Leemos una petición del DatagramSocket
                socketUDP.receive(peticion);

                String mensaje = new String(peticion.getData());

                System.out.print("Datagrama recibido del host: " + peticion.getAddress());
                System.out.println(" desde el puerto remoto: " + peticion.getPort());
                System.out.println(mensaje);

                String url = ".\\src\\CarpetasDeServidores\\" + puerto;

                File carpeta = new File(url);

                if (carpeta.isDirectory()) {
                    File lista[] = carpeta.listFiles();

                    System.out.println("");
                    int cont = 0;
                    for (File lista1 : lista) {
//                    System.out.println(mensaje.length() +" "+ lista1.getName().length());
                        try {
                            if (!mensaje.substring(0, (int) lista1.getName().length()).equals(lista1.getName())) {
                                cont++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (cont == lista.length) {
                        DatagramSocket socketUDP2 = new DatagramSocket();
                        InetAddress hostServidor2 = InetAddress.getByName("localhost");
                        System.out.println("Enviar a "+puerto2);
                        int puertoServidor2 = puerto2;
                        DatagramPacket peticion2 = new DatagramPacket(mensaje.getBytes(), mensaje.length(), hostServidor2, puertoServidor2);
                        socketUDP2.send(peticion2);
                        DatagramPacket respuesta2 = new DatagramPacket(bufer, bufer.length);
                        socketUDP2.receive(respuesta2);
                        System.out.println("Respuesta: " + new String(respuesta2.getData()));
                        socketUDP2.close();
                    } else {
                        DatagramSocket socketUDP2 = new DatagramSocket();
                        InetAddress hostServidor2 = InetAddress.getByName("localhost");
//                        int puertoServidor2 = puerto - 1;
//                        mensaje = InetAddress.getLocalHost().getHostAddress();
                        mensaje = new String();
                        mensaje = (puerto+100)+"";
                        System.out.println("Enviar a "+peticion.getPort());
                        System.out.println(mensaje);
                        DatagramPacket peticion2 = new DatagramPacket(mensaje.getBytes(), mensaje.length(), hostServidor2, peticion.getPort());
                        Thread.sleep(500);
                        socketUDP2.send(peticion2);
                        ServidorF sf = new ServidorF();
//                        DatagramPacket respuesta2 = new DatagramPacket(bufer, bufer.length);
//                        socketUDP2.receive(respuesta2);
//                        System.out.println("Respuesta: " + new String(respuesta2.getData()));
//                        socketUDP2.close();
                    }
                    System.out.println("");
                }

                // Construimos el DatagramPacket para enviar la respuesta
//                DatagramPacket respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(), peticion.getAddress(), peticion.getPort());
//
//                // Enviamos la respuesta, que es un eco
//                socketUDP.send(respuesta);
                Thread.sleep(100);
                socketUDP.close();
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(ServidorD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}