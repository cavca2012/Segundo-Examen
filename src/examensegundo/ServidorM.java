/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cavca
 */
class ServidorM implements Runnable {
    
    ArrayList<String> servidores = new ArrayList<String>();

    @Override
    public void run() {
        System.out.println("ServidorM");
        try {
            while (true) {
                
                for (int i = 0; i < 10;i++){
                MulticastSocket ms = new MulticastSocket(55557);
                    ms.joinGroup(InetAddress.getByName("228.1.1.1"));
                    byte[] puerto = new byte[Integer.BYTES];
                    DatagramPacket dp = new DatagramPacket(puerto, puerto.length);

                    ms.receive(dp);
                    
                    if(!servidores.contains(new String(dp.getData()))){
                        servidores.add(new String(dp.getData()));
                    }
                    
//                    String port = new String(dp.getData());
//                    System.out.println(port);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getServidores() {
        return servidores;
    }

    public void setServidores(ArrayList<String> servidores) {
        this.servidores = servidores;
    }

    
}
