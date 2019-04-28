/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author cavca
 */
class ClienteF implements Runnable {

    @Override
    public void run() {
        System.out.println("ClienteF");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Socket socket;
        String mensaje = "1484-5482-2-PB.pdf";

        try {
            socket = new Socket("127.0.0.1", 9100);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            do {
                //mensaje = in.readLine();
                out.writeUTF(mensaje);
            } while (!mensaje.startsWith("fin"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
