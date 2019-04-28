/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examensegundo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cavca
 */
class ClienteM implements Runnable{

    int port;

    public ClienteM(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        System.out.println("ClienteM");
        try {
            MulticastSocket ms = new MulticastSocket();
            
            byte[] puerto = Integer.toString(port).getBytes();
            DatagramPacket dgp = new DatagramPacket(puerto, puerto.length, InetAddress.getByName("228.1.1.1"), 55557);
            for (;;) {
                ms.send(dgp);
                Thread.sleep(5000);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServidorM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}