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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cavca
 */
class ServidorD implements Runnable {

    int puerto;
    int puerto2;
    String ipServidor = "localhost";
    ArrayList<String> aBuscados = new ArrayList();

    public ServidorD(int puert) {
        puerto = puert;
    }

    public int getPuerto2() {
        return puerto2;
    }

    public void setPuerto2(int puerto2) {
        this.puerto2 = puerto2;
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public ArrayList<String> getaBuscados() {
        return aBuscados;
    }

    public void setaBuscados(ArrayList<String> aBuscados) {
        this.aBuscados = aBuscados;
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

                String puertoYmensaje = new String(peticion.getData());
                int puetoOrigen = Integer.parseInt(puertoYmensaje.split(":")[0]);

                String mensaje;

                if (puetoOrigen == puerto) {
                    DatagramSocket socketUDP2 = new DatagramSocket();
                    InetAddress hostServidor2 = InetAddress.getByName("localhost");
//                        int puertoServidor2 = puerto - 1;
//                        mensaje = InetAddress.getLocalHost().getHostAddress();
//                        mensaje = new String();
                    mensaje = "No F";
                    System.out.println("Enviar a " + peticion.getPort());
                    System.out.println(mensaje);
                    DatagramPacket peticion2 = new DatagramPacket(mensaje.getBytes(), mensaje.length(), hostServidor2, peticion.getPort());
//                        Thread.sleep(1000);
                    socketUDP2.send(peticion2);
                } else {

                    mensaje = puertoYmensaje.split(":")[1];
                    aBuscados.add(mensaje);

                    System.out.print("Datagrama recibido del host: " + peticion.getAddress());
                    System.out.println(" desde el puerto remoto: " + peticion.getPort());
                    System.out.println(mensaje);

                    String url = ".\\src\\CarpetasDeServidores\\" + puerto;

                    File carpeta = new File(url);
                    File enviar = null;

                    if (carpeta.isDirectory()) {
                        File lista[] = carpeta.listFiles();

                        System.out.println("");
                        int cont = 0;
                        for (File lista1 : lista) {
//                    System.out.println(mensaje.length() +" "+ lista1.getName().length());
                            try {
                                if (!mensaje.substring(0, (int) lista1.getName().length()).equals(lista1.getName())) {
                                    cont++;
                                } else {
                                    enviar = lista1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (cont == lista.length) {
                            DatagramSocket socketUDP2 = new DatagramSocket();
                            InetAddress hostServidor2 = InetAddress.getByName("localhost");
                            System.out.println("Enviar a " + puerto2);
                            int puertoServidor2 = puerto2;
                            DatagramPacket peticion2 = new DatagramPacket(puertoYmensaje.getBytes(), puertoYmensaje.length(), hostServidor2, puertoServidor2);
                            socketUDP2.send(peticion2);
                            bufer = new byte[1000];
                            DatagramPacket respuesta2 = new DatagramPacket(bufer, bufer.length);
                            socketUDP2.receive(respuesta2);
                            String r = new String(respuesta2.getData());
                            System.out.println("Respuesta: " + r);
                            System.out.println("Enviar a " + peticion.getPort());
                            DatagramPacket respuesta3 = new DatagramPacket(r.getBytes(), r.length(), peticion.getAddress(), peticion.getPort());
                            socketUDP2.send(respuesta3);
                            Thread.sleep(100);
                            socketUDP2.close();
                        } else {
                            DatagramSocket socketUDP2 = new DatagramSocket();
                            InetAddress hostServidor2 = InetAddress.getByName("localhost");
//                        int puertoServidor2 = puerto - 1;
//                        mensaje = InetAddress.getLocalHost().getHostAddress();
//                        mensaje = new String();
                            mensaje = new String((puerto + 100) + "");
                            System.out.println("Enviar a " + peticion.getPort());
                            System.out.println(mensaje);
                            DatagramPacket peticion2 = new DatagramPacket(mensaje.getBytes(), mensaje.length(), hostServidor2, peticion.getPort());
//                        Thread.sleep(1000);
                            socketUDP2.send(peticion2);
                            ServidorF sf = new ServidorF(puerto + 100, enviar);
                            sf.run();
//                        DatagramPacket respuesta2 = new DatagramPacket(bufer, bufer.length);
//                        socketUDP2.receive(respuesta2);
//                        System.out.println("Respuesta: " + new String(respuesta2.getData()));
                            socketUDP2.close();
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
